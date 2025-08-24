package org.phellang;

import com.intellij.codeInsight.AutoPopupController;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * Typed handler for Phel language - provides auto-closing of paired characters
 * Automatically inserts closing brackets, braces, parentheses, and quotes
 */
public class PhelTypedHandler extends TypedHandlerDelegate {

    @Override
    public @NotNull Result charTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
        if (!(file.getFileType() instanceof PhelFileType)) {
            return Result.CONTINUE;
        }

        // Trigger completion popup for certain contexts after character has been inserted
        if (c == '(' || c == '[') {
            AutoPopupController.getInstance(project).autoPopupMemberLookup(editor, null);
        }

        return Result.CONTINUE;
    }

    @Override
    public @NotNull Result beforeCharTyped(char c, @NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file, @NotNull FileType fileType) {
        if (!(fileType instanceof PhelFileType)) {
            return Result.CONTINUE;
        }

        Document document = editor.getDocument();
        int offset = editor.getCaretModel().getOffset();

        // Skip over closing characters if they're already present
        if (isClosingCharacter(c) && shouldSkipClosingChar(document, offset, c)) {
            editor.getCaretModel().moveToOffset(offset + 1);
            return Result.STOP;
        }

        // Handle opening characters - auto-close them
        String closingChar = getClosingCharacter(c);
        if (closingChar != null && shouldAutoClose(document, offset, c)) {
            // Insert both the opening and closing character
            document.insertString(offset, c + closingChar);
            // Position cursor between them (after the opening character)
            editor.getCaretModel().moveToOffset(offset + 1);
            return Result.STOP; // Prevent other handlers from processing this character
        }

        // Handle quote characters specially
        if (c == '"') {
            return handleQuoteCharacter(project, editor, document, offset);
        }

        return Result.CONTINUE;
    }

    /**
     * Returns the closing character for a given opening character
     */
    private String getClosingCharacter(char c) {
        switch (c) {
            case '(':
                return ")";
            case '[':
                return "]";
            case '{':
                return "}";
            default:
                return null;
        }
    }

    /**
     * Returns true if the character is a closing bracket/brace/parenthesis
     */
    private boolean isClosingCharacter(char c) {
        return c == ')' || c == ']' || c == '}';
    }

    /**
     * Determines if we should auto-close the character at the given position
     */
    private boolean shouldAutoClose(Document document, int offset, char openChar) {
        CharSequence text = document.getCharsSequence();
        
        // Don't auto-close if we're inside a string
        if (isInsideString(text, offset)) {
            return false;
        }
        
        // Don't auto-close if the next character is alphanumeric (avoid interfering with typing)
        if (offset < text.length()) {
            char nextChar = text.charAt(offset);
            if (Character.isLetterOrDigit(nextChar) || nextChar == '-' || nextChar == '_') {
                return false;
            }
        }
        
        return true;
    }

    /**
     * Determines if we should skip typing a closing character because it's already present
     */
    private boolean shouldSkipClosingChar(Document document, int offset, char closingChar) {
        CharSequence text = document.getCharsSequence();
        
        if (offset >= text.length()) {
            return false;
        }
        
        char charAtOffset = text.charAt(offset);
        
        // Skip if the same closing character is already at this position
        return charAtOffset == closingChar;
    }

    /**
     * Special handling for quote characters
     */
    private Result handleQuoteCharacter(Project project, Editor editor, Document document, int offset) {
        CharSequence text = document.getCharsSequence();
        
        // If we're at a quote, skip over it instead of inserting a new pair
        if (offset < text.length() && text.charAt(offset) == '"') {
            if (!isInsideString(text, offset)) {
                editor.getCaretModel().moveToOffset(offset + 1);
                return Result.STOP;
            }
        }
        
        // If we should auto-close the quote
        if (!isInsideString(text, offset)) {
            // Insert both opening and closing quote
            document.insertString(offset, "\"\"");
            // Position cursor between them
            editor.getCaretModel().moveToOffset(offset + 1);
            return Result.STOP;
        }
        
        return Result.CONTINUE;
    }

    /**
     * Simple heuristic to determine if we're inside a string literal
     */
    private boolean isInsideString(CharSequence text, int offset) {
        // Count unescaped quotes before this position
        int quoteCount = 0;
        for (int i = 0; i < offset && i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '"') {
                // Check if this quote is escaped
                int backslashCount = 0;
                int j = i - 1;
                while (j >= 0 && text.charAt(j) == '\\') {
                    backslashCount++;
                    j--;
                }
                // If even number of backslashes (including 0), quote is not escaped
                if (backslashCount % 2 == 0) {
                    quoteCount++;
                }
            }
        }
        
        // Odd number of quotes means we're inside a string
        return quoteCount % 2 == 1;
    }
}

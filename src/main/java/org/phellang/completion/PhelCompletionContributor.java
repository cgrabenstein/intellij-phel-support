package org.phellang.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.phellang.PhelIcons;
import org.phellang.language.psi.PhelSymbol;
import org.phellang.language.psi.PhelTypes;

import javax.swing.*;

/**
 * Main completion contributor for Phel language
 * Provides intelligent completions for API functions, PHP interop, and language constructs
 */
public class PhelCompletionContributor extends CompletionContributor {

    public PhelCompletionContributor() {
        // Complete symbols (functions, variables, etc.)
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(PhelTypes.SYM).withLanguage(org.phellang.PhelLanguage.INSTANCE), new PhelSymbolCompletionProvider());

        // Complete after colon (keywords)
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(PhelTypes.SYM).afterLeaf(PlatformPatterns.psiElement(PhelTypes.COLON)).withLanguage(org.phellang.PhelLanguage.INSTANCE), new PhelKeywordCompletionProvider());

        // Complete after namespace colon
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(PhelTypes.SYM).afterLeaf(PlatformPatterns.psiElement(PhelTypes.COLONCOLON)).withLanguage(org.phellang.PhelLanguage.INSTANCE), new PhelNamespaceKeywordCompletionProvider());
    }

    /**
     * Custom insert handler for namespaced completions to prevent text duplication
     * Handles completions containing '/' by properly replacing the typed prefix
     */
    public static class NamespacedInsertHandler implements InsertHandler<LookupElement> {
        @Override
        public void handleInsert(@NotNull InsertionContext context, @NotNull LookupElement item) {
            Editor editor = context.getEditor();
            Document document = editor.getDocument();
            int caretOffset = context.getStartOffset();
            
            // Find the start of the current symbol
            int symbolStart = caretOffset;
            CharSequence text = document.getCharsSequence();
            
            // Move back to find the beginning of the symbol
            while (symbolStart > 0) {
                char c = text.charAt(symbolStart - 1);
                // Stop at whitespace, opening parenthesis, or other delimiters
                if (Character.isWhitespace(c) || c == '(' || c == '[' || c == '{') {
                    break;
                }
                symbolStart--;
            }
            
            // Replace the entire symbol with the completion
            int endOffset = context.getTailOffset();
            String completionText = item.getLookupString();
            
            document.replaceString(symbolStart, endOffset, completionText);
            editor.getCaretModel().moveToOffset(symbolStart + completionText.length());
        }
    }

    private static final NamespacedInsertHandler NAMESPACED_INSERT_HANDLER = new NamespacedInsertHandler();

    /**
     * Creates a lookup element with proper insert handling for namespaced completions
     */
    public static LookupElementBuilder createNamespacedLookupElement(String name, Icon icon, String typeText, String tailText) {
        LookupElementBuilder builder = LookupElementBuilder.create(name);
        if (icon != null) {
            builder = builder.withIcon(icon);
        }
        if (typeText != null) {
            builder = builder.withTypeText(typeText);
        }
        if (tailText != null) {
            builder = builder.withTailText(tailText, true);
        }
        
        // Apply custom insert handler for completions containing '/'
        if (name.contains("/")) {
            builder = builder.withInsertHandler(NAMESPACED_INSERT_HANDLER);
        }
        
        return builder;
    }

    /**
     * Core symbol completion provider - handles functions, macros, and built-ins
     */
    private static class PhelSymbolCompletionProvider extends CompletionProvider<CompletionParameters> {
        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {

            PsiElement element = parameters.getPosition();
            String prefix = getCompletionPrefix(element);

            // Add core API functions
            PhelApiCompletions.addCoreFunctions(result, prefix);

            // Add special forms and macros
            PhelLanguageCompletions.addSpecialForms(result, prefix);
            PhelLanguageCompletions.addCoreMacros(result, prefix);

            // Add PHP interop completions
            PhelPhpInteropCompletions.addPhpInterop(result, prefix);

            // Add namespace functions
            PhelNamespaceCompletions.addNamespaceFunctions(result, prefix, element);

            // Add built-in predicates
            PhelApiCompletions.addPredicateFunctions(result, prefix);

            // Add collection functions
            PhelApiCompletions.addCollectionFunctions(result, prefix);

            // Add arithmetic and math functions
            PhelApiCompletions.addArithmeticFunctions(result, prefix);
        }

        private String getCompletionPrefix(PsiElement element) {
            if (element instanceof PhelSymbol) {
                return element.getText().replace("IntellijIdeaRulezzz ", "").trim();
            }
            String text = element.getText();
            // Remove IntelliJ's dummy identifier
            return text.replace("IntellijIdeaRulezzz ", "").trim();
        }
    }

    /**
     * Keyword completion provider - handles :keyword completions
     */
    private static class PhelKeywordCompletionProvider extends CompletionProvider<CompletionParameters> {
        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {

            // Common keywords used in Phel
            String[] commonKeywords = {"require", "use", "refer", "as", "import", "export", "private", "doc", "test", "deprecated", "added", "range", "in", "keys", "pairs", "while", "let", "when", "reduce", "debug", "prod", "dev", "cache", "timeout", "method", "path", "headers", "body", "status", "type", "name", "value"};

            Icon keywordIcon = AllIcons.Nodes.PropertyReadWrite;
            for (String keyword : commonKeywords) {
                result.addElement(LookupElementBuilder.create(keyword).withIcon(keywordIcon).withTypeText("keyword"));
            }
        }
    }

    /**
     * Namespace keyword completion provider - handles ::keyword completions
     */
    private static class PhelNamespaceKeywordCompletionProvider extends CompletionProvider<CompletionParameters> {
        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {

            // Current namespace keywords - these would ideally come from analyzing the current file
            String[] namespaceKeywords = {"private", "export", "test", "doc", "deprecated", "inline", "macro", "dynamic", "const"};

            Icon namespaceIcon = AllIcons.Nodes.Constant;
            for (String keyword : namespaceKeywords) {
                result.addElement(LookupElementBuilder.create(keyword).withIcon(namespaceIcon).withTypeText("namespace keyword"));
            }
        }
    }
}

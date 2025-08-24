package org.phellang;

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.psi.tree.TokenSet;
import org.phellang.language.psi.PhelTypes;

/**
 * Quote handler for Phel language - provides smart quote handling
 * Handles auto-closing and navigation within string literals
 */
public class PhelQuoteHandler extends SimpleTokenSetQuoteHandler {

    /**
     * Token types that represent string literals
     */
    private static final TokenSet STRING_LITERALS = TokenSet.create(PhelTypes.STRING);

    public PhelQuoteHandler() {
        super(STRING_LITERALS);
    }

    @Override
    public boolean isClosingQuote(HighlighterIterator iterator, int offset) {
        // Check if this quote closes a string literal
        if (STRING_LITERALS.contains(iterator.getTokenType())) {
            int start = iterator.getStart();
            int end = iterator.getEnd();
            
            // If we're at the end of a string token, this is a closing quote
            return offset == end - 1;
        }
        return false;
    }

    @Override
    public boolean isOpeningQuote(HighlighterIterator iterator, int offset) {
        // Check if this quote opens a string literal
        if (STRING_LITERALS.contains(iterator.getTokenType())) {
            int start = iterator.getStart();
            
            // If we're at the start of a string token, this is an opening quote
            return offset == start;
        }
        return false;
    }


}

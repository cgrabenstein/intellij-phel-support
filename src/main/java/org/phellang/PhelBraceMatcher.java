package org.phellang;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.phellang.language.psi.PhelTypes;

/**
 * Brace matcher for Phel language - handles matching of paired brackets
 * Supports parentheses, square brackets, and curly braces
 */
public class PhelBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = new BracePair[]{
            new BracePair(PhelTypes.PAREN1, PhelTypes.PAREN2, false),      // ()
            new BracePair(PhelTypes.BRACKET1, PhelTypes.BRACKET2, false),  // []
            new BracePair(PhelTypes.BRACE1, PhelTypes.BRACE2, false)       // {}
    };

    @Override
    public BracePair @NotNull [] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        // Allow pairing before most tokens, but not before closing braces
        return contextType != PhelTypes.PAREN2 
            && contextType != PhelTypes.BRACKET2 
            && contextType != PhelTypes.BRACE2;
    }

    @Override
    public int getCodeConstructStart(@NotNull PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}

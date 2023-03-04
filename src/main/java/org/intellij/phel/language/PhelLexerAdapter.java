package org.intellij.phel.language;

import com.intellij.lexer.FlexAdapter;

public class PhelLexerAdapter extends FlexAdapter {
    public PhelLexerAdapter() {
        super(new PhelLexer(null));
    }
}

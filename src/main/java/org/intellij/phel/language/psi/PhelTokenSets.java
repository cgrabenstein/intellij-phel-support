package org.intellij.phel.language.psi;

import com.intellij.psi.tree.TokenSet;

public interface PhelTokenSets {
    TokenSet ACCESS = TokenSet.create(PhelTypes.ACCESS);
    TokenSet COMMENTED = TokenSet.create(PhelTypes.COMMENTED);
    TokenSet CONSTRUCTOR = TokenSet.create(PhelTypes.CONSTRUCTOR);
    TokenSet FORM = TokenSet.create(PhelTypes.FORM);
    TokenSet FUN = TokenSet.create(PhelTypes.FUN);
    TokenSet KEYWORD = TokenSet.create(PhelTypes.KEYWORD);
    TokenSet LIST = TokenSet.create(PhelTypes.LIST);
    TokenSet LITERAL = TokenSet.create(PhelTypes.LITERAL);
    TokenSet MAP = TokenSet.create(PhelTypes.MAP);
    TokenSet METADATA = TokenSet.create(PhelTypes.METADATA);
    TokenSet READER_MACRO = TokenSet.create(PhelTypes.READER_MACRO);
    TokenSet REGEXP = TokenSet.create(PhelTypes.REGEXP);
    TokenSet SET = TokenSet.create(PhelTypes.SET);
    TokenSet SYMBOL = TokenSet.create(PhelTypes.SYMBOL);
    TokenSet VEC = TokenSet.create(PhelTypes.VEC);

    TokenSet AT = TokenSet.create(PhelTypes.AT);
    TokenSet BOOL = TokenSet.create(PhelTypes.BOOL);
    TokenSet BRACE1 = TokenSet.create(PhelTypes.BRACE1);
    TokenSet BRACE2 = TokenSet.create(PhelTypes.BRACE2);
    TokenSet BRACKET1 = TokenSet.create(PhelTypes.BRACKET1);
    TokenSet BRACKET2 = TokenSet.create(PhelTypes.BRACKET2);
    TokenSet CHAR = TokenSet.create(PhelTypes.CHAR);
    TokenSet COLON = TokenSet.create(PhelTypes.COLON);
    TokenSet COLONCOLON = TokenSet.create(PhelTypes.COLONCOLON);
    TokenSet COMMA = TokenSet.create(PhelTypes.COMMA);
    TokenSet DOT = TokenSet.create(PhelTypes.DOT);
    TokenSet DOTDASH = TokenSet.create(PhelTypes.DOTDASH);
    TokenSet HAT = TokenSet.create(PhelTypes.HAT);
    TokenSet HEXNUM = TokenSet.create(PhelTypes.HEXNUM);
    TokenSet NIL = TokenSet.create(PhelTypes.NIL);
    TokenSet NUMBER = TokenSet.create(PhelTypes.NUMBER);
    TokenSet PAREN1 = TokenSet.create(PhelTypes.PAREN1);
    TokenSet QUOTE = TokenSet.create(PhelTypes.QUOTE);
    TokenSet RATIO = TokenSet.create(PhelTypes.RATIO);
    TokenSet RDXNUM = TokenSet.create(PhelTypes.RDXNUM);
    TokenSet SHARP = TokenSet.create(PhelTypes.SHARP);
    TokenSet SHARP_COMMENT = TokenSet.create(PhelTypes.SHARP_COMMENT);
    TokenSet SHARP_EQ = TokenSet.create(PhelTypes.SHARP_EQ);
    TokenSet SHARP_HAT = TokenSet.create(PhelTypes.SHARP_HAT);
    TokenSet SHARP_NS = TokenSet.create(PhelTypes.SHARP_NS);
    TokenSet SHARP_QMARK = TokenSet.create(PhelTypes.SHARP_QMARK);
    TokenSet SHARP_QMARK_AT = TokenSet.create(PhelTypes.SHARP_QMARK_AT);
    TokenSet SHARP_QUOTE = TokenSet.create(PhelTypes.SHARP_QUOTE);
    TokenSet SHARP_SYM = TokenSet.create(PhelTypes.SHARP_SYM);
    TokenSet SLASH = TokenSet.create(PhelTypes.SLASH);
    TokenSet STRING = TokenSet.create(PhelTypes.STRING);
    TokenSet SYM = TokenSet.create(PhelTypes.SYM);
    TokenSet SYNTAX_QUOTE = TokenSet.create(PhelTypes.SYNTAX_QUOTE);
    TokenSet TILDE = TokenSet.create(PhelTypes.TILDE);
    TokenSet TILDE_AT = TokenSet.create(PhelTypes.TILDE_AT);
}

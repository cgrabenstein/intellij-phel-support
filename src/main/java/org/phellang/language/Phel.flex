package org.phellang.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import org.phellang.language.psi.PhelTypes;
import com.intellij.psi.TokenType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;

%%

%public
%class PhelLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

CRLF=\R
WHITE_SPACE=[\ \n\t\f]
END_OF_LINE_COMMENT="#"[^\r\n]*
STR_CHAR=[^\\\"]|\\.|\\\"
STRING=\" {STR_CHAR}* \"
WORD_START=[:jletter:]
WORD_PART=[:jletterdigit:]

%state SYMBOL

%%

<YYINITIAL> {
      {END_OF_LINE_COMMENT}       { return PhelTypes.COMMENT; }
      "("                         { return PhelTypes.PARENS_OPEN; }
      ")"                         { return PhelTypes.PARENS_CLOSE; }
      "["                         { return PhelTypes.BRACKET_OPEN; }
      "]"                         { return PhelTypes.BRACKET_CLOSE; }
      "{"                         { return PhelTypes.BRACE_OPEN; }
      "}"                         { return PhelTypes.BRACE_CLOSE; }
      ","                         { return PhelTypes.COMMA; }
      "'"                         { return PhelTypes.QUOTE; }
      "`"                         { return PhelTypes.SYNTAX_QUOTE; }
      {STRING}                    { return PhelTypes.STRING; }
      {WORD_START}                { yybegin(SYMBOL); return PhelTypes.WORD; }
}

<SYMBOL> {
      {WORD_PART}*                { return PhelTypes.WORD; }
      {WHITE_SPACE}               { yybegin(YYINITIAL); return PhelTypes.WORD; }
}

({CRLF}|{WHITE_SPACE})+         { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

[^]                             { return TokenType.BAD_CHARACTER; }

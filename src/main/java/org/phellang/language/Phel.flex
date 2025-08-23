package org.phellang.language;

import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;

%%

%public
%class PhelLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

%state SYMBOL0, SYMBOL1, SYMBOL2, SYMBOL3

WHITE_SPACE=\s+
LINE_COMMENT=#.*
STR_CHAR=[^\\\"]|\\.|\\\"
STRING=\" {STR_CHAR}* \"
NUMBER=[+-]? [0-9]+ (\.[0-9]*)? ([eE][+-]?[0-9]+)?
HEXNUM=[+-]? "0x" [\da-fA-F_]+ 
BINNUM=[+-]? "0b" [01_]+
OCTNUM=[+-]? "0" [0-7_]+
CHARACTER=\\([btrnf]|u[0-9a-fA-F]{4}|o[0-7]{3}|backspace|tab|newline|formfeed|return|space|.)
BAD_LITERAL=\" ([^\\\"]|\\.|\\\")*

SYM_START=[[\w<>$%&=*+\-!?_|]--#\d] | ".."
SYM_PART=[.]? {SYM_CHAR} | ".."
SYM_CHAR=[\w<>$%&=*+\-!?_|'#]
SYM_ANY={SYM_CHAR} | [./]

SYM_TAIL={SYM_PART}+ (":" {SYM_PART}+)?

%%
<YYINITIAL> {
  {WHITE_SPACE}          { return WHITE_SPACE; }
  {LINE_COMMENT}         { return LINE_COMMENT; }

  "^"                    { return HAT; }
  "~@"                   { return TILDE_AT; }
  "~"                    { return TILDE; }
  "("                    { return PAREN1; }
  ")"                    { return PAREN2; }
  "["                    { return BRACKET1; }
  "]"                    { return BRACKET2; }
  "{"                    { return BRACE1; }
  "}"                    { return BRACE2; }
  ","                    { return COMMA; }
  "'"                    { return QUOTE; }
  "`"                    { return SYNTAX_QUOTE; }

  "nil"                  { return NIL; }
  "NAN"                  { return NAN; }
  true|false             { return BOOL; }

  {STRING}               { return STRING; }
  {NUMBER}               { return NUMBER; }
  {HEXNUM}               { return HEXNUM; }
  {BINNUM}               { return BINNUM; }
  {OCTNUM}               { return OCTNUM; }
  {CHARACTER}            { return CHAR; }
  {BAD_LITERAL}          { return BAD_CHARACTER; }

  "::"                   { yybegin(SYMBOL0); return COLONCOLON; }
  ":"                    { yybegin(SYMBOL0); return COLON; }
  ".-"  /  {SYM_CHAR}    { yybegin(SYMBOL0); return DOTDASH; }
  ".-"                   { return SYM; }
  "."   /  {SYM_CHAR}    { yybegin(SYMBOL0); return DOT; }
  "."                    { return SYM; }
  "/" {SYM_ANY}+         { yybegin(YYINITIAL); return BAD_CHARACTER; }
  "/"                    { return SYM; }

  {SYM_START}{SYM_TAIL}? { yybegin(SYMBOL1); return SYM; }
}

<SYMBOL0> {
  {SYM_TAIL}             { yybegin(SYMBOL1); return SYM; }
  [^]                    { yybegin(YYINITIAL); yypushback(yylength()); }
}

<SYMBOL1> {
  ":"                    { yybegin(YYINITIAL); return BAD_CHARACTER; }
  "/"                    { yybegin(SYMBOL2); return SLASH; }
  "."                    { yybegin(YYINITIAL); return DOT; }
  [^]                    { yybegin(YYINITIAL); yypushback(yylength()); }
}

<SYMBOL2> {
  {SYM_TAIL}             { yybegin(SYMBOL3); return SYM; }
}

<SYMBOL2, SYMBOL3> {
  ":"                    { yybegin(YYINITIAL); return BAD_CHARACTER; }
  "."                    { yybegin(YYINITIAL); return DOT; }
  [^]                    { yybegin(YYINITIAL); yypushback(yylength()); }
}

<YYINITIAL, SYMBOL2, SYMBOL3> {
  "/" {SYM_ANY}+         { yybegin(YYINITIAL); return BAD_CHARACTER; }
}

[^] { return BAD_CHARACTER; }

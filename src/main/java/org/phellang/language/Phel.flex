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
%state DISPATCH

WHITE_SPACE=\s+
LINE_COMMENT=;.*
STR_CHAR=[^\\\"]|\\.|\\\"
STRING=\" {STR_CHAR}* \"
// octal numbers: 023, 023N, but not 023M
NUMBER=[+-]? [0-9]+ (N | M | {NUMBER_EXP} M? | (\.[0-9]*) {NUMBER_EXP}? M?)? // N - BigInteger, M - BigDecimal
NUMBER_EXP=[eE][+-]?[0-9]+
HEXNUM=[+-]? "0x" [\da-fA-F]+ N?
RADIX=[+-]? [0-9]{1,2}r[\da-zA-Z]+
RATIO=[+-]? [0-9]+"/"[0-9]+
CHARACTER=\\([btrnf]|u[0-9a-fA-F]{4}|o[0-7]{3}|backspace|tab|newline|formfeed|return|space|.)
BAD_LITERAL=\" ([^\\\"]|\\.|\\\")*
  | [+-]? "0x" \w+

SYM_START=[[\w<>$%&=*+\-!?_|]--#\d] | ".."
SYM_PART=[.]? {SYM_CHAR} | ".."
SYM_CHAR=[\w<>$%&=*+\-!?_|'#]
SYM_ANY={SYM_CHAR} | [./]

SYM_TAIL={SYM_PART}+ (":" {SYM_PART}+)?

%%
<YYINITIAL> {
  {WHITE_SPACE}          { return WHITE_SPACE; }
  {LINE_COMMENT}         { return LINE_COMMENT; }

  "#"                    { yybegin(DISPATCH); }

  "^"                    { return HAT; }
  "~@"                   { return TILDE_AT; }
  "~"                    { return TILDE; }
  "@"                    { return AT; }
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
  true|false             { return BOOL; }

  {STRING}               { return STRING; }
  {NUMBER}               { return NUMBER; }
  {HEXNUM}               { return HEXNUM; }
  {RADIX}                { return RDXNUM; }
  {RATIO}                { return RATIO; }
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

<DISPATCH> {
  "^"                    { yybegin(YYINITIAL); return SHARP_HAT; }  // Meta
  "'"                    { yybegin(YYINITIAL); return SHARP_QUOTE; }  // Var
  "\""                   { yybegin(YYINITIAL); yypushback(1); return SHARP; }  // Regex
  "("                    { yybegin(YYINITIAL); yypushback(1); return SHARP; }  // Fn
  "{"                    { yybegin(YYINITIAL); yypushback(1); return SHARP; }  // Set
  "="                    { yybegin(YYINITIAL); return SHARP_EQ; }  // Eval
  "!"                    { yybegin(YYINITIAL); return SHARP_COMMENT; }  // Comment
  "<"                    { yybegin(YYINITIAL); return BAD_CHARACTER; }  // Unreadable
  "_"                    { yybegin(YYINITIAL); return SHARP_COMMENT; }  // Discard
  "?@"                   { yybegin(YYINITIAL); return SHARP_QMARK_AT; }  // Conditional w/ Splicing
  "?"                    { yybegin(YYINITIAL); return SHARP_QMARK; }  // Conditional
  "#"                    { yybegin(YYINITIAL); return SHARP_SYM; }  // Conditional
  ":"                    { yybegin(YYINITIAL); yypushback(yylength()); return SHARP_NS; }  // Map ns prefix
  [\s\w]                 { yybegin(YYINITIAL); yypushback(yylength()); return SHARP; }
  [^]                    { yybegin(YYINITIAL); yypushback(yylength()); return BAD_CHARACTER; }

  <<EOF>>                { yybegin(YYINITIAL); return BAD_CHARACTER; }
}

[^] { return BAD_CHARACTER; }

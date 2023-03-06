// This is a generated file. Not intended for manual editing.
package org.phellang.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.phellang.language.psi.impl.*;

public interface PhelTypes {

  IElementType ACCESS = new PhelElementType("ACCESS");
  IElementType COMMENTED = new PhelElementType("COMMENTED");
  IElementType CONSTRUCTOR = new PhelElementType("CONSTRUCTOR");
  IElementType FORM = new PhelElementType("FORM");
  IElementType FUN = new PhelElementType("FUN");
  IElementType KEYWORD = new PhelElementType("KEYWORD");
  IElementType LIST = new PhelElementType("LIST");
  IElementType LITERAL = new PhelElementType("LITERAL");
  IElementType MAP = new PhelElementType("MAP");
  IElementType METADATA = new PhelElementType("METADATA");
  IElementType READER_MACRO = new PhelElementType("READER_MACRO");
  IElementType REGEXP = new PhelElementType("REGEXP");
  IElementType SET = new PhelElementType("SET");
  IElementType SYMBOL = new PhelElementType("SYMBOL");
  IElementType VEC = new PhelElementType("VEC");

  IElementType AT = new PhelTokenType("@");
  IElementType BOOL = new PhelTokenType("bool");
  IElementType BRACE1 = new PhelTokenType("{");
  IElementType BRACE2 = new PhelTokenType("}");
  IElementType BRACKET1 = new PhelTokenType("[");
  IElementType BRACKET2 = new PhelTokenType("]");
  IElementType CHAR = new PhelTokenType("char");
  IElementType COLON = new PhelTokenType(":");
  IElementType COLONCOLON = new PhelTokenType("::");
  IElementType COMMA = new PhelTokenType(",");
  IElementType DOT = new PhelTokenType(".");
  IElementType DOTDASH = new PhelTokenType(".-");
  IElementType HAT = new PhelTokenType("^");
  IElementType HEXNUM = new PhelTokenType("hexnum");
  IElementType NIL = new PhelTokenType("nil");
  IElementType NUMBER = new PhelTokenType("number");
  IElementType PAREN1 = new PhelTokenType("(");
  IElementType PAREN2 = new PhelTokenType(")");
  IElementType QUOTE = new PhelTokenType("'");
  IElementType RATIO = new PhelTokenType("ratio");
  IElementType RDXNUM = new PhelTokenType("rdxnum");
  IElementType SHARP = new PhelTokenType("#");
  IElementType SHARP_COMMENT = new PhelTokenType("#_");
  IElementType SHARP_EQ = new PhelTokenType("#=");
  IElementType SHARP_HAT = new PhelTokenType("#^");
  IElementType SHARP_NS = new PhelTokenType("#:");
  IElementType SHARP_QMARK = new PhelTokenType("#?");
  IElementType SHARP_QMARK_AT = new PhelTokenType("#?@");
  IElementType SHARP_QUOTE = new PhelTokenType("#'");
  IElementType SHARP_SYM = new PhelTokenType("##");
  IElementType SLASH = new PhelTokenType("/");
  IElementType STRING = new PhelTokenType("string");
  IElementType SYM = new PhelTokenType("sym");
  IElementType SYNTAX_QUOTE = new PhelTokenType("`");
  IElementType TILDE = new PhelTokenType("~");
  IElementType TILDE_AT = new PhelTokenType("~@");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ACCESS) {
        return new PhelAccessImpl(node);
      }
      else if (type == COMMENTED) {
        return new PhelCommentedImpl(node);
      }
      else if (type == CONSTRUCTOR) {
        return new PhelConstructorImpl(node);
      }
      else if (type == FORM) {
        return new PhelFormImpl(node);
      }
      else if (type == FUN) {
        return new PhelFunImpl(node);
      }
      else if (type == KEYWORD) {
        return new PhelKeywordImpl(node);
      }
      else if (type == LIST) {
        return new PhelListImpl(node);
      }
      else if (type == LITERAL) {
        return new PhelLiteralImpl(node);
      }
      else if (type == MAP) {
        return new PhelMapImpl(node);
      }
      else if (type == METADATA) {
        return new PhelMetadataImpl(node);
      }
      else if (type == READER_MACRO) {
        return new PhelReaderMacroImpl(node);
      }
      else if (type == REGEXP) {
        return new PhelRegexpImpl(node);
      }
      else if (type == SET) {
        return new PhelSetImpl(node);
      }
      else if (type == SYMBOL) {
        return new PhelSymbolImpl(node);
      }
      else if (type == VEC) {
        return new PhelVecImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

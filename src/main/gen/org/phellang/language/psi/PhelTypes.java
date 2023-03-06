// This is a generated file. Not intended for manual editing.
package org.phellang.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.phellang.language.psi.impl.*;

public interface PhelTypes {

  IElementType LIST_ = new PhelElementType("LIST_");
  IElementType SYMBOL_ = new PhelElementType("SYMBOL_");

  IElementType BRACE_CLOSE = new PhelTokenType("}");
  IElementType BRACE_OPEN = new PhelTokenType("{");
  IElementType BRACKET_CLOSE = new PhelTokenType("]");
  IElementType BRACKET_OPEN = new PhelTokenType("[");
  IElementType CLRF = new PhelTokenType("CLRF");
  IElementType COMMA = new PhelTokenType(",");
  IElementType COMMENT = new PhelTokenType("COMMENT");
  IElementType PARENS_CLOSE = new PhelTokenType("PARENS_CLOSE");
  IElementType PARENS_OPEN = new PhelTokenType("PARENS_OPEN");
  IElementType QUOTE = new PhelTokenType("'");
  IElementType STRING = new PhelTokenType("string");
  IElementType SYNTAX_QUOTE = new PhelTokenType("`");
  IElementType WORD = new PhelTokenType("WORD");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == LIST_) {
        return new PhelList_Impl(node);
      }
      else if (type == SYMBOL_) {
        return new PhelSymbol_Impl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}

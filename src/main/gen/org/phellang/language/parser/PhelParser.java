// This is a generated file. Not intended for manual editing.
package org.phellang.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.phellang.language.psi.PhelTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class PhelParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return simpleFile(b, l + 1);
  }

  /* ********************************************************** */
  // PARENS_OPEN symbol_* PARENS_CLOSE |COMMENT|CLRF
  public static boolean list_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LIST_, "<list>");
    r = list__0(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, CLRF);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // PARENS_OPEN symbol_* PARENS_CLOSE
  private static boolean list__0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list__0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, PARENS_OPEN);
    r = r && list__0_1(b, l + 1);
    r = r && consumeToken(b, PARENS_CLOSE);
    exit_section_(b, m, null, r);
    return r;
  }

  // symbol_*
  private static boolean list__0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list__0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!symbol_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "list__0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // list_*
  static boolean simpleFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "simpleFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!list_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "simpleFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // WORD*
  public static boolean symbol_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol_")) return false;
    Marker m = enter_section_(b, l, _NONE_, SYMBOL_, "<symbol>");
    while (true) {
      int c = current_position_(b);
      if (!consumeToken(b, WORD)) break;
      if (!empty_element_parsed_guard_(b, "symbol_", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

}

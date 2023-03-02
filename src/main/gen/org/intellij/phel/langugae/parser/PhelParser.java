// This is a generated file. Not intended for manual editing.
package org.intellij.phel.langugae.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.intellij.phel.language.psi.PhelTypes.*;
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
    b = adapt_builder_(t, b, this, EXTENDS_SETS_);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return root_entry(b, l + 1);
  }

  public static final TokenSet[] EXTENDS_SETS_ = new TokenSet[] {
    create_token_set_(ACCESS, CONSTRUCTOR, FORM, FUN,
      KEYWORD, LIST, LITERAL, MAP,
      REGEXP, SET, SYMBOL, VEC),
  };

  /* ********************************************************** */
  // ('.' | '.-') symbol
  public static boolean access(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "access")) return false;
    if (!nextTokenIs(b, "<access>", DOT, DOTDASH)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, ACCESS, "<access>");
    r = access_0(b, l + 1);
    r = r && symbol(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '.' | '.-'
  private static boolean access_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "access_0")) return false;
    boolean r;
    r = consumeToken(b, DOT);
    if (!r) r = consumeToken(b, DOTDASH);
    return r;
  }

  /* ********************************************************** */
  // ! '.'
  public static boolean access_left(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "access_left")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _LEFT_ | _NOT_, ACCESS, "<access left>");
    r = !consumeToken(b, DOT);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // &('::' sym)
  static boolean alias_condition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "alias_condition")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = alias_condition_0(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // '::' sym
  private static boolean alias_condition_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "alias_condition_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COLONCOLON, SYM);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "#_" form
  public static boolean commented(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "commented")) return false;
    if (!nextTokenIsFast(b, SHARP_COMMENT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, COMMENTED, null);
    r = consumeTokenFast(b, SHARP_COMMENT);
    p = r; // pin = 1
    r = r && form(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // '#' skip symbol skip form
  public static boolean constructor(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "constructor")) return false;
    if (!nextTokenIs(b, "<form>", SHARP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, CONSTRUCTOR, "<form>");
    r = consumeToken(b, SHARP);
    p = r; // pin = 1
    r = r && report_error_(b, skip(b, l + 1));
    r = p && report_error_(b, symbol(b, l + 1)) && r;
    r = p && report_error_(b, skip(b, l + 1)) && r;
    r = p && form(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // form_prefix form_prefix * form_upper | form_inner
  public static boolean form(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "form")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, FORM, "<form>");
    r = form_0(b, l + 1);
    if (!r) r = form_inner(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // form_prefix form_prefix * form_upper
  private static boolean form_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "form_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = form_prefix(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, form_0_1(b, l + 1));
    r = p && form_upper(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // form_prefix *
  private static boolean form_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "form_0_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!form_prefix(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "form_0_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // p_forms | s_forms | constructor
  static boolean form_inner(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "form_inner")) return false;
    boolean r;
    r = p_forms(b, l + 1);
    if (!r) r = s_forms(b, l + 1);
    if (!r) r = constructor(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // metadata | reader_macro | commented
  static boolean form_prefix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "form_prefix")) return false;
    boolean r;
    r = metadata(b, l + 1);
    if (!r) r = reader_macro(b, l + 1);
    if (!r) r = commented(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // form_inner
  public static boolean form_upper(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "form_upper")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_ | _UPPER_, FORM, "<form>");
    r = form_inner(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '#' '(' list_body ')'
  public static boolean fun(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fun")) return false;
    if (!nextTokenIs(b, SHARP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, FUN, null);
    r = consumeTokens(b, 2, SHARP, PAREN1);
    p = r; // pin = '[\(\[\{]'
    r = r && report_error_(b, list_body(b, l + 1));
    r = p && consumeToken(b, PAREN2) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // <<items_entry <<recover>> <<param>>>> *
  static boolean items(PsiBuilder b, int l, Parser _recover, Parser _param) {
    if (!recursion_guard_(b, l, "items")) return false;
    Marker m = enter_section_(b, l, _NONE_);
    while (true) {
      int c = current_position_(b);
      if (!items_entry(b, l + 1, _recover, _param)) break;
      if (!empty_element_parsed_guard_(b, "items", c)) break;
    }
    exit_section_(b, l, m, true, false, _recover);
    return true;
  }

  /* ********************************************************** */
  // (not_eof <<recover>>) (commented | <<param>>)
  static boolean items_entry(PsiBuilder b, int l, Parser _recover, Parser _param) {
    if (!recursion_guard_(b, l, "items_entry")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = items_entry_0(b, l + 1, _recover);
    r = r && items_entry_1(b, l + 1, _param);
    exit_section_(b, m, null, r);
    return r;
  }

  // not_eof <<recover>>
  private static boolean items_entry_0(PsiBuilder b, int l, Parser _recover) {
    if (!recursion_guard_(b, l, "items_entry_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_eof(b, l + 1);
    r = r && _recover.parse(b, l);
    exit_section_(b, m, null, r);
    return r;
  }

  // commented | <<param>>
  private static boolean items_entry_1(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "items_entry_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = commented(b, l + 1);
    if (!r) r = _param.parse(b, l);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (':' | '::') symbol_qualified
  public static boolean keyword(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keyword")) return false;
    if (!nextTokenIs(b, "<keyword>", COLON, COLONCOLON)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, KEYWORD, "<keyword>");
    r = keyword_0(b, l + 1);
    r = r && symbol_qualified(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // ':' | '::'
  private static boolean keyword_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "keyword_0")) return false;
    boolean r;
    r = consumeToken(b, COLON);
    if (!r) r = consumeToken(b, COLONCOLON);
    return r;
  }

  /* ********************************************************** */
  // '(' list_body ')'
  public static boolean list(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list")) return false;
    if (!nextTokenIs(b, PAREN1)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, LIST, null);
    r = consumeToken(b, PAREN1);
    p = r; // pin = '[\(\[\{]'
    r = r && report_error_(b, list_body(b, l + 1));
    r = p && consumeToken(b, PAREN2) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // <<items !')' form>>
  static boolean list_body(PsiBuilder b, int l) {
    return items(b, l + 1, PhelParser::list_body_0_0, PhelParser::form);
  }

  // !')'
  private static boolean list_body_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "list_body_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, PAREN2);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // number | hexnum | rdxnum | ratio | bool | nil | string | char
  public static boolean literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, LITERAL, "<literal>");
    r = consumeToken(b, NUMBER);
    if (!r) r = consumeToken(b, HEXNUM);
    if (!r) r = consumeToken(b, RDXNUM);
    if (!r) r = consumeToken(b, RATIO);
    if (!r) r = consumeToken(b, BOOL);
    if (!r) r = consumeToken(b, NIL);
    if (!r) r = consumeToken(b, STRING);
    if (!r) r = consumeToken(b, CHAR);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '{' map_body '}'
  public static boolean map(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map")) return false;
    if (!nextTokenIs(b, BRACE1)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, MAP, null);
    r = consumeToken(b, BRACE1);
    p = r; // pin = '[\(\[\{]'
    r = r && report_error_(b, map_body(b, l + 1));
    r = p && consumeToken(b, BRACE2) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // <<items !'}' map_entry>>
  static boolean map_body(PsiBuilder b, int l) {
    return items(b, l + 1, PhelParser::map_body_0_0, PhelParser::map_entry);
  }

  // !'}'
  private static boolean map_body_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_body_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, BRACE2);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // form skip form
  static boolean map_entry(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_entry")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = form(b, l + 1);
    r = r && skip(b, l + 1);
    p = r; // pin = 2
    r = r && form(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // "#:" (':' symbol_plain
  //   | alias_condition '::' symbol_plain
  //   | '::' ) &'{'
  static boolean map_ns_prefix(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_ns_prefix")) return false;
    if (!nextTokenIs(b, SHARP_NS)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, SHARP_NS);
    p = r; // pin = 1
    r = r && report_error_(b, map_ns_prefix_1(b, l + 1));
    r = p && map_ns_prefix_2(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // ':' symbol_plain
  //   | alias_condition '::' symbol_plain
  //   | '::'
  private static boolean map_ns_prefix_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_ns_prefix_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = map_ns_prefix_1_0(b, l + 1);
    if (!r) r = map_ns_prefix_1_1(b, l + 1);
    if (!r) r = consumeToken(b, COLONCOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // ':' symbol_plain
  private static boolean map_ns_prefix_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_ns_prefix_1_0")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, COLON);
    p = r; // pin = 1
    r = r && symbol_plain(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // alias_condition '::' symbol_plain
  private static boolean map_ns_prefix_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_ns_prefix_1_1")) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = alias_condition(b, l + 1);
    p = r; // pin = 1
    r = r && report_error_(b, consumeToken(b, COLONCOLON));
    r = p && symbol_plain(b, l + 1) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // &'{'
  private static boolean map_ns_prefix_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "map_ns_prefix_2")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, BRACE1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // ("^" | "#^") (string | symbol | keyword | map)
  public static boolean metadata(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "metadata")) return false;
    if (!nextTokenIs(b, "<form>", HAT, SHARP_HAT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, METADATA, "<form>");
    r = metadata_0(b, l + 1);
    r = r && metadata_1(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // "^" | "#^"
  private static boolean metadata_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "metadata_0")) return false;
    boolean r;
    r = consumeToken(b, HAT);
    if (!r) r = consumeToken(b, SHARP_HAT);
    return r;
  }

  // string | symbol | keyword | map
  private static boolean metadata_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "metadata_1")) return false;
    boolean r;
    r = consumeToken(b, STRING);
    if (!r) r = symbol(b, l + 1);
    if (!r) r = keyword(b, l + 1);
    if (!r) r = map(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !<<eof>>
  static boolean not_eof(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "not_eof")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !eof(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // list | set | vec | map | fun
  static boolean p_forms(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "p_forms")) return false;
    boolean r;
    r = list(b, l + 1);
    if (!r) r = set(b, l + 1);
    if (!r) r = vec(b, l + 1);
    if (!r) r = map(b, l + 1);
    if (!r) r = fun(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // ('#?' | '#?@') &'('
  static boolean reader_cond(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reader_cond")) return false;
    if (!nextTokenIs(b, "<form>", SHARP_QMARK, SHARP_QMARK_AT)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, null, "<form>");
    r = reader_cond_0(b, l + 1);
    p = r; // pin = 1
    r = r && reader_cond_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // '#?' | '#?@'
  private static boolean reader_cond_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reader_cond_0")) return false;
    boolean r;
    r = consumeToken(b, SHARP_QMARK);
    if (!r) r = consumeToken(b, SHARP_QMARK_AT);
    return r;
  }

  // &'('
  private static boolean reader_cond_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reader_cond_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, PAREN1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // "'" | "~" | "~@" | "@" | "`" | "#'" | "#=" | symbolic_value | reader_cond | map_ns_prefix
  public static boolean reader_macro(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "reader_macro")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, READER_MACRO, "<form>");
    r = consumeToken(b, QUOTE);
    if (!r) r = consumeToken(b, TILDE);
    if (!r) r = consumeToken(b, TILDE_AT);
    if (!r) r = consumeToken(b, AT);
    if (!r) r = consumeToken(b, SYNTAX_QUOTE);
    if (!r) r = consumeToken(b, SHARP_QUOTE);
    if (!r) r = consumeToken(b, SHARP_EQ);
    if (!r) r = symbolic_value(b, l + 1);
    if (!r) r = reader_cond(b, l + 1);
    if (!r) r = map_ns_prefix(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '#' string
  public static boolean regexp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "regexp")) return false;
    if (!nextTokenIs(b, SHARP)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SHARP, STRING);
    exit_section_(b, m, REGEXP, r);
    return r;
  }

  /* ********************************************************** */
  // not_eof (commented | form)
  static boolean root_entry(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_entry")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = not_eof(b, l + 1);
    r = r && root_entry_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // commented | form
  private static boolean root_entry_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "root_entry_1")) return false;
    boolean r;
    r = commented(b, l + 1);
    if (!r) r = form(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // symbol access_left? | keyword | literal | regexp | access
  static boolean s_forms(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_forms")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = s_forms_0(b, l + 1);
    if (!r) r = keyword(b, l + 1);
    if (!r) r = literal(b, l + 1);
    if (!r) r = regexp(b, l + 1);
    if (!r) r = access(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // symbol access_left?
  private static boolean s_forms_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_forms_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = symbol(b, l + 1);
    r = r && s_forms_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // access_left?
  private static boolean s_forms_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "s_forms_0_1")) return false;
    access_left(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '#' '{' set_body '}'
  public static boolean set(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set")) return false;
    if (!nextTokenIs(b, SHARP)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, SET, null);
    r = consumeTokens(b, 2, SHARP, BRACE1);
    p = r; // pin = '[\(\[\{]'
    r = r && report_error_(b, set_body(b, l + 1));
    r = p && consumeToken(b, BRACE2) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // <<items !'}' form>>
  static boolean set_body(PsiBuilder b, int l) {
    return items(b, l + 1, PhelParser::set_body_0_0, PhelParser::form);
  }

  // !'}'
  private static boolean set_body_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "set_body_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, BRACE2);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // commented *
  static boolean skip(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "skip")) return false;
    while (true) {
      int c = current_position_(b);
      if (!commented(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "skip", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // symbol_qualified
  public static boolean symbol(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol")) return false;
    if (!nextTokenIs(b, SYM)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _COLLAPSE_, SYMBOL, null);
    r = symbol_qualified(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '/' sym
  public static boolean symbol_nsq(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol_nsq")) return false;
    if (!nextTokenIsFast(b, SLASH)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _LEFT_, SYMBOL, null);
    r = consumeTokens(b, 0, SLASH, SYM);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // sym
  public static boolean symbol_plain(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol_plain")) return false;
    if (!nextTokenIs(b, SYM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SYM);
    exit_section_(b, m, SYMBOL, r);
    return r;
  }

  /* ********************************************************** */
  // symbol_plain symbol_nsq?
  static boolean symbol_qualified(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol_qualified")) return false;
    if (!nextTokenIs(b, SYM)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = symbol_plain(b, l + 1);
    r = r && symbol_qualified_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // symbol_nsq?
  private static boolean symbol_qualified_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbol_qualified_1")) return false;
    symbol_nsq(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // '##' &sym
  static boolean symbolic_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbolic_value")) return false;
    if (!nextTokenIs(b, SHARP_SYM)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_);
    r = consumeToken(b, SHARP_SYM);
    p = r; // pin = 1
    r = r && symbolic_value_1(b, l + 1);
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  // &sym
  private static boolean symbolic_value_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "symbolic_value_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _AND_);
    r = consumeToken(b, SYM);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // '[' vec_body ']'
  public static boolean vec(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "vec")) return false;
    if (!nextTokenIs(b, BRACKET1)) return false;
    boolean r, p;
    Marker m = enter_section_(b, l, _NONE_, VEC, null);
    r = consumeToken(b, BRACKET1);
    p = r; // pin = '[\(\[\{]'
    r = r && report_error_(b, vec_body(b, l + 1));
    r = p && consumeToken(b, BRACKET2) && r;
    exit_section_(b, l, m, r, p, null);
    return r || p;
  }

  /* ********************************************************** */
  // <<items !']' form>>
  static boolean vec_body(PsiBuilder b, int l) {
    return items(b, l + 1, PhelParser::vec_body_0_0, PhelParser::form);
  }

  // !']'
  private static boolean vec_body_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "vec_body_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, BRACKET2);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

}

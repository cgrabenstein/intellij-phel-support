package org.phellang;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.phellang.language.psi.PhelSymbol;
import org.phellang.language.psi.PhelKeyword;
import org.phellang.language.psi.PhelPsiUtil;

import java.util.Set;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * Advanced annotator for context-sensitive Phel syntax highlighting
 * Handles PHP interop, built-in functions, and namespace-aware highlighting
 */
public class PhelAnnotator implements Annotator {

    // PHP interop highlighting
    public static final TextAttributesKey PHP_INTEROP =
            createTextAttributesKey("PHEL_PHP_INTEROP", DefaultLanguageHighlighterColors.STATIC_METHOD);
    
    public static final TextAttributesKey PHP_VARIABLE =
            createTextAttributesKey("PHEL_PHP_VARIABLE", DefaultLanguageHighlighterColors.GLOBAL_VARIABLE);

    // Built-in function categories
    public static final TextAttributesKey CORE_FUNCTION =
            createTextAttributesKey("PHEL_CORE_FUNCTION", DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL);
    
    public static final TextAttributesKey MACRO =
            createTextAttributesKey("PHEL_MACRO", DefaultLanguageHighlighterColors.KEYWORD);
    
    public static final TextAttributesKey SPECIAL_FORM =
            createTextAttributesKey("PHEL_SPECIAL_FORM", DefaultLanguageHighlighterColors.KEYWORD);

    // Namespace highlighting  
    public static final TextAttributesKey NAMESPACE_PREFIX =
            createTextAttributesKey("PHEL_NAMESPACE", DefaultLanguageHighlighterColors.CLASS_NAME);
    
    // Function parameter highlighting (more distinct color)
    public static final TextAttributesKey FUNCTION_PARAMETER =
            createTextAttributesKey("PHEL_FUNCTION_PARAMETER", DefaultLanguageHighlighterColors.INSTANCE_FIELD);

    // Core Phel special forms
    private static final Set<String> SPECIAL_FORMS = Set.of(
            "def", "fn", "if", "let", "do", "quote", "var", "recur", "throw", "try", "catch", "finally",
            "loop", "case", "cond", "when", "when-not", "when-let", "if-let", "if-not", "ns",
            "defn", "defn-", "defmacro", "defmacro-", "defstruct", "definterface"
    );

    // Core Phel macros
    private static final Set<String> MACROS = Set.of(
            "and", "or", "->", "->>", "as->", "cond->", "cond->>", "some->", "some->>",
            "doto", "for", "dofor", "foreach", "comment", "declare"
    );

    // Common core functions (subset for performance)
    private static final Set<String> CORE_FUNCTIONS = Set.of(
            // Collection operations
            "map", "filter", "reduce", "apply", "count", "first", "rest", "cons", "conj",
            "get", "get-in", "assoc", "dissoc", "update", "merge", "keys", "values",
            "peek", "pop", "push", "remove", "second", "ffirst", "next", "nnext", "nfirst",
            
            // Sequence operations
            "take", "drop", "take-while", "drop-while", "take-nth", "take-last",
            "distinct", "reverse", "sort", "sort-by", "shuffle", "flatten",
            "range", "repeat", "interleave", "interpose", "partition", "split-at", "split-with",
            
            // String operations
            "str", "print", "println", "print-str", "format", "printf",
            "str-contains?", "slurp", "spit",
            
            // Function operations
            "identity", "comp", "partial", "constantly", "complement", "juxt", "memoize",
            
            // Math operations
            "inc", "dec", "sum", "mean", "extreme",
            "+", "-", "*", "/", "mod", "rem", "quot",
            "=", "not=", "<", "<=", ">", ">=", "min", "max", "abs",
            
            // Predicates
            "pos?", "neg?", "zero?", "one?", "even?", "odd?", "nil?", "some?", "all?",
            "true?", "false?", "truthy?", "boolean?", "number?", "string?", "keyword?", "symbol?",
            "list?", "vector?", "map?", "set?", "seq?", "coll?", "empty?", "not-empty",
            "float?", "int?", "function?", "struct?", "hash-map?", "php-array?",
            "php-resource?", "php-object?", "indexed?", "associative?", "var?",
            
            // Random functions
            "rand", "rand-int", "rand-nth",
            
            // Meta and symbol operations
            "meta", "name", "namespace", "full-name", "symbol", "keyword", "gensym",
            
            // Evaluation and compilation
            "eval", "compile", "macroexpand", "macroexpand-1", "read-string",
            
            // Bit operations
            "bit-and", "bit-or", "bit-xor", "bit-not", "bit-shift-left", "bit-shift-right",
            "bit-set", "bit-clear", "bit-flip", "bit-test",
            
            // Other core functions
            "not", "compare", "type", "contains?", "find", "find-index",
            "frequencies", "group-by", "union", "intersection", "difference",
            "symmetric-difference", "select-keys", "invert", "merge-with", "deep-merge",
            
            // String module functions (phel\str)
            "split", "join", "replace", "replace-first", "trim", "triml", "trimr",
            "capitalize", "lower-case", "upper-case", "starts-with?", "ends-with?",
            "blank?", "includes?", "index-of", "last-index-of", "split-lines",
            "pad-left", "pad-right", "trim-newline", "escape", "re-quote-replacement",
            
            // JSON module functions (phel\json)
            "encode", "decode",
            
            // Base64 module functions (phel\base64)
            "encode-url", "decode-url",
            
            // HTML module functions (phel\html)
            "escape-html", "doctype",
            
            // Test module functions (phel\test)
            "report", "print-summary", "run-tests", "successful?",
            
            // REPL module functions (phel\repl)
            "loaded-namespaces", "resolve", "print-colorful", "println-colorful", "compile-str",
            
            // Trace module functions (phel\trace)
            "dotrace", "reset-trace-state!", "set-trace-id-padding!",
            
            // HTTP module functions (phel\http) - most commonly used
            "request-from-globals", "response-from-map", "response-from-string", "emit-response"
    );

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof PhelKeyword) {
            annotatePhelKeyword((PhelKeyword) element, holder);
        } else if (element instanceof PhelSymbol) {
            PhelSymbol symbol = (PhelSymbol) element;
            String text = symbol.getText();
            
            if (text != null) {
                annotateSymbol(symbol, text, holder);
            }
        }
    }

    private void annotateSymbol(@NotNull PhelSymbol symbol, @NotNull String text, @NotNull AnnotationHolder holder) {
        // Function parameters (check first, before other classifications)
        if (PhelPsiUtil.isDefinition(symbol)) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(symbol.getTextRange())
                    .textAttributes(FUNCTION_PARAMETER)
                    .create();
            return;
        }
        
        // PHP interop patterns
        if (isPhpInterop(text)) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(symbol.getTextRange())
                    .textAttributes(PHP_INTEROP)
                    .create();
            return;
        }
        
        // PHP variables (superglobals)
        if (isPhpVariable(text)) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(symbol.getTextRange())
                    .textAttributes(PHP_VARIABLE)
                    .create();
            return;
        }
        
        // Namespace prefix highlighting
        if (hasNamespacePrefix(text)) {
            highlightNamespacePrefix(symbol, text, holder);
            return;
        }
        
        // Special forms (highest priority)
        if (SPECIAL_FORMS.contains(text)) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(symbol.getTextRange())
                    .textAttributes(SPECIAL_FORM)
                    .create();
            return;
        }
        
        // Macros
        if (MACROS.contains(text)) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(symbol.getTextRange())
                    .textAttributes(MACRO)
                    .create();
            return;
        }
        
        // Core functions
        if (CORE_FUNCTIONS.contains(text)) {
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(symbol.getTextRange())
                    .textAttributes(CORE_FUNCTION)
                    .create();
            return;
        }
    }

    private boolean isPhpInterop(@NotNull String text) {
        return text.startsWith("php/") || 
               text.equals("php/->") || 
               text.equals("php/::") ||
               text.equals("php/new") ||
               text.startsWith("php/a") ||  // php/aget, php/aset, php/apush, php/aunset
               text.startsWith("php/o");    // php/oset
    }

    private boolean isPhpVariable(@NotNull String text) {
        return text.startsWith("php/$") ||
               text.equals("php/$_SERVER") ||
               text.equals("php/$_GET") ||
               text.equals("php/$_POST") ||
               text.equals("php/$_COOKIE") ||
               text.equals("php/$_FILES") ||
               text.equals("php/$_SESSION") ||
               text.equals("php/$GLOBALS");
    }

    private boolean hasNamespacePrefix(@NotNull String text) {
        // Check for namespace separators: / or \
        return (text.contains("/") && !text.startsWith("php/")) || 
               text.contains("\\");
    }

    private void highlightNamespacePrefix(@NotNull PhelSymbol symbol, @NotNull String text, @NotNull AnnotationHolder holder) {
        int separatorIndex = -1;
        
        // Find the last separator (/ or \)
        int slashIndex = text.lastIndexOf('/');
        int backslashIndex = text.lastIndexOf('\\');
        separatorIndex = Math.max(slashIndex, backslashIndex);
        
        if (separatorIndex > 0) {
            // Highlight only the namespace part (before the separator)
            int startOffset = symbol.getTextRange().getStartOffset();
            int endOffset = startOffset + separatorIndex;
            
            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(TextRange.create(startOffset, endOffset))
                    .textAttributes(NAMESPACE_PREFIX)
                    .create();
        }
    }
    
    /**
     * Annotate keyword elements to ensure proper highlighting
     */
    private void annotatePhelKeyword(@NotNull PhelKeyword keyword, @NotNull AnnotationHolder holder) {
        // Highlight the entire keyword element
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                .range(keyword.getTextRange())
                .textAttributes(PhelSyntaxHighlighter.KEYWORD)
                .create();
    }
}

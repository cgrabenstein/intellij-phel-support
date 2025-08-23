package org.phellang.completion;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.phellang.language.psi.PhelSymbol;

import java.util.HashMap;
import java.util.Map;

/**
 * Documentation provider for Phel functions and symbols
 * Provides rich documentation for API functions, special forms, and macros
 */
public class PhelDocumentationProvider extends AbstractDocumentationProvider {

    // Documentation for core API functions
    private static final Map<String, String> FUNCTION_DOCS = new HashMap<>();

    static {
        // Core functions documentation
        FUNCTION_DOCS.put("map",
                "<h3>map</h3>" +
                        "<p><b>Signature:</b> <code>(map f coll)</code></p>" +
                        "<p>Returns a new collection with f applied to each element of coll.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(map inc [1 2 3]) ; => [2 3 4]</code></pre>");

        FUNCTION_DOCS.put("filter",
                "<h3>filter</h3>" +
                        "<p><b>Signature:</b> <code>(filter pred coll)</code></p>" +
                        "<p>Returns a collection of items for which pred returns logical true.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(filter even? [1 2 3 4]) ; => [2 4]</code></pre>");

        FUNCTION_DOCS.put("reduce",
                "<h3>reduce</h3>" +
                        "<p><b>Signature:</b> <code>(reduce f coll)</code> or <code>(reduce f init coll)</code></p>" +
                        "<p>Reduces collection using function f. If init is provided, starts with init.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(reduce + [1 2 3 4]) ; => 10<br/>" +
                        "(reduce + 0 [1 2 3 4]) ; => 10</code></pre>");

        FUNCTION_DOCS.put("count",
                "<h3>count</h3>" +
                        "<p><b>Signature:</b> <code>(count coll)</code></p>" +
                        "<p>Returns the number of items in the collection.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(count [1 2 3]) ; => 3<br/>" +
                        "(count {:a 1 :b 2}) ; => 2</code></pre>");

        FUNCTION_DOCS.put("get",
                "<h3>get</h3>" +
                        "<p><b>Signature:</b> <code>(get coll key)</code> or <code>(get coll key default)</code></p>" +
                        "<p>Returns the value mapped to key, or default (nil if not provided) if key not present.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(get {:a 1 :b 2} :a) ; => 1<br/>" +
                        "(get {:a 1} :c :default) ; => :default</code></pre>");

        FUNCTION_DOCS.put("put",
                "<h3>put</h3>" +
                        "<p><b>Signature:</b> <code>(put coll key val)</code></p>" +
                        "<p>Returns a new collection with val added/updated at key.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(put {:a 1} :b 2) ; => {:a 1 :b 2}<br/>" +
                        "(put [1 2 3] 1 :new) ; => [1 :new 3]</code></pre>");

        FUNCTION_DOCS.put("str",
                "<h3>str</h3>" +
                        "<p><b>Signature:</b> <code>(str & args)</code></p>" +
                        "<p>Concatenates string representations of args.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(str \"Hello \" \"world\" \"!\") ; => \"Hello world!\"<br/>" +
                        "(str 1 2 3) ; => \"123\"</code></pre>");

        FUNCTION_DOCS.put("def",
                "<h3>def</h3>" +
                        "<p><b>Special Form</b></p>" +
                        "<p><b>Signature:</b> <code>(def name meta? value)</code></p>" +
                        "<p>Defines a global var with name bound to value. Optionally takes metadata.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(def my-var 42)<br/>" +
                        "(def ^:private secret \"hidden\")</code></pre>");

        FUNCTION_DOCS.put("defn",
                "<h3>defn</h3>" +
                        "<p><b>Special Form</b></p>" +
                        "<p><b>Signature:</b> <code>(defn name doc? attr? [params*] body*)</code></p>" +
                        "<p>Defines a function with name, optional documentation, attributes, parameters, and body.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(defn add [a b]<br/>" +
                        "  (+ a b))<br/><br/>" +
                        "(defn greet<br/>" +
                        "  \"Greets a person\"<br/>" +
                        "  [name]<br/>" +
                        "  (str \"Hello, \" name))</code></pre>");

        FUNCTION_DOCS.put("let",
                "<h3>let</h3>" +
                        "<p><b>Special Form</b></p>" +
                        "<p><b>Signature:</b> <code>(let [bindings*] expr*)</code></p>" +
                        "<p>Creates local bindings and evaluates expressions in that context.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(let [x 1<br/>" +
                        "      y 2]<br/>" +
                        "  (+ x y)) ; => 3</code></pre>");

        FUNCTION_DOCS.put("if",
                "<h3>if</h3>" +
                        "<p><b>Special Form</b></p>" +
                        "<p><b>Signature:</b> <code>(if test then else?)</code></p>" +
                        "<p>Conditional expression. Evaluates then if test is truthy, else otherwise.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(if (even? 4) \"even\" \"odd\") ; => \"even\"<br/>" +
                        "(if false \"no\" \"yes\") ; => \"yes\"</code></pre>");

        // PHP interop documentation
        FUNCTION_DOCS.put("php/new",
                "<h3>php/new</h3>" +
                        "<p><b>PHP Interop</b></p>" +
                        "<p><b>Signature:</b> <code>(php/new class & args)</code></p>" +
                        "<p>Creates a new PHP object instance of the specified class with constructor arguments.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(php/new DateTime)<br/>" +
                        "(php/new DateTime \"2023-01-01\")</code></pre>");

        FUNCTION_DOCS.put("php/->",
                "<h3>php/-></h3>" +
                        "<p><b>PHP Interop</b></p>" +
                        "<p><b>Signature:</b> <code>(php/-> object method & args)</code></p>" +
                        "<p>Calls a method on a PHP object with the given arguments.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(let [date (php/new DateTime)]<br/>" +
                        "  (php/-> date format \"Y-m-d\"))</code></pre>");

        FUNCTION_DOCS.put("php/::",
                "<h3>php/::</h3>" +
                        "<p><b>PHP Interop</b></p>" +
                        "<p><b>Signature:</b> <code>(php/:: class method & args)</code></p>" +
                        "<p>Calls a static method on a PHP class.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(php/:: DateTime createFromFormat \"Y-m-d\" \"2023-01-01\")</code></pre>");

        // Threading macros
        FUNCTION_DOCS.put("->",
                "<h3>-></h3>" +
                        "<p><b>Threading Macro</b></p>" +
                        "<p><b>Signature:</b> <code>(-> x & forms)</code></p>" +
                        "<p>Thread-first macro. Threads x through each form as the first argument.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(-> \"  hello world  \"<br/>" +
                        "    (str/trim)<br/>" +
                        "    (str/upper-case)) ; => \"HELLO WORLD\"</code></pre>");

        FUNCTION_DOCS.put("->>",
                "<h3>->></h3>" +
                        "<p><b>Threading Macro</b></p>" +
                        "<p><b>Signature:</b> <code>(->> x & forms)</code></p>" +
                        "<p>Thread-last macro. Threads x through each form as the last argument.</p>" +
                        "<p><b>Example:</b></p>" +
                        "<pre><code>(->> [1 2 3 4]<br/>" +
                        "     (map inc)<br/>" +
                        "     (filter even?)) ; => [2 4]</code></pre>");

        // Add more documentation entries as needed...
    }

    @Nullable
    @Override
    public String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
        if (element instanceof PhelSymbol) {
            String symbolName = element.getText();
            if (symbolName != null && !symbolName.isEmpty()) {
                String doc = FUNCTION_DOCS.get(symbolName);
                if (doc != null) {
                    return wrapInHtml(doc);
                }

                // Generate basic documentation for unknown symbols
                return generateBasicDoc(symbolName);
            }
        }

        return super.generateDoc(element, originalElement);
    }

    @Nullable
    @Override
    public String getQuickNavigateInfo(PsiElement element, PsiElement originalElement) {
        if (element instanceof PhelSymbol) {
            String symbolName = element.getText();
            if (symbolName != null && !symbolName.isEmpty()) {
                String signature = getSignature(symbolName);
                if (signature != null) {
                    return symbolName + " " + signature;
                }
            }
        }

        return super.getQuickNavigateInfo(element, originalElement);
    }

    private String generateBasicDoc(@NotNull String symbolName) {
        String category = categorizeSymbol(symbolName);

        return wrapInHtml(
                "<h3>" + symbolName + "</h3>" +
                        "<p><b>Type:</b> " + category + "</p>" +
                        "<p>Documentation for <code>" + symbolName + "</code> is not yet available.</p>" +
                        "<p>This appears to be a " + category.toLowerCase() + " in the Phel language.</p>"
        );
    }

    private String categorizeSymbol(@NotNull String symbolName) {
        if (symbolName.endsWith("?")) {
            return "Predicate Function";
        } else if (symbolName.endsWith("!")) {
            return "Side-Effect Function";
        } else if (symbolName.startsWith("php/")) {
            return "PHP Interop Function";
        } else if (symbolName.contains("/")) {
            return "Namespaced Function";
        } else if (symbolName.startsWith(":")) {
            return "Keyword";
        } else if (isSpecialForm(symbolName)) {
            return "Special Form";
        } else if (isMacro(symbolName)) {
            return "Macro";
        } else {
            return "Function";
        }
    }

    private boolean isSpecialForm(String name) {
        return "def defn defn- defmacro defmacro- let if when cond case do loop recur fn quote var ns try catch finally throw for dofor foreach".contains(name);
    }

    private boolean isMacro(String name) {
        return "-> ->> as-> and or not comment declare".contains(name);
    }

    @Nullable
    private String getSignature(@NotNull String symbolName) {
        // Extract signature from documentation
        String doc = FUNCTION_DOCS.get(symbolName);
        if (doc != null && doc.contains("<b>Signature:</b>")) {
            int start = doc.indexOf("<code>") + 6;
            int end = doc.indexOf("</code>");
            if (start > 5 && end > start) {
                return doc.substring(start, end);
            }
        }
        return null;
    }

    private String wrapInHtml(@NotNull String content) {
        return "<html><body>" + content + "</body></html>";
    }
}

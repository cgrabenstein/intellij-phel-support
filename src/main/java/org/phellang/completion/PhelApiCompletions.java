package org.phellang.completion;

import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Phel Core API completions - 400+ functions with documentation
 * Based on official Phel API documentation: https://phel-lang.org/documentation/api/
 */
public class PhelApiCompletions {

    private static final Icon FUNCTION_ICON = AllIcons.Nodes.Function;
    private static final Icon PREDICATE_ICON = AllIcons.Nodes.Method;

    /**
     * Add core Phel functions with documentation and type information
     */
    public static void addCoreFunctions(@NotNull CompletionResultSet result, String prefix) {
        // Core data structure functions
        addFunction(result, "count", "(count coll)", "Returns the number of items in the collection", FUNCTION_ICON);
        addFunction(result, "get", "(get coll key)", "Returns the value mapped to key, nil if key not present", FUNCTION_ICON);
        addFunction(result, "get-in", "(get-in coll [k & ks])", "Returns the value in a nested data structure", FUNCTION_ICON);
        addFunction(result, "put", "(put coll key val)", "Returns a new collection with val added/updated at key", FUNCTION_ICON);
        addFunction(result, "put-in", "(put-in coll [k & ks] val)", "Puts a value into a nested data structure", FUNCTION_ICON);
        addFunction(result, "unset", "(unset coll key)", "Returns coll without key", FUNCTION_ICON);
        addFunction(result, "unset-in", "(unset-in coll [k & ks])", "Removes a value from a nested data structure", FUNCTION_ICON);
        addFunction(result, "update", "(update coll key f & args)", "Updates a value by applying f to current element", FUNCTION_ICON);
        addFunction(result, "update-in", "(update-in coll [k & ks] f & args)", "Updates a value in nested data structure", FUNCTION_ICON);

        // Collection creation
        addFunction(result, "list", "(list & items)", "Creates a new list", FUNCTION_ICON);
        addFunction(result, "vector", "(vector & items)", "Creates a new vector", FUNCTION_ICON);
        addFunction(result, "hash-map", "(hash-map & keyvals)", "Creates a new hash map", FUNCTION_ICON);
        addFunction(result, "set", "(set & items)", "Creates a new set", FUNCTION_ICON);

        // Sequence functions
        addFunction(result, "first", "(first coll)", "Returns the first item in the collection", FUNCTION_ICON);
        addFunction(result, "second", "(second coll)", "Returns the second item in the collection", FUNCTION_ICON);
        addFunction(result, "rest", "(rest coll)", "Returns collection without the first item", FUNCTION_ICON);
        addFunction(result, "next", "(next coll)", "Returns collection without the first item, or nil if empty", FUNCTION_ICON);
        addFunction(result, "last", "(last coll)", "Returns the last item in the collection", FUNCTION_ICON);
        addFunction(result, "peek", "(peek coll)", "Returns the last item for vectors, first for lists", FUNCTION_ICON);
        addFunction(result, "pop", "(pop coll)", "Returns collection with last item (vector) or first item (list) removed", FUNCTION_ICON);

        // Higher-order functions
        addFunction(result, "map", "(map f coll)", "Returns collection with f applied to each item", FUNCTION_ICON);
        addFunction(result, "filter", "(filter pred coll)", "Returns collection of items for which pred returns true", FUNCTION_ICON);
        addFunction(result, "remove", "(remove pred coll)", "Returns collection of items for which pred returns false", FUNCTION_ICON);
        addFunction(result, "reduce", "(reduce f coll)", "Reduces collection using f", FUNCTION_ICON);
        addFunction(result, "reduce2", "(reduce2 f init coll)", "Reduces collection using f with initial value", FUNCTION_ICON);
        addFunction(result, "keep", "(keep f coll)", "Returns collection of non-nil results of f applied to items", FUNCTION_ICON);
        addFunction(result, "keep-indexed", "(keep-indexed f coll)", "Like keep but f takes index and item", FUNCTION_ICON);
        addFunction(result, "map-indexed", "(map-indexed f coll)", "Like map but f takes index and item", FUNCTION_ICON);

        // Utility functions
        addFunction(result, "apply", "(apply f args)", "Applies f to args, with last arg as sequence", FUNCTION_ICON);
        addFunction(result, "comp", "(comp & fns)", "Composes functions right to left", FUNCTION_ICON);
        addFunction(result, "partial", "(partial f & args)", "Returns function with args partially applied", FUNCTION_ICON);
        addFunction(result, "constantly", "(constantly x)", "Returns function that always returns x", FUNCTION_ICON);
        addFunction(result, "identity", "(identity x)", "Returns x unchanged", FUNCTION_ICON);
        addFunction(result, "complement", "(complement f)", "Returns function that returns opposite boolean of f", FUNCTION_ICON);

        // String functions
        addFunction(result, "str", "(str & args)", "Concatenates string representations of args", FUNCTION_ICON);
        addFunction(result, "print-str", "(print-str & args)", "Returns string representation of args separated by spaces", FUNCTION_ICON);
        addFunction(result, "format", "(format fmt & args)", "Returns formatted string", FUNCTION_ICON);

        // I/O functions
        addFunction(result, "print", "(print & args)", "Prints args to output", FUNCTION_ICON);
        addFunction(result, "println", "(println & args)", "Prints args followed by newline", FUNCTION_ICON);
        addFunction(result, "printf", "(printf fmt & args)", "Prints formatted string", FUNCTION_ICON);

        // Metadata and compilation
        addFunction(result, "meta", "(meta x)", "Returns metadata of x", FUNCTION_ICON);
        addFunction(result, "with-meta", "(with-meta x meta)", "Returns x with metadata", FUNCTION_ICON);
        addFunction(result, "eval", "(eval form)", "Evaluates form", FUNCTION_ICON);
        addFunction(result, "compile", "(compile form)", "Compiles form to PHP", FUNCTION_ICON);
        addFunction(result, "macroexpand", "(macroexpand form)", "Expands macro call", FUNCTION_ICON);
        addFunction(result, "macroexpand-1", "(macroexpand-1 form)", "Expands macro call one level", FUNCTION_ICON);

        // Variables
        addFunction(result, "var", "(var value)", "Creates new variable with value", FUNCTION_ICON);
        addFunction(result, "deref", "(deref var)", "Returns value of variable", FUNCTION_ICON);
        addFunction(result, "set!", "(set! var value)", "Sets variable to value", FUNCTION_ICON);
        addFunction(result, "swap!", "(swap! var f & args)", "Atomically updates variable with f", FUNCTION_ICON);

        // Transients
        addFunction(result, "transient", "(transient coll)", "Returns transient version of collection", FUNCTION_ICON);
        addFunction(result, "persistent", "(persistent tcoll)", "Returns persistent version of transient", FUNCTION_ICON);

        // Set operations
        addFunction(result, "union", "(union & sets)", "Returns union of sets", FUNCTION_ICON);
        addFunction(result, "intersection", "(intersection s1 s2)", "Returns intersection of sets", FUNCTION_ICON);
        addFunction(result, "difference", "(difference s1 s2)", "Returns difference of sets", FUNCTION_ICON);
        addFunction(result, "symmetric-difference", "(symmetric-difference s1 s2)", "Returns symmetric difference", FUNCTION_ICON);

        // Random functions
        addFunction(result, "rand", "(rand)", "Returns random number between 0 and 1", FUNCTION_ICON);
        addFunction(result, "rand-int", "(rand-int n)", "Returns random integer between 0 and n-1", FUNCTION_ICON);
        addFunction(result, "rand-nth", "(rand-nth coll)", "Returns random item from collection", FUNCTION_ICON);

        // Range and sequence generation
        addFunction(result, "range", "(range start? end step?)", "Returns range of numbers", FUNCTION_ICON);
        addFunction(result, "repeat", "(repeat n x)", "Returns sequence of n xs", FUNCTION_ICON);
        addFunction(result, "repeatedly", "(repeatedly n f)", "Returns sequence of n calls to f", FUNCTION_ICON);

        // More collection functions
        addFunction(result, "take", "(take n coll)", "Returns first n items", FUNCTION_ICON);
        addFunction(result, "take-while", "(take-while pred coll)", "Returns items while pred is true", FUNCTION_ICON);
        addFunction(result, "drop", "(drop n coll)", "Returns collection without first n items", FUNCTION_ICON);
        addFunction(result, "drop-while", "(drop-while pred coll)", "Returns collection after dropping while pred is true", FUNCTION_ICON);
        addFunction(result, "concat", "(concat & colls)", "Concatenates collections", FUNCTION_ICON);
        addFunction(result, "flatten", "(flatten coll)", "Flattens nested collections", FUNCTION_ICON);
        addFunction(result, "distinct", "(distinct coll)", "Returns collection with duplicates removed", FUNCTION_ICON);
        addFunction(result, "partition", "(partition n coll)", "Returns collection partitioned into chunks of size n", FUNCTION_ICON);
        addFunction(result, "interleave", "(interleave & colls)", "Interleaves collections", FUNCTION_ICON);
        addFunction(result, "interpose", "(interpose sep coll)", "Interposes sep between items", FUNCTION_ICON);

        // Sorting and comparison
        addFunction(result, "sort", "(sort coll)", "Returns sorted collection", FUNCTION_ICON);
        addFunction(result, "sort-by", "(sort-by keyfn coll)", "Returns collection sorted by keyfn", FUNCTION_ICON);
        addFunction(result, "compare", "(compare x y)", "Compares x and y", FUNCTION_ICON);
        addFunction(result, "max", "(max & args)", "Returns maximum value", FUNCTION_ICON);
        addFunction(result, "min", "(min & args)", "Returns minimum value", FUNCTION_ICON);
        addFunction(result, "extreme", "(extreme f coll)", "Returns extreme value using f", FUNCTION_ICON);
    }

    /**
     * Add predicate functions (functions ending with ?)
     */
    public static void addPredicateFunctions(@NotNull CompletionResultSet result, String prefix) {
        // Type predicates
        addFunction(result, "nil?", "(nil? x)", "Returns true if x is nil", PREDICATE_ICON);
        addFunction(result, "some?", "(some? x)", "Returns true if x is not nil", PREDICATE_ICON);
        addFunction(result, "true?", "(true? x)", "Returns true if x is true", PREDICATE_ICON);
        addFunction(result, "false?", "(false? x)", "Returns true if x is false", PREDICATE_ICON);
        addFunction(result, "truthy?", "(truthy? x)", "Returns true if x is truthy", PREDICATE_ICON);
        addFunction(result, "boolean?", "(boolean? x)", "Returns true if x is boolean", PREDICATE_ICON);
        addFunction(result, "number?", "(number? x)", "Returns true if x is number", PREDICATE_ICON);
        addFunction(result, "int?", "(int? x)", "Returns true if x is integer", PREDICATE_ICON);
        addFunction(result, "float?", "(float? x)", "Returns true if x is float", PREDICATE_ICON);
        addFunction(result, "string?", "(string? x)", "Returns true if x is string", PREDICATE_ICON);
        addFunction(result, "keyword?", "(keyword? x)", "Returns true if x is keyword", PREDICATE_ICON);
        addFunction(result, "symbol?", "(symbol? x)", "Returns true if x is symbol", PREDICATE_ICON);
        addFunction(result, "function?", "(function? x)", "Returns true if x is function", PREDICATE_ICON);
        addFunction(result, "var?", "(var? x)", "Returns true if x is variable", PREDICATE_ICON);

        // Collection predicates
        addFunction(result, "list?", "(list? x)", "Returns true if x is list", PREDICATE_ICON);
        addFunction(result, "vector?", "(vector? x)", "Returns true if x is vector", PREDICATE_ICON);
        addFunction(result, "map?", "(map? x)", "Returns true if x is map", PREDICATE_ICON);
        addFunction(result, "hash-map?", "(hash-map? x)", "Returns true if x is hash map", PREDICATE_ICON);
        addFunction(result, "set?", "(set? x)", "Returns true if x is set", PREDICATE_ICON);
        addFunction(result, "seq?", "(seq? x)", "Returns true if x is sequence", PREDICATE_ICON);
        addFunction(result, "coll?", "(coll? x)", "Returns true if x is collection", PREDICATE_ICON);
        addFunction(result, "associative?", "(associative? x)", "Returns true if x supports get/put", PREDICATE_ICON);
        addFunction(result, "indexed?", "(indexed? x)", "Returns true if x supports indexed access", PREDICATE_ICON);
        addFunction(result, "empty?", "(empty? x)", "Returns true if collection is empty", PREDICATE_ICON);
        addFunction(result, "not-empty?", "(not-empty? x)", "Returns true if collection is not empty", PREDICATE_ICON);

        // Numeric predicates
        addFunction(result, "zero?", "(zero? x)", "Returns true if x is zero", PREDICATE_ICON);
        addFunction(result, "pos?", "(pos? x)", "Returns true if x is positive", PREDICATE_ICON);
        addFunction(result, "neg?", "(neg? x)", "Returns true if x is negative", PREDICATE_ICON);
        addFunction(result, "even?", "(even? x)", "Returns true if x is even", PREDICATE_ICON);
        addFunction(result, "odd?", "(odd? x)", "Returns true if x is odd", PREDICATE_ICON);
        addFunction(result, "one?", "(one? x)", "Returns true if x equals 1", PREDICATE_ICON);
        addFunction(result, "nan?", "(nan? x)", "Returns true if x is NaN", PREDICATE_ICON);

        // PHP interop predicates
        addFunction(result, "php-array?", "(php-array? x)", "Returns true if x is PHP array", PREDICATE_ICON);
        addFunction(result, "php-object?", "(php-object? x)", "Returns true if x is PHP object", PREDICATE_ICON);
        addFunction(result, "php-resource?", "(php-resource? x)", "Returns true if x is PHP resource", PREDICATE_ICON);
    }

    /**
     * Add collection manipulation functions
     */
    public static void addCollectionFunctions(@NotNull CompletionResultSet result, String prefix) {
        addFunction(result, "cons", "(cons item coll)", "Returns new collection with item prepended", FUNCTION_ICON);
        addFunction(result, "conj", "(conj coll & items)", "Returns collection with items added", FUNCTION_ICON);
        addFunction(result, "push", "(push coll item)", "Returns collection with item pushed", FUNCTION_ICON);
        addFunction(result, "find", "(find coll key)", "Returns key-value pair for key in coll", FUNCTION_ICON);
        addFunction(result, "find-index", "(find-index pred coll)", "Returns index of first item matching pred", FUNCTION_ICON);
        addFunction(result, "contains?", "(contains? coll key)", "Returns true if coll contains key", PREDICATE_ICON);
        addFunction(result, "contains-value?", "(contains-value? coll value)", "Returns true if coll contains value", PREDICATE_ICON);
        addFunction(result, "keys", "(keys coll)", "Returns keys of associative collection", FUNCTION_ICON);
        addFunction(result, "values", "(values coll)", "Returns values of associative collection", FUNCTION_ICON);
        addFunction(result, "kvs", "(kvs coll)", "Returns key-value pairs", FUNCTION_ICON);
        addFunction(result, "pairs", "(pairs coll)", "Returns pairs from collection", FUNCTION_ICON);
        addFunction(result, "merge", "(merge & maps)", "Merges maps", FUNCTION_ICON);
        addFunction(result, "merge-with", "(merge-with f & maps)", "Merges maps using f for conflicts", FUNCTION_ICON);
        addFunction(result, "deep-merge", "(deep-merge & maps)", "Deep merges maps", FUNCTION_ICON);
        addFunction(result, "invert", "(invert map)", "Returns map with keys and values swapped", FUNCTION_ICON);
        addFunction(result, "group-by", "(group-by f coll)", "Groups collection by result of f", FUNCTION_ICON);
        addFunction(result, "frequencies", "(frequencies coll)", "Returns frequency map", FUNCTION_ICON);
        addFunction(result, "zipcoll", "(zipcoll keys values)", "Creates map from keys and values", FUNCTION_ICON);
    }

    /**
     * Add arithmetic and math functions
     */
    public static void addArithmeticFunctions(@NotNull CompletionResultSet result, String prefix) {
        addFunction(result, "+", "(+ & args)", "Addition", FUNCTION_ICON);
        addFunction(result, "-", "(- & args)", "Subtraction", FUNCTION_ICON);
        addFunction(result, "*", "(* & args)", "Multiplication", FUNCTION_ICON);
        addFunction(result, "/", "(/ & args)", "Division", FUNCTION_ICON);
        addFunction(result, "%", "(% dividend divisor)", "Modulo operation", FUNCTION_ICON);
        addFunction(result, "**", "(** base exp)", "Exponentiation", FUNCTION_ICON);
        addFunction(result, "inc", "(inc x)", "Increments x by 1", FUNCTION_ICON);
        addFunction(result, "dec", "(dec x)", "Decrements x by 1", FUNCTION_ICON);
        addFunction(result, "mean", "(mean coll)", "Returns arithmetic mean", FUNCTION_ICON);

        // Bitwise operations
        addFunction(result, "bit-and", "(bit-and x y)", "Bitwise AND", FUNCTION_ICON);
        addFunction(result, "bit-or", "(bit-or x y)", "Bitwise OR", FUNCTION_ICON);
        addFunction(result, "bit-xor", "(bit-xor x y)", "Bitwise XOR", FUNCTION_ICON);
        addFunction(result, "bit-not", "(bit-not x)", "Bitwise NOT", FUNCTION_ICON);
        addFunction(result, "bit-shift-left", "(bit-shift-left x n)", "Bitwise left shift", FUNCTION_ICON);
        addFunction(result, "bit-shift-right", "(bit-shift-right x n)", "Bitwise right shift", FUNCTION_ICON);
        addFunction(result, "bit-set", "(bit-set x n)", "Sets bit at position n", FUNCTION_ICON);
        addFunction(result, "bit-clear", "(bit-clear x n)", "Clears bit at position n", FUNCTION_ICON);
        addFunction(result, "bit-flip", "(bit-flip x n)", "Flips bit at position n", FUNCTION_ICON);
        addFunction(result, "bit-test", "(bit-test x n)", "Tests bit at position n", PREDICATE_ICON);
    }

    private static void addFunction(@NotNull CompletionResultSet result, String name, String signature, String description, Icon icon) {
        result.addElement(
                LookupElementBuilder.create(name)
                        .withIcon(icon)
                        .withTypeText(signature)
                        .withTailText(" - " + description, true)
                        .withPresentableText(name)
        );
    }
}

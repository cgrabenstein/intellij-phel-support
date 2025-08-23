package org.phellang;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

/**
 * Color settings page for Phel language
 * Allows users to customize syntax highlighting colors
 */
public class PhelColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Comment", PhelSyntaxHighlighter.COMMENT),
            new AttributesDescriptor("String", PhelSyntaxHighlighter.STRING),
            new AttributesDescriptor("Number", PhelSyntaxHighlighter.NUMBER),
            new AttributesDescriptor("Boolean", PhelSyntaxHighlighter.BOOLEAN),
            new AttributesDescriptor("Nil literal", PhelSyntaxHighlighter.NIL_LITERAL),
            new AttributesDescriptor("NAN literal", PhelSyntaxHighlighter.NAN_LITERAL),
            new AttributesDescriptor("Character", PhelSyntaxHighlighter.CHARACTER),
            new AttributesDescriptor("Parentheses", PhelSyntaxHighlighter.PARENTHESES),
            new AttributesDescriptor("Brackets", PhelSyntaxHighlighter.BRACKETS),
            new AttributesDescriptor("Braces", PhelSyntaxHighlighter.BRACES),
            new AttributesDescriptor("Quote", PhelSyntaxHighlighter.QUOTE),
            new AttributesDescriptor("Syntax quote (quasiquote)", PhelSyntaxHighlighter.SYNTAX_QUOTE),
            new AttributesDescriptor("Unquote", PhelSyntaxHighlighter.UNQUOTE),
            new AttributesDescriptor("Unquote splicing", PhelSyntaxHighlighter.UNQUOTE_SPLICING),
            new AttributesDescriptor("Symbol", PhelSyntaxHighlighter.SYMBOL),
            new AttributesDescriptor("Keyword", PhelSyntaxHighlighter.KEYWORD_IDENTIFIER),
            new AttributesDescriptor("Metadata", PhelSyntaxHighlighter.METADATA),
            new AttributesDescriptor("Dot operator", PhelSyntaxHighlighter.DOT_OPERATOR),
            new AttributesDescriptor("Comma", PhelSyntaxHighlighter.COMMA),
            new AttributesDescriptor("Bad character", PhelSyntaxHighlighter.BAD_CHARACTER),
    };

    @Nullable
    @Override
    public Icon getIcon() {
        return PhelIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new PhelSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "# This is a comment - demonstrates Phel syntax highlighting\n" +
                "\n" +
                "(ns my-app\\examples\n" +
                "  (:require phel\\core :refer [print-str str])\n" +
                "  (:require phel\\http :as http)\n" +
                "  (:use \\DateTime))\n" +
                "\n" +
                "# Boolean and nil literals\n" +
                "(def my-config {:debug true :prod false :cache nil :timeout NAN})\n" +
                "\n" +
                "# Numbers in different bases\n" +
                "(def numbers [42 0xFF 0b1010 0o777 3.14159 -1.5e10])\n" +
                "\n" +
                "# Characters and strings\n" +
                "(def greeting \"Hello, Phel!\")\n" +
                "(def newline-char \\newline)\n" +
                "(def unicode-char \\u03BB)\n" +
                "\n" +
                "# Function with destructuring\n" +
                "(defn process-request\n" +
                "  \"Processes an HTTP request with destructuring\"\n" +
                "  [{:method method :path path :headers headers}]\n" +
                "  (let [[first-segment & rest-segments] (str/split path \"/\")]\n" +
                "    (case method\n" +
                "      \"GET\" (handle-get first-segment rest-segments)\n" +
                "      \"POST\" (handle-post path headers))))\n" +
                "\n" +
                "# Macros with quote/unquote\n" +
                "(defmacro when-debug [& body]\n" +
                "  `(if (:debug my-config)\n" +
                "     (do ,@body)))\n" +
                "\n" +
                "# PHP interop\n" +
                "(def now (php/new DateTime))\n" +
                "(def formatted (php/-> now (format \"Y-m-d H:i:s\")))\n" +
                "(def timestamp (php/:: DateTime (createFromFormat \"U\" \"1234567890\")))\n" +
                "\n" +
                "# Advanced features\n" +
                "(def computed-values\n" +
                "  (for [x :range [1 10]\n" +
                "        :when (even? x)\n" +
                "        :let [squared (* x x)]]\n" +
                "    {:original x :squared squared}))\n" +
                "\n" +
                "# Metadata and special forms\n" +
                "(def ^:private secret-value 42)\n" +
                "(def my-var (var 0))\n" +
                "(binding [*debug-mode* true]\n" +
                "  (println \"Debug enabled\"))";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Phel";
    }
}

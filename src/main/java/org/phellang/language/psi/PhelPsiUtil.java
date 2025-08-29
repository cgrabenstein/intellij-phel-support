package org.phellang.language.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility methods for working with Phel PSI elements.
 * This class provides helper functions for analyzing and manipulating Phel code structures.
 */
public class PhelPsiUtil {

    /**
     * Get the name of a Phel symbol.
     * For qualified symbols like "ns/name", returns just "name".
     * For simple symbols like "name", returns "name".
     */
    @Nullable
    public static String getName(@NotNull PhelSymbol symbol) {
        String text = symbol.getText();
        if (text == null) {
            return null;
        }
        
        // Handle qualified symbols (namespace/name)
        int slashIndex = text.lastIndexOf('/');
        if (slashIndex > 0 && slashIndex < text.length() - 1) {
            return text.substring(slashIndex + 1);
        }
        
        return text;
    }

    /**
     * Get the fully qualified name of a symbol including namespace.
     * For "ns/name" returns "ns/name", for "name" returns "name".
     */
    @Nullable
    public static String getQualifiedName(@NotNull PhelSymbol symbol) {
        return symbol.getText();
    }

    /**
     * Get the namespace qualifier of a symbol.
     * For "ns/name" returns "ns", for "name" returns null.
     */
    @Nullable
    public static String getQualifier(@NotNull PhelSymbol symbol) {
        String text = symbol.getText();
        if (text == null) {
            return null;
        }
        
        int slashIndex = text.lastIndexOf('/');
        if (slashIndex > 0) {
            return text.substring(0, slashIndex);
        }
        
        return null;
    }

    /**
     * Get the text offset of the symbol name part (excluding qualifier).
     * Used for proper positioning in rename refactoring.
     */
    public static int getNameTextOffset(@NotNull PhelSymbol symbol) {
        String text = symbol.getText();
        if (text == null) {
            return symbol.getTextRange().getStartOffset();
        }
        
        int slashIndex = text.lastIndexOf('/');
        if (slashIndex > 0 && slashIndex < text.length() - 1) {
            return symbol.getTextRange().getStartOffset() + slashIndex + 1;
        }
        
        return symbol.getTextRange().getStartOffset();
    }

    /**
     * Check if this symbol is in a definition position (being defined rather than referenced).
     * This helps distinguish between definitions and usages for proper highlighting.
     */
    public static boolean isDefinition(@NotNull PhelSymbol symbol) {
        PsiElement parent = symbol.getParent();
        if (parent == null) {
            return false;
        }
        
        // Check if this symbol is a function parameter
        if (isFunctionParameter(symbol)) {
            return true;
        }
        
        // Check if this symbol is a let binding
        if (isLetBinding(symbol)) {
            return true;
        }
        
        // Check if this symbol is the first element in a list starting with def, defn, etc.
        PhelList containingList = PsiTreeUtil.getParentOfType(symbol, PhelList.class);
        if (containingList != null) {
            PhelForm firstForm = PsiTreeUtil.findChildOfType(containingList, PhelForm.class);
            if (firstForm != null) {
                PhelSymbol firstSymbol = PsiTreeUtil.findChildOfType(firstForm, PhelSymbol.class);
                if (firstSymbol != null) {
                    String firstSymbolText = firstSymbol.getText();
                    if (isDefiningForm(firstSymbolText)) {
                        // Check if this symbol is the second element (the name being defined)
                        PsiElement[] forms = PsiTreeUtil.getChildrenOfType(containingList, PhelForm.class);
                        if (forms != null && forms.length > 1) {
                            PhelSymbol definedName = PsiTreeUtil.findChildOfType(forms[1], PhelSymbol.class);
                            return symbol == definedName;
                        }
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * Check if a symbol represents a defining form (def, defn, defmacro, etc.).
     */
    public static boolean isDefiningForm(@Nullable String symbolText) {
        if (symbolText == null) {
            return false;
        }
        
        return symbolText.equals("def") || symbolText.equals("defn") || symbolText.equals("defmacro") ||
               symbolText.equals("defstruct") || symbolText.equals("definterface") || 
               symbolText.equals("defexception") || symbolText.equals("declare") ||
               symbolText.equals("def-") || symbolText.equals("defn-") || symbolText.equals("defmacro-");
    }

    /**
     * Get the name of a keyword.
     * For ":keyword" returns "keyword", for "::ns-keyword" returns "ns-keyword".
     */
    @Nullable
    public static String getName(@NotNull PhelKeyword keyword) {
        String text = keyword.getText();
        if (text == null) {
            return null;
        }
        
        // Remove leading colons
        if (text.startsWith("::")) {
            return text.substring(2);
        } else if (text.startsWith(":")) {
            return text.substring(1);
        }
        
        return text;
    }

    /**
     * Get the namespace part of a keyword.
     * For namespaced keywords like ":ns/keyword" returns "ns".
     */
    @Nullable
    public static String getNamespace(@NotNull PhelKeyword keyword) {
        String text = keyword.getText();
        if (text == null) {
            return null;
        }
        
        // Remove leading colons
        String nameText = text.startsWith("::") ? text.substring(2) : 
                         text.startsWith(":") ? text.substring(1) : text;
        
        int slashIndex = nameText.lastIndexOf('/');
        if (slashIndex > 0) {
            return nameText.substring(0, slashIndex);
        }
        
        return null;
    }

    /**
     * Get the fully qualified name of a keyword.
     */
    @Nullable
    public static String getQualifiedName(@NotNull PhelKeyword keyword) {
        return getName(keyword);
    }

    /**
     * Get the text offset for keywords (used in rename refactoring).
     */
    public static int getNameTextOffset(@NotNull PhelKeyword keyword) {
        String text = keyword.getText();
        if (text == null) {
            return keyword.getTextRange().getStartOffset();
        }
        
        // Skip over the leading colons
        int offset = 0;
        if (text.startsWith("::")) {
            offset = 2;
        } else if (text.startsWith(":")) {
            offset = 1;
        }
        
        return keyword.getTextRange().getStartOffset() + offset;
    }
    
    /**
     * Check if this symbol is a function parameter.
     * Parameters appear in parameter vectors: (defn name [param1 param2] ...) or (fn [param1 param2] ...)
     */
    private static boolean isFunctionParameter(@NotNull PhelSymbol symbol) {
        // Check if symbol is inside a parameter vector
        PhelVec paramVec = PsiTreeUtil.getParentOfType(symbol, PhelVec.class);
        if (paramVec == null) return false;
        
        // Check if the parameter vector is part of a function definition
        PhelList containingList = PsiTreeUtil.getParentOfType(paramVec, PhelList.class);
        if (containingList == null) return false;
        
        PhelForm[] forms = PsiTreeUtil.getChildrenOfType(containingList, PhelForm.class);
        if (forms == null || forms.length < 2) return false;
        
        // Check first symbol to see if it's a function-defining form
        PhelSymbol firstSymbol = PsiTreeUtil.findChildOfType(forms[0], PhelSymbol.class);
        if (firstSymbol == null) return false;
        
        String keyword = firstSymbol.getText();
        
        if (keyword.equals("defn") || keyword.equals("defn-") || 
            keyword.equals("defmacro") || keyword.equals("defmacro-")) {
            // For (defn name [params] ...) and (defn- name [params] ...), parameter vector is at forms[2]
            // forms[2] IS the parameter vector directly (not wrapped)
            return forms.length >= 3 && forms[2] == paramVec;
        } else if (keyword.equals("fn")) {
            // For (fn [params] ...), parameter vector is at forms[1]  
            // forms[1] IS the parameter vector directly (not wrapped)
            return forms.length >= 2 && forms[1] == paramVec;
        }
        
        return false;
    }
    
    /**
     * Check if this symbol is a let binding.
     * Let bindings appear in binding vectors: (let [symbol value symbol2 value2] ...)
     */
    private static boolean isLetBinding(@NotNull PhelSymbol symbol) {
        // Check if symbol is inside a binding vector
        PhelVec bindingVec = PsiTreeUtil.getParentOfType(symbol, PhelVec.class);
        if (bindingVec == null) return false;
        
        // Check if the binding vector is part of a let form
        PhelList containingList = PsiTreeUtil.getParentOfType(bindingVec, PhelList.class);
        if (containingList == null) return false;
        
        PhelForm[] forms = PsiTreeUtil.getChildrenOfType(containingList, PhelForm.class);
        if (forms == null || forms.length < 2) return false;
        
        // Check if first symbol is a binding form
        PhelSymbol firstSymbol = PsiTreeUtil.findChildOfType(forms[0], PhelSymbol.class);
        if (firstSymbol == null) return false;
        String formType = firstSymbol.getText();
        if (!"let".equals(formType) && !"for".equals(formType) && !"binding".equals(formType) &&
            !"loop".equals(formType) && !"foreach".equals(formType) && !"dofor".equals(formType)) return false;
        
        // Check if binding vector is at forms[1] - forms[1] IS the bindingVec directly
        if (forms[1] != bindingVec) return false;
        
        // Check if symbol is in an even position (binding symbols are at even indices)
        PhelForm[] bindings = PsiTreeUtil.getChildrenOfType(bindingVec, PhelForm.class);
        if (bindings == null) return false;
        
        for (int i = 0; i < bindings.length; i += 2) {
            PhelSymbol bindingSymbol = PsiTreeUtil.findChildOfType(bindings[i], PhelSymbol.class);
            if (bindingSymbol == symbol) {
                return true; // Found symbol at even index (binding position)
            }
        }
        
        return false;
    }
}


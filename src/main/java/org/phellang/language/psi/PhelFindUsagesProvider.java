package org.phellang.language.psi;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.phellang.language.PhelLexerAdapter;

/**
 * Provides information about how Phel elements should be displayed in find usages and navigation.
 * This determines whether elements are labeled as "variable", "function", "macro", etc.
 */
public class PhelFindUsagesProvider implements FindUsagesProvider {

    @Override
    @Nullable
    public WordsScanner getWordsScanner() {
        return new DefaultWordsScanner(new PhelLexerAdapter(),
                PhelTokenSets.SYM,  // Use SYM tokens for identifiers
                PhelTokenSets.LINE_COMMENT,  // Use LINE_COMMENT for comments
                TokenSet.create(PhelTypes.STRING, PhelTypes.NUMBER, PhelTypes.BOOL, PhelTypes.NIL));  // Literals
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof PsiNamedElement;
    }

    @Override
    @Nullable
    public String getHelpId(@NotNull PsiElement psiElement) {
        return null; // No specific help ID needed
    }

    /**
     * Returns the type description for an element (e.g., "variable", "function", "macro").
     * This is what appears in the "Choose Declaration" modal.
     */
    @Override
    @NotNull
    public String getType(@NotNull PsiElement element) {
        if (element instanceof PhelSymbol) {
            PhelSymbol symbol = (PhelSymbol) element;
            
            // Check if this is a definition and determine its type
            if (PhelPsiUtil.isDefinition(symbol)) {
                String definingKeyword = getDefiningKeyword(symbol);
                
                switch (definingKeyword) {
                    case "def":
                    case "def-":
                        return "variable";
                    case "defn":
                    case "defn-":
                        return "function";
                    case "defmacro":
                    case "defmacro-":
                        return "macro";
                    case "defstruct":
                        return "struct";
                    case "definterface":
                        return "interface";
                    case "defexception":
                        return "exception";
                    case "declare":
                        return "declaration";
                    default:
                        return "symbol";
                }
            }
            
            // For usages, try to determine type by looking at the definition
            return "symbol";
        }
        
        return "element";
    }

    /**
     * Get the defining keyword (def, defn, defmacro, etc.) for a symbol definition.
     */
    @Nullable
    private String getDefiningKeyword(@NotNull PhelSymbol symbol) {
        PhelList containingList = PsiTreeUtil.getParentOfType(symbol, PhelList.class);
        if (containingList != null) {
            PhelForm firstForm = PsiTreeUtil.findChildOfType(containingList, PhelForm.class);
            if (firstForm != null) {
                PhelSymbol firstSymbol = PsiTreeUtil.findChildOfType(firstForm, PhelSymbol.class);
                if (firstSymbol != null) {
                    return firstSymbol.getText();
                }
            }
        }
        return null;
    }

    @Override
    @NotNull
    public String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof PhelSymbol) {
            PhelSymbol symbol = (PhelSymbol) element;
            String name = PhelPsiUtil.getName(symbol);
            if (name != null) {
                // Add type information to the descriptive name
                String type = getType(element);
                if (!"symbol".equalsIgnoreCase(type)) {
                    return name + " [" + type + "]";
                }
                return name;
            }
            return "<unnamed>";
        }
        return element.toString();
    }

    @Override
    @NotNull
    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        if (element instanceof PhelSymbol) {
            PhelSymbol symbol = (PhelSymbol) element;
            String name = PhelPsiUtil.getName(symbol);
            if (name != null) {
                // Add context information for better identification
                String context = getElementContext(symbol);
                String location = getLocationInfo(symbol);
                
                StringBuilder result = new StringBuilder();
                result.append(useFullName ? getFullyQualifiedName(element, name) : name);
                
                if (context != null && !context.isEmpty()) {
                    result.append(" ").append(context);
                }
                
                if (location != null && !location.isEmpty()) {
                    result.append(" ").append(location);
                }
                
                return result.toString();
            }
        }
        return element.toString();
    }

    /**
     * Get contextual information about where/how the element is used.
     */
    @Nullable
    private String getElementContext(@NotNull PhelSymbol symbol) {
        // Get the containing list to understand the context
        PhelList containingList = PsiTreeUtil.getParentOfType(symbol, PhelList.class);
        if (containingList != null) {
            String listText = containingList.getText();
            if (listText != null && listText.length() > 50) {
                // Truncate long expressions and show the beginning
                return "in (" + listText.substring(1, Math.min(47, listText.length() - 1)) + "...)";
            } else if (listText != null) {
                return "in " + listText;
            }
        }
        return null;
    }

    /**
     * Get location information (file name and line number).
     */
    @NotNull
    private String getLocationInfo(@NotNull PhelSymbol symbol) {
        StringBuilder location = new StringBuilder();
        
        // Add file name
        PsiFile containingFile = symbol.getContainingFile();
        if (containingFile != null) {
            String fileName = containingFile.getName();
            location.append("(").append(fileName);
            
            // Add line number
            int lineNumber = getLineNumber(symbol);
            if (lineNumber > 0) {
                location.append(":").append(lineNumber);
            }
            
            location.append(")");
        }
        
        return location.toString();
    }

    /**
     * Calculate the line number for a PSI element.
     */
    private int getLineNumber(@NotNull PsiElement element) {
        PsiFile containingFile = element.getContainingFile();
        if (containingFile != null) {
            String fileText = containingFile.getText();
            if (fileText != null) {
                int offset = element.getTextOffset();
                int lineNumber = 1;
                for (int i = 0; i < offset && i < fileText.length(); i++) {
                    if (fileText.charAt(i) == '\n') {
                        lineNumber++;
                    }
                }
                return lineNumber;
            }
        }
        return 0;
    }

    /**
     * Get the fully qualified name including namespace if applicable.
     */
    @NotNull
    private String getFullyQualifiedName(@NotNull PsiElement element, @NotNull String name) {
        if (element instanceof PhelSymbol) {
            String qualifier = PhelPsiUtil.getQualifier((PhelSymbol) element);
            if (qualifier != null) {
                return qualifier + "/" + name;
            }
        }
        return name;
    }
}

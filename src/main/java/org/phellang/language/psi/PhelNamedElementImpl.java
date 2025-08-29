package org.phellang.language.psi;

import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.phellang.language.psi.impl.PhelSFormImpl;

/**
 * Base implementation for named Phel elements that can be referenced and renamed.
 * This class implements PsiNameIdentifierOwner to enable proper Go to Definition functionality.
 */
public abstract class PhelNamedElementImpl extends PhelSFormImpl implements PsiNameIdentifierOwner {

    public PhelNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    @Nullable
    public String getName() {
        if (this instanceof PhelSymbol) {
            return PhelPsiUtil.getName((PhelSymbol) this);
        }
        return null;
    }

    @Override
    public PsiElement setName(@NonNls @NotNull String name) throws IncorrectOperationException {
        // For now, return this unchanged. In a full implementation, 
        // this would create a new PSI element with the new name.
        return this;
    }

    @Override
    @Nullable
    public PsiElement getNameIdentifier() {
        // For symbols, the entire element is the name identifier
        return this;
    }

    @Override
    @Nullable  
    public PsiReference getReference() {
        // Provide references for both definitions and usages
        if (this instanceof PhelSymbol) {
            PhelSymbol symbol = (PhelSymbol) this;
            boolean isDefinition = PhelPsiUtil.isDefinition(symbol);
            
            if (isDefinition) {
                return new PhelReference(symbol, true);  // findUsages = true
            } else {
                return new PhelReference(symbol, false); // findUsages = false
            }
        }
        return null;
    }

    @Override
    public int getTextOffset() {
        return PhelPsiUtil.getNameTextOffset((PhelSymbol) this);
    }

    /**
     * Get the qualified name including namespace.
     */
    @Nullable
    public String getQualifiedName() {
        return PhelPsiUtil.getQualifiedName((PhelSymbol) this);
    }

    /**
     * Get the namespace qualifier part.
     */
    @Nullable
    public String getQualifier() {
        return PhelPsiUtil.getQualifier((PhelSymbol) this);
    }

    /**
     * Check if this symbol is a definition.
     */
    public boolean isDefinition() {
        return PhelPsiUtil.isDefinition((PhelSymbol) this);
    }

    /**
     * Provide custom presentation for navigation dialogs.
     */
    @Override
    @Nullable
    public ItemPresentation getPresentation() {
        if (this instanceof PhelSymbol) {
            return new PhelItemPresentation((PhelSymbol) this);
        }
        return super.getPresentation();
    }
}

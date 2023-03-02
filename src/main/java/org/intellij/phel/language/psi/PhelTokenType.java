package org.intellij.phel.language.psi;

import com.intellij.psi.tree.IElementType;
import org.intellij.phel.PhelLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class PhelTokenType extends IElementType {
    public PhelTokenType(@NonNls @NotNull String debugName) {
        super(debugName, PhelLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "PhelTokenType." + super.toString();
    }
}


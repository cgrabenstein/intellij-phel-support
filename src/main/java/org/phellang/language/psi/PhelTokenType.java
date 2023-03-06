package org.phellang.language.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.phellang.PhelLanguage;

public class PhelTokenType extends IElementType {
    public PhelTokenType(@NonNls @NotNull String debugName) {
        super(debugName, PhelLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "PhelTokenType." + super.toString();
    }
}

package org.phellang.language.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.phellang.PhelLanguage;

public class PhelElementType extends IElementType {
    public PhelElementType(@NotNull @NonNls String debugName) {
        super(debugName, PhelLanguage.INSTANCE);
    }
}

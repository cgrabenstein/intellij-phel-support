package org.intellij.phel.language.psi;

import com.intellij.psi.tree.IElementType;
import org.intellij.phel.PhelLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class PhelElementType extends IElementType {
    public PhelElementType(@NotNull @NonNls String debugName) {
        super(debugName, PhelLanguage.INSTANCE);
    }
}

package org.phellang.language.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import org.phellang.PhelFileType;
import org.phellang.PhelLanguage;

public class PhelFile extends PsiFileBase {
    public PhelFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, PhelLanguage.INSTANCE);
    }

    @Override
    public @NotNull FileType getFileType() {
        return PhelFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Phel File";
    }
}

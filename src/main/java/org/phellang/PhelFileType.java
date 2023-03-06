package org.phellang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PhelFileType extends LanguageFileType {

    public static final PhelFileType INSTANCE = new PhelFileType();

    private PhelFileType() {
        super(PhelLanguage.INSTANCE);
    }

    @Override
    public @NotNull String getName() {
        return "Phel file";
    }

    @Override
    public @NotNull String getDescription() {
        return "Phel language file";
    }

    @Override
    public @NotNull String getDefaultExtension() {
        return "phel";
    }

    @Override
    public @Nullable Icon getIcon() {
        return PhelIcons.FILE;
    }
}

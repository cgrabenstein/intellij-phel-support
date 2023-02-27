package org.intellij.phel;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class PhelFileType extends LanguageFileType {
    public static final PhelFileType INSTANCE = new PhelFileType();

    @Override
    public @NotNull String getName() {
        return "Phel File";
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
    public Icon getIcon() {
        return PhelIcons.FILE;
    }

    private PhelFileType() {
        super(PhelLanguage.INSTANCE);
    }
}

package org.intellij.phel;

import com.intellij.lang.Language;

public class PhelLanguage extends Language {
    public static final PhelLanguage INSTANCE = new PhelLanguage();

    protected PhelLanguage() {
        super("phel");
    }
}

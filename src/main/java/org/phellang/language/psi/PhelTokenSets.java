package org.phellang.language.psi;

import com.intellij.psi.tree.TokenSet;

public interface PhelTokenSets {
    TokenSet COMMENTS = TokenSet.create(PhelTypes.COMMENT);
}

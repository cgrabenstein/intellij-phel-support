// This is a generated file. Not intended for manual editing.
package org.intellij.phel.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.intellij.phel.language.psi.PhelTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.intellij.phel.language.psi.*;

public class PhelReaderMacroImpl extends ASTWrapperPsiElement implements PhelReaderMacro {

  public PhelReaderMacroImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PhelVisitor visitor) {
    visitor.visitReaderMacro(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PhelVisitor) accept((PhelVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PhelSymbol getSymbol() {
    return findChildByClass(PhelSymbol.class);
  }

}

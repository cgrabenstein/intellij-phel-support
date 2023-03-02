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

public class PhelFormImpl extends ASTWrapperPsiElement implements PhelForm {

  public PhelFormImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PhelVisitor visitor) {
    visitor.visitForm(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PhelVisitor) accept((PhelVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<PhelMetadata> getMetas() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PhelMetadata.class);
  }

  @Override
  @NotNull
  public List<PhelReaderMacro> getReaderMacros() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, PhelReaderMacro.class);
  }

}

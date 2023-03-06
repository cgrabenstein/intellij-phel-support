// This is a generated file. Not intended for manual editing.
package org.phellang.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.phellang.language.psi.PhelTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.phellang.language.psi.*;

public class PhelSymbol_Impl extends ASTWrapperPsiElement implements PhelSymbol_ {

  public PhelSymbol_Impl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull PhelVisitor visitor) {
    visitor.visitSymbol_(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof PhelVisitor) accept((PhelVisitor)visitor);
    else super.accept(visitor);
  }

}

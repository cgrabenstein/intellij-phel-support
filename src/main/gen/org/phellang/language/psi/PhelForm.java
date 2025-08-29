// This is a generated file. Not intended for manual editing.
package org.phellang.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface PhelForm extends PsiElement {

  @Nullable
  PhelSymbol getSymbol();

  @NotNull
  List<PhelMetadata> getMetas();

  @NotNull
  List<PhelReaderMacro> getReaderMacros();

  //WARNING: toString(...) is skipped
  //matching toString(PhelForm, ...)
  //methods are not found in null

}

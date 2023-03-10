package org.phellang.language;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.phellang.language.parser.PhelParser;
import org.phellang.language.psi.PhelFile;
import org.phellang.language.psi.PhelTokenSets;
import org.phellang.language.psi.PhelTypes;
import org.jetbrains.annotations.NotNull;
import org.phellang.PhelLanguage;

class PhelParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE = new IFileElementType(PhelLanguage.INSTANCE);

    @Override
    public @NotNull Lexer createLexer(Project project) {
        return new PhelLexerAdapter();
    }

    @Override
    public @NotNull TokenSet getCommentTokens() {
        return PhelTokenSets.COMMENTED;
    }

    @Override
    public @NotNull TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @Override
    public @NotNull PsiParser createParser(Project project) {
        return new PhelParser();
    }

    @Override
    public @NotNull IFileElementType getFileNodeType() {
        return FILE;
    }

    @Override
    public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
        return new PhelFile(viewProvider);
    }

    @Override
    public @NotNull PsiElement createElement(ASTNode node) {
        return PhelTypes.Factory.createElement(node);
    }
}

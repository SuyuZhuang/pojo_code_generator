package com.asu.plugins.pojo_code_generator;

import com.asu.plugins.pojo_code_generator.dialog.PsiJavaFileDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

public class PojoFromDml extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        PsiDirectory directory;
        if (psiElement instanceof PsiDirectory) {
            directory = (PsiDirectory) psiElement;
        } else {
            assert psiElement != null;
            directory = psiElement.getContainingFile().getContainingDirectory();
        }
        new PsiJavaFileDialog(directory).showAndGet();
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(e.getProject() != null);
    }
}

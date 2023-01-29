package com.asu.plugins.pojo_code_generator.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiJavaFile;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.swing.*;

public class PsiJavaFileDialog extends DialogWrapper {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    private final boolean createState;
    private Project myProject;
    private PsiDirectory myPsiDirectory;
    private PsiJavaFile myPsiJavaFile;


    public PsiJavaFileDialog(@Nonnull PsiDirectory directory) {
        super(true);
        createState = true;
        setTitle("Generate Pojo");
        myPsiDirectory = directory;
        myProject = myPsiDirectory.getProject();
        init();
    }

    @Override
    protected void doOKAction() {
        // 关闭窗口
        super.doOKAction();
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return null;
    }
}

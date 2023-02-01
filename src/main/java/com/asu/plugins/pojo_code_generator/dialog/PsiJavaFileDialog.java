package com.asu.plugins.pojo_code_generator.dialog;

import com.asu.plugins.pojo_code_generator.common.MyNotifier;
import com.asu.plugins.pojo_code_generator.manager.PsiClassGenerator;
import com.asu.plugins.pojo_code_generator.model.ParsedJavaClass;
import com.asu.plugins.pojo_code_generator.utils.DruidSqlParserUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.psi.PsiDirectory;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Objects;

public class PsiJavaFileDialog extends DialogWrapper {

    private JPanel contentPane;
    private JTextArea text;
    private JComboBox comboBox;

    private final boolean createState;
    private Project myProject;
    private PsiDirectory myPsiDirectory;


    public PsiJavaFileDialog(@Nonnull PsiDirectory directory) {
        super(true);
        createState = true;
        setTitle("Generate Pojo");
        setSize(800, 500);
        myPsiDirectory = directory;
        myProject = myPsiDirectory.getProject();
        init();
    }

    @Override
    protected void doOKAction() {
        try {
            createJavaFile();
        } catch (Exception e) {
            MyNotifier.notifyError(myProject, e.getMessage());
        }
        close(OK_EXIT_CODE);
    }

    private void createJavaFile() {
        String pojoType = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
        String createStatement = text.getText();
        // 解析createStatement
        ParsedJavaClass parsedJavaClass = DruidSqlParserUtil.parse(createStatement);
        // 检查和创建class
        Objects.requireNonNull(PsiClassGenerator.getInstance(pojoType))
                .createJavaClassFromDml(parsedJavaClass, myProject, myPsiDirectory);
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return contentPane;
    }
}

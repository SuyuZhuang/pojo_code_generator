package com.asu.plugins.pojo_code_generator.manager;

import com.asu.plugins.pojo_code_generator.common.MyNotifier;
import com.asu.plugins.pojo_code_generator.model.ParsedColumn;
import com.asu.plugins.pojo_code_generator.model.ParsedJavaClass;
import com.asu.plugins.pojo_code_generator.model.SqlColumnTypeEnum;
import com.asu.plugins.pojo_code_generator.utils.StrUtils;
import com.intellij.codeInsight.actions.OptimizeImportsProcessor;
import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.codeInsight.generation.GenerateMembersUtil;
import com.intellij.codeInsight.generation.PsiGenerationInfo;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Objects;

public abstract class AbstractPojoClassGenerator implements PsiClassGenerator{

    public void createJavaClassFromDml(ParsedJavaClass parsedJavaClass, Project myProject, PsiDirectory psiDirectory) {
        String className = parseClassName(parsedJavaClass.getTableName()) + "DO";

        // 对PSI执行非读操作（创建、编辑、删除），要用WriteCommandAction
        WriteCommandAction.runWriteCommandAction(myProject, () -> {
            try {
                JavaDirectoryService.getInstance().checkCreateClass(psiDirectory, className);
                PsiClass psiClass = doCreateJavaClassFromDml(className, parsedJavaClass, myProject, psiDirectory);
                psiClass.navigate(true);
            } catch (Exception e) {
                MyNotifier.notifyError(myProject, e.getMessage());
                return;
            }
        });


    }

    protected abstract PsiClass doCreateJavaClassFromDml(String className, ParsedJavaClass parsedJavaClass, Project myProject, PsiDirectory psiDirectory);

    protected String parseClassName(String tableName) {
        tableName = StrUtils.toCamelCase(tableName);
        tableName = StringUtils.capitalize(tableName);
        return tableName;
    }

    protected void generateDataAnnotation(@Nonnull PsiClass psiClass, @Nonnull Project project) {
        PsiAnnotation annotation = psiClass.getAnnotation(LOMBOK_DATA_ANN);
        if (annotation == null) {
            annotation = Objects.requireNonNull(psiClass.getModifierList()).addAnnotation(LOMBOK_DATA_ANN);
        }
        // 自动import
        JavaCodeStyleManager.getInstance(project).shortenClassReferences(annotation);
    }


    protected void generateFields(ParsedJavaClass parsedJavaClass, PsiClass psiClass, Project project) {
        if (parsedJavaClass == null || parsedJavaClass.getParsedColumnList() == null) {
            return;
        }

        PsiElementFactory factory = PsiElementFactory.getInstance(project);
        for (ParsedColumn parsedColumn : parsedJavaClass.getParsedColumnList()) {
            String javaType = SqlColumnTypeEnum.valueOfSqlType(parsedColumn.getColumnType()).getJavaType();
            PsiType psiType = factory.createTypeFromText(javaType, null);
            PsiField psiField = factory.createField(parseFieldName(parsedColumn.getName()), psiType);
            // 注释
            if (StringUtils.isNotBlank(parsedColumn.getComment())) {
                String commentStr = "/**\n" +
                        "     * " + parsedColumn.getComment() + "\n" +
                        "     */";
                PsiComment comment = factory.createCommentFromText(commentStr, psiField);
                psiField.addBefore(comment, psiField.getFirstChild());
            }

            PsiGenerationInfo<PsiField> psi = new PsiGenerationInfo<>(psiField);

            // 指定位置插入
            GenerateMembersUtil.insertMembersBeforeAnchor(psiClass, psiClass.getLastChild(), Collections.singletonList(psi));
            new ReformatCodeProcessor(psiClass.getContainingFile(), false).run();
        }
    }

    protected String parseFieldName(String columnName) {
        columnName = StringUtils.uncapitalize(columnName.trim());
        columnName = StrUtils.toCamelCase(columnName);
        return columnName;
    }
}

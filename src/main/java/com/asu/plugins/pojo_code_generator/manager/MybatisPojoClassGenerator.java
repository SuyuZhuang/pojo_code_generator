package com.asu.plugins.pojo_code_generator.manager;

import com.asu.plugins.pojo_code_generator.model.ParsedColumn;
import com.asu.plugins.pojo_code_generator.model.ParsedJavaClass;
import com.asu.plugins.pojo_code_generator.model.SqlColumnTypeEnum;
import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.codeInsight.generation.GenerateMembersUtil;
import com.intellij.codeInsight.generation.PsiGenerationInfo;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Objects;

@Service
public final class MybatisPojoClassGenerator extends AbstractPojoClassGenerator{
    @Override
    protected PsiClass doCreateJavaClassFromDml(String className, ParsedJavaClass parsedJavaClass, Project myProject, PsiDirectory psiDirectory) {
        PsiClass psiClass = JavaDirectoryService.getInstance().createClass(psiDirectory, className);

        // 添加@Data注解
        generateDataAnnotation(psiClass, myProject);
        // 添加@Accessors(chain = true)注解
        generateAccessorsAnnotation(psiClass, myProject);
        // 添加@TableName
        generateTableAnnotation(parsedJavaClass, psiClass, myProject);

        // 添加字段
        generateFields(parsedJavaClass, psiClass, myProject);

        // 添加字段
        return psiClass;
    }

    protected void generateTableAnnotation(ParsedJavaClass parsedJavaClass, @Nonnull PsiClass psiClass, @Nonnull Project project) {
        PsiAnnotation annotation = psiClass.getAnnotation("com.baomidou.mybatisplus.annotation.TableName");
        if (annotation == null) {
            annotation = Objects.requireNonNull(psiClass.getModifierList()).addAnnotation("com.baomidou.mybatisplus.annotation.TableName");
        }
        annotation = (PsiAnnotation) annotation.replace(PsiElementFactory.getInstance(project).createAnnotationFromText("@com.baomidou.mybatisplus.annotation.TableName(\"" + parsedJavaClass.getTableName() + "\")", psiClass));
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

            // 添加@TableId
            if (parsedColumn.isPrimary() != null && parsedColumn.isPrimary()) {
                String fieldName = parseFieldName(parsedColumn.getName());
                generateTableIdAnnotation(psiField, fieldName, project);
            }

            PsiGenerationInfo<PsiField> psi = new PsiGenerationInfo<>(psiField);

            // 指定位置插入
            GenerateMembersUtil.insertMembersBeforeAnchor(psiClass, psiClass.getLastChild(), Collections.singletonList(psi));
            new ReformatCodeProcessor(psiClass.getContainingFile(), false).run();
        }
    }

    private void generateTableIdAnnotation(PsiField psiField, String fieldName, Project project) {
        PsiAnnotation annotation = psiField.getAnnotation("com.baomidou.mybatisplus.annotation.TableId");
        if (annotation == null) {
            annotation = Objects.requireNonNull(psiField.getModifierList()).addAnnotation("com.baomidou.mybatisplus.annotation.TableId");
        }
        annotation = (PsiAnnotation) annotation.replace(PsiElementFactory.getInstance(project).createAnnotationFromText("@com.baomidou.mybatisplus.annotation.TableId(value=\"" + fieldName + "\", type=com.baomidou.mybatisplus.annotation.IdType.AUTO)", psiField));
        // 自动import
        JavaCodeStyleManager.getInstance(project).shortenClassReferences(annotation);
    }
}

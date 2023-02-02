package com.asu.plugins.pojo_code_generator.manager;

import com.asu.plugins.pojo_code_generator.model.ParsedJavaClass;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;

@Service
public final class SimplePojoClassGenerator extends AbstractPojoClassGenerator{
    @Override
    protected PsiClass doCreateJavaClassFromDml(String className, ParsedJavaClass parsedJavaClass, Project myProject, PsiDirectory psiDirectory) {
        PsiClass psiClass = JavaDirectoryService.getInstance().createClass(psiDirectory, className);

        // 添加@Data注解
        generateDataAnnotation(psiClass, myProject);

        // 添加字段
        generateFields(parsedJavaClass, psiClass, myProject);

        // 添加字段
        return psiClass;
    }
}

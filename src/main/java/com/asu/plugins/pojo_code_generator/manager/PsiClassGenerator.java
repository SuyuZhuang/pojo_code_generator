package com.asu.plugins.pojo_code_generator.manager;

import com.asu.plugins.pojo_code_generator.model.ParsedJavaClass;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;

public interface PsiClassGenerator {

    String LOMBOK_DATA_ANN = "lombok.Data";
    String LOMBOK_ACCESSORS_ANN = "lombok.experimental.Accessors";

    /**
     * 创建pojo文件
     *
     * @param parsedJavaClass 字段信息
     */
    void createJavaClassFromDml(ParsedJavaClass parsedJavaClass, Project myProject, PsiDirectory psiDirectory);


    static PsiClassGenerator getInstance(String pojoType) {
        if ("Simple POJO".equals(pojoType)) {
            return ApplicationManager.getApplication().getService(SimplePojoClassGenerator.class);
        } else {
            return ApplicationManager.getApplication().getService(SimplePojoClassGenerator.class);
        }
    }
}

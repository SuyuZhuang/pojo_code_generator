package com.asu.plugins.pojo_code_generator.common;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;

import javax.annotation.Nullable;

public class MyNotifier {

    public static void notifyError(@Nullable Project project, String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("generator_group")
                .createNotification(content, NotificationType.ERROR)
                .notify(project);
    }

    public static void notifyInfo(@Nullable Project project, String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("generator_group")
                .createNotification(content, NotificationType.INFORMATION)
                .notify(project);
    }
}

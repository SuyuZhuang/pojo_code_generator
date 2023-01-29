package com.asu.plugins.pojo_code_generator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.pom.Navigatable;
import org.jetbrains.annotations.NotNull;

public class PopupDemo extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        // Using the event, create and show a dialog
        Project currentProject = event.getProject();
        String dlgTitle = "Aha_Title";
        Messages.showMessageDialog(currentProject, "This is MSG", dlgTitle, Messages.getInformationIcon());
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(true);
    }
}

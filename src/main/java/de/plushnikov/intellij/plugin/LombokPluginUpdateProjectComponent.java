package de.plushnikov.intellij.plugin;

import com.intellij.notification.*;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * Shows update notification
 */
public class LombokPluginUpdateProjectComponent implements ProjectComponent {
  private LombokPluginApplicationComponent application;
  private final Project myProject;

  /**
   * Constructor.
   *
   * @param project current project
   */
  public LombokPluginUpdateProjectComponent(@NotNull Project project) {
    myProject = project;
  }

  @Override
  public void initComponent() {
    application = LombokPluginApplicationComponent.getInstance();
  }

  @Override
  public void disposeComponent() {
  }

  @NotNull
  @Override
  public String getComponentName() {
    return "LombokPluginUpdateProjectComponent";
  }

  @Override
  public void projectOpened() {
    if (application.isUpdated() && !application.isUpdateNotificationShown()) {
      application.setUpdateNotificationShown(true);

      NotificationGroup group = new NotificationGroup(Version.PLUGIN_NAME, NotificationDisplayType.STICKY_BALLOON, true);
      Notification notification = group.createNotification(
        LombokBundle.message("daemon.donate.title", Version.PLUGIN_VERSION),
        LombokBundle.message("daemon.donate.content"),
        NotificationType.INFORMATION,
        new NotificationListener.UrlOpeningListener(false)
      );

      Notifications.Bus.notify(notification, myProject);
    }
  }

  @Override
  public void projectClosed() {

  }
}

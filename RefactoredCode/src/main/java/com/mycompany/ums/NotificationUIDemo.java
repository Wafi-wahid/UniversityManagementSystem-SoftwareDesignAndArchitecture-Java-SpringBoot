package com.mycompany.ums;

import java.util.List;

/**
 * Interface segregation: Separate UI interface for notification display.
 */
interface NotificationUI {
    void showHeader();
    void displayMessages(List<String> messages);
}

/**
 * Concrete implementation of NotificationUI for console output.
 * Open for extension: New UI types can implement NotificationUI.
 */
class ConsoleNotificationUI implements NotificationUI {
    private static final String NOTIFICATIONS_HEADER = "\nYour Notifications";

    @Override
    public void showHeader() {
        System.out.println(NOTIFICATIONS_HEADER);
    }

    @Override
    public void displayMessages(List<String> messages) {
        messages.forEach(System.out::println);
    }
}

/**
 * Demo main class to show usage.
 * Demonstrates DIP: depends on abstraction NotificationUI, not concrete.
 */
public class NotificationUIDemo {
    private final NotificationUI ui;

    public NotificationUIDemo(NotificationUI ui) {
        if (ui == null) throw new IllegalArgumentException("UI implementation cannot be null");
        this.ui = ui;
    }

    public void showNotifications(List<String> messages) {
        ui.showHeader();
        ui.displayMessages(messages);
    }

    public static void main(String[] args) {
        NotificationUI ui = new ConsoleNotificationUI();
        NotificationUIDemo demo = new NotificationUIDemo(ui);

        demo.showNotifications(List.of("Assignment due tomorrow", "New message from instructor"));
    }
}

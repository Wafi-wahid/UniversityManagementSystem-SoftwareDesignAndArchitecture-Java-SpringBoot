package com.mycompany.ums;

import java.util.Collections;
import java.util.List;
import java.util.Map;

// ISP: Validator interface segregated for notifications
interface Validator<T> {
    void validate(T input);
}

// Input holder for notification validation
class NotificationInput {
    final String studentId;
    final Map<String, List<String>> notifications;

    NotificationInput(String studentId, Map<String, List<String>> notifications) {
        this.studentId = studentId;
        this.notifications = notifications;
    }
}

// OCP & DIP: Concrete notification validator implementing Validator interface
class NotificationValidator implements Validator<NotificationInput> {
    @Override
    public void validate(NotificationInput input) {
        if (input.studentId == null || input.studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (input.notifications == null) {
            throw new IllegalArgumentException("Notifications map cannot be null");
        }
    }
}

// Abstraction for notification fetching (ISP & DIP)
interface NotificationService {
    List<String> getNotifications(String studentId);
}

// OCP & LSP: Concrete implementation that fetches notifications from a map
class MapNotificationService implements NotificationService {
    private final Map<String, List<String>> notifications;

    MapNotificationService(Map<String, List<String>> notifications) {
        if (notifications == null) {
            throw new IllegalArgumentException("Notifications map cannot be null");
        }
        this.notifications = notifications;
    }

    @Override
    public List<String> getNotifications(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        return notifications.getOrDefault(studentId, Collections.emptyList());
    }
}

// UI abstraction (ISP)
interface NotificationUI {
    void showHeader();
    void displayMessages(List<String> messages);
}

// Concrete UI implementation
class ConsoleNotificationUI implements NotificationUI {
    @Override
    public void showHeader() {
        System.out.println("\nNotifications");
        System.out.println("-------------");
    }

    @Override
    public void displayMessages(List<String> messages) {
        if (messages.isEmpty()) {
            System.out.println("No notifications available.");
        } else {
            messages.forEach(System.out::println);
        }
    }
}

// High-level Notification class depends on abstractions (DIP)
public final class Notification {
    private final Validator<NotificationInput> validator;
    private final NotificationService service;
    private final NotificationUI ui;

    public Notification(Validator<NotificationInput> validator, NotificationService service, NotificationUI ui) {
        if (validator == null || service == null || ui == null) {
            throw new IllegalArgumentException("Dependencies cannot be null");
        }
        this.validator = validator;
        this.service = service;
        this.ui = ui;
    }

    public void view(String studentId, Map<String, List<String>> notificationsMap) {
        NotificationInput input = new NotificationInput(studentId, notificationsMap);
        validator.validate(input);
        ui.showHeader();
        List<String> messages = service.getNotifications(studentId);
        ui.displayMessages(messages);
    }

    // Example main method to demonstrate usage
    public static void main(String[] args) {
        Map<String, List<String>> data = Map.of(
            "S001", List.of("Assignment 1 due tomorrow", "Lab canceled"),
            "S002", List.of("Project deadline extended")
        );

        Validator<NotificationInput> validator = new NotificationValidator();
        NotificationService service = new MapNotificationService(data);
        NotificationUI ui = new ConsoleNotificationUI();

        Notification notification = new Notification(validator, service, ui);
        notification.view("S001", data);
        notification.view("S003", data);  // No notifications case
    }
}

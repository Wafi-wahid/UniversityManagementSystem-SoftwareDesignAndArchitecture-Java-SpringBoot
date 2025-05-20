package com.mycompany.ums;

import java.util.List;
import java.util.Map;

// Interface Segregation + Dependency Inversion
interface Validator<T> {
    void validate(T input);
}

// Concrete Validator for Notification Input
class NotificationValidator implements Validator<NotificationInput> {
    @Override
    public void validate(NotificationInput input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        if (input.studentId == null || input.studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (input.notifications == null) {
            throw new IllegalArgumentException("Notifications map cannot be null");
        }
    }
}

// DTO for input data to validator and service
class NotificationInput {
    public final String studentId;
    public final Map<String, List<String>> notifications;

    public NotificationInput(String studentId, Map<String, List<String>> notifications) {
        this.studentId = studentId;
        this.notifications = notifications;
    }
}

// Interface for Notification Service (ISP + DIP)
interface NotificationService {
    List<String> getNotifications(NotificationInput input);
}

// Concrete Notification Service implementing abstraction
class NotificationServiceImpl implements NotificationService {
    private static final String DEFAULT_NOTIFICATION = "No new notifications.";

    @Override
    public List<String> getNotifications(NotificationInput input) {
        List<String> messages = input.notifications.getOrDefault(input.studentId, List.of());
        return messages.isEmpty() ? List.of(DEFAULT_NOTIFICATION) : messages;
    }
}

// Interface for Notification UI (ISP)
interface NotificationUI {
    void showHeader();
    void displayMessages(List<String> messages);
}

// Concrete UI Implementation
class ConsoleNotificationUI implements NotificationUI {
    private static final String HEADER = "\nYour Notifications";

    @Override
    public void showHeader() {
        System.out.println(HEADER);
    }

    @Override
    public void displayMessages(List<String> messages) {
        messages.forEach(System.out::println);
    }
}

// High-level Notification Controller depending on abstractions (DIP)
public class NotificationController {
    private final Validator<NotificationInput> validator;
    private final NotificationService service;
    private final NotificationUI ui;

    public NotificationController(Validator<NotificationInput> validator,
                                  NotificationService service,
                                  NotificationUI ui) {
        this.validator = validator;
        this.service = service;
        this.ui = ui;
    }

    public void viewNotifications(String studentId, Map<String, List<String>> notifications) {
        NotificationInput input = new NotificationInput(studentId, notifications);
        validator.validate(input);
        ui.showHeader();
        List<String> messages = service.getNotifications(input);
        ui.displayMessages(messages);
    }

    // Demo Main
    public static void main(String[] args) {
        Map<String, List<String>> sampleData = Map.of(
            "S001", List.of("Exam on Monday", "Assignment due Friday"),
            "S002", List.of("Project deadline extended")
        );

        NotificationController controller = new NotificationController(
            new NotificationValidator(),
            new NotificationServiceImpl(),
            new ConsoleNotificationUI()
        );

        controller.viewNotifications("S001", sampleData);
        controller.viewNotifications("S003", sampleData);  // no notifications, should show default message
    }
}

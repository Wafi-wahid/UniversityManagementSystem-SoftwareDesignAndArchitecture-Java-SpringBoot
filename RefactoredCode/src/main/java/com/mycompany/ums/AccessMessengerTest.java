package com.mycompany.ums;

// --- Student class (simple data class) ---
class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// --- ISP: Messaging interface does one job only ---
interface AccessMessageStrategy {
    void printMessage(Student student, boolean granted);
}

// --- OCP + LSP: Extensible and substitutable implementation ---
class DefaultAccessMessenger implements AccessMessageStrategy {
    private static final String ACCESS_GRANTED_FORMAT = "%s has been granted online access.";
    private static final String ACCESS_DENIED_MESSAGE = "Access denied. Clear dues first.";

    @Override
    public void printMessage(Student student, boolean granted) {
        if (granted) {
            System.out.println(String.format(ACCESS_GRANTED_FORMAT, student.getName()));
        } else {
            System.out.println(ACCESS_DENIED_MESSAGE);
        }
    }
}

// --- OCP + LSP: Another valid and swappable implementation ---
class FriendlyAccessMessenger implements AccessMessageStrategy {
    @Override
    public void printMessage(Student student, boolean granted) {
        if (granted) {
            System.out.println("Welcome " + student.getName() + "! You now have access.");
        } else {
            System.out.println("Oops! Please clear your dues to get access.");
        }
    }
}

// --- DIP: Depends on abstraction (AccessMessageStrategy), not implementation ---
class AccessNotifier {
    private final AccessMessageStrategy messenger;

    public AccessNotifier(AccessMessageStrategy messenger) {
        this.messenger = messenger;
    }

    public void notify(Student student, boolean granted) {
        messenger.printMessage(student, granted);
    }
}

// --- Test class ---
public class AccessMessengerTest {
    public static void main(String[] args) {
        Student student1 = new Student("Ali");
        Student student2 = new Student("Sara");

        // Use default messenger
        AccessNotifier defaultNotifier = new AccessNotifier(new DefaultAccessMessenger());
        System.out.println("Using DefaultAccessMessenger:");
        defaultNotifier.notify(student1, true);
        defaultNotifier.notify(student2, false);

        // Use friendly messenger
        AccessNotifier friendlyNotifier = new AccessNotifier(new FriendlyAccessMessenger());
        System.out.println("\nUsing FriendlyAccessMessenger:");
        friendlyNotifier.notify(student1, true);
        friendlyNotifier.notify(student2, false);
    }
}

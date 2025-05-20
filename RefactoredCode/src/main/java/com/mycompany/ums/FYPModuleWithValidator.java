package com.mycompany.ums;

import java.util.*;

public class FYPModuleWithValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String studentId = "S101";

        Map<String, Integer> completedCredits = new HashMap<>();
        Map<String, Boolean> fypStatus = new HashMap<>();
        Map<String, List<String>> enrolledCourses = new HashMap<>();
        Map<String, List<String>> prerequisites = new HashMap<>();

        completedCredits.put(studentId, 135);
        fypStatus.put(studentId, false);
        enrolledCourses.put(studentId, List.of("CS301", "CS302"));
        prerequisites.put(studentId, List.of("CS301", "CS302"));

        // Validate inputs first
        FYPValidator.validate(studentId, completedCredits, fypStatus, enrolledCourses, prerequisites, scanner);

        // Eligibility rules list for OCP
        List<EligibilityRule> rules = List.of(
            new SufficientCreditsRule(),
            new NotAlreadyRegisteredRule(),
            new CompletedPrerequisitesRule()
        );

        FYPInput input = new ConsoleFYPInput(scanner);
        FYPRegistrationService registrationService = new SimpleFYPRegistrationService();

        FYPManager manager = new FYPManager(input, rules, registrationService);
        manager.registerForFYP(studentId, completedCredits, fypStatus, enrolledCourses, prerequisites);
    }
}

// =========================
// Validator Class
// =========================
final class FYPValidator {
    public static void validate(String studentId,
                                Map<String, Integer> completedCredits,
                                Map<String, Boolean> fypStatus,
                                Map<String, List<String>> enrolledCourses,
                                Map<String, List<String>> prerequisites,
                                Scanner scanner) {
        if (studentId == null || studentId.isBlank())
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        if (completedCredits == null || fypStatus == null || enrolledCourses == null || prerequisites == null)
            throw new IllegalArgumentException("Map parameters cannot be null");
        if (scanner == null)
            throw new IllegalArgumentException("Scanner cannot be null");
    }
}

// =============================
// High-Level Module
// =============================
class FYPManager {
    private static final String HEADER = "\n--- FYP Registration ---";
    private static final String SUCCESS_MSG = "FYP Registration Successful!";

    private final FYPInput input;
    private final List<EligibilityRule> rules;
    private final FYPRegistrationService registrationService;

    public FYPManager(FYPInput input, List<EligibilityRule> rules, FYPRegistrationService registrationService) {
        this.input = input;
        this.rules = rules;
        this.registrationService = registrationService;
    }

    public void registerForFYP(String studentId,
                                Map<String, Integer> completedCredits,
                                Map<String, Boolean> fypStatus,
                                Map<String, List<String>> enrolledCourses,
                                Map<String, List<String>> prerequisites) {
        System.out.println(HEADER);

        for (EligibilityRule rule : rules) {
            if (!rule.isEligible(studentId, completedCredits, fypStatus, enrolledCourses, prerequisites)) {
                return;
            }
        }

        FYPDetails details = input.collectFYPDetails();
        registrationService.register(studentId, fypStatus, enrolledCourses, details);
        System.out.println(SUCCESS_MSG);
    }
}

// =============================
// Interface Segregation: Input
// =============================
interface FYPInput {
    FYPDetails collectFYPDetails();
}

class ConsoleFYPInput implements FYPInput {
    private final Scanner scanner;

    public ConsoleFYPInput(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public FYPDetails collectFYPDetails() {
        String title = prompt("Enter FYP Title: ");
        String supervisor = prompt("Enter Supervisor Name: ");
        return new FYPDetails(title, supervisor);
    }

    private String prompt(String message) {
        System.out.print(message);
        String input;
        do {
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }
}

// =============================
// Entity
// =============================
class FYPDetails {
    private final String title;
    private final String supervisor;

    public FYPDetails(String title, String supervisor) {
        this.title = title;
        this.supervisor = supervisor;
    }

    public String getTitle() {
        return title;
    }

    public String getSupervisor() {
        return supervisor;
    }
}

// =============================
// Interface Segregation: Registration Service
// Dependency Inversion: High-level depends on interface
// =============================
interface FYPRegistrationService {
    void register(String studentId,
                  Map<String, Boolean> fypStatus,
                  Map<String, List<String>> enrolledCourses,
                  FYPDetails details);
}

class SimpleFYPRegistrationService implements FYPRegistrationService {
    private static final String FYP_COURSE_CODE = "CS801";

    @Override
    public void register(String studentId,
                         Map<String, Boolean> fypStatus,
                         Map<String, List<String>> enrolledCourses,
                         FYPDetails details) {
        fypStatus.put(studentId, true);
        enrolledCourses.computeIfAbsent(studentId, k -> new ArrayList<>()).add(FYP_COURSE_CODE);
        System.out.printf("FYP Title: %s | Supervisor: %s%n", details.getTitle(), details.getSupervisor());
    }
}

// =============================
// Open-Closed Principle + LSP: Eligibility Rules
// =============================
interface EligibilityRule {
    boolean isEligible(String studentId,
                       Map<String, Integer> completedCredits,
                       Map<String, Boolean> fypStatus,
                       Map<String, List<String>> enrolledCourses,
                       Map<String, List<String>> prerequisites);
}

class SufficientCreditsRule implements EligibilityRule {
    private static final int REQUIRED_CREDITS = 130;

    @Override
    public boolean isEligible(String studentId,
                              Map<String, Integer> completedCredits,
                              Map<String, Boolean> fypStatus,
                              Map<String, List<String>> enrolledCourses,
                              Map<String, List<String>> prerequisites) {
        boolean result = completedCredits.getOrDefault(studentId, 0) >= REQUIRED_CREDITS;
        if (!result) {
            System.out.println("❌ You must have at least 130 credits to register for FYP.");
        }
        return result;
    }
}

class NotAlreadyRegisteredRule implements EligibilityRule {
    @Override
    public boolean isEligible(String studentId,
                              Map<String, Integer> completedCredits,
                              Map<String, Boolean> fypStatus,
                              Map<String, List<String>> enrolledCourses,
                              Map<String, List<String>> prerequisites) {
        boolean result = !fypStatus.getOrDefault(studentId, false);
        if (!result) {
            System.out.println("❌ You are already registered for FYP.");
        }
        return result;
    }
}

class CompletedPrerequisitesRule implements EligibilityRule {
    @Override
    public boolean isEligible(String studentId,
                              Map<String, Integer> completedCredits,
                              Map<String, Boolean> fypStatus,
                              Map<String, List<String>> enrolledCourses,
                              Map<String, List<String>> prerequisites) {
        List<String> required = prerequisites.getOrDefault(studentId, List.of());
        List<String> enrolled = enrolledCourses.getOrDefault(studentId, List.of());
        boolean result = enrolled.containsAll(required);
        if (!result) {
            System.out.println("❌ You must complete all prerequisite courses to register for FYP.");
        }
        return result;
    }
}

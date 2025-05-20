package com.mycompany.ums;

import java.util.*;

public class FYPSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Integer> completedCredits = new HashMap<>();
        Map<String, Boolean> fypStatus = new HashMap<>();
        Map<String, List<String>> enrolledCourses = new HashMap<>();
        Map<String, List<String>> prerequisites = new HashMap<>();

        String studentId = "S123";
        completedCredits.put(studentId, 130);
        fypStatus.put(studentId, false);
        enrolledCourses.put(studentId, List.of("CS301", "CS302"));
        prerequisites.put(studentId, List.of("CS301", "CS302"));

        List<EligibilityRule> rules = List.of(
            new SufficientCreditsRule(),
            new NotAlreadyRegisteredRule(),
            new CompletedPrerequisitesRule()
        );

        FYPManager manager = new FYPManager(new ConsoleInputHandler(scanner), rules, new FYPRegistrar());
        manager.registerForFYP(studentId, completedCredits, fypStatus, enrolledCourses, prerequisites);
    }
}

// High-level class
class FYPManager {
    private static final String HEADER = "\nFinal Year Project Registration";
    private static final String SUCCESS_MSG = "Successfully registered for FYP!";

    private final FYPInput input;
    private final List<EligibilityRule> eligibilityRules;
    private final FYPRegistrationService registrationService;

    public FYPManager(FYPInput input, List<EligibilityRule> rules, FYPRegistrationService service) {
        this.input = input;
        this.eligibilityRules = rules;
        this.registrationService = service;
    }

    public void registerForFYP(String studentId,
                                Map<String, Integer> completedCredits,
                                Map<String, Boolean> fypStatus,
                                Map<String, List<String>> enrolledCourses,
                                Map<String, List<String>> prerequisites) {
        System.out.println(HEADER);

        for (EligibilityRule rule : eligibilityRules) {
            if (!rule.isEligible(studentId, completedCredits, fypStatus, enrolledCourses, prerequisites)) {
                return;
            }
        }

        FYPDetails details = input.collectFYPDetails();
        registrationService.register(studentId, fypStatus, enrolledCourses, details);
        System.out.println(SUCCESS_MSG);
    }
}

// Abstraction for input handling (ISP)
interface FYPInput {
    FYPDetails collectFYPDetails();
}

// Concrete input class
class ConsoleInputHandler implements FYPInput {
    private final Scanner scanner;

    public ConsoleInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public FYPDetails collectFYPDetails() {
        String title = prompt("Enter FYP title: ");
        String supervisor = prompt("Enter supervisor name: ");
        return new FYPDetails(title, supervisor);
    }

    private String prompt(String message) {
        String input;
        do {
            System.out.print(message);
            input = scanner.nextLine().trim();
        } while (input.isEmpty());
        return input;
    }
}

// Entity class
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

// Eligibility check abstraction (OCP, LSP)
interface EligibilityRule {
    boolean isEligible(String studentId,
                       Map<String, Integer> completedCredits,
                       Map<String, Boolean> fypStatus,
                       Map<String, List<String>> enrolledCourses,
                       Map<String, List<String>> prerequisites);
}

// Rule: Student must have sufficient credits
class SufficientCreditsRule implements EligibilityRule {
    private static final int REQUIRED_CREDITS = 130;

    @Override
    public boolean isEligible(String studentId,
                              Map<String, Integer> completedCredits,
                              Map<String, Boolean> fypStatus,
                              Map<String, List<String>> enrolledCourses,
                              Map<String, List<String>> prerequisites) {
        boolean eligible = completedCredits.getOrDefault(studentId, 0) >= REQUIRED_CREDITS;
        if (!eligible) System.out.println("Insufficient credits for FYP.");
        return eligible;
    }
}

// Rule: Student must not be already registered
class NotAlreadyRegisteredRule implements EligibilityRule {
    @Override
    public boolean isEligible(String studentId,
                              Map<String, Integer> completedCredits,
                              Map<String, Boolean> fypStatus,
                              Map<String, List<String>> enrolledCourses,
                              Map<String, List<String>> prerequisites) {
        boolean eligible = !fypStatus.getOrDefault(studentId, false);
        if (!eligible) System.out.println("You are already registered for FYP.");
        return eligible;
    }
}

// Rule: Student must have completed prerequisites
class CompletedPrerequisitesRule implements EligibilityRule {
    @Override
    public boolean isEligible(String studentId,
                              Map<String, Integer> completedCredits,
                              Map<String, Boolean> fypStatus,
                              Map<String, List<String>> enrolledCourses,
                              Map<String, List<String>> prerequisites) {
        List<String> required = prerequisites.getOrDefault(studentId, List.of());
        List<String> enrolled = enrolledCourses.getOrDefault(studentId, List.of());
        boolean eligible = enrolled.containsAll(required);
        if (!eligible) System.out.println("You must complete all FYP prerequisites.");
        return eligible;
    }
}

// Service abstraction for FYP registration (DIP)
interface FYPRegistrationService {
    void register(String studentId, Map<String, Boolean> fypStatus,
                  Map<String, List<String>> enrolledCourses, FYPDetails details);
}

// Concrete registration service
class FYPRegistrar implements FYPRegistrationService {
    @Override
    public void register(String studentId, Map<String, Boolean> fypStatus,
                         Map<String, List<String>> enrolledCourses, FYPDetails details) {
        fypStatus.put(studentId, true);
        // Save FYPDetails in DB or Map if needed
        System.out.printf("Registered FYP: \"%s\" under Supervisor: %s%n", details.getTitle(), details.getSupervisor());
    }
}

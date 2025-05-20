package com.mycompany.ums;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Interface segregation: Separate interface for UI display.
 */
interface PlacementUI {
    void display(List<String> opportunities);
}

/**
 * Concrete implementation of PlacementUI for console output.
 * Open for extension: other UI types can implement this.
 */
class ConsolePlacementUI implements PlacementUI {
    private static final String HEADER = "\nPlacement Opportunities";

    @Override
    public void display(List<String> opportunities) {
        System.out.println(HEADER);
        if (opportunities.isEmpty()) {
            System.out.println("No placement opportunities found.");
        } else {
            opportunities.forEach(System.out::println);
        }
    }
}

/**
 * Interface segregation: Separate interface for validation.
 */
interface PlacementValidator {
    void validate(String studentId, Map<String, List<String>> placementInfo);
}

/**
 * Concrete implementation of PlacementValidator.
 * Open for extension: other validators can implement this.
 */
class DefaultPlacementValidator implements PlacementValidator {
    @Override
    public void validate(String studentId, Map<String, List<String>> placementInfo) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
        if (placementInfo == null) {
            throw new IllegalArgumentException("Placement info map cannot be null");
        }
    }
}

/**
 * Interface segregation: Separate interface for service to fetch placement data.
 */
interface PlacementService {
    List<String> getOpportunities(String studentId, Map<String, List<String>> placementInfo);
}

/**
 * Concrete implementation of PlacementService.
 * Open for extension: other data sources can implement this.
 */
class DefaultPlacementService implements PlacementService {
    @Override
    public List<String> getOpportunities(String studentId, Map<String, List<String>> placementInfo) {
        return placementInfo.getOrDefault(studentId, Collections.emptyList());
    }
}

/**
 * Main Placement class depends on abstractions (DIP).
 * Open for extension via constructor injection.
 */
public class Placement {
    private final PlacementValidator validator;
    private final PlacementService service;
    private final PlacementUI ui;

    public Placement(PlacementValidator validator, PlacementService service, PlacementUI ui) {
        if (validator == null || service == null || ui == null) {
            throw new IllegalArgumentException("None of the dependencies can be null");
        }
        this.validator = validator;
        this.service = service;
        this.ui = ui;
    }

    public void view(String studentId, Map<String, List<String>> placementInfo) {
        validator.validate(studentId, placementInfo);
        List<String> opportunities = service.getOpportunities(studentId, placementInfo);
        ui.display(opportunities);
    }

    // Demo main method to test
    public static void main(String[] args) {
        Map<String, List<String>> placementData = Map.of(
            "S001", List.of("Google Internship", "Microsoft Full-time"),
            "S002", List.of("Amazon Internship")
        );

        PlacementValidator validator = new DefaultPlacementValidator();
        PlacementService service = new DefaultPlacementService();
        PlacementUI ui = new ConsolePlacementUI();

        Placement placement = new Placement(validator, service, ui);
        placement.view("S001", placementData);
        placement.view("S003", placementData);
    }
}

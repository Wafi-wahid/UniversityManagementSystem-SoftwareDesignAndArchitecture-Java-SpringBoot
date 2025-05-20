package com.mycompany.ums;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Interface segregation: Validator interface.
 */
interface PlacementValidator {
    void validate(String studentId, Map<String, List<String>> placementInfo);
}

/**
 * Concrete implementation of PlacementValidator.
 * Open for extension, closed for modification.
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
 * Interface segregation: Service interface for placement data fetching.
 */
interface PlacementService {
    List<String> getOpportunities(String studentId, Map<String, List<String>> placementInfo);
}

/**
 * Concrete implementation of PlacementService.
 */
class DefaultPlacementService implements PlacementService {
    private static final String NO_OPPORTUNITIES_MSG = "No placement opportunities available at the moment.";

    @Override
    public List<String> getOpportunities(String studentId, Map<String, List<String>> placementInfo) {
        List<String> opportunities = placementInfo.getOrDefault(studentId, Collections.emptyList());
        return opportunities.isEmpty() ? Collections.singletonList(NO_OPPORTUNITIES_MSG) : opportunities;
    }
}

/**
 * Interface segregation: UI interface for placement display.
 */
interface PlacementUI {
    void display(List<String> opportunities);
}

/**
 * Concrete UI implementation for console.
 */
class ConsolePlacementUI implements PlacementUI {
    private static final String PLACEMENT_HEADER = "\nPlacement Information";

    @Override
    public void display(List<String> opportunities) {
        System.out.println(PLACEMENT_HEADER);
        opportunities.forEach(System.out::println);
    }
}

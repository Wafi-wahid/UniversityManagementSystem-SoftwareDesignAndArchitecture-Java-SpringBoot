package com.mycompany.ums;

import java.util.Collections;
import java.util.List;
import java.util.Map;



/**
 * Interface segregation: Separate interface for service to fetch placement data.
 */
interface PlacementService {
    List<String> getOpportunities(String studentId, Map<String, List<String>> placementInfo);
}

/**
 * Concrete implementation of PlacementService.
 * Open for extension: other implementations can be added without modifying this.
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
 * Interface segregation: Separate interface for validation.
 */
interface PlacementValidator {
    void validate(String studentId, Map<String, List<String>> placementInfo);
}

/**
 * Concrete implementation of PlacementValidator.
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
 * Interface segregation: Separate interface for UI display.
 */
interface PlacementUI {
    void display(List<String> opportunities);
}

/**
 * Concrete implementation of PlacementUI for console output.
 */
class ConsolePlacementUI implements PlacementUI {
    private static final String HEADER = "\nPlacement Opportunities";

    @Override
    public void display(List<String> opportunities) {
        System.out.println(HEADER);
        opportunities.forEach(System.out::println);
    }
}

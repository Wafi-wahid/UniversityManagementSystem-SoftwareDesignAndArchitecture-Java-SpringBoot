package com.mycompany.ums;

import java.util.List;
import java.util.Map;

/**
 * Generic Validator interface for any type T
 */
interface Validator<T> {
    void validate(T input) throws IllegalArgumentException;
}

/**
 * Bundle to hold all necessary maps for validation
 */
class MapBundle {
    public final Map<String, Boolean> degreeClearance;
    public final Map<String, Boolean> feeStatus;
    public final Map<String, Integer> completedCredits;

    public MapBundle(Map<String, Boolean> degreeClearance,
                     Map<String, Boolean> feeStatus,
                     Map<String, Integer> completedCredits) {
        this.degreeClearance = degreeClearance;
        this.feeStatus = feeStatus;
        this.completedCredits = completedCredits;
    }
}

/**
 * Validate student ID is not null or empty
 */
class StudentIdValidator implements Validator<String> {
    @Override
    public void validate(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or empty");
        }
    }
}

/**
 * Validate maps are not null
 */
class MapsNotNullValidator implements Validator<MapBundle> {
    @Override
    public void validate(MapBundle maps) {
        if (maps.degreeClearance == null || maps.feeStatus == null || maps.completedCredits == null) {
            throw new IllegalArgumentException("Maps cannot be null");
        }
    }
}

/**
 * Validate clearance is not already in progress
 */
class ClearanceInProgressValidator implements Validator<String> {
    private final Map<String, Boolean> degreeClearance;

    public ClearanceInProgressValidator(Map<String, Boolean> degreeClearance) {
        this.degreeClearance = degreeClearance;
    }

    @Override
    public void validate(String studentId) {
        if (degreeClearance.getOrDefault(studentId, false)) {
            throw new IllegalArgumentException("Degree clearance already in progress");
        }
    }
}

/**
 * Validate fees are paid
 */
class FeesPaidValidator implements Validator<String> {
    private final Map<String, Boolean> feeStatus;

    public FeesPaidValidator(Map<String, Boolean> feeStatus) {
        this.feeStatus = feeStatus;
    }

    @Override
    public void validate(String studentId) {
        if (!feeStatus.getOrDefault(studentId, false)) {
            throw new IllegalArgumentException("Unpaid fees");
        }
    }
}

/**
 * Validate required credits completed
 */
class RequiredCreditsValidator implements Validator<String> {
    private final Map<String, Integer> completedCredits;
    private final int requiredCredits;

    public RequiredCreditsValidator(Map<String, Integer> completedCredits, int requiredCredits) {
        this.completedCredits = completedCredits;
        this.requiredCredits = requiredCredits;
    }

    @Override
    public void validate(String studentId) {
        if (completedCredits.getOrDefault(studentId, 0) < requiredCredits) {
            throw new IllegalArgumentException("Insufficient credits");
        }
    }
}

/**
 * Main validator orchestrator applying SOLID principles
 */
public final class DegreeClearanceValidator {
    private final List<Validator<String>> stringValidators;
    private final List<Validator<MapBundle>> mapValidators;

    public DegreeClearanceValidator(List<Validator<String>> stringValidators,
                                    List<Validator<MapBundle>> mapValidators) {
        this.stringValidators = stringValidators;
        this.mapValidators = mapValidators;
    }

    public void validate(String studentId, MapBundle maps) {
        for (Validator<String> validator : stringValidators) {
            validator.validate(studentId);
        }
        for (Validator<MapBundle> validator : mapValidators) {
            validator.validate(maps);
        }
    }
}

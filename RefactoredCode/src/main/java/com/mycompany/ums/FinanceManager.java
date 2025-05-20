package com.mycompany.ums;

import java.util.Map;

/**
 * Manages student fee status and amount using an extensible policy-based design.
 * 
 * SOLID Applied:
 * - SRP: FinanceManager manages fee status and uses FeePolicy for fee rules.
 * - OCP: FeePolicy interface allows extension of fee rules without modifying FinanceManager.
 * - LSP: Different FeePolicy implementations can substitute without breaking FinanceManager.
 * - ISP: FeePolicy interface has focused responsibilities.
 * - DIP: FinanceManager depends on FeePolicy abstraction.
 */
public class FinanceManager {
    private final FeePolicy feePolicy;

    // Default constructor with default policy
    public FinanceManager() {
        this(new DefaultFeePolicy());
    }

    // Constructor with injected policy for flexibility
    public FinanceManager(FeePolicy feePolicy) {
        if (feePolicy == null) {
            throw new IllegalArgumentException("FeePolicy cannot be null");
        }
        this.feePolicy = feePolicy;
    }

    /**
     * Checks if student has paid fees based on stored status.
     * 
     * @param studentId Student identifier
     * @param feeStatus Map of studentId to payment status
     * @return true if paid, false otherwise
     */
    public boolean hasPaidFees(String studentId, Map<String, Boolean> feeStatus) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or blank");
        }
        if (feeStatus == null) {
            throw new IllegalArgumentException("Fee status map cannot be null");
        }
        return feeStatus.getOrDefault(studentId, false);
    }

    /**
     * Updates fee payment status based on amount and policy.
     * 
     * @param studentId Student identifier
     * @param feeStatus Map of studentId to payment status
     * @param amount Amount paid
     */
    public void updatePaymentStatus(String studentId, Map<String, Boolean> feeStatus, double amount) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID cannot be null or blank");
        }
        if (feeStatus == null) {
            throw new IllegalArgumentException("Fee status map cannot be null");
        }
        if (feePolicy.isFullPayment(amount)) {
            feeStatus.put(studentId, true);
        }
    }

    /**
     * Returns the full fee amount required.
     * 
     * @return full fee amount
     */
    public double getFullFeeAmount() {
        return feePolicy.getFullFeeAmount();
    }

    /**
     * FeePolicy abstraction to apply OCP and DIP.
     */
    public interface FeePolicy {
        boolean isFullPayment(double amount);
        double getFullFeeAmount();
    }

    /**
     * Default implementation with fixed fee amount.
     */
    public static class DefaultFeePolicy implements FeePolicy {
        private static final double FULL_FEE_AMOUNT = 50000.0;

        @Override
        public boolean isFullPayment(double amount) {
            return amount >= FULL_FEE_AMOUNT;
        }

        @Override
        public double getFullFeeAmount() {
            return FULL_FEE_AMOUNT;
        }
    }
}

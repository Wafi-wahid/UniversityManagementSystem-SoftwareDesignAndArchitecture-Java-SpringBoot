package com.mycompany.ums;

import java.util.Map;

/**
 * Finance class managing fee payment, designed with SOLID principles:
 * - SRP: UI, validation, business logic, and payment processing are separated.
 * - OCP: PaymentHandler interface allows extending payment behavior without modifying Finance.
 * - LSP: PaymentHandler implementations interchangeable.
 * - ISP: Interfaces are focused (UI separate from validation, payment handling).
 * - DIP: Finance depends on abstractions/interfaces, not concrete classes.
 */
public class Finance {

    private final Finance ui;
    private final FinanceValidator validator;
    private final FinanceManager manager;
    private final PaymentHandler paymentHandler;

    // Constructor with default payment handler
    public Finance(Finance ui, FinanceValidator validator, FinanceManager manager) {
        this(ui, validator, manager,  DefaultPaymentHandlnewer());
    }

    private static PaymentHandler DefaultPaymentHandlnewer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'DefaultPaymentHandlnewer'");
    }

    // Constructor with injectable payment handler (for extension, testing, etc.)
    public Finance(Finance ui, FinanceValidator validator, FinanceManager manager, PaymentHandler paymentHandler) {
        this.ui = ui;
        this.validator = validator;
        this.manager = manager;
        this.paymentHandler = paymentHandler;
    }

    /**
     * Processes fee payment flow.
     */
    public void payFees(String studentId, Map<String, Boolean> feeStatus) {
        // Validate inputs via abstraction
        validator.validate(studentId, feeStatus);

        ui.showMessage("\nFee Payment");

        if (manager.hasPaidFees(studentId, feeStatus)) {
            ui.showMessage("Your fees are already paid for this semester.");
            return;
        }

        double fullAmount = manager.getFullFeeAmount();
        ui.showMessage(String.format("Total fee amount: %,d PKR", (int) fullAmount));

        double amount;
        try {
            amount = ui.getPaymentAmount();
        } catch (IllegalArgumentException e) {
            ui.showMessage(e.getMessage());
            return;
        }

        // Delegate payment processing to handler
        paymentHandler.processPayment(studentId, amount, fullAmount, feeStatus);
    }

    private double getPaymentAmount() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaymentAmount'");
    }

    private void showMessage(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showMessage'");
    }

    /**
     * PaymentHandler abstraction to support OCP and DIP.
     */
    public interface PaymentHandler {
        void processPayment(String studentId, double amount, double fullFeeAmount, Map<String, Boolean> feeStatus);
    }

    /**
     * Default payment handler implementation.
     */
    public class DefaultPaymentHandler implements PaymentHandler {
        @Override
        public void processPayment(String studentId, double amount, double fullFeeAmount, Map<String, Boolean> feeStatus) {
            // Update payment status via manager (SRP)
            manager.updatePaymentStatus(studentId, feeStatus, amount);

            if (amount >= fullFeeAmount) {
                ui.showMessage("Payment successful! Fees paid in full.");
            } else {
                double remaining = fullFeeAmount - amount;
                ui.showMessage(String.format("Partial payment received. Remaining balance: %,.2f PKR", remaining));
                ui.showMessage("Note: Full payment is required for course registration.");
            }
        }
    }
}

package com.mycompany.ums;

import java.util.Scanner;

/**
 * Interface to handle user interaction for finance-related operations.
 * 
 * SOLID:
 * - SRP: Interface only defines UI contract.
 * - OCP: New UI implementations can be added without modifying existing code.
 * - ISP: Interface is minimal, only UI relevant methods.
 * - DIP: Higher-level modules depend on abstraction, not concrete UI.
 */
public interface FinanceView {
    void showMessage(String message);
    double getPaymentAmount();
}

/**
 * Console-based UI implementation.
 * Uses Scanner for input.
 */
// public class ConsoleFinanceUI implements FinanceView {
//     private final Scanner scanner;

//     public ConsoleFinanceUI(Scanner scanner) {
//         if (scanner == null) {
//             throw new IllegalArgumentException("Scanner cannot be null");
//         }
//         this.scanner = scanner;
//     }

//     @Override
//     public void showMessage(String message) {
//         System.out.println(message);
//     }

//     @Override
//     public double getPaymentAmount() {
//         while (true) {
//             System.out.print("Enter amount to pay: ");
//             try {
//                 double amount = scanner.nextDouble();
//                 scanner.nextLine(); // consume newline
//                 if (amount < 0) {
//                     showMessage("Amount cannot be negative. Please enter again.");
//                     continue;
//                 }
//                 return amount;
//             } catch (Exception e) {
//                 scanner.nextLine(); // clear invalid input
//                 showMessage("Invalid payment amount entered. Please enter a numeric value.");
//             }
//         }
//     }

//     public Scanner getScanner() {
//         return scanner;
//     }
// }

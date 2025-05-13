/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Finance class for fee payment

import java.util.Map;
import java.util.Scanner;

class Finance {
    public static void payFees(String studentId, Map<String, Boolean> feeStatus, Scanner scanner) {
        System.out.println("\nFee Payment");
        
        if (feeStatus.getOrDefault(studentId, false)) {
            System.out.println("Your fees are already paid for this semester.");
            return;
        }
        
        System.out.println("Total fee amount: 50,000 PKR");
        System.out.print("Enter amount to pay: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        
        if (amount >= 50000) {
            feeStatus.put(studentId, true);
            System.out.println("Payment successful! Fees paid in full.");
        } else {
            System.out.println("Partial payment received. Remaining balance: " + (50000 - amount));
            System.out.println("Note: Full payment is required for course registration.");
        }
    }
}
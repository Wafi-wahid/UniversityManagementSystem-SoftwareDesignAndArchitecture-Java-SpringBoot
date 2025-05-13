/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Academic Advisor class for credit load calculation
class Advisor {
    public static int calculateMaxCredits(double gpa) {
        if (gpa < 2.0) {
            return 12; // minimum load for low GPA
        } else if (gpa < 3.0) {
            return 15;
        } else {
            return 18; // maximum normal load
        }
    }
}

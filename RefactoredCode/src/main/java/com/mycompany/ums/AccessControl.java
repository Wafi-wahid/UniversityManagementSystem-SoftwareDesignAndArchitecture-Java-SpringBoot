/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

public class AccessControl {
    public static void grantAccess(Student student) {
        if (student.areDuesCleared()) {
            System.out.println(student.getName() + " has been granted online access.");
        } else {
            System.out.println("Access denied. Clear dues first.");
        }
    }
}

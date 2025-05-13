/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;
// Notification class

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Notification {
    public static void viewNotifications(String studentId, Map<String, List<String>> notifications) {
        System.out.println("\nYour Notifications");
        
        List<String> msgs = notifications.getOrDefault(studentId, new ArrayList<>());
        if (msgs.isEmpty()) {
            msgs.add("No new notifications.");
        }
        
        msgs.forEach(System.out::println);
    }
}

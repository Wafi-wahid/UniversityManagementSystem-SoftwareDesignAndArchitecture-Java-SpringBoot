/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.ums;

// Placement class

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Placement {
    public static void viewPlacementInfo(String studentId, Map<String, List<String>> placementInfo) {
        System.out.println("\nPlacement Information");
        
        List<String> info = placementInfo.getOrDefault(studentId, new ArrayList<>());
        if (info.isEmpty()) {
            info.add("No placement opportunities available at the moment.");
        }
        
        info.forEach(System.out::println);
    }
}

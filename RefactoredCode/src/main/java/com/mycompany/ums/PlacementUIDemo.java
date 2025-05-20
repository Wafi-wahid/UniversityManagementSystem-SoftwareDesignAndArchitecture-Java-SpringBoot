package com.mycompany.ums;

import java.util.List;

/**
 * Interface segregation: Separate interface for Placement UI.
 */
interface PlacementUI {
    void display(List<String> opportunities);
}

/**
 * Concrete implementation of PlacementUI for console output.
 * Open for extension: new UI types can implement PlacementUI.
 */
class ConsolePlacementUI implements PlacementUI {
    private static final String PLACEMENT_HEADER = "\nPlacement Information";

    @Override
    public void display(List<String> opportunities) {
        System.out.println(PLACEMENT_HEADER);
        opportunities.forEach(System.out::println);
    }
}

/**
 * Client class that depends on PlacementUI abstraction (DIP).
 */
public class PlacementUIDemo {
    private final PlacementUI ui;

    public PlacementUIDemo(PlacementUI ui) {
        if (ui == null) {
            throw new IllegalArgumentException("PlacementUI implementation cannot be null");
        }
        this.ui = ui;
    }

    public void showPlacementInfo(List<String> opportunities) {
        ui.display(opportunities);
    }

    // Demo main method
    public static void main(String[] args) {
        PlacementUI ui = new ConsolePlacementUI();
        PlacementUIDemo demo = new PlacementUIDemo(ui);

        demo.showPlacementInfo(List.of("Google Internship", "Microsoft Full-time Position"));
    }
}

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // List of flavors for the first coffee
        java.util.ArrayList<String> flavors1 = new java.util.ArrayList<>();
        flavors1.add("Chocolate");
        flavors1.add("and Nuts");

        // First Coffee Object
        Coffee coffee1 = new Coffee(
                "Espresso",
                "Arabica",
                "Medium", 4.99,
                "Dark",
                "Colombia",
                false,
                10, flavors1,
                "Espresso");

        // List of flavors for the second coffee
        ArrayList<String> flavors2 = new ArrayList<>();
        flavors2.add("Steamed milk");

        // Second Coffee Object
        Coffee coffee2 = new Coffee(
                "Latte",
                "Robusta",
                "Large",
                5.99,
                "Medium",
                "Brazil",
                true,
                5, flavors2,
                "Drip");

        // Display details
        System.out.println(coffee1.describe());
        System.out.println("Stock available: " + coffee1.checkStock());
        coffee1.discount(5);
        System.out.println("New price after discount: ₱" + coffee1.getPrice());

        coffee2.addFlavor("and Cocoa powder on top");
        System.out.println(coffee2.describe());
        coffee2.updateStock(3);
        System.out.println("Updated stock: " + coffee2.checkStock());
    }
}

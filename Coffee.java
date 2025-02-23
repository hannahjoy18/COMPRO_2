import java.util.ArrayList;

class Coffee {
    String name;
    String beanType;
    String size;
    double price;
    String roastLevel;
    String origin;
    boolean hasMilk;
    int stock;
    ArrayList<String> flavors;
    String brewMethod;

    public Coffee(
            String name,
            String beanType,
            String size,
            double price,
            String roastLevel,
            String origin,
            boolean hasMilk,
            int stock,
            ArrayList<String> flavors,
            String brewMethod) {

        this.name = name;
        this.beanType = beanType;
        this.size = size;
        this.price = price;
        this.roastLevel = roastLevel;
        this.origin = origin;
        this.hasMilk = hasMilk;
        this.stock = stock;
        this.flavors = new ArrayList<>(flavors);
        this.brewMethod = brewMethod;
    }

    public String describe() {
        return "Coffee: " + name + " (" + size + ") - " + beanType + " beans, " + roastLevel + " roast, " +
                "Origin: " + origin + ", Price: ₱" + price + ", Brewed using " + brewMethod +
                ". Flavors: " + flavors + " (Milk: " + (hasMilk ? "Yes" : "No") + ")";
    }

    public int checkStock() {
        return stock;
    }

    public void discount(double amount) {
        if (amount > 0 && amount < price) {
            price -= amount;
        }
    }

    public void addFlavor(String flavor) {
        flavors.add(flavor);
    }

    public void updateStock(int quantity) {
        if (quantity > 0) {
            stock += quantity;
        }
    }

    public double getPrice() {
        return price;
    }
}

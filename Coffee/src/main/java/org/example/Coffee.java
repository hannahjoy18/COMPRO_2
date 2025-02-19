package org.example;

import java.util.Arrays;

class Coffee {
    private String name;
    private String type;
    private String size;
    private double price;
    private String roastLevel;
    private String origin;
    private boolean stock;
    private boolean isDecaf;
    private String[] flavorNotes;
    private String brewMethod;

    public Coffee(String name, String type, String size, double price, String roastLevel, String origin,
                  boolean stock, boolean isDecaf, String[] flavorNotes, String brewMethod) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.price = price;
        this.roastLevel = roastLevel;
        this.origin = origin;
        this.stock = stock;
        this.isDecaf = isDecaf;
        this.flavorNotes = flavorNotes;
        this.brewMethod = brewMethod;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", price=" + price +
                ", roastLevel='" + roastLevel + '\'' +
                ", origin='" + origin + '\'' +
                ", stock=" + stock +
                ", isDecaf=" + isDecaf +
                ", flavorNotes=" + Arrays.toString(flavorNotes) +
                ", brewMethod='" + brewMethod + '\'' +
                '}';
    }
}

public class Main {
    public static void main(String[] args) {
        Coffee coffee = new Coffee("Espresso", "Arabica", "Medium", 4.99, "Dark", "Colombia", true, false,
                new String[]{"Chocolate", "Nutty"}, "Espresso");
        System.out.println(coffee);
    }
}

package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner hannah = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = hannah.nextLine();
        System.out.print("Enter last name: ");
        String lastName = hannah.nextLine();

        System.out.println("Hello " + firstName + " " + lastName);

        hannah.nextLine();
        System.out.print("Enter first number: ");
        int firstNumber = hannah.nextInt();
        System.out.print("Enter operator(+, -, *, /: ");
        char operator = hannah.next().charAt(0);

        System.out.print("Enter last number: ");
        int lastNumber = hannah.nextInt();

        switch (operator) {
            case '+':
                int result = firstNumber + lastNumber;
                System.out.print("The result is: " + result);
                System.out.println();
                break;

            case '-':
                result = firstNumber - lastNumber;
                System.out.print("The result is: " + result);
                System.out.println();
                break;

            case '*':
                result = firstNumber * lastNumber;
                System.out.print("The result is: " + result);
                System.out.println();
                break;

            case '/':
                if (lastNumber != 0) {
                    result = firstNumber / lastNumber;
                    System.out.println("The result is: " + result);
                } else {
                    System.out.println("Error! Cannot divide by zero.");
                }
                break;
            default:
                System.out.println("Invalid operator");
                break;
        }

        System.out.print("\nEnter a distance in km: ");
        double kilometers = hannah.nextDouble();
        double miles = kilometers / 1.609344;
        System.out.print(kilometers + "km is " + miles + " mi");

        System.out.println();
        System.out.print("\nEnter a number: ");
        int n = hannah.nextInt();
        System.out.println("Looping through numbers from 1 to " + n + ":");
        // for loop
        for (int i = 1; i <= n; ++i) {
            System.out.println(i);
        }

        System.out.println();
        hannah.nextLine();
        System.out.print("Enter a word: ");
        String word = hannah.nextLine();
        String reverseWord = "";
        for (int i = word.length() - 1; i >= 0; i--) {
            reverseWord += word.charAt(i);
        }
        System.out.print("The reversed word is: " + reverseWord);

        hannah.nextLine();
        System.out.print("\nEnter a message: ");
        String message = hannah.nextLine();
        try (FileWriter writer = new FileWriter("target/message.txt")) {
            writer.write(message);
            System.out.println("The message is saved in target/message.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the message.");

            // Close the scanner
            hannah.close();
        }
    }
}
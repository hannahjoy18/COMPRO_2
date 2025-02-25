package org.example;

import java.io.*;
import java.util.*;

/**
 * Student Grade Calculator
 * Lets the user enter their prelim, midterm, and final grades.
 * The program computes the final rating and saves the grades into a file.
 */
public class Grade_Calculator {
    public static final double MIN_GRADE = 50;
    public static final String FILE_DIR = "target/records";
    public static final int NUM_OF_SUBJECTS = 64;
    public static final int NUM_OF_TERMS = 3;

    public static void main(String[] args) {
        String name;
        String[] subjects = new String[NUM_OF_SUBJECTS];
        double[][] grades = new double[NUM_OF_SUBJECTS][NUM_OF_TERMS];
        String[] terms = {"Prelim", "Midterm", "Finals"};

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        name = scanner.nextLine().trim();

        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append(String.format("%-15s%-10s%-10s%-10s%s\n", "SUBJECTS", "PRELIM", "MIDTERM", "FINAL", "FINAL RATING"));

        int subjectCount = 0;

        while (subjectCount < NUM_OF_SUBJECTS) {
            System.out.print("Enter subject: ");
            subjects[subjectCount] = scanner.nextLine().trim();

            if (subjects[subjectCount].isEmpty()) {
                System.out.println("Subject name cannot be empty. Try again.");
                continue;
            }

            sb.append(String.format("%-15s", subjects[subjectCount]));

            for (int j = 0; j < NUM_OF_TERMS; j++) {
                while (true) {
                    System.out.print("\t" + terms[j] + ": ");
                    try {
                        grades[subjectCount][j] = Double.parseDouble(scanner.nextLine().trim());
                        if (grades[subjectCount][j] < MIN_GRADE) {
                            throw new IllegalArgumentException("Error! No grades less than 50.");
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("\tError! Invalid number. Please enter a valid grade.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("\t" + e.getMessage());
                    }
                }
                sb.append(String.format("%-10.2f", grades[subjectCount][j]));
            }

            sb.append(String.format("%.2f\n", getFinalRating(grades[subjectCount])));

            subjectCount++;

            System.out.print("Add another subject? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (!choice.equals("y")) {
                break;
            }
        }

        System.out.println(sb);
        writeToFile(name.replaceAll("\\s+", "_"), sb.toString());
    }

    /**
     * Computes the final rating.
     * Formula: 30% of Prelim + 30% of Midterm + 40% of Finals.
     */
    public static double getFinalRating(double[] termGrades) {
        return termGrades[0] * 0.3 + termGrades[1] * 0.3 + termGrades[2] * 0.4;
    }

    /**
     * Writes student data to a file.
     */
    public static void writeToFile(String fileName, String data) {
        File folder = new File(FILE_DIR);
        if (!folder.exists() && !folder.mkdirs()) {
            System.out.println("Error: Could not create directory " + FILE_DIR);
            return;
        }

        File file = new File(folder, fileName + ".txt");
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(data);
            System.out.println("Grades successfully saved to " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    /**
     * Reads all student files in the records directory.
     */
    public static void readAllFiles() {
        File folder = new File(FILE_DIR);
        if (!folder.exists() || folder.listFiles() == null) {
            System.out.println("No records found.");
            return;
        }

        File[] studentGradeFiles = folder.listFiles();
        if (studentGradeFiles.length == 0) {
            System.out.println("No student grade records available.");
            return;
        }

        for (File file : studentGradeFiles) {
            if (file.isFile()) {
                readFile(file);
            }
        }
    }

    /**
     * Reads and prints the contents of a student file.
     */
    public static void readFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            System.out.println("\n--------------------------");
            System.out.println("Reading file: " + file.getName());
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
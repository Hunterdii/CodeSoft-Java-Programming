import java.util.Scanner;

public class StudentGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();
        int[] marks = new int[numSubjects];
        int totalMarks = 0;
        int maxMarks = 0;
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter maximum marks for subject " + (i + 1) + ": ");
            int maxMark = scanner.nextInt();
            maxMarks += maxMark;
            System.out.print("Enter marks obtained in subject " + (i + 1) + ": ");
            marks[i] = scanner.nextInt();
            totalMarks += marks[i];
        }
        double averagePercentage = (double) totalMarks / maxMarks * 100;
        String grade = "";
        if (averagePercentage >= 90) {
            grade = "A";
        } else if (averagePercentage >= 80) {
            grade = "B";
        } else if (averagePercentage >= 70) {
            grade = "C";
        } else if (averagePercentage >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }
        System.out.println("Total marks: " + totalMarks);
        System.out.println("Average percentage: " + averagePercentage + "%");
        System.out.println("Grade: " + grade);
    }
}
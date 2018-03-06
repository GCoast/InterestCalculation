package lt.swedbank.interestcalculator;

import java.util.Arrays;
import java.util.Scanner;

public class CompoundInterestCalculator {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        double amount = readAmount();
        double[] interestRate = readInterestRate();
        int periodLength = readPeriodLength();
        String frequency = readCompoundFrequency();
//      double[] yearlyInterests = calculateYearlyInterests(periodLength, amount, periodsPerYear, interestRate);
//      printYearlyInterests(yearlyInterests);

        double[][] intermediateInterestMatrix = new double[interestRate.length][];
        int periodsPerYear = returnPeriodsPerYear(frequency);

        for (int i = 0; i < intermediateInterestMatrix.length; i++) {
            intermediateInterestMatrix[i] = calculateIntermediateInterests(periodLength, periodsPerYear, amount, interestRate[i]);
        }
        printIntermediateInterestMatrix(intermediateInterestMatrix);

//        System.out.printf("Total amount: %.2f", (amount + yearlyInterests[yearlyInterests.length - 1]));
//        printIntermediateInterests(intermediateInterests);
    }

    private static void printIntermediateInterestMatrix(double[][] intermediateInterestMatrix) {
        for (double[] row : intermediateInterestMatrix) {
            for (double interest : row) {
                System.out.printf(" %.2f", interest);
            }
            System.out.println();
        }
    }

    private static double readAmount() {
        do {
            try {
                System.out.print("Amount: ");
                double amount = scan.nextDouble();
                if (amount <= 0) {
                    throw new Exception();
                }
                return amount;
            } catch (Exception e) {
                printInputError();
                scan.nextLine();
            }
        } while (true);

    }

    private static double[] readInterestRate() {
        double[] interestRates = new double[0];
        double tempInterestRate;
        do {
            while (true) {
                try {
                    System.out.print("Interest rate (%): ");
                    tempInterestRate = scan.nextDouble();
                    if (tempInterestRate > 100 || tempInterestRate < 0) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    printInputError();
                    scan.nextLine();
                }
            }
            if (tempInterestRate == 0) {
                break;
            }
            interestRates = Arrays.copyOf(interestRates, interestRates.length + 1);
            interestRates[interestRates.length - 1] = tempInterestRate / 100;

        } while (true);
        return interestRates;
    }

    private static int readPeriodLength() {
        while (true) {
            try {
                System.out.print("Period length (years): ");
                int periodLength = scan.nextInt();
                if (periodLength < 1) {
                    throw new Exception();
                }
                return periodLength;
            } catch (Exception e) {
                printInputError();
                scan.nextLine();
            }
        }
    }

    private static String readCompoundFrequency() {
        String testInput = "DWMQHY";
        while (true) {
            try {
                System.out.print("Compound frequency: ");
                String frequency = scan.next();
                if (!testInput.contains(frequency)) {
                    throw new Exception();
                }
                return frequency;
            } catch (Exception e) {
                printInputError();
            }
        }
    }

    private static double[] calculateIntermediateInterests(int periodLength, int periodsPerYear, double amount, double interestRate) {
        int totalPeriods = periodLength * periodsPerYear;
        double[] intermediateInterests = new double[totalPeriods];
        double intermediateAmount = amount;
        for (int i = 1; i <= intermediateInterests.length; i++) {
            intermediateInterests[i - 1] = Math.pow(interestRate / periodsPerYear + 1, i) * amount - intermediateAmount;
            intermediateAmount += intermediateInterests[i - 1];
        }
        return intermediateInterests;
    }

    private static int returnPeriodsPerYear(String frequency) {
        switch (frequency) {
            case "D":
                return 365;
            case "W":
                return 52;
            case "M":
                return 12;
            case "Q":
                return 4;
            case "H":
                return 2;
            default:
                return 1;
        }
    }

    private static void printInputError() {
        System.out.println("Invalid input! Try again!");
    }

    private static double[] calculateYearlyInterests(int periodLength, double amount, int periodsPerYear, double interestRate) {
        double[] yearlyInterests = new double[periodLength];
        for (int i = 1; i <= periodLength; i++) {
            yearlyInterests[i - 1] = Math.pow(interestRate / periodsPerYear + 1, i * periodsPerYear) * amount - amount;
        }
        return yearlyInterests;
    }

    private static void printYearlyInterests(double[] yearlyInterests) {
        for (int i = 0; i < yearlyInterests.length; i++) {
            System.out.printf("Interest amount after year " + (i + 1) + ": %.2f\n", yearlyInterests[i]);
        }
    }

    private static void printIntermediateInterests(double[] intermediateInterests) {
        System.out.println("Intermediate interest amounts: " + Arrays.toString(intermediateInterests));

    }
}
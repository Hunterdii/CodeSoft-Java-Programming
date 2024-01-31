import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner; 

public class ATM_Transaction {
    private static Account account = new Account(123456, 1234, 5000, 5000);
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ATM_Transaction atm = new ATM_Transaction();
        atm.start();
    }

    public void start() {
        while (true) {
            System.out.println("Automated Teller Machine");
            System.out.println("Choose 1 for Withdraw");
            System.out.println("Choose 2 for Deposit");
            System.out.println("Choose 3 for Check Balance");
            System.out.println("Choose 4 for Transfer");
            System.out.println("Choose 5 for EXIT");
            System.out.print("Choose the operation you want to perform:");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    withdraw();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.exit(0);
            }
        }
    }

    public void withdraw() {
        System.out.print("Enter money to be withdrawn:");
        double amount = scanner.nextDouble();
        try {
            account.getCheckingWithdrawInput(amount);
            System.out.println("Withdrawal successful. Checkings Account Balance: " + account.getCheckingBalance());
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deposit() {
        System.out.print("Enter money to be deposited:");
        double amount = scanner.nextDouble();

        System.out.println("Select an account to deposit into:");
        System.out.println("1. Checkings");
        System.out.println("2. Savings");
        System.out.print("Choice: ");
        int accountChoice = scanner.nextInt();

        switch (accountChoice) {
            case 1:
                account.depositToChecking(amount);
                System.out.println("Deposit successful. Checkings Account Balance: " + account.getCheckingBalance());
                break;
            case 2:
                account.depositToSaving(amount);
                System.out.println("Deposit successful. Savings Account Balance: " + account.getSavingBalance());
                break;
            default:
                System.out.println("Invalid Choice.");
        }
    }

    public void checkBalance() {
        System.out.println("Checkings Account Balance: " + account.getCheckingBalance());
        System.out.println("Savings Account Balance: " + account.getSavingBalance());
    }

    public void transfer() {
        System.out.println("Select an account you wish to transfer funds from:");
        System.out.println("1. Checkings");
        System.out.println("2. Savings");
        System.out.print("Choice: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.print("Enter amount to transfer: ");
                double transferAmount = scanner.nextDouble();
                account.transferToSaving(transferAmount);
                System.out.println("Transfer successful.");
                break;
            case 2:
                System.out.print("Enter amount to transfer: ");
                transferAmount = scanner.nextDouble();
                account.transferToChecking(transferAmount);
                System.out.println("Transfer successful.");
                break;
            default:
                System.out.println("Invalid Choice.");
        }
    }
}
public class Account {
    private int accountNumber;
    private int pin;
    private double checkingBalance;
    private double savingBalance;

    public Account(int accountNumber, int pin, double checkingBalance, double savingBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.checkingBalance = checkingBalance;
        this.savingBalance = savingBalance;
    }

    public void depositToChecking(double amount) {
        checkingBalance += amount;
    }

    public void depositToSaving(double amount) {
        savingBalance += amount;
    }

    public void getCheckingWithdrawInput(double amount) throws InsufficientBalanceException {
        if (checkingBalance < amount) {
            throw new InsufficientBalanceException("Insufficient balance in Checking Account.");
        }
        checkingBalance -= amount;
    }

    public void transferToSaving(double amount) {
        if (checkingBalance >= amount) {
            checkingBalance -= amount;
            savingBalance += amount;
        } else {
            System.out.println("Insufficient balance in Checking Account.");
        }
    }

    public void transferToChecking(double amount) {
        if (savingBalance >= amount) {
            savingBalance -= amount;
            checkingBalance += amount;
        } else {
            System.out.println("Insufficient balance in Savings Account.");
        }
    }

    public double getCheckingBalance() {
        return checkingBalance;
    }

    public double getSavingBalance() {
        return savingBalance;
    }
}
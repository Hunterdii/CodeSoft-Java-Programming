import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean transfer(double amount, BankAccount recipientAccount) {
        if (withdraw(amount)) {
            recipientAccount.deposit(amount);
            return true;
        } else {
            return false;
        }
    }
}

class ATM {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    public boolean withdraw(double amount) {
        return account.withdraw(amount);
    }

    public void deposit(double amount) {
        account.deposit(amount);
    }

    public double checkBalance() {
        return account.getBalance();
    }

    public boolean transfer(double amount, BankAccount recipientAccount) {
        return account.transfer(amount, recipientAccount);
    }
}

public class ATMGUI {
    private ATM atm;

    public ATMGUI(ATM atm) {
        this.atm = atm;

        JFrame frame = new JFrame("ATM Interface");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set width and height
        frame.setLocationRelativeTo(null); // Center the frame

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));

        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton balanceButton = new JButton("Check Balance");
        JButton transferButton = new JButton("Transfer");
        JButton exitButton = new JButton("Exit");

        Color buttonColor = new Color(55, 205, 50);
        withdrawButton.setBackground(buttonColor);
        depositButton.setBackground(buttonColor);
        balanceButton.setBackground(buttonColor);
        transferButton.setBackground(buttonColor);
        exitButton.setBackground(buttonColor);

        withdrawButton.setForeground(Color.WHITE);
        depositButton.setForeground(Color.WHITE);
        balanceButton.setForeground(Color.WHITE);
        transferButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);

        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        withdrawButton.setFont(buttonFont);
        depositButton.setFont(buttonFont);
        balanceButton.setFont(buttonFont);
        transferButton.setFont(buttonFont);
        exitButton.setFont(buttonFont);

        JLabel resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24));
        resultLabel.setForeground(Color.blue);

        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);
        buttonPanel.add(balanceButton);
        buttonPanel.add(transferButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultLabel, BorderLayout.SOUTH);

        frame.add(mainPanel);

        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:");
                try {
                    double amount = Double.parseDouble(amountString);
                    boolean success = atm.withdraw(amount);
                    if (success) {
                        resultLabel.setText("Withdrawal successful. New balance: $" + atm.checkBalance());
                    } else {
                        resultLabel.setText("Insufficient funds. Current balance: $" + atm.checkBalance());
                    }
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid input. Please enter a valid number.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog(frame, "Enter amount to deposit:");
                try {
                    double amount = Double.parseDouble(amountString);
                    atm.deposit(amount);
                    resultLabel.setText("Deposit successful. New balance: $" + atm.checkBalance());
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid input. Please enter a valid number.");
                }
            }
        });

        balanceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultLabel.setText("Current balance: $" + atm.checkBalance());
            }
        });

        transferButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amountString = JOptionPane.showInputDialog(frame, "Enter amount to transfer:");
                try {
                    double amount = Double.parseDouble(amountString);
                    String recipientAccountString = JOptionPane.showInputDialog(frame, "Enter recipient's account number:");
                    BankAccount recipientAccount = new BankAccount(0);
                    boolean success = atm.transfer(amount, recipientAccount);
                    if (success) {
                        resultLabel.setText("Transfer successful. New balance: $" + atm.checkBalance());
                    } else {
                        resultLabel.setText("Transfer failed. Insufficient funds.");
                    }
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Invalid input. Please enter a valid number.");
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(100000);
        ATM atm = new ATM(account);
        new ATMGUI(atm);
    }
}

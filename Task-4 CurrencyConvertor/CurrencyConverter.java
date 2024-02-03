import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter {

    private JTextField amountField;
    private JComboBox<String> baseCurrencyComboBox;
    private JComboBox<String> targetCurrencyComboBox;
    private JLabel resultLabel;
    private JLabel feedbackLabel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CurrencyConverter().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Currency Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400); 
        frame.getContentPane().setBackground(new Color(54, 57, 63)); 
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(new EmptyBorder(30, 30, 30, 30)); 
        inputPanel.setBackground(new Color(54, 57, 63));
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel title = new JLabel("Currency Converter");
        title.setFont(new Font("Arial", Font.BOLD, 28)); 
        title.setForeground(Color.WHITE); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        inputPanel.add(title, gbc);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 10);
        inputPanel.add(amountLabel, gbc);

        amountField = new JTextField(15); 
        amountField.setPreferredSize(new Dimension(200, 30)); 
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        inputPanel.add(amountField, gbc);

        JLabel baseCurrencyLabel = new JLabel("Base Currency:");
        baseCurrencyLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 10);
        inputPanel.add(baseCurrencyLabel, gbc);

        baseCurrencyComboBox = new JComboBox<>(new String[]{"Indian Rupee (INR)", "US Dollar (USD)", "Euro (EUR)", "British Pound (GBP)", "Japanese Yen (JPY)", "Australian Dollar (AUD)", "Canadian Dollar (CAD)", "Swiss Franc (CHF)", "Chinese Yuan (CNY)", "Swedish Krona (SEK)"});
        baseCurrencyComboBox.setPreferredSize(new Dimension(200, 30)); 
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        inputPanel.add(baseCurrencyComboBox, gbc);

        JLabel targetCurrencyLabel = new JLabel("Target Currency:");
        targetCurrencyLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 10);
        inputPanel.add(targetCurrencyLabel, gbc);

        targetCurrencyComboBox = new JComboBox<>(new String[]{"Indian Rupee (INR)", "US Dollar (USD)", "Euro (EUR)", "British Pound (GBP)", "Japanese Yen (JPY)", "Australian Dollar (AUD)", "Canadian Dollar (CAD)", "Swiss Franc (CHF)", "Chinese Yuan (CNY)", "Swedish Krona (SEK)"});
        targetCurrencyComboBox.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 10, 0);
        inputPanel.add(targetCurrencyComboBox, gbc);

        JButton convertButton = new JButton("Convert");
        convertButton.setBackground(new Color(255, 193, 7)); 
        convertButton.setForeground(Color.BLACK); 
        convertButton.setFocusPainted(false); 
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_END;
        inputPanel.add(convertButton, gbc);

        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultPanel.setBackground(new Color(54, 57, 63));

        resultLabel = new JLabel();
        resultLabel.setFont(new Font("Arial", Font.BOLD, 24)); 
        resultLabel.setForeground(Color.WHITE);
        resultPanel.add(resultLabel);

        feedbackLabel = new JLabel();
        feedbackLabel.setForeground(Color.RED);
        resultPanel.add(feedbackLabel);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(resultPanel, BorderLayout.CENTER);
        
        frame.setLocationRelativeTo(null);
        
        frame.setVisible(true);
    }

    private void convertCurrency() {
        try {
            String baseCurrency = ((String) baseCurrencyComboBox.getSelectedItem()).split("\\(")[1].split("\\)")[0].trim();
            String targetCurrency = ((String) targetCurrencyComboBox.getSelectedItem()).split("\\(")[1].split("\\)")[0].trim();
            double amount = Double.parseDouble(amountField.getText());

            // Fetch exchange rate from API
            String apiUrl = "https://api.exchangerate-api.com/v4/latest/" + baseCurrency;
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            connection.disconnect();

            double exchangeRate = Double.parseDouble(response.toString().split("\"" + targetCurrency + "\":")[1]
                    .split(",")[0].replace("}", "").trim());

            double result = amount * exchangeRate;

            String currencySign = getCurrencySign(targetCurrency);

            resultLabel.setText(String.format("%.2f %s", result, currencySign + " " + targetCurrency));
            feedbackLabel.setText(""); // Clear any previous error messages
        } catch (Exception ex) {
            resultLabel.setText("");
            feedbackLabel.setText("Error converting currency. Please check your input.");
        }
    }

    private String getCurrencySign(String currencyCode) {
        switch (currencyCode) {
            case "INR":
                return "₹";
            case "USD":
                return "$";
            case "EUR":
                return "€";
            case "GBP":
                return "£";
            case "JPY":
                return "¥";
            case "AUD":
                return "A$";
            case "CAD":
                return "C$";
            case "CHF":
                return "CHF";
            case "CNY":
                return "¥";
            case "SEK":
                return "kr";
            default:
                return "";
        }
    }
}

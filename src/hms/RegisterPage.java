package hms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterPage extends JFrame implements ActionListener {

    private JTextField nameField, emailField, phoneField, addressField, usernameField, ageField;
    private JPasswordField passwordField;
    private JComboBox<String> genderComboBox;
    private JSpinner dobSpinner;
    private JButton registerButton, backButton;

    public RegisterPage() {
        setTitle("Patient Registration");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set JFrame to fullscreen
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(10, 2, 10, 10)); // 10 rows, 2 columns, with gaps

        // Create labels and text fields
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Address:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Gender:"));
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        add(genderComboBox);

        add(new JLabel("Date of Birth:"));
        dobSpinner = new JSpinner(new SpinnerDateModel());
        add(dobSpinner);

        add(new JLabel("Age:"));
        ageField = new JTextField();
        add(ageField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        registerButton = new JButton("Register");
        backButton = new JButton("Back");

        add(registerButton);
        add(backButton);

        registerButton.addActionListener(this);
        backButton.addActionListener(this);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Register")) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "dhanushmm@2204");
                String sql = "INSERT INTO patient (name, username, email, phone, address, gender, dob, age, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nameField.getText());
                stmt.setString(2, usernameField.getText());
                stmt.setString(3, emailField.getText());
                stmt.setString(4, phoneField.getText());
                stmt.setString(5, addressField.getText());
                stmt.setString(6, (String) genderComboBox.getSelectedItem());
                stmt.setDate(7, new java.sql.Date(((java.util.Date) dobSpinner.getValue()).getTime()));
                stmt.setInt(8, Integer.parseInt(ageField.getText()));
                stmt.setString(9, new String(passwordField.getPassword()));

                // Execute the update
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
                }

                // Close connections
                stmt.close();
                conn.close();
                
                dispose(); // Close the registration window

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            }
        } else if (command.equals("Back")) {
            new Index().setVisible(true); 
            dispose(); 
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterPage::new);
    }
}

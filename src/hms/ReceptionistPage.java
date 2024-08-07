package hms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class ReceptionistPage extends JFrame implements ActionListener {

    private Font buttonFont;
    private JButton btnViewDoctors, btnAddPatients, btnAddAppointments, btnExit, btnBack;

    // No-argument constructor
    public ReceptionistPage() {
        this(null); // Call the constructor with String argument
    }

    // Constructor with String argument
    public ReceptionistPage(String name) {
        setTitle("Receptionist Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize JFrame to full screen
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null); // Use absolute positioning for buttons

        buttonFont = new Font("MS UI Gothic", Font.BOLD, 18);

        // Set background image
        ImageIcon backgroundIcon = loadImageIcon("Hospital/icons/hms.jpg");
        if (backgroundIcon == null) {
            throw new RuntimeException("Background image not found");
        }
        Image img = backgroundIcon.getImage().getScaledInstance(1530, 820, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, 1530, 820);
        add(background);

        // Create buttons
        btnViewDoctors = createButton("View Doctors", 650, 100);
        btnAddPatients = createButton("Add Patients", 650, 200);
        btnAddAppointments = createButton("Add Appointments", 650, 300); // New button
        btnExit = createButton("Exit", 650, 400);
        btnBack = createButton("Back", 650, 500);

        background.add(btnViewDoctors);
        background.add(btnAddPatients);
        background.add(btnAddAppointments); // Add new button
        background.add(btnExit);
        background.add(btnBack);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBounds(x, y, 250, 60); // Set button size and position
        button.setBackground(Color.BLACK);
        button.setForeground(Color.YELLOW);
        button.addActionListener(this);
        return button;
    }

    private ImageIcon loadImageIcon(String path) {
        URL resourceUrl = ClassLoader.getSystemResource(path);
        if (resourceUrl == null) {
            System.err.println("Resource not found: " + path);
            return null;
        }
        return new ImageIcon(resourceUrl);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "View Doctors":
                new DViewDoctorPage().setVisible(true);
                break;
            case "Add Patients":
                new AddPatientPage().setVisible(true);
                break;
            case "Add Appointments":
                new PAddAppointmentPage("patient").setVisible(true); // Handle new button action
                break;
            case "Exit":
                dispose();
                break;
            case "Back":
                this.setVisible(false);
                new Index().setVisible(true); // Replace with your actual Index page class
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReceptionistPage().setVisible(true));
    }
}

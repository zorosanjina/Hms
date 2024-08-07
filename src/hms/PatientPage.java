package hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PatientPage extends JFrame {
    JLabel l1;
    Font f1;

    JButton btnViewAppointments, btnViewDoctors, btnAddAppointment, btnExit, btnBack;
    private String loggedInUsername; // Variable to store the logged-in username
    private int loggedInUserId; // Variable to store the logged-in user's ID

    public PatientPage(String username) {
        super("PatientPage");
        this.loggedInUsername = username; // Store the username

        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize JFrame to full screen

        // Load and set the background image
        ImageIcon ic = new ImageIcon(ClassLoader.getSystemResource("Hospital/icons/hms.jpg"));
        Image img = ic.getImage().getScaledInstance(1530, 820, Image.SCALE_SMOOTH);
        ImageIcon ic1 = new ImageIcon(img);
        l1 = new JLabel(ic1);
        l1.setLayout(null); // Use absolute positioning for buttons

        f1 = new Font("MS UI Gothic", Font.BOLD, 18);

        // Create buttons
        btnViewAppointments = createButton("View Appointments", f1, 650, 100);
        btnViewDoctors = createButton("View Doctors", f1, 650, 200);
        btnAddAppointment = createButton("Add Appointment", f1, 650, 300);
        btnExit = createButton("Exit", f1, 650, 400);
        btnBack = createButton("Back", f1, 650, 500);

        add(l1, BorderLayout.CENTER);

        // Set action listeners
        btnViewAppointments.addActionListener(e -> viewAppointments());
        btnViewDoctors.addActionListener(e -> new DViewDoctorPage().setVisible(true));
        btnAddAppointment.addActionListener(e -> new PAddAppointmentPage("Patient").setVisible(true)); // Adjust as needed
        btnExit.addActionListener(e -> System.exit(0));
        btnBack.addActionListener(e -> {
            this.setVisible(false);
            new Index().setVisible(true); // Replace with your actual Index page class
        });

        // Retrieve user ID based on username
        fetchUserId();

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JButton createButton(String text, Font font, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBounds(x, y, 250, 60); // Set button size and position
        button.setBackground(Color.BLACK);
        button.setForeground(Color.YELLOW);
        l1.add(button);
        return button;
    }

    private void fetchUserId() {
        try {
            ConnectionClass obj = new ConnectionClass();
            String query = "SELECT id FROM patient WHERE username=?";
            PreparedStatement pstmt = obj.getConnection().prepareStatement(query);
            pstmt.setString(1, loggedInUsername);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                loggedInUserId = rs.getInt("id"); // Store the user ID
            } else {
                JOptionPane.showMessageDialog(this, "User ID not found for username: " + loggedInUsername);
            }

            rs.close();
            pstmt.close();
            obj.close(); // Close resources

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching user ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAppointments() {
        new PViewAppointmentPage(loggedInUserId).setVisible(true);
    }

    public static void main(String[] args) {
        // For testing purposes, pass a hardcoded username
        new PatientPage("someUsername").setVisible(true);
    }
}

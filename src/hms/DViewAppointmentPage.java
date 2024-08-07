package hms;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DViewAppointmentPage extends JFrame {

    private JTable appointmentTable;
    private String loggedInDoctorUsername;
    private ConnectionClass obj;
    private String loggedInDoctorName;
    private JButton openButton;
    private JButton closeButton;

    // Constructor accepting the logged-in doctor's username
    public DViewAppointmentPage(String doctorUsername) {
        this.loggedInDoctorUsername = doctorUsername;
        setTitle("View Appointments");
        setSize(800, 600); // Increased height for the new column
        setLocation(350, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        appointmentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create buttons
        JPanel buttonPanel = new JPanel();
        openButton = new JButton("Open");
        closeButton = new JButton("Close");
        buttonPanel.add(openButton);
        buttonPanel.add(closeButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Set button actions
        openButton.addActionListener(e -> updateStatus("Open"));
        closeButton.addActionListener(e -> updateStatus("Close"));

        obj = new ConnectionClass();
        fetchDoctorName(); // Retrieve the doctor's name based on username
        fetchAppointmentData(); // Populate table with data

        setVisible(true);
    }

    // Fetch doctor's name using username
    private void fetchDoctorName() {
        String query = "SELECT name FROM doctor WHERE username = ?";

        try (PreparedStatement pst = obj.getConnection().prepareStatement(query)) {
            pst.setString(1, loggedInDoctorUsername);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    loggedInDoctorName = rs.getString("name");
                } else {
                    JOptionPane.showMessageDialog(this, "Doctor not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    dispose(); // Close the window if doctor is not found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching doctor name: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fetch appointment data for the logged-in doctor
    private void fetchAppointmentData() {
        String query = "SELECT id, patient_name, doctor_name, date, time, status FROM appointment WHERE doctor_name = ?";

        try (PreparedStatement pst = obj.getConnection().prepareStatement(query)) {
            pst.setString(1, loggedInDoctorName);
            try (ResultSet rs = pst.executeQuery()) {
                DefaultTableModel model = new DefaultTableModel();
                appointmentTable.setModel(model);
                model.setColumnIdentifiers(new String[]{"ID", "Patient Name", "Doctor Name", "Date", "Time", "Status"});

                while (rs.next()) {
                    model.addRow(new Object[]{
                            rs.getInt("id"), // Assuming there's an ID column to uniquely identify appointments
                            rs.getString("patient_name"),
                            rs.getString("doctor_name"),
                            rs.getString("date"),
                            rs.getString("time"),
                            rs.getString("status") // Display the status
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching appointments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update the status of the selected appointment
    private void updateStatus(String status) {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            int appointmentId = (Integer) appointmentTable.getValueAt(selectedRow, 0); // Assuming ID is in the first column
            String updateQuery = "UPDATE appointment SET status = ? WHERE id = ?";

            try (PreparedStatement pst = obj.getConnection().prepareStatement(updateQuery)) {
                pst.setString(1, status);
                pst.setInt(2, appointmentId);
                pst.executeUpdate();
                // Refresh table data
                fetchAppointmentData();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating status: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No appointment selected.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DViewAppointmentPage("doctorUsername").setVisible(true)); // Example username
    }
}

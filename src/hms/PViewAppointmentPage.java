package hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PViewAppointmentPage extends JFrame {

    private JTable appointmentTable;
    private int userId;  // Variable to store the logged-in user's ID

    // Constructor accepting the logged-in user's ID
    public PViewAppointmentPage(int userId) {
        this.userId = userId;  // Store the user ID
        setTitle("View Appointments");
        setSize(900, 600);  // Adjusted size to accommodate the extra column
        setLocation(350, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        appointmentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        fetchAppointmentData();

        setVisible(true);
    }

    private void fetchAppointmentData() {
        ConnectionClass obj = new ConnectionClass();
        try {
            // Adjust query to use userId and include the status column
            String query = "SELECT id, patient_name, doctor_name, date, time, status FROM appointment WHERE id = ?";
            PreparedStatement pstmt = obj.getConnection().prepareStatement(query);
            pstmt.setInt(1, userId);  // Set the user ID in the query

            ResultSet rs = pstmt.executeQuery();

            // Create table model and set column names including Status
            DefaultTableModel model = new DefaultTableModel();
            appointmentTable.setModel(model);
            model.setColumnIdentifiers(new String[]{"ID", "Patient Name", "Doctor Name", "Date", "Time", "Status"});

            // Populate table with data
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),  // Include the id column
                        rs.getString("patient_name"),
                        rs.getString("doctor_name"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("status")  // Add the status column
                });
            }

            rs.close();
            pstmt.close();
            obj.close();  // Close resources

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching appointments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

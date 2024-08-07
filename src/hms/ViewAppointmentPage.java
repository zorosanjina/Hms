package hms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewAppointmentPage extends JFrame {

    private JTable appointmentTable;

    public ViewAppointmentPage() {
        setTitle("View Appointments");
        setSize(900, 600);  // Adjusted size to fit the new column
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        appointmentTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        fetchAppointmentData(); // Populate table with data

        setVisible(true);
    }

    private void fetchAppointmentData() {
        try {
            ConnectionClass obj = new ConnectionClass();
            String query = "SELECT patient_name, doctor_name, date, time, status FROM appointment"; // Updated query to include status
            ResultSet rs = obj.stm.executeQuery(query);

            // Create table model and set column names
            DefaultTableModel model = new DefaultTableModel();
            appointmentTable.setModel(model);
            model.setColumnIdentifiers(new String[]{"Patient Name", "Doctor Name", "Date", "Time", "Status"}); // Added Status column

            // Populate table with data
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("patient_name"),
                        rs.getString("doctor_name"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("status") // Include status in the row
                });
            }

            rs.close();
            obj.close(); // Close resources

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching appointments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewAppointmentPage().setVisible(true));
    }
}

package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AddAppointmentPage extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5, l6;
    JComboBox<String> cbPatientName, cbPatientId, cb3, cb2;
    JTextField tf;
    JTextArea ta;
    JButton bt1, bt2;
    private String userRole; // Variable to store the user role

    AddAppointmentPage(String userRole) {
        super("Add Appointment");
        this.userRole = userRole; // Initialize the user role
        setSize(400, 500);
        setLocation(450, 200);

        l1 = new JLabel("Add Appointment");
        l1.setFont(new Font("Arial", Font.BOLD, 25));
        l1.setBounds(120, 10, 200, 30);
        add(l1);

        l2 = new JLabel("Patient Name:");
        l2.setBounds(30, 70, 100, 30);
        add(l2);

        cbPatientName = new JComboBox<>();
        cbPatientName.setBounds(140, 70, 200, 30);
        if (userRole.equals("admin")) { // Show the combo box only for admin
            add(cbPatientName);
        }
        cbPatientName.addActionListener(this);

        JLabel l7 = new JLabel("Patient ID:");
        l7.setBounds(30, 110, 100, 30);
        add(l7);

        cbPatientId = new JComboBox<>();
        cbPatientId.setBounds(140, 110, 200, 30);
        if (userRole.equals("admin")) { // Show the combo box only for admin
            add(cbPatientId);
        }

        l6 = new JLabel("Disease:");
        l6.setBounds(30, 150, 100, 30);
        add(l6);

        cb3 = new JComboBox<>();
        cb3.setBounds(140, 150, 200, 30);
        cb3.addActionListener(this);
        add(cb3);

        l3 = new JLabel("Doctor Name:");
        l3.setBounds(30, 190, 100, 30);
        add(l3);

        cb2 = new JComboBox<>();
        cb2.setBounds(140, 190, 200, 30);
        add(cb2);

        l4 = new JLabel("Date (YYYY-MM-DD):");
        l4.setBounds(30, 230, 150, 30);
        add(l4);

        tf = new JTextField();
        tf.setBounds(180, 230, 160, 30);
        add(tf);

        l5 = new JLabel("Time:");
        l5.setBounds(30, 270, 100, 30);
        add(l5);

        ta = new JTextArea();
        ta.setBounds(140, 270, 200, 50);
        add(ta);

        bt1 = new JButton("Add");
        bt1.setBackground(Color.BLACK);
        bt1.setForeground(Color.WHITE);
        bt1.setBounds(50, 340, 120, 40);
        bt1.addActionListener(this);
        add(bt1);

        bt2 = new JButton("Back");
        bt2.setBackground(Color.BLACK);
        bt2.setForeground(Color.WHITE);
        bt2.setBounds(200, 340, 120, 40);
        bt2.addActionListener(this);
        add(bt2);

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Populate comboboxes with data from the database
        populateComboBoxes();
    }

    AddAppointmentPage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void populateComboBoxes() {
        try {
            ConnectionClass obj = new ConnectionClass();

            // Populate patient names and IDs only if the user is an admin
            if (userRole.equals("admin")) {
                String queryPatients = "SELECT id, name FROM patient";
                ResultSet rsPatients = obj.stm.executeQuery(queryPatients);
                while (rsPatients.next()) {
                    cbPatientName.addItem(rsPatients.getString("name"));
                    cbPatientId.addItem(rsPatients.getString("id"));
                }
            }

            // Populate diseases
            String queryDiseases = "SELECT DISTINCT specialization FROM doctor";
            ResultSet rsDiseases = obj.stm.executeQuery(queryDiseases);
            while (rsDiseases.next()) {
                cb3.addItem(rsDiseases.getString("specialization"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateDoctors(String disease) {
        try {
            cb2.removeAllItems();
            ConnectionClass obj = new ConnectionClass();
            String queryDoctors = "SELECT name FROM doctor WHERE specialization = ?";
            PreparedStatement pst = obj.con.prepareStatement(queryDoctors);
            pst.setString(1, disease);
            ResultSet rsDoctors = pst.executeQuery();
            while (rsDoctors.next()) {
                cb2.addItem(rsDoctors.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching doctors: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cb3) {
            String disease = (String) cb3.getSelectedItem();
            populateDoctors(disease);
        } else if (e.getSource() == cbPatientName) {
            // Update the patient ID combo box based on the selected patient name
            String selectedPatientName = (String) cbPatientName.getSelectedItem();
            updatePatientIdComboBox(selectedPatientName);
        } else if (e.getSource() == bt1) {
            String patientId = userRole.equals("admin") ? (String) cbPatientId.getSelectedItem() : "N/A";
            String patientName = (String) cbPatientName.getSelectedItem();
            String doctorName = (String) cb2.getSelectedItem();
            String date = tf.getText();
            String time = ta.getText();

            try {
                ConnectionClass obj = new ConnectionClass();
                String query = "INSERT INTO appointment(patient_name,id,doctor_name, date, time) VALUES(?, ?, ?, ?, ?)";
                PreparedStatement pst = obj.con.prepareStatement(query);
                pst.setString(2, patientId);
                pst.setString(1, patientName);
                pst.setString(3, doctorName);
                pst.setString(4, date);
                pst.setString(5, time);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Appointment added successfully!");
                this.setVisible(false);
            } catch (Exception exe) {
                exe.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding appointment: " + exe.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == bt2) {
            this.setVisible(false);
        }
    }

    private void updatePatientIdComboBox(String patientName) {
        try {
            cbPatientId.removeAllItems();
            ConnectionClass obj = new ConnectionClass();
            String query = "SELECT id FROM patient WHERE name = ?";
            PreparedStatement pst = obj.con.prepareStatement(query);
            pst.setString(1, patientName);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cbPatientId.addItem(rs.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching patient ID: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new AddAppointmentPage("admin");
    }
}

package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class PAddAppointmentPage extends JFrame {
    JLabel l1, l2, l3, l4, l5, l6;
    JTextField tf, patientNameField;
    JTextArea ta;
    JComboBox<String> cb2, cb3;
    JButton bt1, bt2;
    String username;
    int patientId;  // Variable to store the patient's ID

    public PAddAppointmentPage(String username) {
        super("Add Appointment");
        this.username = username;

        setSize(400, 500);  // Adjusted size
        setLocation(450, 200);

        l1 = new JLabel("Add Appointment");
        l1.setFont(new Font("Arial", Font.BOLD, 25));
        l1.setBounds(120, 10, 200, 30);
        add(l1);

        l2 = new JLabel("Patient Name:");
        l2.setBounds(30, 70, 100, 30);
        add(l2);

        patientNameField = new JTextField(); // For entering patient's name
        patientNameField.setBounds(140, 70, 200, 30);
        add(patientNameField);

        l6 = new JLabel("Disease:");
        l6.setBounds(30, 110, 100, 30);
        add(l6);

        cb3 = new JComboBox<>();
        cb3.setBounds(140, 110, 200, 30);
        cb3.addActionListener(e -> populateDoctors((String) cb3.getSelectedItem()));
        add(cb3);

        l4 = new JLabel("Doctor Name:");
        l4.setBounds(30, 150, 100, 30);
        add(l4);

        cb2 = new JComboBox<>();
        cb2.setBounds(140, 150, 200, 30);
        add(cb2);

        l5 = new JLabel("Date (YYYY-MM-DD):");
        l5.setBounds(30, 190, 150, 30);
        add(l5);

        tf = new JTextField();
        tf.setBounds(180, 190, 160, 30);
        add(tf);

        l6 = new JLabel("Time:");
        l6.setBounds(30, 230, 100, 30);
        add(l6);

        ta = new JTextArea();
        ta.setBounds(140, 230, 200, 50);
        add(ta);

        bt1 = new JButton("Add");
        bt1.setBackground(Color.BLACK);
        bt1.setForeground(Color.WHITE);
        bt1.setBounds(50, 300, 120, 40);
        bt1.addActionListener(e -> addAppointment());
        add(bt1);

        bt2 = new JButton("Back");
        bt2.setBackground(Color.BLACK);
        bt2.setForeground(Color.WHITE);
        bt2.setBounds(200, 300, 120, 40);
        bt2.addActionListener(e -> this.setVisible(false));
        add(bt2);

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        populateComboBoxes();
        setPatientInfo();
    }

    private void setPatientInfo() {
        try {
            ConnectionClass obj = new ConnectionClass();
            String query = "SELECT id, name FROM patient WHERE username = ?";
            PreparedStatement pst = obj.con.prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                patientId = rs.getInt("id");  // Store patient ID
                String patientName = rs.getString("name");
                patientNameField.setText(patientName); // Set the patient name field
            } else {
                patientNameField.setText(""); // Clear field if not found
            }

            rs.close();
            pst.close();
            obj.close();  // Close resources
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching patient info: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateComboBoxes() {
        try {
            ConnectionClass obj = new ConnectionClass();
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

    private void addAppointment() {
        String patientName = patientNameField.getText(); // Use JTextField for patient name
        String doctorName = (String) cb2.getSelectedItem();
        String date = tf.getText();
        String time = ta.getText();

        try {
            ConnectionClass obj = new ConnectionClass();
            String query = "INSERT INTO appointment(patient_id, patient_name, doctor_name, date, time) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pst = obj.con.prepareStatement(query);
            pst.setInt(1, patientId);  // Use patient ID
            pst.setString(2, patientName);
            pst.setString(3, doctorName);
            pst.setString(4, date);
            pst.setString(5, time);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Appointment added successfully!");
            this.setVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error adding appointment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

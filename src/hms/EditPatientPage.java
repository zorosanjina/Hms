package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class EditPatientPage extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5, l6, l7;
    JTextField tf1, tf2, tf3, tf4, tf5, tf6;
    JTextArea ta1;
    JButton bt1, bt2;
    String patientId;

    EditPatientPage(String id) {
        super("Edit Patient");
        this.patientId = id;

        setSize(400, 450);
        setLocation(450, 200);

        l1 = new JLabel("Edit Patient");
        l1.setFont(new Font("Arial", Font.BOLD, 25));
        l1.setBounds(120, 10, 200, 30);
        add(l1);

        l2 = new JLabel("Name:");
        l2.setBounds(30, 70, 100, 30);
        add(l2);

        tf1 = new JTextField();
        tf1.setBounds(140, 70, 200, 30);
        add(tf1);

        l3 = new JLabel("Age:");
        l3.setBounds(30, 110, 100, 30);
        add(l3);

        tf2 = new JTextField();
        tf2.setBounds(140, 110, 200, 30);
        add(tf2);

        l4 = new JLabel("Gender:");
        l4.setBounds(30, 150, 100, 30);
        add(l4);

        tf3 = new JTextField();
        tf3.setBounds(140, 150, 200, 30);
        add(tf3);

        l5 = new JLabel("Phone:");
        l5.setBounds(30, 190, 100, 30);
        add(l5);

        tf4 = new JTextField();
        tf4.setBounds(140, 190, 200, 30);
        add(tf4);

        l6 = new JLabel("Address:");
        l6.setBounds(30, 230, 100, 30);
        add(l6);

        ta1 = new JTextArea();
        ta1.setBounds(140, 230, 200, 50);
        add(ta1);

        l7 = new JLabel("ID:");
        l7.setBounds(30, 300, 100, 30);
        add(l7);

        tf5 = new JTextField();
        tf5.setBounds(140, 300, 200, 30);
        tf5.setEditable(false); // ID should not be editable
        add(tf5);

        bt1 = new JButton("Update");
        bt1.setBackground(Color.BLACK);
        bt1.setForeground(Color.WHITE);
        bt1.setBounds(50, 350, 120, 40);
        bt1.addActionListener(this);
        add(bt1);

        bt2 = new JButton("Cancel");
        bt2.setBackground(Color.BLACK);
        bt2.setForeground(Color.WHITE);
        bt2.setBounds(200, 350, 120, 40);
        bt2.addActionListener(this);
        add(bt2);

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        loadPatientDetails();
    }

    private void loadPatientDetails() {
        try {
            ConnectionClass obj = new ConnectionClass();
            String query = "SELECT * FROM patient WHERE id = ?";
            PreparedStatement pst = obj.con.prepareStatement(query);
            pst.setString(1, patientId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                tf1.setText(rs.getString("name"));
                tf2.setText(String.valueOf(rs.getInt("age")));
                tf3.setText(rs.getString("gender"));
                tf4.setText(rs.getString("phone"));
                ta1.setText(rs.getString("address"));
                tf5.setText(rs.getString("id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading patient details: " + ex.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            String name = tf1.getText();
            String age = tf2.getText();
            String gender = tf3.getText();
            String phone = tf4.getText();
            String address = ta1.getText();

            if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required");
            } else {
                try {
                    ConnectionClass obj = new ConnectionClass();
                    String query = "UPDATE patient SET name = ?, age = ?, gender = ?, phone = ?, address = ? WHERE id = ?";
                    PreparedStatement pst = obj.con.prepareStatement(query);
                    pst.setString(1, name);
                    pst.setInt(2, Integer.parseInt(age));
                    pst.setString(3, gender);
                    pst.setString(4, phone);
                    pst.setString(5, address);
                    pst.setString(6, patientId);

                    int result = pst.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Patient updated successfully");
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error updating patient");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage());
                }
            }
        } else if (e.getSource() == bt2) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new EditPatientPage("patientId"); 
    }
}

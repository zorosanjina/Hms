package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class AddPatientPage extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10;
    JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8;
    JTextArea ta;
    JButton bt1, bt2;

    AddPatientPage() {
        super("Add Patient");
        setSize(500, 600);
        setLocation(500, 110);

        l1 = new JLabel("Add Patient");
        l1.setFont(new Font("Arial", Font.BOLD, 25));
        l1.setBounds(150, 10, 200, 30);
        add(l1);

        l2 = new JLabel("Name:");
        l2.setBounds(30, 70, 100, 30);
        add(l2);

        tf1 = new JTextField();
        tf1.setBounds(200, 70, 250, 30);
        add(tf1);

        l3 = new JLabel("Username:");
        l3.setBounds(30, 110, 100, 30);
        add(l3);

        tf2 = new JTextField();
        tf2.setBounds(200, 110, 250, 30);
        add(tf2);

        l4 = new JLabel("Password:");
        l4.setBounds(30, 150, 100, 30);
        add(l4);

        tf3 = new JPasswordField();
        tf3.setBounds(200, 150, 250, 30);
        add(tf3);

        l5 = new JLabel("Gender:");
        l5.setBounds(30, 190, 100, 30);
        add(l5);

        tf4 = new JTextField();
        tf4.setBounds(200, 190, 250, 30);
        add(tf4);

        l6 = new JLabel("Phone:");
        l6.setBounds(30, 230, 100, 30);
        add(l6);

        tf5 = new JTextField();
        tf5.setBounds(200, 230, 250, 30);
        add(tf5);

        l7 = new JLabel("Date of Birth (YYYY-MM-DD):");
        l7.setBounds(30, 270, 200, 30);
        add(l7);

        tf6 = new JTextField();
        tf6.setBounds(250, 270, 200, 30);
        add(tf6);

        l8 = new JLabel("Age:");
        l8.setBounds(30, 310, 100, 30);
        add(l8);

        tf7 = new JTextField();
        tf7.setBounds(200, 310, 250, 30);
        add(tf7);

        l9 = new JLabel("Address:");
        l9.setBounds(30, 350, 100, 30);
        add(l9);

        ta = new JTextArea();
        ta.setBounds(200, 350, 250, 50);
        add(ta);

        l10 = new JLabel("Email ID:");
        l10.setBounds(30, 410, 100, 30);
        add(l10);

        tf8 = new JTextField();
        tf8.setBounds(200, 410, 250, 30);
        add(tf8);

        bt1 = new JButton("Add");
        bt1.setBackground(Color.BLACK);
        bt1.setForeground(Color.WHITE);
        bt1.setBounds(100, 500, 120, 40);
        bt1.addActionListener(this);
        add(bt1);

        bt2 = new JButton("Back");
        bt2.setBackground(Color.BLACK);
        bt2.setForeground(Color.WHITE);
        bt2.setBounds(250, 500, 120, 40);
        bt2.addActionListener(this);
        add(bt2);

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            String name = tf1.getText();
            String username = tf2.getText();
            String password = tf3.getText();
            String gender = tf4.getText();
            String phone = tf5.getText();
            String dob = tf6.getText();
            String age = tf7.getText();
            String address = ta.getText();
            String email = tf8.getText();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false);
                sdf.parse(dob);

                ConnectionClass obj = new ConnectionClass();
                String query = "INSERT INTO patient(name, username, password, gender, phone, dob, age, address, email) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = obj.con.prepareStatement(query);
                pst.setString(1, name);
                pst.setString(2, username);
                pst.setString(3, password);
                pst.setString(4, gender);
                pst.setString(5, phone);
                pst.setString(6, dob);
                pst.setInt(7, Integer.parseInt(age));
                pst.setString(8, address);
                pst.setString(9, email);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Patient added successfully!");
                this.setVisible(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding patient: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        } else if (e.getSource() == bt2) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddPatientPage();
    }
}

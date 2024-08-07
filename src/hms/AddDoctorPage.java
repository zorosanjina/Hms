package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class AddDoctorPage extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13;
    JTextField tf1, tf2, tf3, tf4, tf5, tf6, tf7, tf8, tf9;
    JTextArea ta1;
    JComboBox<String> cb1;
    JButton bt1, bt2;
    Font f1, f2;

    AddDoctorPage() {
        super("Add Doctor");
        setLocation(410, 80);
        setSize(600, 700);

        f1 = new Font("Arial", Font.BOLD, 18);
        f2 = new Font("Arial", Font.PLAIN, 14);

        l1 = new JLabel("Add Doctor");
        l1.setFont(new Font("Arial", Font.BOLD, 25));
        l1.setBounds(200, 30, 200, 30);
        add(l1);

        l2 = new JLabel("Name:");
        l2.setFont(f1);
        l2.setBounds(50, 80, 150, 30);
        add(l2);

        tf1 = new JTextField();
        tf1.setFont(f2);
        tf1.setBounds(200, 80, 300, 30);
        add(tf1);

        l3 = new JLabel("Username:");
        l3.setFont(f1);
        l3.setBounds(50, 120, 150, 30);
        add(l3);

        tf2 = new JTextField();
        tf2.setFont(f2);
        tf2.setBounds(200, 120, 300, 30);
        add(tf2);

        l4 = new JLabel("Password:");
        l4.setFont(f1);
        l4.setBounds(50, 160, 150, 30);
        add(l4);

        tf3 = new JPasswordField();
        tf3.setFont(f2);
        tf3.setBounds(200, 160, 300, 30);
        add(tf3);

        l5 = new JLabel("Specialization:");
        l5.setFont(f1);
        l5.setBounds(50, 200, 150, 30);
        add(l5);

        tf4 = new JTextField();
        tf4.setFont(f2);
        tf4.setBounds(200, 200, 300, 30);
        add(tf4);

        l6 = new JLabel("Gender:");
        l6.setFont(f1);
        l6.setBounds(50, 240, 150, 30);
        add(l6);

        String[] genders = { "Male", "Female", "Other" };
        cb1 = new JComboBox<>(genders);
        cb1.setFont(f2);
        cb1.setBounds(200, 240, 300, 30);
        add(cb1);

        l7 = new JLabel("Phone:");
        l7.setFont(f1);
        l7.setBounds(50, 280, 150, 30);
        add(l7);

        tf5 = new JTextField();
        tf5.setFont(f2);
        tf5.setBounds(200, 280, 300, 30);
        add(tf5);

        l8 = new JLabel("Email:");
        l8.setFont(f1);
        l8.setBounds(50, 320, 150, 30);
        add(l8);

        tf6 = new JTextField();
        tf6.setFont(f2);
        tf6.setBounds(200, 320, 300, 30);
        add(tf6);

        l9 = new JLabel("Date of Birth (YYYY-MM-DD):");
        l9.setFont(f1);
        l9.setBounds(50, 360, 250, 30);
        add(l9);

        tf7 = new JTextField();
        tf7.setFont(f2);
        tf7.setBounds(300, 360, 200, 30);
        add(tf7);

        l10 = new JLabel("Age:");
        l10.setFont(f1);
        l10.setBounds(50, 400, 150, 30);
        add(l10);

        tf8 = new JTextField();
        tf8.setFont(f2);
        tf8.setBounds(200, 400, 300, 30);
        add(tf8);

        l11 = new JLabel("Address:");
        l11.setFont(f1);
        l11.setBounds(50, 440, 150, 30);
        add(l11);

        ta1 = new JTextArea();
        ta1.setFont(f2);
        ta1.setBounds(200, 440, 300, 50);
        add(ta1);

        l12 = new JLabel("ID:");
        l12.setFont(f1);
        l12.setBounds(50, 500, 150, 30);
        add(l12);

        tf9 = new JTextField();
        tf9.setFont(f2);
        tf9.setBounds(200, 500, 300, 30);
        add(tf9);

        bt1 = new JButton("Add");
        bt1.setFont(f1);
        bt1.setBackground(Color.BLACK);
        bt1.setForeground(Color.WHITE);
        bt1.setBounds(150, 550, 100, 40);
        bt1.addActionListener(this);
        add(bt1);

        bt2 = new JButton("Cancel");
        bt2.setFont(f1);
        bt2.setBackground(Color.BLACK);
        bt2.setForeground(Color.WHITE);
        bt2.setBounds(300, 550, 100, 40);
        bt2.addActionListener(this);
        add(bt2);

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bt1) {
            String name = tf1.getText();
            String username = tf2.getText();
            String password = tf3.getText();
            String specialization = tf4.getText();
            String gender = cb1.getSelectedItem().toString();
            String phone = tf5.getText();
            String email = tf6.getText();
            String dob = tf7.getText();
            String age = tf8.getText();
            String address = ta1.getText();
            String id = tf9.getText();

            if (name.isEmpty() || username.isEmpty() || password.isEmpty() || specialization.isEmpty() || gender.isEmpty() || phone.isEmpty() || email.isEmpty() || dob.isEmpty() || age.isEmpty() || address.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(null, "All fields are required");
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    sdf.setLenient(false);
                    sdf.parse(dob);

                    ConnectionClass obj = new ConnectionClass();
                    String q = "insert into doctor (name, username, password, specialization, gender, phone, email, dob, age, address, id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst = obj.con.prepareStatement(q);
                    pst.setString(1, name);
                    pst.setString(2, username);
                    pst.setString(3, password);
                    pst.setString(4, specialization);
                    pst.setString(5, gender);
                    pst.setString(6, phone);
                    pst.setString(7, email);
                    pst.setString(8, dob);
                    pst.setString(9, age);
                    pst.setString(10, address);
                    pst.setString(11, id);

                    int result = pst.executeUpdate();
                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Doctor added successfully");
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error adding doctor");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
            }
        } else if (ae.getSource() == bt2) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new AddDoctorPage();
    }
}

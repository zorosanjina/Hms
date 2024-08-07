package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AddReceptionistPage extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5, l6, l7, l8;
    JTextField tf1, tf2, tf3, tf4, tf7;
    JPasswordField pf1;
    JTextArea ta;
    JButton bt1, bt2;

    AddReceptionistPage() {
        super("Add Receptionist");
        setSize(400, 500); // Increased size to accommodate new fields
        setLocation(450, 200);

        l1 = new JLabel("Add Receptionist");
        l1.setFont(new Font("Arial", Font.BOLD, 25));
        l1.setBounds(120, 10, 200, 30);
        add(l1);

        l2 = new JLabel("Username:");
        l2.setBounds(30, 70, 100, 30);
        add(l2);

        tf7 = new JTextField();
        tf7.setBounds(140, 70, 200, 30);
        add(tf7);

        l3 = new JLabel("Password:");
        l3.setBounds(30, 110, 100, 30);
        add(l3);

        pf1 = new JPasswordField();
        pf1.setBounds(140, 110, 200, 30);
        add(pf1);

        l4 = new JLabel("Name:");
        l4.setBounds(30, 150, 100, 30);
        add(l4);

        tf1 = new JTextField();
        tf1.setBounds(140, 150, 200, 30);
        add(tf1);

        l5 = new JLabel("Age:");
        l5.setBounds(30, 190, 100, 30);
        add(l5);

        tf2 = new JTextField();
        tf2.setBounds(140, 190, 200, 30);
        add(tf2);

        l6 = new JLabel("Phone:");
        l6.setBounds(30, 230, 100, 30);
        add(l6);

        tf3 = new JTextField();
        tf3.setBounds(140, 230, 200, 30);
        add(tf3);

        l7 = new JLabel("Address:");
        l7.setBounds(30, 270, 100, 30);
        add(l7);

        ta = new JTextArea();
        ta.setBounds(140, 270, 200, 50);
        add(ta);

        l8 = new JLabel("Gender:");
        l8.setBounds(30, 330, 100, 30);
        add(l8);

        tf4 = new JTextField();
        tf4.setBounds(140, 330, 200, 30);
        add(tf4);

        bt1 = new JButton("Add");
        bt1.setBackground(Color.BLACK);
        bt1.setForeground(Color.WHITE);
        bt1.setBounds(50, 380, 120, 40);
        bt1.addActionListener(this);
        add(bt1);

        bt2 = new JButton("Back");
        bt2.setBackground(Color.BLACK);
        bt2.setForeground(Color.WHITE);
        bt2.setBounds(200, 380, 120, 40);
        bt2.addActionListener(this);
        add(bt2);

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            String username = tf7.getText();
            String password = new String(pf1.getPassword());
            String name = tf1.getText();
            String age = tf2.getText();
            String phone = tf3.getText();
            String address = ta.getText();
            String gender = tf4.getText();

            try {
                ConnectionClass obj = new ConnectionClass();
                String query = "INSERT INTO receptionist(username, password, name, age, phone_no, address, gender) VALUES(?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = obj.con.prepareStatement(query);
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, name);
                pst.setInt(4, Integer.parseInt(age));
                pst.setString(5, phone);
                pst.setString(6, address);
                pst.setString(7, gender);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Receptionist added successfully!");
                this.setVisible(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding receptionist: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage());
            }
        } else if (e.getSource() == bt2) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddReceptionistPage();
    }
}

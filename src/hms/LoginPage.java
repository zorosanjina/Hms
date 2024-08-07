package hms;

import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class LoginPage extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4;
    JTextField tf;
    JPasswordField pf;
    JButton bt1, bt2;
    JComboBox<String> userTypeComboBox;

    LoginPage() {
        setTitle("Login");
        setBackground(Color.WHITE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set JFrame to maximize to full screen

        l1 = new JLabel();
        l1.setBounds(0, 0, 1530, 820); // Set bounds to match full screen

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("Hospital/icons/sethascope.jpg"));
        Image i = img.getImage().getScaledInstance(1530, 820, Image.SCALE_SMOOTH);
        ImageIcon img1 = new ImageIcon(i);
        l1.setIcon(img1);

        l2 = new JLabel("Login");
        l2.setBounds(720, 150, 500, 50);
        l2.setFont(new Font("Arial", Font.BOLD, 50));
        l2.setForeground(Color.BLACK);
        l1.add(l2);

        l3 = new JLabel("Username:");
        l3.setBounds(600, 250, 500, 40);
        l3.setFont(new Font("Arial", Font.BOLD, 30));
        l3.setForeground(Color.BLACK);
        l1.add(l3);

        l4 = new JLabel("Password:");
        l4.setBounds(600, 300, 500, 40);
        l4.setFont(new Font("Arial", Font.BOLD, 30));
        l4.setForeground(Color.BLACK);
        l1.add(l4);

        tf = new JTextField();
        tf.setBounds(800, 250, 250, 40);
        l1.add(tf);

        pf = new JPasswordField();
        pf.setBounds(800, 300, 250, 40);
        l1.add(pf);

        bt1 = new JButton("Login");
        bt1.setBackground(Color.BLACK);
        bt1.setForeground(Color.WHITE);
        bt1.setBounds(500, 350, 250, 40);
        l1.add(bt1);

        bt2 = new JButton("Back");
        bt2.setBackground(Color.BLACK);
        bt2.setForeground(Color.WHITE);
        bt2.setBounds(800, 350, 250, 40);
        l1.add(bt2);

        userTypeComboBox = new JComboBox<>(new String[]{"Admin", "Doctor", "Patient", "Receptionist"});
        userTypeComboBox.setBounds(800, 200, 250, 40);
        l1.add(userTypeComboBox);

        bt1.addActionListener(this);
        bt2.addActionListener(this);

        add(l1);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            try {
                ConnectionClass obj = new ConnectionClass();
                String username = tf.getText();
                String password = new String(pf.getPassword());
                String userType = (String) userTypeComboBox.getSelectedItem();

                String query = "";
                switch (userType) {
                    case "Admin":
                        query = "SELECT * FROM admin WHERE username='" + username + "' AND password='" + password + "'";
                        break;
                    case "Doctor":
                        query = "SELECT * FROM doctor WHERE username='" + username + "' AND password='" + password + "'";
                        break;
                    case "Patient":
                        query = "SELECT * FROM patient WHERE username='" + username + "' AND password='" + password + "'";
                        break;
                    case "Receptionist":
                        query = "SELECT * FROM receptionist WHERE username='" + username + "' AND password='" + password + "'";
                        break;
                }

                ResultSet rs = obj.stm.executeQuery(query);
                if (rs.next()) {
                    // Open appropriate page based on user type
                    switch (userType) {
                        case "Admin":
                            new AdminPage(username).setVisible(true);
                            break;
                        case "Doctor":
                            new DoctorPage(username).setVisible(true);
                            break;
                        case "Patient":
                            new PatientPage(username).setVisible(true);
                            break;
                        case "Receptionist":
                            new ReceptionistPage(username).setVisible(true);
                            break;
                    }
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "You have entered the wrong username or password");
                    tf.setText("");
                    pf.setText("");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == bt2) {
            this.setVisible(false);
            new Index().setVisible(true);
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}

package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Index extends JFrame implements ActionListener {

    JLabel l1, l2, l3;
    JButton b1, b2, b3, b4, b5; // Added b5 for the new button

    Index() {
        setTitle("Index Page");
        setBackground(Color.WHITE);
        setLayout(null);
        setSize(1530, 820);
        setLocation(0, 0);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel();
        l1.setBounds(0, 0, 1530, 820);
        l1.setLayout(null);
        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("Hospital/icons/img1.jpg"));
        Image i1 = img.getImage().getScaledInstance(1530, 820, Image.SCALE_SMOOTH);
        ImageIcon img1 = new ImageIcon(i1);
        l1.setIcon(img1);
        add(l1);

        l2 = new JLabel("Social medical group");
        l2.setBounds(670, 10, 1500, 80);
        l2.setFont(new Font("Times new Roman", Font.BOLD, 50));
        l2.setForeground(Color.BLACK);
        l1.add(l2);

        l3 = new JLabel("no.1 quality services");
        l3.setBounds(65, 180, 500, 40);
        l3.setFont(new Font("Times new Roman", Font.BOLD, 30));
        l3.setForeground(Color.BLACK);
        l1.add(l3);

        b1 = new JButton("Doctor");
        b1.setBounds(50, 270, 150, 50);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        l1.add(b1);

        b2 = new JButton("Patient");
        b2.setBounds(300, 270, 150, 50);
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        l1.add(b2);

        b3 = new JButton("Receptionist");
        b3.setBounds(50, 400, 150, 50);
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);
        l1.add(b3);

        b4 = new JButton("Admin");
        b4.setBounds(300, 400, 150, 50);
        b4.setBackground(Color.BLACK);
        b4.setForeground(Color.WHITE);
        l1.add(b4);

        // Add the new "Patient Register" button
        b5 = new JButton("Patient Register");
        b5.setBounds(175, 500, 200, 50);
        b5.setBackground(Color.BLACK);
        b5.setForeground(Color.WHITE);
        l1.add(b5);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this); // Add action listener for the new button

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1 || ae.getSource() == b2 || ae.getSource() == b3 || ae.getSource() == b4) {
            this.setVisible(false);
            new LoginPage(); // Redirect to LoginPage
        } else if (ae.getSource() == b5) {
            this.setVisible(false);
            new RegisterPage(); // Open the registration page
        }
    }

    public static void main(String[] args) {
        new Index();
    }
}

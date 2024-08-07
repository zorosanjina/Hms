package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class DoctorPage extends JFrame implements ActionListener {
    JLabel l1;
    Font f, f1;
    
    JButton btnViewDoctors, btnViewAppointment, btnExit, btnBack;
    private String doctorUsername; // Add this field

    DoctorPage(String username) {
        super("DoctorPage");
        this.doctorUsername = username; // Store the username

        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize JFrame to full screen

        ImageIcon ic = new ImageIcon(ClassLoader.getSystemResource("Hospital/icons/hms.jpg"));
        Image img = ic.getImage().getScaledInstance(1530, 820, Image.SCALE_SMOOTH);
        ImageIcon ic1 = new ImageIcon(img);
        l1 = new JLabel(ic1);
        l1.setLayout(null); // Use absolute positioning for buttons

        f = new Font("Lucida Fax", Font.BOLD, 20);
        f1 = new Font("MS UI Gothic", Font.BOLD, 18);

        // Create buttons
        btnViewDoctors = createButton("View Doctors", f1, 650, 100);
        btnViewAppointment = createButton("View Appointment", f1, 650, 200);
        btnExit = createButton("Exit", f1, 650, 300);
        btnBack = createButton("Back", f1, 650, 400);

        add(l1, BorderLayout.CENTER);

        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JButton createButton(String text, Font font, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBounds(x, y, 250, 60); // Set button size and position
        button.setBackground(Color.BLACK);
        button.setForeground(Color.YELLOW);
        button.addActionListener(this);
        l1.add(button);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();

        switch (command) {
            case "View Doctors":
                new DViewDoctorPage().setVisible(true);
                break;
            case "View Appointment":
                new DViewAppointmentPage(doctorUsername).setVisible(true); // Pass the stored username
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Back":
                this.setVisible(false);
                new Index().setVisible(true); // Replace with your actual Index page class
                break;
        }
    }

    public static void main(String[] args) {
        new DoctorPage("Doctor").setVisible(true);
    }
}

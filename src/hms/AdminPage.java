package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AdminPage extends JFrame implements ActionListener {
    JLabel l1;
    Font f1;

    JButton btnAddDoctor, btnViewDoctor, btnAddPatient, btnViewPatient, btnAddReceptionist, btnViewReceptionist, btnAddAppointment, btnViewAppointment, btnExit, btnBack;

    AdminPage(String adminname) {
        super("AdminPage");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Set JFrame to maximize to full screen

        ImageIcon ic = new ImageIcon(ClassLoader.getSystemResource("Hospital/icons/sethascope.jpg"));
        Image img = ic.getImage().getScaledInstance(1530, 820, Image.SCALE_SMOOTH);
        ImageIcon ic1 = new ImageIcon(img);
        l1 = new JLabel(ic1);
        l1.setLayout(null);

        f1 = new Font("MS UI Gothic", Font.BOLD, 18);

        btnAddDoctor = createButton("Add Doctor", f1, 450, 100);
        btnViewDoctor = createButton("View Doctor", f1, 850, 100);
        btnAddPatient = createButton("Add Patient", f1, 450, 200);
        btnViewPatient = createButton("View Patient", f1, 850, 200);
        btnAddReceptionist = createButton("Add Receptionist", f1, 450, 300);
        btnViewReceptionist = createButton("View Receptionist", f1, 850, 300);
        btnAddAppointment = createButton("Add Appointment", f1, 450, 400);
        btnViewAppointment = createButton("View Appointment", f1, 850, 400);
        btnExit = createButton("Exit", f1, 650, 500);
        btnBack = createButton("Back", f1, 650, 580); // Position and size the "Back" button

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
            case "Add Doctor":
                new AddDoctorPage().setVisible(true);
                break;
            case "View Doctor":
                new ViewDoctorPage().setVisible(true);
                break;
            case "Add Patient":
                new AddPatientPage().setVisible(true);
                break;
            case "View Patient":
                new ViewPatientPage().setVisible(true);
                break;
            case "Add Receptionist":
                new AddReceptionistPage().setVisible(true);
                break;
            case "View Receptionist":
                new ViewReceptionistPage().setVisible(true);
                break;
            case "Add Appointment":
                new AddAppointmentPage("admin").setVisible(true); // Pass the role as admin
                break;
            case "View Appointment":
                new ViewAppointmentPage().setVisible(true);
                break;
            case "Exit":
                System.exit(0);
                break;
            case "Back":
                new LoginPage().setVisible(true); // Redirect to the login page or appropriate page
                this.setVisible(false); // Hide the AdminPage
                break;
        }
    }

    public static void main(String[] args) {
        new AdminPage("Admin").setVisible(true);
    }
}

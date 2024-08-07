package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class DViewDoctorPage extends JFrame implements ActionListener {
    JTable table;
    JButton bt2;
    String[] columnNames = {"ID", "Name", "Username", "Specialization", "Gender", "Phone", "Email"};
    String[][] data = new String[20][7]; 
    int i = 0, j = 0;

    DViewDoctorPage() {
        super("View Doctor");
        setSize(800, 400);
        setLocation(350, 200);

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "select * from doctor";
            ResultSet rs = obj.stm.executeQuery(q);
            while (rs.next()) {
                data[i][j++] = rs.getString("id");
                data[i][j++] = rs.getString("name");
                data[i][j++] = rs.getString("username");
                data[i][j++] = rs.getString("specialization");
                data[i][j++] = rs.getString("gender");
                data[i][j++] = rs.getString("phone");
                data[i][j++] = rs.getString("email");
                i++;
                j = 0;
            }
            table = new JTable(data, columnNames);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane sp = new JScrollPane(table);
        add(sp);

        bt2 = new JButton("Close");
        bt2.setFont(new Font("Arial", Font.BOLD, 15));
        bt2.setBackground(Color.BLACK);
        bt2.setForeground(Color.WHITE);
        bt2.addActionListener(this);
        add(bt2, BorderLayout.NORTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bt2) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new DViewDoctorPage();
    }
}

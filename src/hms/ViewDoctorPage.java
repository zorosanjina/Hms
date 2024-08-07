package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ViewDoctorPage extends JFrame implements ActionListener {
    JTable table;
    JButton bt1, bt2;
    JTextField tf1;
    String[] columnNames = {"ID", "Name", "Username", "Specialization", "Gender", "Phone", "Email", "DOB", "Age", "Address"};
    String[][] data = new String[20][10];
    int i = 0, j = 0;

    ViewDoctorPage() {
        super("View Doctor");
        setSize(800, 400);
        setLocation(200, 200);

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
                data[i][j++] = rs.getString("dob");
                data[i][j++] = rs.getString("age");
                data[i][j++] = rs.getString("address");
                i++;
                j = 0;
            }
            table = new JTable(data, columnNames);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane sp = new JScrollPane(table);
        add(sp);

        bt1 = new JButton("Edit");
        bt1.setFont(new Font("Arial", Font.BOLD, 15));
        bt1.setBackground(Color.BLACK);
        bt1.setForeground(Color.WHITE);
        bt1.addActionListener(this);
        add(bt1, BorderLayout.SOUTH);

        bt2 = new JButton("Close");
        bt2.setFont(new Font("Arial", Font.BOLD, 15));
        bt2.setBackground(Color.BLACK);
        bt2.setForeground(Color.WHITE);
        bt2.addActionListener(this);
        add(bt2, BorderLayout.NORTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bt1) {
            int row = table.getSelectedRow();
            if (row != -1) {
                String id = (String) table.getValueAt(row, 0);
                new EditDoctorPage(id);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a doctor to edit.");
            }
        } else if (ae.getSource() == bt2) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new ViewDoctorPage();
    }
}

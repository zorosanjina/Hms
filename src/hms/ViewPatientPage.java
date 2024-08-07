package hms;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ViewPatientPage extends JFrame {
    JTable table;
    JButton bt1, bt2;
    JScrollPane scrollPane;
    Vector<Vector<Object>> data;
    Vector<String> columnNames;

    ViewPatientPage() {
        super("View Patients");
        setSize(800, 450);
        setLocation(450, 200);

        columnNames = new Vector<>();
        data = new Vector<>();

        columnNames.add("Name");
        columnNames.add("Age");
        columnNames.add("Gender");
        columnNames.add("Phone");
        columnNames.add("Address");
        columnNames.add("ID");

        try {
            ConnectionClass obj = new ConnectionClass();
            String query = "SELECT * FROM patient";
            ResultSet rs = obj.stm.executeQuery(query);

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("name"));
                row.add(rs.getInt("age"));
                row.add(rs.getString("gender"));
                row.add(rs.getString("phone"));
                row.add(rs.getString("address"));
                row.add(rs.getString("id"));

                data.add(row);
            }

            table = new JTable(data, columnNames);
            scrollPane = new JScrollPane(table);
            scrollPane.setBounds(20, 20, 740, 300);
            add(scrollPane);

            // Edit button
            bt1 = new JButton("Edit");
            bt1.setBounds(350, 330, 100, 30);
            bt1.setBackground(Color.BLACK);
            bt1.setForeground(Color.WHITE);
            bt1.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1)
                {
                    String patientId = table.getValueAt(selectedRow, 5).toString();
                    new EditPatientPage(patientId); 
                } else 
                {
                    JOptionPane.showMessageDialog(null, "Please select a patient to edit");
                }
            });
            add(bt1);
            bt2 = new JButton("Back");
            bt2.setBounds(470, 330, 100, 30);
            bt2.setBackground(Color.BLACK);
            bt2.setForeground(Color.WHITE);
            bt2.addActionListener(e -> this.setVisible(false));
            add(bt2);

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching patients: " + ex.getMessage());
        }

        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        new ViewPatientPage();
    }
}

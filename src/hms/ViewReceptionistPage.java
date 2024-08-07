package hms;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class ViewReceptionistPage extends JFrame {
    private JTable table;
    private Vector<String> columnNames;
    private Vector<Vector<Object>> data;

    public ViewReceptionistPage() {
        setTitle("View Receptionists");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        columnNames = new Vector<>();
        data = new Vector<>();

        columnNames.add("Name");
        columnNames.add("Age");
        columnNames.add("Gender");
        columnNames.add("Phone_no");
        columnNames.add("Address");

        loadData();

        table = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        setVisible(true);
    }

    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/hms";
        String dbUsername = "root";
        String dbPassword = "dhanushmm@2204";
        String query = "SELECT name,age,gender,phone_no,address FROM receptionist";

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getString("name"));
                row.add(rs.getInt("age")); 
                row.add(rs.getString("gender"));
                row.add(rs.getString("phone_no"));
                row.add(rs.getString("address"));

                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewReceptionistPage::new);
    }
}

package hms;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
public class ConnectionClass {
    Connection con;
    Statement stm;
    public ConnectionClass() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hms", "root", "dhanushmm@2204");
            stm = con.createStatement();
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found. Please include the JDBC driver in your project.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database or creating Statement.");
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return con;
    }
    public Statement getStatement() {
        return stm;
    }
    public void close() {
        try {
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing resources.");
            e.printStackTrace();
        }
    }

    class connection {

        static PreparedStatement prepareStatement(String query) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public connection() {
        }
    }
}

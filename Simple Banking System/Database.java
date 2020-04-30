package banking;

import java.sql.*;

public class Database {
    private static String url = "jdbc:sqlite:" + "card.s3db";
    private static Connection conn = null;
    Statement pstmt = null;
    /**
     * Connect to a sample database
     */
    Database(String filename) throws SQLException {
        conn = Connect(filename);
        pstmt = conn.createStatement();
    }
    public Connection Connect(String name) {
        try {
            // db parameters
            url = "jdbc:sqlite:" + name;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            // System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public void closeConnection(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void createNewTable() throws SQLException {
        // SQLite connection string

        // SQL statement for creating a new table
        String sql = String.format("CREATE TABLE IF NOT EXISTS card(\n" +
                "id INTEGER,\n" +
                "number TEXT,\n" +
                "pin TEXT,\n" +
                "balance INTEGER DEFAULT 0\n);");
        //System.out.println(sql);
             Statement stmt = conn.createStatement();
            //System.out.println("create table ok");
            // create a new table
            stmt.execute(sql);
        }
    public void insert(int id, String number, String pin, int balance) throws SQLException {
        String sql = String.format("INSERT INTO card(id,number,pin,balance)\n" +
                "VALUES("+id+","+"\'"+number+"\',\'"+pin+"\',"+balance+");");
            pstmt.execute(sql);

            // System.out.println("insert ok x");
    }
    public void deposit(String number,int depo) throws SQLException {

        String sql = "UPDATE card SET balance = "
                + depo +"\n WHERE number = "
                + "\'"+number+"\';";
        pstmt.execute(sql);
    }
    public void transferTo(String number,int transfer) throws SQLException {

        String sql = "UPDATE card SET balance = balance +"
                + transfer +"\n WHERE number = "
                +number+";";
        pstmt.execute(sql);
    }
    public boolean checkAccount(String recipient) throws SQLException {
        String sql = "SELECT count(*) FROM card WHERE number =" + recipient + ";";
        pstmt.execute(sql);

        Statement stmt  = conn.createStatement();
        ResultSet rs    = stmt.executeQuery(sql);

        int i = rs.getInt("count(*)");
        if(i == 1)
            return true;
        else
            return false;
    }
    public void closeAccount(String bankNumber) throws SQLException {
        String sql ="DELETE FROM card where number =\'" + bankNumber + "\';";
        pstmt.execute(sql);
    }

}
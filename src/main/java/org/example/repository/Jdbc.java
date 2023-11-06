package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import org.example.dto.Env;

public class Jdbc {


    public Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/quote_board?", Env.mySqlId, Env.mySqlPassword);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException();
        }
        return conn;
    }

    public void createTable() throws SQLException {
        String createTable = "CREATE TABLE quote ("
                + "id INT NOT NULL,\n "
                + "content VARCHAR(100) NOT NULL,\n "
                + "author VARCHAR(30) NOT NULL,\n "
                + "created DATETIME NOT NULL,\n "
                + ");";
        Connection conn = getConnection();
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(createTable);
    }
}

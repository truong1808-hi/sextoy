package com.toystore.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class DBContext {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cuahangbanhang?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "123456";
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
     public static void close(ResultSet rs, Statement st, Connection conn) {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
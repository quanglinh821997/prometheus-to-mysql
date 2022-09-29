package com.microtech.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://103.157.218.84:3306?autoCommit=false&rewriteBatchedStatements=true";
            String user = "root";
            String password = "Meocon@123";

            Connection connection = DriverManager.getConnection(url, user, password);

            return connection;
        } catch (Exception e) {
            System.out.println("Connect JDBC error: " + e);
        }

        return null;
    }
}

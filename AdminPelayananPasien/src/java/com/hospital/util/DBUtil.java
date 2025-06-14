package com.hospital.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // Ganti jika pakai user lain
    private static final String PASSWORD = "";     // Ganti jika ada password

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Driver MySQL Connector/J
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC MySQL tidak ditemukan", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Gagal koneksi ke database", e);
        }
    }
}

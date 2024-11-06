/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trackedu;

/**
 *
 * @author divya
 */
import java.io.*;
import java.sql.*;

public class FileDatabaseHelper {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/users";
    private static final String USER = "root";
    private static final String PASS = "admin";

    public static void saveFile(String fileName, File file) throws SQLException, IOException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "INSERT INTO uploaded_files (file_name, file_data) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, fileName);
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    pstmt.setBinaryStream(2, fileInputStream, (int) file.length());
                    pstmt.executeUpdate();
                }
            }
        }
    }

    public static InputStream getFileData(String fileName) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        String query = "SELECT file_data FROM uploaded_files WHERE file_name = ? ORDER BY id DESC LIMIT 1";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, fileName);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            return rs.getBinaryStream("file_data");
        }
        return null;
    }

    public static String getLatestFileName() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT file_name FROM uploaded_files ORDER BY id DESC LIMIT 1")) {

            if (rs.next()) {
                return rs.getString("file_name");
            }
            return null;
        }
    }
}
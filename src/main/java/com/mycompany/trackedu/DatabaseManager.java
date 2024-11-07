/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trackedu;

/**
 *
 * @author arshi
 */
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/attendance_db";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to close the connection
    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert attendance record
    public void insertAttendance(String studentId, String date, String status, String subject, String section) {
        String query = "INSERT INTO attendance (student_id, date, status, subject, section) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setString(2, date);
            stmt.setString(3, status);
            stmt.setString(4, subject);
            stmt.setString(5, section);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update attendance record
    public void updateAttendance(int id, String studentId, String date, String status) {
        String query = "UPDATE attendance SET student_id = ?, date = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setString(2, date);
            stmt.setString(3, status);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete attendance record by id
    public void deleteAttendance(int id) {
        String query = "DELETE FROM attendance WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all attendance records
    public ArrayList<String[]> getAllAttendance() {
        ArrayList<String[]> records = new ArrayList<>();
        String query = "SELECT * FROM attendance";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String[] record = new String[5];
                record[0] = rs.getString("student_id");
                record[1] = rs.getString("date");
                record[2] = rs.getString("status");
                record[3] = rs.getString("subject");
                record[4] = rs.getString("section");
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
    public String getAttendanceStatus(String studentId, String subject, String section, String date) {
    String attendanceStatus = "No Data"; // Default status when no record is found
    String query = "SELECT status FROM attendance WHERE student_id = ? AND subject = ? AND section = ? AND date = ?";

    try (PreparedStatement stmt = connection.prepareStatement(query)){
      

        // Set parameters
        stmt.setString(1, studentId);
        stmt.setString(2, subject);
        stmt.setString(3, section);
        stmt.setString(4, date);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            attendanceStatus = rs.getString("status"); // Get the status (Present/Absent)
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return attendanceStatus;
}
    public ArrayList<String[]> getAttendanceRecordsInRange(String studentId, String subject, String section, String startDate, String endDate) {
    ArrayList<String[]> records = new ArrayList<>();
    String query = "SELECT * FROM attendance WHERE student_id = ? AND subject = ? AND section = ? AND date BETWEEN ? AND ? ORDER BY date ASC";
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, studentId);
        stmt.setString(2, subject);
        stmt.setString(3, section);
        stmt.setString(4, startDate); // Starting date in yyyy-MM-dd format
        stmt.setString(5, endDate);   // Ending date in yyyy-MM-dd format

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String[] record = new String[2];
            record[0] = rs.getString("date");   // Date
            record[1] = rs.getString("status"); // Status (Present/Absent)
            records.add(record);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return records;
}


}
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
            System.err.println("Failed to connect to database: " + e.getMessage());
        }
    }

    // Method to close the connection
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            System.err.println("Failed to close database connection: " + e.getMessage());
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
            System.err.println("Failed to insert attendance: " + e.getMessage());
        }
    }

    // Update attendance record
public boolean updateAttendanceRow(String studentId, String date, String status, String subject, String section) {
    String query = "UPDATE attendance SET status = ? WHERE student_id = ? AND date = ? AND subject = ? AND section = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, status);
        stmt.setString(2, studentId);
        stmt.setString(3, date);
        stmt.setString(4, subject);
        stmt.setString(5, section);
        
        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0; // return true if update succeeded
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    // Delete attendance record by id
public void deleteAttendance(String studentId, String date, String subject, String section) {
    String query = "DELETE FROM attendance WHERE student_id = ? AND date = ? AND subject = ? AND section = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, studentId);  // Set studentId as a string
        stmt.setString(2, date);       // Set date as a string (ensure the format matches your database)
        stmt.setString(3, subject);    // Set subject as a string
        stmt.setString(4, section);    // Set section as a string
        stmt.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Failed to delete attendance: " + e.getMessage());
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
            System.err.println("Failed to retrieve attendance records: " + e.getMessage());
        }
        return records;
    }
    public ArrayList<String[]> getAttendanceForSubjectAndSection(String subject, String section) {
    ArrayList<String[]> records = new ArrayList<>();
    String query = "SELECT student_id, date, status FROM attendance WHERE subject = ? AND section = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
         
        
        stmt.setString(1, subject);
        stmt.setString(2, section);
        
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            String studentId = rs.getString("student_id");
            String date = rs.getString("date");
            String status = rs.getString("status");
            records.add(new String[] {studentId, date, status});
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return records;
}

    // Retrieve specific attendance status
    public String getAttendanceStatus(String studentId, String subject, String section, String date) {
        String attendanceStatus = "No Data";
        String query = "SELECT status FROM attendance WHERE student_id = ? AND subject = ? AND section = ? AND date = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setString(2, subject);
            stmt.setString(3, section);
            stmt.setString(4, date);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                attendanceStatus = rs.getString("status");
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve attendance status: " + e.getMessage());
        }

        return attendanceStatus;
    }

    // Retrieve attendance records within a date range
    public ArrayList<String[]> getAttendanceRecordsInRange(String studentId, String subject, String section, String startDate, String endDate) {
        ArrayList<String[]> records = new ArrayList<>();
        String query = "SELECT * FROM attendance WHERE student_id = ? AND subject = ? AND section = ? AND date BETWEEN ? AND ? ORDER BY date ASC";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, studentId);
            stmt.setString(2, subject);
            stmt.setString(3, section);
            stmt.setString(4, startDate);
            stmt.setString(5, endDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String[] record = new String[2];
                record[0] = rs.getString("date");
                record[1] = rs.getString("status");
                records.add(record);
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve attendance records in range: " + e.getMessage());
        }
        return records;
    }
}

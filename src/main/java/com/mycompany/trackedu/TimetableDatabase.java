/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trackedu;

/**
 *
 * @author divya
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimetableDatabase {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/users";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "admin";
    private Connection conn;

    private Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Insert timetable data (optional)
    public void insertTimetableData(String timeSlot, String monday, String tuesday, String wednesday, String thursday, String friday) {
        String query = "INSERT INTO timetableA (time_slot, monday, tuesday, wednesday, thursday, friday) VALUES "
                + "('8:00 - 9:00', '', '', '', '', ''),\n" +
"    ('9:00 - 10:00', '', '', '', '', ''),\n" +
"    ('10:00 - 11:00', '', '', '', '', ''),\n" +
"    ('11:00 - 12:00', '', '', '', '', ''),\n" +
"    ('12:00 - 1:00', '', '', '', '', ''),\n" +
"    ('1:00 - 2:00', '', '', '', '', ''),\n" +
"    ('2:00 - 3:00', '', '', '', '', ''),\n" +
"    ('3:00 - 4:00', '', '', '', '', ''),\n" +
"    ('4:00 - 5:00', '', '', '', '', '')";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, timeSlot);
            pstmt.setString(2, monday);
            pstmt.setString(3, tuesday);
            pstmt.setString(4, wednesday);
            pstmt.setString(5, thursday);
            pstmt.setString(6, friday);
            pstmt.executeUpdate();
            System.out.println("New timetable row inserted for time slot: " + timeSlot);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch timetable data
    public List<String[]> getTimetableData() {
        List<String[]> timetable = new ArrayList<>();
        String query = "SELECT * FROM timetableA";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String timeSlot = rs.getString("time_slot");
                String monday = rs.getString("monday");
                String tuesday = rs.getString("tuesday");
                String wednesday = rs.getString("wednesday");
                String thursday = rs.getString("thursday");
                String friday = rs.getString("friday");

                timetable.add(new String[]{timeSlot, monday, tuesday, wednesday, thursday, friday});
            }
            System.out.println("Data retrieved from database: " + timetable.size() + " rows"); // Log data size
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timetable;
    }

    // Update timetable data
    public void updateTimetableData(String timeSlot, String monday, String tuesday, String wednesday, String thursday, String friday) {
        String query = "UPDATE timetableA SET monday = ?, tuesday = ?, wednesday = ?, thursday = ?, friday = ? WHERE time_slot = ?";

        try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, monday);
            stmt.setString(2, tuesday);
            stmt.setString(3, wednesday);
            stmt.setString(4, thursday);
            stmt.setString(5, friday);
            stmt.setString(6, timeSlot);
            stmt.executeUpdate();
            System.out.println("Timetable updated for time slot: " + timeSlot);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

    // Delete timetable data (optional)
    public void deleteTimetableData(String timeSlot) {
        String query = "DELETE FROM timetableA WHERE time_slot = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, timeSlot);
            pstmt.executeUpdate();
            System.out.println("Timetable row deleted for time slot: " + timeSlot);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Close the connection (optional)
    
}


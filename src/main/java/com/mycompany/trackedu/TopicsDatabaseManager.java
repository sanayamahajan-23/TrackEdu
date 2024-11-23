/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trackedu;

/**
 *
 * @author HP
 */
import java.sql.*;
import java.util.ArrayList;

public class TopicsDatabaseManager {
    private Connection connection;

    public TopicsDatabaseManager() {
        try {
       
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/topics", "root", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addTopic(String subject, String section, String topic) {
        String query = "INSERT INTO topics (subject, section, topic) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subject);
            stmt.setString(2, section);
            stmt.setString(3, topic);
            return stmt.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Topic> getTopics(String subject, String section) {
        ArrayList<Topic> topics = new ArrayList<>();
        String query = "SELECT id, subject, section, topic FROM topics WHERE subject = ? AND section = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, subject);
            stmt.setString(2, section);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String topicName = rs.getString("topic");
                topics.add(new Topic(id, subject, section, topicName)); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topics;
    }

    public boolean updateTopic(int id, String newTopic) {
        String query = "UPDATE topics SET topic = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newTopic);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeTopic(int id) {
        String query = "DELETE FROM topics WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

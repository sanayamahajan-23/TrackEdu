/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trackedu;

/**
 *
 * @author HP
 */
public class Topic {
    private int id;
    private String subject;
    private String section;
    private String topic;

    public Topic(int id, String subject, String section, String topic) {
        this.id = id;
        this.subject = subject;
        this.section = section;
        this.topic = topic;
    }

    public int getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return topic; // Display topic name
    }
}

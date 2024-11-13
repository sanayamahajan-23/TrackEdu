/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.trackedu;

/**
 *
 * @author divya
 */
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
public class FileUploader extends javax.swing.JFrame {
    private  JButton uploadButton;
    private  JButton saveButton;
    private JLabel fileLabel;
    private File selectedFile;
    /**
     * Creates new form FileUploader
     */
    public FileUploader() {
           setTitle("File Uploader");
            getContentPane().setBackground(new Color(0xCAE9F5));
        setSize(817, 478);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("<");
        JMenuItem backItem = new JMenuItem("Back");

        // Add action for "Back" menu item
        backItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Code to go back to the home page
                // Assuming HomePage is the class for the home page JFrame
               java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new homet().setVisible(true);
            }
        });
                dispose(); // Close the FileUploader window
            }
        });

        menu.add(backItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        fileLabel = new JLabel("No file selected");
        
        add(fileLabel);

        uploadButton = new JButton("Upload File");
        uploadButton.setBounds(20, 60, 120, 30);
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(null);
                if (option == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    fileLabel.setText("File: " + selectedFile.getName());
                }
            }
        });

        // Action listener for Save button
         add(uploadButton);

        saveButton = new JButton("Save File");
        saveButton.setBounds(160, 60, 120, 30);
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    try {
                        FileDatabaseHelper.saveFile(selectedFile.getName(), selectedFile);
                        JOptionPane.showMessageDialog(null, "File saved successfully!");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No file selected!");
                }
            }
        });
        add(saveButton);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileUploader uploader = new FileUploader();
            uploader.setVisible(true);
        });
    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
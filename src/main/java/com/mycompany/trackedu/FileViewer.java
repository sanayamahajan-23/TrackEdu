/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.trackedu;

/**
 *
 * @author divya
 */
import javax.swing.*;
import java.awt.*;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.*;
import java.sql.SQLException;
public class FileViewer extends javax.swing.JFrame {
    private JLabel linkLabel;
    private static final String SAVE_DIRECTORY = System.getProperty("user.home") + "/Documents/ProjectFiles/";
    /**
     * Creates new form FileViewer
     */
    public FileViewer() {
        setTitle("File Viewer");
        setSize(800, 400);
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
                String username = null;
                new home(username).setVisible(true);
            }
        });
                dispose(); // Close the FileUploader window
            }
        });

        menu.add(backItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        linkLabel = new JLabel();
        linkLabel.setForeground(Color.BLUE.darker());
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        linkLabel.setText("Click here to open the PDF");

        add(linkLabel);
        displayPDFLink();
    }

    // Method to load file from the database and display it
     private void displayPDFLink() {
        try {
            // Retrieve the latest file from the database
            String fileName = FileDatabaseHelper.getLatestFileName();
            if (fileName != null && fileName.toLowerCase().endsWith(".pdf")) {
                InputStream fileData = FileDatabaseHelper.getFileData(fileName);

                // Ensure the save directory exists
                Path saveDir = Paths.get(SAVE_DIRECTORY);
                if (!Files.exists(saveDir)) {
                    Files.createDirectories(saveDir);
                }

                // Define the file path and save it permanently in the designated folder
                Path filePath = saveDir.resolve(fileName);
                Files.copy(fileData, filePath, StandardCopyOption.REPLACE_EXISTING);

                // Set up the clickable link to open the saved file
                linkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        try {
                            Desktop.getDesktop().open(filePath.toFile());
                        } catch (IOException e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Could not open file: " + e.getMessage());
                        }
                    }
                });
            } else {
                linkLabel.setText("No PDF file found in the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 855, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileViewer fileViewer = new FileViewer();
            fileViewer.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
}

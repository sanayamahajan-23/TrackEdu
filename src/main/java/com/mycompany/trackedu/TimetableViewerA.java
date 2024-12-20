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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
public class TimetableViewerA extends javax.swing.JFrame {

    /**
     * Creates new form TimetableViewerA
     */
      private JTable table;
    private DefaultTableModel model;
   private TimetableDatabase db;
    public TimetableViewerA() {
        setTitle("Timetable Viewer");
        getContentPane().setBackground(new Color(0xCAE9F5));
        setSize(817, 477);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
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

        // Initialize Database Connection
        db = new TimetableDatabase();
        // Initialize table with column names
        String[] columns = {"Time Slot", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setEnabled(false); // Make table non-editable

        // Populate table with data
        loadData();
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Set up the table model
    private void loadData() {
        List<String[]> timetableData = db.getTimetableData();

        if (timetableData.isEmpty()) {
            System.out.println("No data available to display.");
        } else {
            for (String[] row : timetableData) {
                model.addRow(row);
            }
            System.out.println("Table loaded with " + timetableData.size() + " rows.");
        }
    }

    // Load timetable data from the database into JTable
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 839, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TimetableViewerA viewer = new TimetableViewerA();
            viewer.setVisible(true);  // Ensure frame is set to visible
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}

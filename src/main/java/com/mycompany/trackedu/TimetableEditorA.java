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
import java.awt.event.*;
import java.util.List;
public class TimetableEditorA extends javax.swing.JFrame {
    private JTable table;
    private DefaultTableModel model;
     private TimetableDatabase db;
    /**
     * Creates new form TimetableEditorA
     */
    public TimetableEditorA() {
         setTitle("Timetable Editor");
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
                new homet().setVisible(true);
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
        initializeTableModel();
        loadTimetableData();
        adjustColumnWidths();

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveChanges());
        add(saveButton, BorderLayout.SOUTH);
        validate();
        setVisible(true);
        

    }

    // Connect to the database
        private void initializeTableModel() {
        String[] columns = {"Time Slot", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;  // Make cells editable, except for the time slot
            }
        };
           
        
         table = new JTable(model);
         table.setRowHeight(30); // Set row height for cell visibility
        table.setShowGrid(true); 
        table.setGridColor(Color.LIGHT_GRAY);  // Add a grid color for clarity
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Load timetable data from the database into JTable
    private void loadTimetableData() {
        List<String[]> timetableData = db.getTimetableData();
        for (String[] row : timetableData) {
            model.addRow(row);
        }
    }
     private void adjustColumnWidths() {
        if (table != null) {
            // Set preferred column widths for better visibility
            table.getColumnModel().getColumn(0).setPreferredWidth(100); // Time Slot
            for (int i = 1; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(150); // Other days
            }
        }
    }

    // Save updated data back to the database
    private void saveChanges() {
        for (int i = 0; i < model.getRowCount(); i++) {
            String timeSlot = model.getValueAt(i, 0).toString();
            String monday = model.getValueAt(i, 1).toString();
            String tuesday = model.getValueAt(i, 2).toString();
            String wednesday = model.getValueAt(i, 3).toString();
            String thursday = model.getValueAt(i, 4).toString();
            String friday = model.getValueAt(i, 5).toString();

            db.updateTimetableData(timeSlot, monday, tuesday, wednesday, thursday, friday);
        }
        JOptionPane.showMessageDialog(this, "Changes saved successfully!");
    
    
        
    }
    // Example of adding a new row in TimetableEditorA


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
            .addGap(0, 774, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 515, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
             SwingUtilities.invokeLater(TimetableEditorA::new);
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

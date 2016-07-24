package arms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import arms.api.CourseInstance;
import arms.dataAccess.DbActions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewPastRequests {

	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewPastRequests window = new ViewPastRequests();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ViewPastRequests() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 726, 588);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblYourPastSchedule = new JLabel("Your Past Schedule Requests");
		lblYourPastSchedule.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourPastSchedule.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblYourPastSchedule.setBounds(217, 29, 278, 60);
		frame.getContentPane().add(lblYourPastSchedule);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(36, 99, 646, 404);
		frame.getContentPane().add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JButton btnCloseButton = new JButton("Close");
		btnCloseButton.setBounds(595, 515, 89, 23);
		frame.getContentPane().add(btnCloseButton);
	}
}

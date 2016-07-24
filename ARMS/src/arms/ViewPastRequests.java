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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import arms.api.CourseInstance;
import arms.api.ScheduleRequest;
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
	public static void NewScreen(final String stdID) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewPastRequests window = new ViewPastRequests(stdID);
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
	public ViewPastRequests(String stdID) {
		initialize(stdID);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String stdID) {
		frame = new JFrame();
		frame.setBounds(100, 100, 997, 593);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblYourPastSchedule = new JLabel("Your Past Schedule Requests");
		lblYourPastSchedule.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourPastSchedule.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblYourPastSchedule.setBounds(371, 28, 278, 60);
		frame.getContentPane().add(lblYourPastSchedule);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(36, 99, 917, 404);
		frame.getContentPane().add(scrollPane_1);
		
		DefaultTableModel model = new DefaultTableModel();
		
		table_1 = new JTable(model);
		scrollPane_1.setViewportView(table_1);		
		
		// Add columns to model
		model.addColumn("Student Request ID");
		model.addColumn("Courses Requested");
		
		List<ScheduleRequest> pastRequests = DbActions.getScheduleRequests(Integer.parseInt(stdID), (-1));
		
		// Create a list of all courses in each Course Request ID
		String strCourseRequests = null;
		HashMap<Integer, Integer> requestedCourses = new HashMap<Integer, Integer>();
		
		for (ScheduleRequest schedReq : pastRequests){
			
			strCourseRequests = null;
			
			requestedCourses = schedReq.getRequestedCourses();
			
			int counter = 0;
			
			for (Map.Entry<Integer, Integer> entry : requestedCourses.entrySet()){
				
				int key = entry.getKey();
				
				String courseName = DbActions.getCourseName(key);				
				
				if (counter > 0){
					strCourseRequests += "; " + courseName;
				} else {
					strCourseRequests = courseName;
				}		
				
				counter++;
			}	
			
			model.addRow(new Object[] { schedReq.getSRID(), strCourseRequests });                               

		}
		
		table_1.getColumnModel().getColumn(0).setMinWidth(125);
		table_1.getColumnModel().getColumn(0).setMaxWidth(125);

		
		JButton btnCloseButton = new JButton("Close");
		btnCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCloseButton.setBounds(864, 514, 89, 23);
		frame.getContentPane().add(btnCloseButton);
	}
}

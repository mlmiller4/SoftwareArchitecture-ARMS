package arms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
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
		
		DefaultTableModel model = new DefaultTableModel();
		
		table_1 = new JTable(model);
		scrollPane_1.setViewportView(table_1);		
		
		// Add columns to model
		model.addColumn("Student Request ID");
		model.addColumn("Courses Requested");
		
		List<ScheduleRequest> pastRequests = DbActions.getScheduleRequests(Integer.parseInt(stdID), (-1));
		
		
		String strCourseRequests = null;
		HashMap<Integer, Integer> requestedCourses = new HashMap<Integer, Integer>();
		
		for (ScheduleRequest schedReq : pastRequests){
			
			strCourseRequests = null;
			
			requestedCourses = schedReq.getRequestedCourses();
			
			for (int i=2; i<requestedCourses.size(); i++){
				
				Integer myHash = requestedCourses.get(i);
				
				String course = requestedCourses.get(i).toString();
				
				if (i>0){
					strCourseRequests += ", " + course;
				} else {
					strCourseRequests = course;
				}				
			}		
			
			model.addRow(new Object[] { schedReq.getSRID(), strCourseRequests });                               

		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JButton btnCloseButton = new JButton("Close");
		btnCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCloseButton.setBounds(595, 515, 89, 23);
		frame.getContentPane().add(btnCloseButton);
	}
}

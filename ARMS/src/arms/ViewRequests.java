package arms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.List;

import javax.swing.*;

import arms.api.*;
import arms.dataAccess.*;

public class ViewRequests extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;

	private DbActions dbactions = new DbActions();
	//private List<ScheduleRequest> scheduleRequests = dbactions.getScheduleRequests(studentId, courseId);
	//private List<Student> students = dbactions.getStudents(); 
	//private List<CourseInstance> catalog = dbactions.getCatalog();
	
	private CourseInstance updateCourse = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewRequests frame = new ViewRequests();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;

	/**
	 * Create the frame.
	 */
	public ViewRequests() {
		connection = sqliteConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 981, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblViewScheduleRequests = new JLabel("View Schedule Requests");
		lblViewScheduleRequests.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewScheduleRequests.setForeground(Color.BLACK);
		lblViewScheduleRequests.setFont(new Font("Dialog", Font.BOLD, 20));
		lblViewScheduleRequests.setBounds(115, 0, 682, 49);
		contentPane.add(lblViewScheduleRequests);

		JButton btnLoadTable = new JButton("Load Table");
		btnLoadTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			}
		});
		btnLoadTable.setBounds(12, 80, 117, 25);
		contentPane.add(btnLoadTable);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(158, 126, 718, 359);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		PopulateTable();

	}

	// TODO: complete method
	// Make combo box to select student or course...based on selection call the helper object
	// Allow to select all students or all courses
	public void PopulateTable() {
		// headers for the table
		Object[] columns = { "Student ID", "SR ID", "Course ID",
				"Semester", "Class Size", "Remaining Seats", "Submit Time" };
		DefaultTableModel model = new DefaultTableModel(new Object[0][0], columns);
		for (Integer i = 0; i < 5; i++)
		{
			Object[] o = new Object[7];
			o[0] = i.toString();
			o[1] = i.toString();
			o[2] = i.toString();
			o[3] = "Fall 2016";
			o[4] = "100";
			o[5] = "50";
			o[6] = "2016-07-20 13:55:00:123";
			model.addRow(o);
		}
		table.setModel(model);
		
	}
	
	//TODO: Create filter based on Student object and CourseInstance
}

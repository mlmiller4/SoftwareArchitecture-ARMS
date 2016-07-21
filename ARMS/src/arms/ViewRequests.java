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

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.*;

import arms.api.*;
import arms.dataAccess.*;

public class ViewRequests extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JTextField studentIdField;
	private JTextField courseIdField;
	
	//private List<ScheduleRequest> scheduleRequests = dbactions.getScheduleRequests(studentId, courseId);
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
		setBounds(100, 100, 883, 600);
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

		scrollPane = new JScrollPane();
		scrollPane.setBounds(68, 166, 718, 359);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		//Hidden Field to store student id
		studentIdField = new JTextField();
		studentIdField.setVisible(false);
		
		//Hidden Field to store course name
		courseIdField = new JTextField();
		courseIdField.setVisible(false);
		
		String [] studentList = CommonFunctions.getStudentList();
		JComboBox<String[]> students = new JComboBox(studentList);
		students.setSelectedItem("-SELECT-");
		students.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox<String> cb = (JComboBox) arg0.getSource();
				String student = (String) cb.getSelectedItem();
				studentIdField.setText(student);
			}
		});
		students.setBounds(214, 60, 124, 20);
		contentPane.add(students);
		
		String [] courseList = CommonFunctions.getCourseList();
		JComboBox courses = new JComboBox(courseList);
		courses.setSelectedItem("-SELECT-");
		courses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JComboBox<String> cb = (JComboBox) arg0.getSource();
				String course = (String) cb.getSelectedItem();
				courseIdField.setText(course);
			}
		});
		courses.setBounds(214, 98, 124, 20);
		contentPane.add(courses);
		
		JLabel lblSelectByStudents = new JLabel("Filter by students:");
		lblSelectByStudents.setBounds(68, 63, 124, 14);
		contentPane.add(lblSelectByStudents);
		
		JLabel lblFilterByCourse = new JLabel("Filter by course:");
		lblFilterByCourse.setBounds(68, 101, 124, 14);
		contentPane.add(lblFilterByCourse);
		
		JButton btnLoadSchedules = new JButton("Load Schedules");
		btnLoadSchedules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<ScheduleRequest> requests = null;
				if ( studentIdField.getText().equals("-SELECT-") && courseIdField.getText().equals("-SELECT-"))
				{
					requests = DbActions.getAllScheduleRequests();
				}
				else if (studentIdField.getText().equals("-SELECT-") && !courseIdField.getText().equals("-SELECT-"))
				{
					Integer cId = new Integer(0);
					cId.parseInt(courseIdField.getText());
					requests = DbActions.getCourseScheduleRequests(cId);
				}
				else if (!studentIdField.getText().equals("-SELECT-") && courseIdField.getText().equals("-SELECT-"))
				{
					Integer sId = new Integer(0);
					sId.parseInt(studentIdField.getText());
					requests = DbActions.getStudentScheduleRequests(sId);
				}
				getScheduleTableEntries(requests);
			}
		});
		btnLoadSchedules.setBounds(65, 132, 127, 23);
		contentPane.add(btnLoadSchedules);
		//PopulateTable();

	}

	private void getScheduleTableEntries(List<ScheduleRequest> requests)
	{
		// headers for the table
		Object[] columns = { "Student ID", "SR ID", "Course ID",
				"Semester", "Class Size", "Remaining Seats", "Submit Time" };
		DefaultTableModel model = new DefaultTableModel(new Object[0][0], columns);
		
		if ( requests != null)
		{
			for (ScheduleRequest schedule : requests)
			{
				if ( schedule != null)
				{
					for (HashMap.Entry<Integer, Integer> entry : schedule.getRequestedCourses().entrySet())
					{
						Object[] o = new Object[7];
						o[0] = schedule.getStudentId();
						o[1] = schedule.getSRID();
						o[2] = entry.getKey();
						CourseInstance instance = CommonFunctions.getCourseInstanceById(entry.getValue());
						if ( instance != null)
						{
							o[3] = instance.getSemester();
							o[4] = instance.getClassSize();
							o[5] = instance.getRemSeats();
						}
						o[6] = schedule.getSubmitTime();
						model.addRow(o);
					}
				}
			}
		}
		table.setModel(model);
	}	
}
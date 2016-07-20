package arms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.sql.*;
import java.util.ArrayList;

public class UpdateCourse extends JFrame {

	private JPanel contentPane;
	private JTextField titleField;
	private JTextField classroomSize;
	private JTextField idField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateCourse frame = new UpdateCourse();
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
	public UpdateCourse() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 566);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JLabel lblCourseCatalog = new JLabel("Update Course");
		lblCourseCatalog.setForeground(Color.BLACK);
		lblCourseCatalog.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblCourseCatalog.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseCatalog.setBounds(12, 0, 682, 49);
		contentPane.add(lblCourseCatalog);

		JLabel lblCourseId = new JLabel("Course Id:");
		lblCourseId.setBounds(50, 83, 90, 15);
		contentPane.add(lblCourseId);

		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(50, 127, 90, 15);
		contentPane.add(lblTitle);

		titleField = new JTextField();
		titleField.setBounds(182, 125, 274, 19);
		contentPane.add(titleField);
		titleField.setColumns(10);

		idField = new JTextField();
		idField.setVisible(false);

		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setBounds(50, 161, 90, 15);
		contentPane.add(lblSemester);

		connection = sqliteConnection.dbConnector();

		// TBD: Grab list of course Ids and store into an array of strings
		// This will be used to populate the courseId combo box
		ArrayList<String> data = getCourseList();
		String[] coureList = data.toArray(new String[data.size()]);
		JComboBox courseId = new JComboBox(courseList);
		courseId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String course = (String) cb.getSelectedItem();
				String courseName = new String();
				Integer idcourse = 0;
				// Once course is selected populate the title text field
				// This is retrieved from the Courses table
				try {
					String sqlst = "select * from Courses where Id=?";
					PreparedStatement pst;
					pst = connection.prepareStatement(sqlst);
					pst.setString(1, course);
					ResultSet rs = pst.executeQuery();

					while (rs.next()) {
						idcourse = rs.getInt("Id");
						courseName = rs.getString("Name");
					}
					rs.close();
					pst.close();

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				titleField.setText(courseName);
				idField.setText(idcourse.toString());
			}
		});
		courseId.setBounds(182, 78, 153, 24);
		contentPane.add(courseId);

		JLabel lblClassroomSize = new JLabel("Classroom Size:");
		lblClassroomSize.setBounds(50, 197, 114, 15);
		contentPane.add(lblClassroomSize);

		classroomSize = new JTextField();
		classroomSize.setColumns(10);
		classroomSize.setBounds(182, 195, 105, 19);
		contentPane.add(classroomSize);

		// TBD: Grab a list of semester Ids valid for a specific course and
		// store into an array of strings
		// This will be used to populate the semesterId combo box
		ArrayList<Integer> offering = getCourseOffering(Integer
				.parseInt(idField.getText()));
		JComboBox semesterId = new JComboBox((ComboBoxModel) offering);
		semesterId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				// Once semester is selected, populate the classroom size text
				// field
				// with the existing data
			}
		});
		semesterId.setBounds(182, 156, 153, 24);
		contentPane.add(semesterId);

		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				// TBD: Update CourseInstance object
				arms.api.CourseInstance courseInstance = null;
			}
		});
		btnUpdate.setBounds(50, 237, 117, 25);
		contentPane.add(btnUpdate);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				contentPane.setVisible(false);
				dispose();
				AdminFrame af = new AdminFrame();
				af.setVisible(true);
			}
		});
		btnBack.setBounds(182, 237, 117, 25);
		contentPane.add(btnBack);
	}

	public ArrayList<String> getCourseList() {
		ArrayList<String> courses = new ArrayList<String>();
		// Check user name and password for student log in
		try {
			String query = "select CourseID, Name from Courses";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				courses.add(rs.getString("CourseID") + rs.getString("Name"));
			}
			rs.close();
			pst.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return courses;
	}

	public ArrayList<String> getCourseOffering(Integer idcourse) {
		ArrayList<String> offering = new ArrayList<Integer>();
		// Check user name and password for student log in
		try {
			String query = "select SemesterId from CourseOfferings where CourseId=?";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setInt(1, idcourse);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				offering.add(rs.getInt("SemesterId"));
			}
			rs.close();
			pst.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return offering;
	}
}

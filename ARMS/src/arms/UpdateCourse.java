package arms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JButton;

public class UpdateCourse extends JFrame {

	private JPanel contentPane;
	private JTextField titleField;
	private JTextField classroomSize;

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

		// TBD: Grab list of course Ids and store into an array of strings
		// This will be used to populate the courseId combo box
		JComboBox courseId = new JComboBox();
		courseId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Once course is selected populate the title text field
				// with the existing data
			}
		});
		courseId.setBounds(182, 78, 153, 24);
		contentPane.add(courseId);

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

		JLabel lblSemester = new JLabel("Semester:");
		lblSemester.setBounds(50, 161, 90, 15);
		contentPane.add(lblSemester);

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
		JComboBox semesterId = new JComboBox();
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
				// TBD: Call database action to update CourseOfferings table
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
}

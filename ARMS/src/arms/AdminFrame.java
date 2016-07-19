package arms;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class AdminFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame frame = new AdminFrame();
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
	public AdminFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 721, 596);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAdminFrame = new JLabel("Admin Menu");
		lblAdminFrame.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdminFrame.setForeground(Color.RED);
		lblAdminFrame.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblAdminFrame.setBounds(285, 12, 163, 14);
		contentPane.add(lblAdminFrame);

		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ARMS_LogIn.main(null);
			}
		});
		btnLogOut.setBounds(550, 509, 127, 23);
		contentPane.add(btnLogOut);

		JButton btnViewCourseCatalog = new JButton("View Course Catalog");
		btnViewCourseCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
				dispose();
				CourseCatalog.NewScreen();
			}
		});
		btnViewCourseCatalog.setBounds(136, 187, 200, 25);
		contentPane.add(btnViewCourseCatalog);

		JButton btnRegisterStudent = new JButton("Register Student");
		btnRegisterStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
				dispose();
				RegisterStudent registerStudent = new RegisterStudent();
				registerStudent.setVisible(true);
			}
		});
		btnRegisterStudent.setBounds(136, 94, 200, 25);
		contentPane.add(btnRegisterStudent);

		JButton btnDeRegisterStudent = new JButton("Deregister Student");
		btnDeRegisterStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
				dispose();
				DeregisterStudent deregisterStudent = new DeregisterStudent();
				deregisterStudent.setVisible(true);
			}
		});
		btnDeRegisterStudent.setBounds(415, 94, 200, 25);
		contentPane.add(btnDeRegisterStudent);

		JButton btnSummaryReport = new JButton("Summary Report");
		btnSummaryReport.setBounds(136, 380, 200, 25);
		contentPane.add(btnSummaryReport);

		JButton btnViewRequests = new JButton("View Requests");
		btnViewRequests.setBounds(415, 289, 200, 25);
		contentPane.add(btnViewRequests);

		JButton btnShadowMode = new JButton("Shadow Mode");
		btnShadowMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				// Ask Matthew if a shadow boolean variable can be passed into
				// the StudentFrame class
				StudentFrame sf = new StudentFrame();
				sf.setVisible(true);
			}
		});
		btnShadowMode.setBounds(136, 289, 200, 25);
		contentPane.add(btnShadowMode);

		JButton btnUpdateCourse = new JButton("Update Course");
		btnUpdateCourse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				UpdateCourse updateCourse = new UpdateCourse();
				updateCourse.setVisible(true);
			}
		});
		btnUpdateCourse.setBounds(415, 187, 200, 25);
		contentPane.add(btnUpdateCourse);

		JLabel lblStudentActions = new JLabel("Student Actions:");
		lblStudentActions.setBounds(56, 54, 127, 15);
		contentPane.add(lblStudentActions);

		JSeparator separator = new JSeparator();
		separator.setBounds(55, 131, 622, 32);
		contentPane.add(separator);

		JLabel lblCourseActions = new JLabel("Course Actions:");
		lblCourseActions.setBounds(56, 164, 127, 15);
		contentPane.add(lblCourseActions);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(55, 219, 622, 32);
		contentPane.add(separator_1);

		JLabel lblScheduleActions = new JLabel("Schedule Actions:");
		lblScheduleActions.setBounds(56, 263, 127, 15);
		contentPane.add(lblScheduleActions);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(56, 326, 622, 32);
		contentPane.add(separator_2);

		JLabel lblReportActions = new JLabel("Report Actions:");
		lblReportActions.setBounds(56, 350, 127, 15);
		contentPane.add(lblReportActions);

	}
}

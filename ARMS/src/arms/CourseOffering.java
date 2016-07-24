package arms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CourseOffering {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseOffering window = new CourseOffering();
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
	public CourseOffering() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 604, 382);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCourseOffering = new JLabel("Proposed Course Schedule");
		lblCourseOffering.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseOffering.setBounds(201, 11, 166, 34);
		frame.getContentPane().add(lblCourseOffering);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(54, 66, 490, 212);
		frame.getContentPane().add(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel();
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		JButton btnCloseButton = new JButton("Close");
		btnCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		btnCloseButton.setBounds(489, 309, 89, 23);
		frame.getContentPane().add(btnCloseButton);
		
		// Add columns to model
		model.addColumn("Course Offering ID");
		model.addColumn("Course ID");
		model.addColumn("Course Name");
		model.addColumn("Semester");
		
	}

}

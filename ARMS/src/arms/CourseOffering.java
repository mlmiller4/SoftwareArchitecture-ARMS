package arms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import arms.api.API;
import arms.api.CourseInstance;
import arms.api.ScheduleRequest;
import arms.dataAccess.DbActions;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class CourseOffering {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void NewScreen(final int studentID) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseOffering window = new CourseOffering(studentID);
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
	public CourseOffering(int studentID) {
		initialize(studentID);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int studentID) {
		frame = new JFrame();
		frame.setBounds(100, 100, 690, 367);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCourseOffering = new JLabel("Proposed Course Schedule");
		lblCourseOffering.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseOffering.setBounds(269, 21, 166, 34);
		frame.getContentPane().add(lblCourseOffering);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 66, 600, 212);
		frame.getContentPane().add(scrollPane);
		
		final DefaultTableModel model = new DefaultTableModel();
		
		table = new JTable(model);
		scrollPane.setViewportView(table);
		
		JButton btnCloseButton = new JButton("Return to Menu");
		btnCloseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				table = null;
				for (int i=0; i<model.getRowCount(); i++){
					model.removeRow(i);
				}
			}
		});
		btnCloseButton.setBounds(476, 294, 157, 23);
		frame.getContentPane().add(btnCloseButton);
		
//		JButton btnAcceptProposedSchedule = new JButton("Accept Proposed Schedule");
//		btnAcceptProposedSchedule.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				frame.dispose();
//				table = null;
//				for (int i=0; i<model.getRowCount(); i++){
//					model.removeRow(i);
//				}
//			}
//		});
//		btnAcceptProposedSchedule.setBounds(269, 294, 197, 23);
//		frame.getContentPane().add(btnAcceptProposedSchedule);
		
		// Add columns to model
		//model.addColumn("Course Offering ID");
		model.addColumn("Course ID");
		model.addColumn("Course Name");
		model.addColumn("Semester");
		
		List<ScheduleRequest> recommendations = API.getRecentScheduleRequestsFromMem();
		ScheduleRequest mySchedule = null;		
		
		for (ScheduleRequest req : recommendations){			
			
			if (req.getStudentId() == studentID){
				mySchedule = req;
			}			
		}
		
		
		HashMap<Integer, Integer> myCourses = null;
		myCourses =	mySchedule.getRequestedCourses();
		CourseInstance course = null;
		
		for (Map.Entry<Integer, Integer> entry : myCourses.entrySet()){
			
			int value = entry.getValue();
			
			course = DbActions.getCourseInstanceByID(value);
			
			String offeringID = Integer.toString(course.getId());
			String courseID = Integer.toString(course.getCourseId());
			String name = course.getCourseName();
			String semester = course.getSemester();		
			
			
			model.addRow(new Object[] { courseID, name, semester });			
		}
		
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		
		table.getColumnModel().getColumn(0).setMinWidth(75);
		table.getColumnModel().getColumn(0).setMaxWidth(75);
		table.getColumnModel().getColumn(2).setMinWidth(150);
		table.getColumnModel().getColumn(2).setMaxWidth(150);
		
		
		//model.addRow(mySchedule);
		//JOptionPane.showMessageDialog(null,"");
		
	}

}

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
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import arms.api.CourseInstance;
import arms.api.ScheduleRequest;
import arms.api.SystemReport;
import arms.dataAccess.DbActions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JTextField;

public class SummaryReport extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private JTextField studentCount;
	private JTextField scheduleCount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SummaryReport frame = new SummaryReport();
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
	public SummaryReport() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblSummaryReport = new JLabel("Summary Report");
		lblSummaryReport.setHorizontalAlignment(SwingConstants.CENTER);
		lblSummaryReport.setForeground(Color.BLACK);
		lblSummaryReport.setFont(new Font("Dialog", Font.BOLD, 20));
		lblSummaryReport.setBounds(10, 0, 756, 49);
		contentPane.add(lblSummaryReport);

		JLabel lblNumberOfStudents = new JLabel("Number of students:");
		lblNumberOfStudents.setBounds(32, 87, 158, 15);
		contentPane.add(lblNumberOfStudents);

		JLabel lblNumberOfSchedule = new JLabel("Number of schedule requests:");
		lblNumberOfSchedule.setBounds(32, 117, 225, 15);
		contentPane.add(lblNumberOfSchedule);

		JLabel lblDemandByCourse = new JLabel("Demand by Course");
		lblDemandByCourse.setHorizontalAlignment(SwingConstants.CENTER);
		lblDemandByCourse.setForeground(Color.BLACK);
		lblDemandByCourse.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblDemandByCourse.setBounds(32, 134, 263, 49);
		contentPane.add(lblDemandByCourse);

		JLabel lblDemandByStudent = new JLabel("Demand by Student");
		lblDemandByStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblDemandByStudent.setForeground(Color.BLACK);
		lblDemandByStudent.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblDemandByStudent.setBounds(363, 134, 365, 49);
		contentPane.add(lblDemandByStudent);

		JLabel lblConfiguration = new JLabel("Configuration");
		lblConfiguration.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguration.setForeground(Color.BLACK);
		lblConfiguration.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblConfiguration.setBounds(32, 398, 263, 49);
		contentPane.add(lblConfiguration);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 181, 263, 190);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
				dispose();
				AdminFrame af = new AdminFrame();
				af.setVisible(true);
			}
		});
		btnNewButton.setBounds(233, 635, 89, 23);
		contentPane.add(btnNewButton);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(363, 181, 365, 190);
		contentPane.add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);

		JButton btnNewButton_1 = new JButton("Generate Report");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SystemReport report = DbActions.generateSystemReport();
				Integer count = new Integer(0);
				count = report.getStudentsNum();
				studentCount.setText(count.toString());

				count = report.getScheduleRequestsNum();
				scheduleCount.setText(count.toString());

				getCourseDemandTable(report);
				getStudentDemandTable(report);
				getConfigTable(report);
			}
		});
		btnNewButton_1.setBounds(32, 634, 176, 25);
		contentPane.add(btnNewButton_1);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(37, 442, 258, 149);
		contentPane.add(scrollPane_2);

		table_2 = new JTable();
		scrollPane_2.setViewportView(table_2);

		studentCount = new JTextField();
		studentCount.setEditable(false);
		studentCount.setBounds(254, 85, 114, 19);
		contentPane.add(studentCount);
		studentCount.setColumns(10);

		scheduleCount = new JTextField();
		scheduleCount.setEditable(false);
		scheduleCount.setColumns(10);
		scheduleCount.setBounds(254, 115, 114, 19);
		contentPane.add(scheduleCount);

	}

	private void getCourseDemandTable(SystemReport report) {
		// headers for the table
		Object[] columns = { "Course ID", "# Students" };
		DefaultTableModel model = new DefaultTableModel(new Object[0][0],
				columns);

		for (HashMap.Entry<Integer, Integer> entry : report.getCourseDemand()
				.entrySet()) {
			Object[] o = new Object[2];
			o[0] = entry.getKey();
			o[1] = entry.getValue();
			model.addRow(o);
		}
		table.setModel(model);
	}

	private void getStudentDemandTable(SystemReport report) {
		// headers for the table
		Object[] columns = { "StudentID", "# Next Semester",
				"# Future Semesters", "Unavailable" };
		DefaultTableModel model = new DefaultTableModel(new Object[0][0],
				columns);

		for (HashMap.Entry<Integer, ArrayList<Integer>> entry : report
				.getRequestResultsInfo().entrySet()) {
			Object[] o = new Object[4];
			o[0] = entry.getKey();
			o[1] = entry.getValue().get(0);
			o[2] = entry.getValue().get(1);
			o[3] = entry.getValue().get(2);
			model.addRow(o);
		}
		table_1.setModel(model);
	}

	private void getConfigTable(SystemReport report) {
		// headers for the table
		Object[] columns = { "Parameter", "Value" };
		DefaultTableModel model = new DefaultTableModel(new Object[0][0],
				columns);

		for (HashMap.Entry<String, Float> entry : report.getConfigParameters()
				.entrySet()) {
			Object[] o = new Object[2];
			o[0] = entry.getKey();
			o[1] = entry.getValue();
			model.addRow(o);
		}
		table_2.setModel(model);
	}
}

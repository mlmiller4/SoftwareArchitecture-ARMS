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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import arms.api.CourseInstance;
import arms.dataAccess.DbActions;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CourseCatalog {

	private JFrame frame;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void NewScreen() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CourseCatalog window = new CourseCatalog();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	Connection connection = null;
	private JButton btnClose;
	
	/**
	 * Create the application.
	 */
	public CourseCatalog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1400, 707);
		//frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCourseCatalog = new JLabel("Course Catalog");
		lblCourseCatalog.setForeground(Color.BLACK);
		lblCourseCatalog.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblCourseCatalog.setHorizontalAlignment(SwingConstants.CENTER);
		lblCourseCatalog.setBounds(600, 11, 262, 80);
		frame.getContentPane().add(lblCourseCatalog);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 104, 1350, 510);
		frame.getContentPane().add(scrollPane);
		
		DefaultTableModel model = new DefaultTableModel(){    
			@Override
            public Class getColumnClass(int column) {
				switch (column) {
                case 0:
                    return Integer.class;
                case 1:
                	return Integer.class;
                case 2:
                    return String.class;
                case 3:
                	return String.class;
                case 4:
                	return Integer.class;
                case 5:
                	return Integer.class;
                case 6:
                	return String.class;
                default:
                    return String.class;
        	}
		}
	};
				
		table = new JTable(model);
		scrollPane.setViewportView(table);
		table.setAutoCreateRowSorter(true);
		
		connection = sqliteConnection.dbConnector();
		
		// Add columns to model
		model.addColumn("Course ID");
		model.addColumn("Offering ID");
		model.addColumn("Course Title");
		model.addColumn("Semester");
		model.addColumn("Class Size");
		model.addColumn("Remaining Seats");
		model.addColumn("Prerequisites");		

		// Get Course details and add to model
		//List<CourseInstance> catalog = new ArrayList<CourseInstance>();
		
		List<CourseInstance> catalog = DbActions.getCatalog();
		
		String strPrereqs = null;
		List<String> currentPrereqs = null;
		
		for (CourseInstance currentCourse : catalog){
			
			strPrereqs = null;
			
			currentPrereqs = currentCourse.getPrerequisits();
			
			for (int i=0; i<currentPrereqs.size(); i++){
				
				String currPrereq = currentPrereqs.get(i);
				
				if (currPrereq != null){
					
					if (i > 0){
						strPrereqs += ", " + currPrereq;
					} else {
						strPrereqs = currPrereq;
					}					
				}				
			}
			
			model.addRow(new Object[] {currentCourse.getCourseId(), currentCourse.getId(), currentCourse.getCourseName(),
					currentCourse.getSemester(), currentCourse.getClassSize(), currentCourse.getRemSeats(),
					strPrereqs});				
		}
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		// Adjust column widths for formatting
		table.getColumnModel().getColumn(0).setMaxWidth(75);
		table.getColumnModel().getColumn(1).setMaxWidth(75);
		table.getColumnModel().getColumn(2).setMinWidth(300);
		table.getColumnModel().getColumn(2).setMaxWidth(300);
		table.getColumnModel().getColumn(3).setMaxWidth(125);
		table.getColumnModel().getColumn(3).setMaxWidth(125);
		table.getColumnModel().getColumn(4).setMaxWidth(75);
		table.getColumnModel().getColumn(4).setMaxWidth(75);
		table.getColumnModel().getColumn(5).setMaxWidth(110);
		table.getColumnModel().getColumn(5).setMaxWidth(110);
		table.getColumnModel().getColumn(6).setMaxWidth(700);
		table.getColumnModel().getColumn(6).setMaxWidth(700);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
		table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
		
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnClose.setBounds(1250, 625, 89, 23);
		frame.getContentPane().add(btnClose);
		
	//	try{
			//String query = "select * from CourseOfferings";
//			String query = "select * from Courses";
//			PreparedStatement pst = connection.prepareStatement(query);
//			ResultSet rs = pst.executeQuery();
			
//			ResultSet rs = (ResultSet) DbActions.getCatalog();
//			table.setModel(DbUtils.resultSetToTableModel(rs));
			
//			btnClose = new JButton("Close");
//			btnClose.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					frame.dispose();
//				}
//			});
//			btnClose.setBounds(1025, 625, 89, 23);
//			frame.getContentPane().add(btnClose);
//			
//			
//		}catch (Exception e){
//			e.printStackTrace();
//		}
		
		//table.getColumnModel().getColumn(2).setMinWidth(150);
		
//		Default
//		
//		table.getColumnModel().getColumn(0).setHeaderValue("Course ID");
//		table.getColumnModel().getColumn(1).setHeaderValue("Course Number");
//		table.getColumnModel().getColumn(2).setHeaderValue("Course Title");
//		table.getColumnModel().getColumn(3).setHeaderValue("Semesters Available");
//		table.getColumnModel().getColumn(4).setHeaderValue("Class Size");
//		table.getColumnModel().getColumn(5).setHeaderValue("Remaining Seats");
		
		
//		table.setModel(new DefaultTableModel(
//			new Object[][] {
//			},
//			new String[] {
//				//"Course ID", "Course Title", "Semesters Offered", "Year", "Class Size", "Seats Remaining"
//				"ID", "CourseID", "FallSem", "SpringSem", "SummerSem", "ClassSize", "RemSeats", "CourseTitle"
//			}
//		) {
//			Class[] columnTypes = new Class[] {
//				Object.class, Object.class, String.class, String.class, Object.class, Object.class
//			};
//			public Class getColumnClass(int columnIndex) {
//				return columnTypes[columnIndex];
//			}
//		});
		
	}
}

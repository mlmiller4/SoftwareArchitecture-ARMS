package arms;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class ViewRequests extends JFrame {

	private JPanel contentPane;
	private JTable table;
	Connection connection = null;

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

	/**
	 * Create the frame.
	 */
	public ViewRequests() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 822, 482);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JLabel lblViewScheduleRequests = new JLabel("View Schedule Requests");
		lblViewScheduleRequests.setHorizontalAlignment(SwingConstants.CENTER);
		lblViewScheduleRequests.setForeground(Color.BLACK);
		lblViewScheduleRequests.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblViewScheduleRequests.setBounds(282, 0, 262, 80);
		contentPane.add(lblViewScheduleRequests);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 79, 806, 324);
		contentPane.add(scrollPane);
		table = new JTable();
		scrollPane.setViewportView(table);
		
		connection = sqliteConnection.dbConnector();
		
		try{
			//String query = "select * from CourseOfferings";
			String query = "select * from Courses";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			
			JButton btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg1) {
					contentPane.setVisible(false);
					dispose();
					AdminFrame af = new AdminFrame();
					af.setVisible(true);
				}
			});
			btnBack.setBounds(0, 410, 89, 23);
			contentPane.add(btnBack);
			// Include code here to extract list of courses
			// Use loop to loop through the list

			
	
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		table.getColumnModel().getColumn(2).setMinWidth(150);
		
		table.getColumnModel().getColumn(0).setHeaderValue("Student ID");
		table.getColumnModel().getColumn(1).setHeaderValue("SR ID");
		table.getColumnModel().getColumn(2).setHeaderValue("Submit Time");
    	table.getColumnModel().getColumn(3).setHeaderValue("Course ID");
		table.getColumnModel().getColumn(4).setHeaderValue("Semester ID");
		table.getColumnModel().getColumn(4).setHeaderValue("Class Size");
		table.getColumnModel().getColumn(5).setHeaderValue("Remaining Seats");
	
	}
}

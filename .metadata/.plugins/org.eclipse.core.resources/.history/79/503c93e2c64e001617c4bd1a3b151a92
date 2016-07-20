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
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

import net.proteanit.sql.DbUtils;

public class ViewRequests extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;

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
				try {
					// TODO: Call database actions to grab all ScheduleRequests
					// as
					// ScheduleRequest objects

					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		btnLoadTable.setBounds(12, 80, 117, 25);
		contentPane.add(btnLoadTable);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(158, 126, 718, 359);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

	}

	// TODO: complete method
	public PopulateTable() {
		// headers for the table
		String[] columns = new String[] { "Student ID", "SR ID", "Course ID",
				"Semester", "Class Size", "Remaining Seats", "Submit Time" };
	}
}

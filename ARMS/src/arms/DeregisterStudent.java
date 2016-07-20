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
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DeregisterStudent extends JFrame {

	private JPanel contentPane;
	private JTextField userId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeregisterStudent frame = new DeregisterStudent();
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
	public DeregisterStudent() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 214);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDeregisterStudent = new JLabel("Deregister Student");
		lblDeregisterStudent.setBounds(5, 5, 438, 24);
		lblDeregisterStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblDeregisterStudent.setForeground(Color.BLACK);
		lblDeregisterStudent.setFont(new Font("Dialog", Font.BOLD, 20));
		contentPane.add(lblDeregisterStudent);

		JLabel lblUserId = new JLabel("Student Id:");
		lblUserId.setBounds(44, 64, 94, 15);
		contentPane.add(lblUserId);

		userId = new JTextField();
		userId.setBounds(156, 62, 114, 19);
		contentPane.add(userId);
		userId.setColumns(10);

		JButton btnDeregister = new JButton("Deregister");
		btnDeregister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int userIdInt = Integer.parseInt(userId.getText());

				// Call Database Action to delete student from Students table
			}
		});
		btnDeregister.setBounds(44, 110, 117, 25);
		contentPane.add(btnDeregister);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg1) {
				contentPane.setVisible(false);
				dispose();
				AdminFrame af = new AdminFrame();
				af.setVisible(true);
			}
		});
		btnBack.setBounds(198, 110, 117, 25);
		contentPane.add(btnBack);
	}

}

package arms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ARMS_LogIn {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ARMS_LogIn window = new ARMS_LogIn();
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
	public ARMS_LogIn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnLoginStudentButton = new JButton("Log In As Student");
		btnLoginStudentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "Logging in as student...");				
				frame.dispose();
				StudentFrame sf = new StudentFrame();
				sf.setVisible(true);
			}
		});
		btnLoginStudentButton.setBounds(112, 129, 200, 23);
		frame.getContentPane().add(btnLoginStudentButton);
		
		JButton btnLoginAdminButton = new JButton("Log In As Administrator");
		btnLoginAdminButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "Logging in as Administrator...");
				frame.dispose();
				AdminFrame af = new AdminFrame();
				af.setVisible(true);
			}
		});
		btnLoginAdminButton.setBounds(112, 209, 200, 23);
		frame.getContentPane().add(btnLoginAdminButton);
		
		JLabel lblAcademicResourceManagement = new JLabel("Academic Resource Management System");
		lblAcademicResourceManagement.setForeground(Color.RED);
		lblAcademicResourceManagement.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblAcademicResourceManagement.setHorizontalAlignment(SwingConstants.CENTER);
		lblAcademicResourceManagement.setBackground(Color.WHITE);
		lblAcademicResourceManagement.setBounds(62, 38, 320, 51);
		frame.getContentPane().add(lblAcademicResourceManagement);
	}
}

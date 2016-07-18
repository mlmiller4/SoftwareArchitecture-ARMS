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
		lblAdminFrame.setBounds(261, 11, 163, 14);
		contentPane.add(lblAdminFrame);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ARMS_LogIn.main(null);				
			}
		});
		btnLogOut.setBounds(588, 509, 89, 23);
		contentPane.add(btnLogOut);
		
		JButton btnViewCourseCatalog = new JButton("View Course Catalog");
		btnViewCourseCatalog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CourseCatalog.NewScreen();
			}
		});
		btnViewCourseCatalog.setBounds(55, 60, 200, 25);
		contentPane.add(btnViewCourseCatalog);
	}
}

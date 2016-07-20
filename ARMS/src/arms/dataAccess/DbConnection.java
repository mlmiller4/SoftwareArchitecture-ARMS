package arms.dataAccess;


import java.sql.*;
import javax.swing.*;


public class DbConnection {

	Connection conn = null;

	public static Connection dbConnector()
	{
		try{
			Class.forName("org.sqlite.JDBC");			
			Connection conn = DriverManager.getConnection("jdbc:sqlite:armsDB.sqlite");
			//JOptionPane.showMessageDialog(null, "Database Connection Successful");
			return conn;			

		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}		
	}
}
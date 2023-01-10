package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
	
	public static Connection connect() {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:dbproject.db");
//			System.out.println("Connected");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e+" ");
		}
		
		return conn;
	}
	
}

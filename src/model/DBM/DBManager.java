package model.DBM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	
	private static DBManager instance;
	
	private Connection connection;
	
	private static final String DB_IP = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "technomarket";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD ="44237899a";
	private static final String URL = "jdbc:mysql://"+DB_IP+":"+DB_PORT+"/"+DB_NAME;
	
	private DBManager() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized DBManager getInstance(){
		if(instance == null){
			instance = new DBManager();
		}
		return instance;
	}
	
	public Connection getConnections(){
		if(this.connection == null){
			try {
				this.connection = DriverManager.getConnection(URL,DB_USERNAME,DB_PASSWORD);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this.connection;
	}
	
	
	

}

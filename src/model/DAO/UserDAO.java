package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.DBM.DBManager;

public class UserDAO {
	private static UserDAO userDAO;
	private Connection connection;
	

	private UserDAO() {

	}

	public UserDAO getInstance() {
		if (this.userDAO == null) {
			this.userDAO = new UserDAO();
		}
		return this.userDAO;
	}
	
//	public void insert(){
//		this.connection = DBManager.getInstance().getConnections();
//	}

}

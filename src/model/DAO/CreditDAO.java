package model.DAO;

import java.sql.Connection;

public class CreditDAO {
	
	private static CreditDAO creditDAO;
	private Connection connection;
	

	private CreditDAO() {
	}

	public static synchronized CreditDAO getInstance() {
		if (creditDAO == null) {
			creditDAO = new CreditDAO();
		}
		return creditDAO;
	}

}

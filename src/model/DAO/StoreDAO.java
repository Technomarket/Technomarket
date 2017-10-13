package model.DAO;

import java.sql.Connection;

public class StoreDAO {
	
	private static StoreDAO storeDAO;
	private Connection connection;
	

	private StoreDAO() {

	}

	public static synchronized StoreDAO getInstance() {
		if (storeDAO == null) {
			storeDAO = new StoreDAO();
		}
		return storeDAO;
	}
	
	

}

package model.DAO;

import java.sql.Connection;

public class CharacterisicsDAO {

	private static CharacterisicsDAO characterisicsDAO;
	private Connection connection;
	

	private CharacterisicsDAO() {

	}

	public synchronized CharacterisicsDAO getInstance() {
		if (this.characterisicsDAO == null) {
			this.characterisicsDAO = new CharacterisicsDAO();
		}
		return this.characterisicsDAO;
	}
	
	
	
	
}

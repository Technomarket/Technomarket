package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import model.Characteristics;
import model.Product;
import model.DBM.DBManager;

public class CharacterisicsDAO {

	private static CharacterisicsDAO characterisicsDAO;
	private Connection connection;
	

	private CharacterisicsDAO() {

	}

	public static synchronized CharacterisicsDAO getInstance() {
		if (characterisicsDAO == null) {
			characterisicsDAO = new CharacterisicsDAO();
		}
		return characterisicsDAO;
	}
	
	static void addProductInCharacteristicsTable(Product p) throws SQLException{
		for (Iterator<Characteristics> iterator = p.getCharacteristics().iterator(); iterator.hasNext();) {
			Characteristics characteristic = iterator.next();
			long typeId = getCharacteristicsTypeId(characteristic);
			Connection con = DBManager.getInstance().getConnections();
			PreparedStatement ps = con.prepareStatement("INSERT INTO technomarket.characteristics (product_id, characteristics_name, characteristics_type_id) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, p.getProductId());
			ps.setString(2, characteristic.getName());
			ps.setLong(3, typeId);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			characteristic.setCharacteristicsId(rs.getLong(1));	
		}
	}

	private static long getCharacteristicsTypeId(Characteristics characteristic) throws SQLException {
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("SELECT characteristics_type_id FROM technomarket.characteristics_type WHERE characteristics_type_name LIKE '?';");
		ps.setString(1, characteristic.getTypeCharacteristics());
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getLong("characteristics_type_id");	
	}
	
	
}

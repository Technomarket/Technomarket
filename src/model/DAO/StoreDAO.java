package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.Product;
import model.Store;
import model.DBM.DBManager;

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
	
	//change quantity of product in store with int change, where int change can be positive or negative:
	
	public void changeQuantityInStore(Store s, Product p, int change) throws SQLException{
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("INSERT INTO technomarket.store_has_product (store_id, product_id, amount) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, s.getStoreId());
		ps.setLong(2, p.getProductId());
		ps.setInt(2, change);
		ps.executeUpdate();
	}

	//returns status of product amount in specific store:
	
	public String checkQuantity(Store s, Product p) throws SQLException{
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("SELECT amount FROM technomarket.store_has_product WHERE product_id = ? AND store_id = ?;");
		ps.setLong(1, p.getProductId());
		ps.setLong(2, s.getStoreId());
		ResultSet rs = ps.executeQuery();
		rs.next();
		int amount = rs.getInt("amount");
		
		if(amount == 0){
			return "No amount";
		}else if(amount > 0 && amount < 10){
			return "Nearly no amount";
		}else if(amount >= 10 & amount <= 30){
			return "Average amount";
		}else{
			return "Product is available";
		}
	}

	public void insertNewStore(Store s) throws SQLException {
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("INSERT INTO technomarket.stores (city, address, phone, working_time, email, gps) VALUES (?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, s.getAddres().getCity());
		ps.setString(2, s.getAddres().getAddres());
		ps.setString(3, s.getPhoneNumber());
		ps.setString(4, s.getWorkingTime());
		ps.setString(5, s.getEmail());
		ps.setString(6, s.getGps());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		s.setStoreId(rs.getLong(1));
	}
}

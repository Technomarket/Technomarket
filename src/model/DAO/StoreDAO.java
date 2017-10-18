package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashMap;

import model.Order;
import model.Product;
import model.Store;
import model.DBM.DBManager;
import model.exceptions.InvalidStoreDataException;

public class StoreDAO {
	
	private enum Status {NO_STATUS, RED_STATUS, YELLOW_STATUS, GREEN_STATUS};
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
	
	public HashSet<Store> getStoresPerCity(String city) throws SQLException, InvalidStoreDataException{
		HashSet<Store> stores = new HashSet<>();
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM technomarket.stores WHERE city LIKE '?';");
		ps.setString(1, city);
		ps.executeUpdate();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Store s = new Store();
			s.setStoreId(rs.getLong("store_id"));
			s.setAddress(s.new Address(rs.getString("city"), rs.getString("address")));
			s.setPhoneNumber(rs.getString("phone"));
			s.setWorkingTime(rs.getString("working_time"));
			s.setEmail(rs.getString("email"));
			s.setGps(rs.getString("gps"));
			s.setStoreImageUrl(rs.getString("store_image_url"));
			stores.add(s);
		}
		return stores;
		
	}
	
	//change quantity of product in store with int change, where int change can be positive or negative:
	
	public void changeQuantityInStore(Store s, Product p, int change) throws SQLException{
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("UPDATE technomarket.store_has_product SET amount=? WHERE store_id = ? AND product_id = ?;", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, change);
		ps.setLong(2, s.getStoreId());
		ps.setLong(3, p.getProductId());
		ps.executeUpdate();
	}

	//returns status of product amount in specific store:
	
	public Status checkQuantity(Store s, Product p) throws SQLException{
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("SELECT amount FROM technomarket.store_has_product WHERE product_id = ? AND store_id = ?;");
		ps.setLong(1, p.getProductId());
		ps.setLong(2, s.getStoreId());
		ResultSet rs = ps.executeQuery();
		rs.next();
		int amount = rs.getInt("amount");
		
		if(amount == 0){
			return Status.NO_STATUS;
		}else if(amount > 0 && amount < 10){
			return Status.RED_STATUS;
		}else if(amount >= 10 & amount <= 30){
			return Status.YELLOW_STATUS;
		}else{
			return Status.GREEN_STATUS;
		}
	}
	
	//Admin panel in Stores: 
	
	public void insertProductInStore(Store s, Product p, int amount) throws SQLException{
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("INSERT INTO technomarket.store_has_product (store_id, product_id, amount) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, s.getStoreId());
		ps.setLong(2, p.getProductId());
		ps.setInt(3, amount);
		ps.executeUpdate();
	}

	public void insertNewStore(Store s) throws SQLException {
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("INSERT INTO technomarket.stores (city, address, phone, working_time, email, gps, store_image_url) VALUES (?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, s.getAddres().getCity());
		ps.setString(2, s.getAddres().getAddres());
		ps.setString(3, s.getPhoneNumber());
		ps.setString(4, s.getWorkingTime());
		ps.setString(5, s.getEmail());
		ps.setString(6, s.getGps());
		ps.setString(7, s.getStoreImageUrl());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		s.setStoreId(rs.getLong(1));
	}
}

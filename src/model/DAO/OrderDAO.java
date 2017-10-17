package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map.Entry;

import model.Characteristics;
import model.Order;
import model.Product;
import model.User;
import model.DBM.DBManager;
import model.exceptions.IlligalAdminActionException;
import model.exceptions.IlligalUserActionException;
import model.exceptions.InvalidCategoryDataException;
import model.exceptions.InvalidCharacteristicsDataException;

public class OrderDAO {
	
	private static OrderDAO orderDAO;
	private Connection connection;

	private OrderDAO() {
	}

	public static synchronized OrderDAO getInstance() {
		if (orderDAO == null) {
			orderDAO = new OrderDAO();
		}
		return orderDAO;
	}

	//-> select all orders per user_id, fill them in HashSet, select all products per Order, their quantity, and fields it in HashMap of every Order:
	public LinkedHashSet<Order> getOrdersForUser(long long1) throws SQLException, InvalidCharacteristicsDataException, InvalidCategoryDataException {
		LinkedHashSet<Order> orders = new LinkedHashSet<>();
		String query = "SELECT * FROM technomarket.orders WHERE user_id = ?;";
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statment = this.connection.prepareStatement(query);
		statment.setLong(1, long1);
		ResultSet result = statment.executeQuery();
		while (result.next()) {
			Order o = new Order();
			o.setOrderId(result.getLong("order_id"));
			o.setTime(returnDateAndTime(result.getString("date_time")));
			o.setAddress(result.getString("address"));
			o.setPayment(result.getString("payment"));
			o.setNotes(result.getString("notes"));
			o.setConfirmed(result.getBoolean("isCnfirmed"));
			o.setPaid(result.getBoolean("isPaid"));
			o.setShipingType(getTheRightShipingType(result.getString("shiping_type")));
			LinkedHashMap<Product, Integer> products = fillAllProductsInOrder(o);
			o.setProducts(products);
		}
		return orders;
	}

	


	private LinkedHashMap<Product, Integer> fillAllProductsInOrder(Order o) throws SQLException, InvalidCharacteristicsDataException, InvalidCategoryDataException {
		LinkedHashMap<Product, Integer> products = new LinkedHashMap<>();
		String query = "SELECT * FROM technomarket.order_has_product WHERE order_id = ?;";
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statment = this.connection.prepareStatement(query);
		statment.setLong(1, o.getOrderId());
		ResultSet result = statment.executeQuery();
		while (result.next()) {
			products.put(ProductDAO.getInstance().getProduct(result.getLong("product_id")), result.getInt("quantity"));
		}
		return products;
	}

	private Order.Shiping getTheRightShipingType(String shipingType) {
		if(shipingType.equals("HOME_ADDRESS")){
			return Order.Shiping.HOME_ADDRESS;
		}
		
		if(shipingType.equals("STORE")){
			return Order.Shiping.STORE;
		}
		
		return null;
	}

	private LocalDateTime returnDateAndTime(String dateAndTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(dateAndTime, formatter);
		return dateTime;
	}

	//user panel in Orders:
	
	public void insertNewOrder(User u, Order o) throws SQLException{
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("INSERT INTO technomarket.orders (user_id, date_time, address, payment, notes, isConfirmed, isPaid, shiping_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, u.getUserId());
		ps.setString(2, o.getTime().toString());
		ps.setString(3, o.getAddress());
		ps.setString(4, o.getPayment());
		ps.setString(5, o.getNotes());
		ps.setBoolean(6, o.getIsConfirmed());
		ps.setBoolean(7, o.getIsPaid());
		ps.setString(8, o.getShipingType().toString());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		o.setOrderId(rs.getLong(1));
		addOrderedProductsToTable(o);
	}
	
	private void addOrderedProductsToTable(Order o) throws SQLException{
		Connection con = DBManager.getInstance().getConnections();
		for (Iterator<Entry<Product, Integer>> iterator = o.getProducts().entrySet().iterator(); iterator.hasNext();) {
			Entry<Product, Integer> entry = iterator.next();
			PreparedStatement ps = con.prepareStatement("INSERT INTO technomarket.order_has_product (order_id, product_id, quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, o.getOrderId());
			ps.setLong(2, entry.getKey().getProductId());
			ps.setInt(3, entry.getValue());
			ps.executeUpdate();
		}
	}
	
	public void setOrderAsConfirmed(Order o, boolean isConfirmed) throws SQLException, IlligalUserActionException {
		if (o.getIsConfirmed() && isConfirmed) {
			throw new IlligalUserActionException();
		} else if (!o.getIsConfirmed() && !isConfirmed) {
			throw new IlligalUserActionException();
		} else {
			Connection con = DBManager.getInstance().getConnections();
			PreparedStatement ps = con.prepareStatement("UPDATE technomarket.orders SET isConfirmed = ? WHERE order_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setBoolean(1, isConfirmed);
			ps.setLong(2, o.getOrderId());
			ps.executeUpdate();
		}
		
	}
	
	//admin panel in Orders:

	public void setOrderAsPaid(Order o, boolean isPaid) throws IlligalAdminActionException, SQLException {
		if (o.getIsConfirmed() && isPaid) {
			throw new IlligalAdminActionException();
		} else if (!o.getIsConfirmed() && !isPaid) {
			throw new IlligalAdminActionException();
		} else {
			Connection con = DBManager.getInstance().getConnections();
			PreparedStatement ps = con.prepareStatement("UPDATE technomarket.orders SET isConfirmed = ? WHERE order_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setBoolean(1, isPaid);
			ps.setLong(2, o.getOrderId());
			ps.executeUpdate();
		}
		
	}

}

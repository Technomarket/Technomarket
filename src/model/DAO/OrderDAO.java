package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import model.Order;
import model.Product;
import model.User;
import model.DBM.DBManager;
import model.exceptions.IlligalAdminActionException;

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

	public HashSet<Order> getOrdersForUser(long long1) {
		HashSet<Orders>
		
		
		// TODO Auto-generated method stub
		return null;
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
	
	//admin panel in Orders:
	
	public void setOrderAsConfirmed(Order o, boolean isConfirmed) throws IlligalAdminActionException, SQLException {
		if (o.getIsConfirmed() && isConfirmed) {
			throw new IlligalAdminActionException();
		} else if (!o.getIsConfirmed() && !isConfirmed) {
			throw new IlligalAdminActionException();
		} else {
			Connection con = DBManager.getInstance().getConnections();
			PreparedStatement ps = con.prepareStatement("UPDATE technomarket.orders SET isConfirmed = ? WHERE order_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setBoolean(1, isConfirmed);
			ps.setLong(2, o.getOrderId());
			ps.executeUpdate();
		}
		
	}

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

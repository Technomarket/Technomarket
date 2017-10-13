package model.DAO;

import java.sql.Connection;
import java.util.HashSet;

import model.Order;

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
		// TODO Auto-generated method stub
		return null;
	}

}

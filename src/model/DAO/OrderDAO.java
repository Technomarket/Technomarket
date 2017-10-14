package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import model.Order;
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
		// TODO Auto-generated method stub
		return null;
	}

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

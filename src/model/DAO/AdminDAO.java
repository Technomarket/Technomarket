package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Iterator;

import model.Category;
import model.Characteristics;
import model.Order;
import model.Product;
import model.Store;
import model.User;
import model.DBM.DBManager;
import model.exceptions.IlligalAdminActionException;
import model.exceptions.NotAnAdminException;

public class AdminDAO {
	
	private static AdminDAO adminDAO;
	private Connection connection;
	

	private AdminDAO() {
	}

	public static synchronized AdminDAO getInstance() {
		if (adminDAO == null) {
			adminDAO = new AdminDAO();
		}
		return adminDAO;
	}
	
	public void insertNewProduct(Product p, User admin) throws SQLException, NotAnAdminException{
		if(admin.getIsAdmin()){
			ProductDAO.getInstance().insertNewProduct(p);
		}else{
			throw new NotAnAdminException();
		}
		
	}
	
	public void removeProduct(Product p, User admin) throws NotAnAdminException, SQLException{
		if(admin.getIsAdmin()){
			ProductDAO.getInstance().removeProduct(p);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void changeQuantityInStore(Store s, Product p, int change, User admin) throws SQLException, NotAnAdminException{
		if(admin.getIsAdmin()){
			StoreDAO.getInstance().changeQuantityInStore(s, p, change);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void insertNewStore(User admin, Store s) throws SQLException, NotAnAdminException{
		if(admin.getIsAdmin()){
			StoreDAO.getInstance().insertNewStore(s);
		}else{
			throw new NotAnAdminException();
		}
	}

	//changes the promo percent of product, adds promo sticker in view
	public void setPromoPercent(User admin, Product p, int percent) throws SQLException, NotAnAdminException{
		if(admin.getIsAdmin()){
			ProductDAO.getInstance().setPromoPercent(p, percent);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	//makes user admin:
	public void changeUserIsAdminStatus(User admin, User u, boolean isAdmin) throws NotAnAdminException, SQLException, IlligalAdminActionException{
		if(admin.getIsAdmin()){
			UserDAO.getInstance().changeUserIsAdminStatus(u, isAdmin);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	//banns user:
	public void changeUserIsBannedStatus(User admin, User u, boolean isBanned) throws NotAnAdminException, SQLException, IlligalAdminActionException{
		if(admin.getIsAdmin()){
			UserDAO.getInstance().changeUserIsBannedStatus(u, isBanned);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	//set order as paid:
	public void setOrderAsPaid(User admin, Order o, boolean isPaid) throws NotAnAdminException, SQLException, IlligalAdminActionException{
		if(admin.getIsAdmin()){
			OrderDAO.getInstance().setOrderAsPaid(o, isPaid);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	
}

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
import model.Product;
import model.Store;
import model.User;
import model.DBM.DBManager;
import model.exceptions.AlreadyAnAdminException;
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
	
	public void insertNewProduct(Product p, User u) throws SQLException, NotAnAdminException{
		if(u.getIsAdmin()){
			//inserts Product into product table:
			int tradeMarkId = getTradeMarkId(p.getTradeMark());
			Connection con = DBManager.getInstance().getConnections();
			PreparedStatement ps = con.prepareStatement("INSERT INTO technomarket.product (trade_mark_id, credit_id, product_name, price, warranty, percent_promo, date_added, product_number) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, tradeMarkId);
			ps.setString(2, null);
			ps.setString(3, p.getName());
			ps.setBigDecimal(4, p.getPrice());
			ps.setInt(5, p.getWorranty());
			ps.setInt(6, p.getPercentPromo());
			ps.setString(7, LocalDate.now().toString());
			ps.setString(8, p.getProductNumber());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			p.setProductId(rs.getLong(1));	
			
			//insert Product and its Category into product_has_category table:
			insertProductIntoProductHasCategory(p);
			
			//insert new row in characteristics table with the product id and its characteristics name and type:
			insertProductIntoCharacteristics(p);
			
			
		}else{
			throw new NotAnAdminException();
		}
		
	}

	private void insertProductIntoCharacteristics(Product p) throws SQLException {
		CharacterisicsDAO.getInstance().addProductInCharacteristicsTable(p);
	}
	
	private void insertProductIntoProductHasCategory(Product p) throws SQLException {
		CategoryDAO.getInstance().insertProductIntoProductHasCategory(p);
	}

	private int getTradeMarkId(String tradeMark) throws SQLException {
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("SELECT trade_mark_id FROM technomarket.trade_marks WHERE trade_mark_name LIKE '?';");
		ps.setString(1, tradeMark);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("trade_mark_id");
	}
	
	public void changeQuantityInStore(Store s, Product p, int change, User u) throws SQLException, NotAnAdminException{
		if(u.getIsAdmin()){
			StoreDAO.getInstance().changeQuantityInStore(s, p, change);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void insertNewStore(User u, Store s) throws SQLException, NotAnAdminException{
		if(u.getIsAdmin()){
			StoreDAO.getInstance().insertNewStore(s);
		}else{
			throw new NotAnAdminException();
		}
	}

	public void setPromoPercent(User u, Product p, int percent) throws SQLException, NotAnAdminException{
		if(u.getIsAdmin()){
			ProductDAO.getInstance().setPromoPercent(p, percent);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	public void setUserToAdmin(User u) throws NotAnAdminException, SQLException, AlreadyAnAdminException{
		if(u.getIsAdmin()){
			UserDAO.getInstance().setUserToAdmin(u);
		}else{
			throw new NotAnAdminException();
		}
	}
	
	
}

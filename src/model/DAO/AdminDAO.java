package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.Category;
import model.Product;
import model.User;
import model.DBM.DBManager;

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
	
	public void insertNewProduct(Product p, User u) throws SQLException{
		if(u.isAdmin()){
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
			//throws new NotAnAdminException();
		}
		
	}

	private void insertProductIntoCharacteristics(Product p) {
		// TODO Auto-generated method stub
		
	}

	private void insertProductIntoProductHasCategory(Product p) throws SQLException {
		long categoryId = getCategoryId(p.getCategory());
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("INSERT INTO technomarket.product_has_category (category_id, product_id) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS);
		ps.setLong(1, categoryId);
		ps.setLong(2, p.getProductId());
		ps.executeUpdate();
	}

	private long getCategoryId(Category category) throws SQLException {
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("SELECT category_id FROM technomarket.categories WHERE category_name LIKE '?';");
		ps.setString(1, category.getName());
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getLong("category_id");
	}

	private int getTradeMarkId(String tradeMark) throws SQLException {
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("SELECT trade_mark_id FROM technomarket.trade_marks WHERE trade_mark_name LIKE '?';");
		ps.setString(1, tradeMark);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("trade_mark_id");
	}
	
	

}

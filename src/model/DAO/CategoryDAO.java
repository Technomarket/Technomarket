package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Category;
import model.Product;
import model.DBM.DBManager;

public class CategoryDAO {
	
	private static CategoryDAO categoryDAO;
	private Connection connection;
	

	private CategoryDAO() {

	}

	public static synchronized CategoryDAO getInstance() {
		if (categoryDAO == null) {
			categoryDAO = new CategoryDAO();
		}
		return categoryDAO;
	}
	
	public void insertProductIntoProductHasCategory(Product p) throws SQLException {
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

}

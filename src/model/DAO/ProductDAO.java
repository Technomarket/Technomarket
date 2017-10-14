package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.Product;
import model.DBM.DBManager;

public class ProductDAO {
	private static ProductDAO productDAO;
	private Connection connection;

	private ProductDAO() {

	}

	public static synchronized ProductDAO getInstance() {
		if (productDAO == null) {
			productDAO = new ProductDAO();
		}
		return productDAO;
	}

	// Метод който връща даден продукт от базата
	public Product getProduct(int productID) throws SQLException {
		String query = "SELECT * FROM technomarket.product WHERE product_id =" + productID;
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statment = this.connection.prepareStatement(query);
		ResultSet result = statment.executeQuery();
		Product pro = new Product();
		result.next();
		pro.setName(result.getString("product_name"));
		pro.setPrice(result.getString("price"));
		pro.setWorranty(result.getInt("warranty"));
		pro.setPercentPromo(result.getInt("percent_promo"));
		pro.setDateAdded(LocalDate.parse(result.getString("date_added")));
		pro.setProductNumber(result.getString("product_number"));
		pro.setProductId(result.getLong("product_id"));
		pro.setTradeMark(getTradMark(pro.getProductId()));

		return pro;
	}

	public String getTradMark(long id) throws SQLException {
		String query = "SELECT trade_mark_name FROM technomarket.trade_marks AS t JOIN technomarket.product AS p ON(t.trade_mark_id = p.trade_mark_id)WHERE product_id ="
				+ id;
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statment = this.connection.prepareStatement(query);
		ResultSet resut = statment.executeQuery();
		resut.next();
		return resut.getString("trade_mark_name");
	}

	public void setPromoPercent(Product p, int percent) throws SQLException {
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement("UPDATE technomarket.product SET percent_promo = ? WHERE product_id = ?", Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, percent);
		ps.setLong(2, p.getProductId());
		ps.executeUpdate();
	}

}

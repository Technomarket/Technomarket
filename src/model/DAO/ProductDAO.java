package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.sun.javafx.UnmodifiableArrayList;
import com.sun.javafx.collections.UnmodifiableListSet;

import model.Category;
import model.Characteristics;
import model.Product;
import model.Product.campareEnum;
import model.DBM.DBManager;
import model.exceptions.InvalidCharacteristicsDataException;

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
	public Product getProduct(long productID) throws SQLException, InvalidCharacteristicsDataException {
		String query = "SELECT * FROM technomarket.product WHERE product_id = ?;";
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statment = this.connection.prepareStatement(query);
		statment.setLong(1, productID);
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
		ArrayList<Characteristics> characteristics = CharacterisicsDAO.getInstance()
				.getProducsCharacteristics(pro.getProductId());
		pro.setCharacteristics(characteristics);
		return pro;
	}

	public String getTradMark(long id) throws SQLException {
		String query = "SELECT trade_mark_name FROM technomarket.trade_marks AS t JOIN technomarket.product AS p ON(t.trade_mark_id = p.trade_mark_id)WHERE product_id = ?";
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statment = this.connection.prepareStatement(query);
		statment.setLong(1, id);
		ResultSet resut = statment.executeQuery();
		resut.next();
		return resut.getString("trade_mark_name");
	}

	public void setPromoPercent(Product p, int percent) throws SQLException {
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps = con.prepareStatement(
				"UPDATE technomarket.product SET percent_promo = ? WHERE product_id = ?",
				Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, percent);
		ps.setLong(2, p.getProductId());
		ps.executeUpdate();
	}

	// remove product modul:

	public void removeProduct(Product p) throws SQLException {
		Connection con = DBManager.getInstance().getConnections();
		PreparedStatement ps1 = con.prepareStatement("DELETE FROM technomarket.product WHERE product_id = ?",
				Statement.RETURN_GENERATED_KEYS);
		ps1.setLong(1, p.getProductId());
		ps1.executeUpdate();

		PreparedStatement ps2 = con.prepareStatement("DELETE FROM technomarket.order_has_product WHERE product_id = ?",
				Statement.RETURN_GENERATED_KEYS);
		ps2.setLong(1, p.getProductId());
		ps2.executeUpdate();

		PreparedStatement ps3 = con.prepareStatement(
				"DELETE FROM technomarket.product_has_category WHERE product_id = ?", Statement.RETURN_GENERATED_KEYS);
		ps3.setLong(1, p.getProductId());
		ps3.executeUpdate();

		PreparedStatement ps4 = con.prepareStatement("DELETE FROM technomarket.store_has_product WHERE product_id = ?",
				Statement.RETURN_GENERATED_KEYS);
		ps4.setLong(1, p.getProductId());
		ps4.executeUpdate();

		PreparedStatement ps5 = con.prepareStatement("DELETE FROM technomarket.user_has_favourite WHERE product_id = ?",
				Statement.RETURN_GENERATED_KEYS);
		ps5.setLong(1, p.getProductId());
		ps5.executeUpdate();

		PreparedStatement ps6 = con.prepareStatement("DELETE FROM technomarket.characteristics WHERE product_id = ?",
				Statement.RETURN_GENERATED_KEYS);
		ps6.setLong(1, p.getProductId());
		ps6.executeUpdate();
	}

	// Search product by name;
	public Product searchProductByName(String productName) throws SQLException {
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statment = this.connection.prepareStatement(
				"select product.product_id, trade_marks.trade_mark_name, product.product_name, product.price,product.warranty, product.percent_promo, product.date_added, product.product_number from technomarket.product join technomarket.trade_marks on(product.trade_mark_id = trade_marks.trade_mark_id)  where product.product_name = ?");
		statment.setString(1, productName);
		ResultSet result = statment.executeQuery();
		Product product = new Product();
		result.next();
		product.setProductId(result.getLong(1));
		product.setTradeMark(result.getString(2));
		product.setName(result.getString(3));
		product.setPrice(result.getString(4));
		product.setWorranty(result.getInt(5));
		product.setPercentPromo(result.getInt(6));
		product.setDateAdded(LocalDate.parse(result.getString(7)));
		product.setProductNumber(result.getString(8));
		return product;
	}

	// Search product by category
	public List<Product> searchProductByCategory(Category category, campareEnum campare)
			throws SQLException {
		String orderBy = "";
		if(campare == campareEnum.defaultt){
			//default date_added
			orderBy+="date_added";
		}else{
			orderBy+=campare;
		}
		LinkedList<Product> products = new LinkedList<>();
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statement = this.connection.prepareStatement(
				"select product.product_id, trade_marks.trade_mark_name, product.product_name, product.price,product.warranty"
						+ ", product.percent_promo, product.date_added, product.product_number from technomarket.product"
						+ "join technomarket.trade_marks on(product.trade_mark_id = trade_marks.trade_mark_id)"
						+ "join technomarket.product_has_category on(product_has_category.product_id = product_has_category.category_id)"
						+ "join technomarket.categories on(categories.category_id = product_has_category.category_id) where technomarket.categories.categoy_name = ? group by ? order by ?");
        statement.setString(1, category.getName());
        statement.setString(2, orderBy);
        statement.setString(3, orderBy);
        ResultSet result = statement.executeQuery();
        while(result.next()){
        	Product pro = new Product();
        	pro.setProductId(result.getLong(1));
        	pro.setTradeMark(result.getString(2));
        	pro.setName(result.getString(3));
        	pro.setPrice(result.getString(4));
        	pro.setWorranty(result.getInt(5));
        	pro.setPercentPromo(result.getInt(6));
        	pro.setDateAdded(LocalDate.parse(result.getString(7)));
        	pro.setProductNumber(result.getString(8));
        	products.add(pro);
        }
		return products;

	}

}

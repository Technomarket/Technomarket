package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.Category;
import model.Characteristics;
import model.Product;
import model.Product.campareEnum;
import model.DBM.DBManager;
import model.exceptions.InvalidCategoryDataException;
import model.exceptions.InvalidCharacteristicsDataException;
import model.exceptions.InvalidProductDataException;

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
	public Product getProduct(long productID)
			throws SQLException, InvalidCharacteristicsDataException, InvalidCategoryDataException {

		String query = "SELECT * FROM technomarket.product WHERE product_id = ?;";
		this.connection = DBManager.getInstance().getConnections();
			PreparedStatement statment = this.connection.prepareStatement(query);
			statment.setLong(1, productID);
			this.connection.commit();
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
			pro.setTradeMark(getTradeMark(pro.getProductId()));
			pro.setImageUrl(result.getString("image_url"));
			ArrayList<Characteristics> characteristics = CharacterisicsDAO.getInstance()
					.getProducsCharacteristics(pro.getProductId());
			pro.setCharacteristics(characteristics);
			pro.setCategory(CategoryDAO.getInstance().getProductsCategory(pro.getProductId()));
			return pro;
	}

	public String getTradeMark(long id) throws SQLException {
		String query = "SELECT trade_mark_name FROM technomarket.trade_marks AS t JOIN technomarket.product AS p ON(t.trade_mark_id = p.trade_mark_id) WHERE product_id = ?";
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setLong(1, id);
		ResultSet resut = statement.executeQuery();
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

	// insert product module:

	public void insertNewProduct(Product p) throws SQLException {
		// inserts Product into product table:
		Connection con = DBManager.getInstance().getConnections();
		con.setAutoCommit(false);
		try {
			int tradeMarkId = getTradeMarkId(p.getTradeMark());
			PreparedStatement ps = con.prepareStatement(
					"INSERT INTO technomarket.product (trade_mark_id, credit_id, product_name, price, warranty, percent_promo, date_added, product_number, image_url) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, tradeMarkId);
			ps.setString(2, null);
			ps.setString(3, p.getName());
			ps.setBigDecimal(4, p.getPrice());
			ps.setInt(5, p.getWorranty());
			ps.setInt(6, p.getPercentPromo());
			ps.setString(7, LocalDate.now().toString());
			ps.setString(8, p.getProductNumber());
			ps.setString(9, p.getImageUrl());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			p.setProductId(rs.getLong(1));

			// insert Product and its Category into product_has_category table:
			insertProductIntoProductHasCategory(p);

			// insert new row in characteristics table with the product id and
			// its
			// characteristics name and type:
			insertProductIntoCharacteristics(p);
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw new SQLException();
		} finally {
			con.setAutoCommit(true);
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
		PreparedStatement ps = con
				.prepareStatement("SELECT trade_mark_id FROM technomarket.trade_marks WHERE trade_mark_name LIKE '?';");
		ps.setString(1, tradeMark);
		ResultSet rs = ps.executeQuery();
		rs.next();
		return rs.getInt("trade_mark_id");
	}

	// remove product module:

	public void removeProduct(Product p) throws SQLException {
		Connection con = DBManager.getInstance().getConnections();
		con.setAutoCommit(false);
		try {
			PreparedStatement ps1 = con.prepareStatement("DELETE FROM technomarket.product WHERE product_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps1.setLong(1, p.getProductId());
			ps1.executeUpdate();

			PreparedStatement ps2 = con.prepareStatement(
					"DELETE FROM technomarket.order_has_product WHERE product_id = ?", Statement.RETURN_GENERATED_KEYS);
			ps2.setLong(1, p.getProductId());
			ps2.executeUpdate();

			PreparedStatement ps3 = con.prepareStatement(
					"DELETE FROM technomarket.product_has_category WHERE product_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps3.setLong(1, p.getProductId());
			ps3.executeUpdate();

			PreparedStatement ps4 = con.prepareStatement(
					"DELETE FROM technomarket.store_has_product WHERE product_id = ?", Statement.RETURN_GENERATED_KEYS);
			ps4.setLong(1, p.getProductId());
			ps4.executeUpdate();

			PreparedStatement ps5 = con.prepareStatement(
					"DELETE FROM technomarket.user_has_favourite WHERE product_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps5.setLong(1, p.getProductId());
			ps5.executeUpdate();

			PreparedStatement ps6 = con.prepareStatement(
					"DELETE FROM technomarket.characteristics WHERE product_id = ?", Statement.RETURN_GENERATED_KEYS);
			ps6.setLong(1, p.getProductId());
			ps6.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			con.rollback();
			throw new SQLException();
		} finally {
			con.setAutoCommit(true);
		}

	}

	// Search product by name;
	public Product searchProductByName(String productName) throws SQLException {
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statment = this.connection.prepareStatement(
				"SELECT product.product_id, trade_marks.trade_mark_name, product.product_name, product.price,product.warranty, product.percent_promo, product.date_added, product.product_number FROM technomarket.product JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id)  WHERE product.product_name = ?");
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
	public List<Product> searchProductByCategory(Category category, campareEnum campare) throws SQLException {
		String orderBy = "";
		if (campare == campareEnum.defaultt) {
			// default date_added
			orderBy += "date_added";
		} else {
			orderBy += campare;
		}
		LinkedList<Product> products = new LinkedList<>();
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statement = this.connection.prepareStatement(
				"SELECT product.product_id, trade_marks.trade_mark_name, product.product_name, product.price, product.warranty"
						+ ", product.percent_promo, product.date_added, product.product_number FROM technomarket.product"
						+ "JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id)"
						+ "JOIN technomarket.product_has_category ON(product_has_category.product_id = product_has_category.category_id)"
						+ "JOIN technomarket.categories on(categories.category_id = product_has_category.category_id) WHERE technomarket.categories.categoy_name = ? GROUP BY ? ORDER BY ?");
		statement.setString(1, category.getName());
		statement.setString(2, orderBy);
		statement.setString(3, orderBy);
		ResultSet result = statement.executeQuery();
		while (result.next()) {
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

	// Search product with promo price
	public Set<Product> getPromoProduct() throws SQLException {
		LinkedHashSet<Product> products = new LinkedHashSet<>();
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statement = this.connection.prepareStatement(
				"SELECT product.product_id, trade_marks.trade_mark_name, product.product_name, product.price,product.warranty, product.percent_promo, product.date_added, product.product_number, product.image_url FROM technomarket.product JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id)  WHERE product.percent_promo > 0");
		ResultSet result = statement.executeQuery();
		Product product = null;
		while (result.next()) {
			product = new Product();
			product.setProductId(result.getLong(1));
			product.setTradeMark(result.getString(2));
			product.setName(result.getString(3));
			product.setPrice(result.getString(4));
			product.setWorranty(result.getInt(5));
			product.setPercentPromo(result.getInt(6));
			product.setDateAdded(LocalDate.parse(result.getString(7)));
			product.setProductNumber(result.getString(8));
			product.setImageUrl(result.getString(9));
			products.add(product);
		}
		return products;
	}

	// Search produc where trade marke is apple

	public Set<Product> getAppleProduct() throws SQLException {
		LinkedHashSet<Product> products = new LinkedHashSet<>();
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statement = this.connection.prepareStatement(
				"SELECT product.product_id, trade_marks.trade_mark_name, product.product_name, product.price,product.warranty, product.percent_promo, product.date_added, product.product_number, product.image_url FROM technomarket.product JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id)  WHERE trade_mark_name LIKE 'apple%' or LIKE 'ipad'");
		ResultSet result = statement.executeQuery();
		Product product = null;
		while (result.next()) {
			product = new Product();
			product.setProductId(result.getLong(1));
			product.setTradeMark(result.getString(2));
			product.setName(result.getString(3));
			product.setPrice(result.getString(4));
			product.setWorranty(result.getInt(5));
			product.setPercentPromo(result.getInt(6));
			product.setDateAdded(LocalDate.parse(result.getString(7)));
			product.setProductNumber(result.getString(8));
			product.setImageUrl(result.getString(9));
			products.add(product);
		}
		return products;
	}

	// Search product by price
	public Set<Product> searchProductByPrice(String fromPrice, String toPrice)
			throws InvalidProductDataException, SQLException {
		LinkedHashSet<Product> products = new LinkedHashSet<>();
		if (fromPrice.compareTo(toPrice) > 0) {
			throw new InvalidProductDataException();
		}
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statement = this.connection.prepareStatement(
				"SELECT product.product_id, product.product_name, product.price, product.product_number"
						+ ",trade_marks.trade_mark_name , product.warranty, product.percent_promo"
						+ "FROM technomarket.product JOIN technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id)"
						+ "WHERE product.price between ? and ?");
		statement.setString(1, fromPrice);
		statement.setString(2, toPrice);
		ResultSet result = statement.executeQuery();
		Product product = null;
		while (result.next()) {
			product = new Product();
			product.setProductId(result.getLong(1));
			product.setName(result.getString(2));
			product.setPrice(result.getString(3));
			product.setProductNumber(result.getString(4));
			product.setTradeMark(result.getString(5));
			product.setWorranty(result.getInt(6));
			product.setPercentPromo(result.getInt(7));
			products.add(product);
		}

		return products;
	}

	// Search produc where category is home
	public Set<Product> searchProductWithCategoryHome() throws SQLException {
		LinkedHashSet<Product> products = new LinkedHashSet<>();
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statement = this.connection.prepareStatement(
				"SELECT product.product_id, product.product_name, product.price, product.product_number,"
						+ "trade_marks.trade_mark_name , product.warranty, product.percent_promo , categories.categoy_name"
						+ "FROM technomarket.product join technomarket.trade_marks ON(product.trade_mark_id = trade_marks.trade_mark_id)"
						+ "JOIN technomarket.categories ON(product.credit_id = categories.category_id)"
						+ "WHERE categoy_name LIKE 'Home'");
		ResultSet result = statement.executeQuery();
		Product product = null;
		while (result.next()) {
			product = new Product();
			product.setProductId(result.getLong(1));
			product.setName(result.getString(2));
			product.setPrice(result.getString(3));
			product.setProductNumber(result.getString(4));
			product.setTradeMark(result.getString(5));
			product.setWorranty(result.getInt(6));
			product.setPercentPromo(result.getInt(7));
			products.add(product);
		}

		return products;
	}

}

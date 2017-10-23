package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

import model.Order;
import model.Product;
import model.User;
import model.DBM.DBManager;
import model.exceptions.EmailAlreadyInUseException;
import model.exceptions.IlligalAdminActionException;
import model.exceptions.IlligalUserActionException;
import model.exceptions.InvalidCategoryDataException;
import model.exceptions.InvalidCharacteristicsDataException;
import util.Encrypter;

public class UserDAO {
	private static UserDAO userDAO;
	private Connection connection;

	private UserDAO() {

	}

	public static synchronized UserDAO getInstance() {
		if (userDAO == null) {
			userDAO = new UserDAO();
		}
		return userDAO;
	}

	// registration of user:
	public void insertUser(User user) throws SQLException, EmailAlreadyInUseException {
		if (checkIfUserWithSameEmailExist(user.getEmail())) {
			throw new EmailAlreadyInUseException();
		} else {
			String query = "INSERT INTO technomarket.users ( first_name, last_name, email, password, gender,date_of_birth, isAdmin,isAbonat,isBAnned)VALUES(?,?,?,?,?,?,?,?,?);";
			this.connection = DBManager.getInstance().getConnections();
			java.sql.PreparedStatement statement = this.connection.prepareStatement(query,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setString(3, user.getEmail());
			statement.setString(4, Encrypter.encrypt(user.getPassword()));
			statement.setString(5, user.getGender());
			statement.setString(6, user.getBirthDate().toString());
			statement.setString(7, "" + (user.getIsAdmin() ? 1:0));
			statement.setString(8, "" + (user.getIsAbonat() ? 1:0));
			statement.setString(9, "" + (user.getIsBanned() ? 1:0));
			statement.executeUpdate();
			ResultSet resutSet = statement.getGeneratedKeys();
			// Взимаме от базата идто и го слагаме на обекта;
			// За да можем после да ми направим сесия;
			while (resutSet.next()) {
				user.setId(resutSet.getInt(1));
			}
		}
	}

	// log in of user:
	public boolean existingUser(String userName, String pasword) throws SQLException {
		String checkQuery = "SELECT * FROM technomarket.users WHERE email = ? and password = ?";
		this.connection = DBManager.getInstance().getConnections();
		java.sql.PreparedStatement statement = this.connection.prepareStatement(checkQuery);
		statement.setString(1, userName);
		statement.setString(2, Encrypter.encrypt(pasword));
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			return true;
		}
		return false;
	}
	/*
	 * public User getUser(String username) throws SQLException{ Connection con
	 * = DBManager.getInstance().getConnection(); PreparedStatement ps = con.
	 * prepareStatement("SELECT id, username, password, email FROM users WHERE username = ?"
	 * ); ps.setString(1, username); ResultSet rs = ps.executeQuery();
	 * rs.next(); HashSet<Order> orders =
	 * OrderDao.getInstance().getOrdersForUser(rs.getLong("id")); return new
	 * User( rs.getLong("id"), username, rs.getString("password"),
	 * rs.getString("email"), orders); }
	 * 
	 */

	// Метода exitsUser проверява и ако каже че има се връща потребителя от този
	// метод!
	public User getUser(String userName)
			throws SQLException, InvalidCharacteristicsDataException, InvalidCategoryDataException {
		this.connection = DBManager.getInstance().getConnections();
		User user = new User();
		PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM technomarket.users WHERE email = ?");
		statement.setString(1, userName);
		ResultSet result = statement.executeQuery();
		result.next();
		user.setId(result.getLong("user_id"));
		user.setFirstName(result.getString("first_name"));
		user.setLastName(result.getString("last_name"));
		user.setEmail(result.getString("email"));
		user.setPassword(result.getString("password"));
		user.setGender(result.getString("gender"));
		user.setBirthDate(LocalDate.parse(result.getString("date_of_birth")));
		user.setAdmin(result.getBoolean("isAdmin"));
		user.setAbonat(result.getBoolean("isAbonat"));
		user.setBanned(result.getBoolean("isBanned"));
		LinkedHashSet<Order> orders = OrderDAO.getInstance().getOrdersForUser(user.getUserId());
		user.setOrders(orders);
		result.close();
		statement.close();
		return user;
	}

	// Checks if the email exist in DB in connection with user. Used when password is forgotten or to check is such user is already registrated:
	public boolean checkIfUserWithSameEmailExist(String email) throws SQLException {
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statement = this.connection.prepareStatement("SELECT users.email FROM technomarket.users WHERE users.email = ?");
		statement.setString(1, email);
		ResultSet result = statement.executeQuery();
		boolean exist = result.next();
		statement.close();
		return exist;
		
	}

	// user confirming his order:

	public void confirmOrder(Order o, boolean isConfirmed) throws SQLException, IlligalUserActionException {
		OrderDAO.getInstance().setOrderAsConfirmed(o, isConfirmed);
	}

	// favourite products:

	// Adding favourite product to user account:
	public void addInFavorite(User user, Product product) throws SQLException {
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statement = this.connection.prepareStatement("INSER INTO technomarket.users_has_favourite (user_id, product_id) VALUES (?, ?)");
		statement.setLong(1, user.getUserId());
		statement.setLong(2, product.getProductId());
		statement.executeQuery();
		statement.close();
		
	}

	// Remove favourite product:
	public void removeFavouriteProduct(User u, Product p) throws SQLException {
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statment = this.connection.prepareStatement("DELETE FROM technomarket.user_has_favouite WHERE user_id = ? AND product_id = ?");
		statment.setLong(1, u.getUserId());
		statment.setLong(2, p.getProductId());
		statment.executeQuery();
		statment.close();
	
	}

	// Listing all favourite products:
	public 	LinkedHashSet<Product> viewFavourite(User user) throws SQLException {
		String query = "SELECT * FROM technomarket.product AS p JOIN technomarket.user_has_favourite AS f ON(p.product_id = f.product_id)WHERE f.user_id ="
				+ user.getUserId();
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statement = this.connection.prepareStatement(query);
		ResultSet result = statement.executeQuery();
		Product product = null;
		LinkedHashSet<Product> products = new LinkedHashSet<>();
		while (result.next()) {
			product = new Product();
			product.setName(result.getString("product_name"));
			product.setPrice(result.getString("price"));
			product.setWorranty(result.getInt("warranty"));
			product.setPercentPromo(result.getInt("percent_promo"));
			product.setDateAdded(LocalDate.parse(result.getString("date_added")));
			product.setProductNumber(result.getString("product_number"));
			product.setProductId(result.getLong("product_id"));
			product.setTradeMark(ProductDAO.getInstance().getTradeMark(product.getProductId()));
			products.add(product);
		}
		
		statement.close();
		return products;
	}

	// user statuses, used by admins:

	public void changeUserIsAdminStatus(User u, boolean isAdmin) throws SQLException, IlligalAdminActionException {
		if (u.getIsAdmin() && isAdmin) {
			throw new IlligalAdminActionException();
		} else if (!u.getIsAdmin() && !isAdmin) {
			throw new IlligalAdminActionException();
		} else {
			this.connection = DBManager.getInstance().getConnections();
			PreparedStatement ps = this.connection.prepareStatement("UPDATE technomarket.users SET isAdmin = ? WHERE user_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setBoolean(1, isAdmin);
			ps.setLong(2, u.getUserId());
			ps.executeUpdate();
			
			ps.close();
		}
	}

	public void changeUserIsBannedStatus(User u, boolean isBanned) throws IlligalAdminActionException, SQLException {
		if (u.getIsBanned() && isBanned) {
			throw new IlligalAdminActionException();
		} else if (!u.getIsBanned() && !isBanned) {
			throw new IlligalAdminActionException();
		} else {
			this.connection = DBManager.getInstance().getConnections();
			PreparedStatement ps = this.connection.prepareStatement("UPDATE technomarket.users SET isBanned = ? WHERE user_id = ?",
					Statement.RETURN_GENERATED_KEYS);
			ps.setBoolean(1, isBanned);
			ps.setLong(2, u.getUserId());
			ps.executeUpdate();
			ps.close();
		}
	}

}

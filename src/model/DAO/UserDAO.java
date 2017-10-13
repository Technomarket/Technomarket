package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;

import com.mysql.jdbc.PreparedStatement;

import model.Order;
import model.User;
import model.DBM.DBManager;

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
//При регистриране създава потребител и го пуска в базата
	public void insertUser(User user) throws SQLException {
		String query = "INSERT INTO technomarket.users ( first_name, last_name, email, password, gender,date_of_birth, isAdmin,isAbonat,isBAnned)VALUES(?,?,?,?,?,?,?,?);";
		this.connection = DBManager.getInstance().getConnections();
		java.sql.PreparedStatement statment = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statment.setString(1, user.getFirstName());
		statment.setString(2, user.getLastName());
		statment.setString(3, user.getEmail());
		statment.setString(4, user.getPassword());
		statment.setString(5, user.getBirthDate().toString());
		statment.setString(6, ""+user.getIsAdmin());
		statment.setString(7, ""+user.getIsAbonat());
		statment.setString(8, ""+user.getIsBanned());
		statment.executeUpdate();
		ResultSet resutSet = statment.getGeneratedKeys();
		// Взимаме от базата идто и го слагаме на обекта;
		// За да можем после да ми направим сесия;
		while (resutSet.next()) {
			user.setId(resutSet.getInt(1));
		}
	}
// Проверява при логване дали има такъв потребител!
	public boolean existingUser(String userName, String pasword) throws SQLException {
		String checkQuery = "SELECT * FROM technomarket.users WHERE email = ? and pasword = ?";
		this.connection = DBManager.getInstance().getConnections();
		java.sql.PreparedStatement statment = this.connection.prepareStatement(checkQuery);
		statment.setString(1, userName);
		statment.setString(2, pasword);
		ResultSet resultSet = statment.executeQuery();
		if (resultSet.next()) {
			return true;
		}
		return false;
	} 
	/*public User getUser(String username) throws SQLException{
		Connection con = DBManager.getInstance().getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT id, username, password, email FROM users WHERE username = ?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		rs.next();
		HashSet<Order> orders = OrderDao.getInstance().getOrdersForUser(rs.getLong("id"));
		return new User(
				rs.getLong("id"), 
				username, 
				rs.getString("password"), 
				rs.getString("email"),
				orders);
	}
	 * 
	 */
	
	//Метода exitsUser проверява и ако каже че има се връща потребителя от този метод!
	public User getUser(String userName) throws SQLException{
		String getQuery = "SELECT * FROM technomarket.users WHERE email ="+userName;
		User user = new User();
		java.sql.PreparedStatement statment = this.connection.prepareStatement(getQuery);
		ResultSet result = statment.executeQuery();
	    result.next();
			user.setId(result.getLong("id"));
			user.setFirstName(result.getString("first_name"));
			user.setLastName(result.getString("last_name"));
			user.setEmail(result.getString("email"));
			user.setPassword(result.getString("password"));
			user.setGender(result.getString("gender"));
			user.setBirthDate(LocalDate.parse(result.getString("date_of_birth")));
			user.setAdmin(result.getBoolean("isAdmin"));
			user.setAbonat(result.getBoolean("isAbonat"));
			user.setBanned(result.getBoolean("isBanned"));
		HashSet<Order> orders = OrderDAO.getInstance().getOrdersForUser(result.getLong("id"));
		return user;
		
	}
	//В сличай на забравена парола се проверава дали има такъв емайл ако има се праща емайл с паролата
	public boolean checkEmail(String email) throws SQLException{
		String getQuery = "SELECT users.email FROM technomarket.users WHERE users.email ="+email;
		this.connection = DBManager.getInstance().getConnections();
		java.sql.PreparedStatement statments = this.connection.prepareStatement(getQuery);
		ResultSet result = statments.executeQuery();
		if(result.next()){
			return true;
		}
		return false;
	}
	
	//Добавяне в любими на даден потребител като вкарваме ид и ид на продукта
	public void addInFAvorite(int userID, int productID){
		
	}

}

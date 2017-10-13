package model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Product;
import model.DBM.DBManager;

public class ProductDAO {
	private static ProductDAO productDAO;
	private Connection connection;
	
	private ProductDAO(){
		
	}
	
	public static synchronized ProductDAO getInstance(){
		if(productDAO == null){
			productDAO = new ProductDAO();
		}
		return productDAO;
	}
	
	
	//Метод който връща даден продукт от базата
	public Product getProduct(int productID) throws SQLException{
		String query = "SELECT * FROM technomarket.product WHERE product_id ="+productID;
		this.connection = DBManager.getInstance().getConnections();
		PreparedStatement statment = this.connection.prepareStatement(query);
		ResultSet set = statment.executeQuery();


		
		return null;
	}
	
	

}

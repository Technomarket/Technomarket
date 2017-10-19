package Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.DAO.UserDAO;
import model.exceptions.EmailAlreadyInUseException;
import model.exceptions.InvalidCategoryDataException;
import model.exceptions.InvalidCharacteristicsDataException;

/**
 * Servlet implementation class LoginServet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			boolean exist = UserDAO.getInstance().existingUser(userName, password);
			if(exist){
				try {
					User user = (User)UserDAO.getInstance().getUser(userName);
					request.getSession().setAttribute("user", user);
					request.getRequestDispatcher("mainPage.jsp").forward(request, response);
					
					
//					 ServletContext application = getServletConfig().getServletContext();
//					 synchronized (application) {
//					 if(application.getAttribute("products") == null){
//					 HashSet<Product> products = ProductDao.getInstance().getAllProducts();
//					 application.setAttribute("products", products);
		
				} catch (InvalidCharacteristicsDataException e) {
					e.printStackTrace();
				} catch (InvalidCategoryDataException e) {
					e.printStackTrace();
				}
			}else{
				request.setAttribute("error", "user does not exist");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}

package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.DAO.UserDAO;
import model.exceptions.EmailAlreadyInUseException;
import model.exceptions.InvalidUserDataException;

/**
 * Servlet implementation class RegistrationsServlet
 */
@WebServlet("/RegistrationsServlet")
public class RegistrationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			if(!request.getParameter("password").equals(request.getParameter("password1"))){
				request.setAttribute("invalidPassword", "Passwords are not the same ");
				request.getRequestDispatcher("register.jsp");
			}
			User user = new User(request.getParameter("firstName"),
					request.getParameter("lastName"),
					request.getParameter("email"),
					request.getParameter("password"),
					request.getParameter("gender"),
					LocalDate.parse(request.getParameter("bday")),
					request.getParameter("abonat").equals("1")? true: false, false, false);
			try {
				UserDAO.getInstance().insertUser(user);
				request.getSession().setAttribute("login", true);
				request.getRequestDispatcher("mainPage.jsp").forward(request, response);
			} catch (SQLException e) {
				request.getRequestDispatcher("errorPage.jsp").forward(request, response);
				e.printStackTrace();
				System.out.println("SQL Exception");
			} catch (EmailAlreadyInUseException e) {
				request.setAttribute("invalidEmailAddres", "Email addres is exist");
				request.getRequestDispatcher("register.jsp").forward(request, response);
				
			}
		} catch (InvalidUserDataException e) {
			request.setAttribute("invalidDate≈rror", "Invalid date for user");
			request.getRequestDispatcher("register.jsp").forward(request, response);
			
		}
		
		
	}

}

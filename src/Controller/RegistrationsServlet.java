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
			System.out.println(request.getParameter("firstName"));
			System.out.println(request.getParameter("lastName"));
			System.out.println(request.getParameter("email"));
			System.out.println(request.getParameter("password"));
			System.out.println(request.getParameter("gender"));
			System.out.println(request.getParameter("day")+request.getParameter("moth")+request.getParameter("year"));
			User user = new User(request.getParameter("firstName"),
					request.getParameter("lastName"),
					request.getParameter("email"),
					request.getParameter("password"),
					request.getParameter("gender"),
					LocalDate.now(),
					request.getParameter("abonat").equals("1")? true: false, false, false);
			try {
				UserDAO.getInstance().insertUser(user);
				request.getSession().setAttribute("user", user);
				request.getRequestDispatcher("mainPage.jsp").forward(request, response);
			} catch (SQLException e) {
				request.getRequestDispatcher("errorPage.jsp").forward(request, response);
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

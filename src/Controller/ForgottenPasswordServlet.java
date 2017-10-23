package Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.UserDAO;


@WebServlet("/ForgottenPasswordServlet")
public class ForgottenPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("view/username");
		try {
			boolean exist = UserDAO.getInstance().checkIfUserWithSameEmailExist(email);
			if(exist){
				//Send email to email addres
				req.getRequestDispatcher("view/email_sent.jsp").forward(req, resp);
			}else{
				req.setAttribute("error", "email not valid");
				req.getRequestDispatcher("view/forgotten_password.jsp").forward(req, resp);
			}
		} catch (SQLException e) {
			req.getRequestDispatcher("view/errorPage.jsp");
			System.out.println("Ops SQL Exceptions");
		}
		
	}

}

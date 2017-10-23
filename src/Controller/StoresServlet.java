package Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO.StoreDAO;


@WebServlet("/StoresServlet")
public class StoresServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.getSession().setAttribute("allNameOfStores", StoreDAO.getInstance().getAllCities());
			request.getRequestDispatcher("contacts/stores.jsp").forward(request, response);
			
		} catch (SQLException e) {
			request.getRequestDispatcher("error.jsp").forward(request, response);
			System.out.println("Sql Exception in storesServlet");
		}
	}

	

}

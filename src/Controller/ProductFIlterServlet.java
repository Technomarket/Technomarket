package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Product;
import model.DAO.ProductDAO;

@WebServlet("/product_filter")
public class ProductFIlterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductFIlterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("filtredProducts") != null) {
			request.getSession().removeAttribute("filtredProducts");
		}
		LinkedHashSet<Product> filtredProducts = null;
		try {
			if (request.getParameter("type") != null) {
				if (request.getParameter("type").equals("promo")) {
					filtredProducts = (LinkedHashSet<Product>) ProductDAO.getInstance().getPromoProduct();
				} else if (request.getParameter("type").equals("apple")) {
					filtredProducts = (LinkedHashSet<Product>) ProductDAO.getInstance().getAppleProduct();
				} else if (request.getParameter("type").equals("home")) {
					filtredProducts = (LinkedHashSet<Product>) ProductDAO.getInstance().searchProductWithCategoryHome();
				}
			}else{
				request.getRequestDispatcher("products/invalid_filtring.jsp").forward(request, response);
			}
			if(request.getParameter("searched_text") != null){
				filtredProducts = ProductDAO.getInstance().searchProductByName(request.getParameter("searched_text"));
			}else{
				request.getRequestDispatcher("products/invalid_filtring.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("filtredProducts", filtredProducts);
		request.getRequestDispatcher("products/filtred_products.jsp").forward(request, response);
	}

}

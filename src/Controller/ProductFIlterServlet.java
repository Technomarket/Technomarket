package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Product;
import model.DAO.ProductDAO;

@WebServlet("/ProductFIlterServlet")
public class ProductFIlterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductFIlterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String get = request.getParameter("type");
		System.out.println(get);
		LinkedHashSet<Product> filtredProducts = null;
		Map<String, String[]> map = request.getParameterMap();
		for(Iterator<Entry<String, String[]>> iterator = map.entrySet().iterator(); iterator.hasNext();){
			Entry<String, String[]> entry = iterator.next();
			System.out.println("key: "+entry.getKey());
			String [] arr = entry.getValue();
			for(String str : arr){
				System.out.println("value: "+str);
			}
			
		}
		try {
			if (request.getParameterMap().containsKey("type")) {
				System.out.println(get);
				if (get.equals("promo")){
					System.out.println(get);
					filtredProducts = (LinkedHashSet<Product>) ProductDAO.getInstance().getPromoProduct();
				} else if (request.getParameter("type").equals("apple")) {
					filtredProducts = (LinkedHashSet<Product>) ProductDAO.getInstance().getAppleProduct();
				} else if (request.getParameter("type").equals("home")) {
					filtredProducts = (LinkedHashSet<Product>) ProductDAO.getInstance().searchProductWithCategoryHome();
				}
			}else{
				request.getRequestDispatcher("view/invalid_filtring.jsp").forward(request, response);
				return;
			}
//			if(request.getParameter("searched_text") != null){
//				filtredProducts = ProductDAO.getInstance().searchProductByName(request.getParameter("searched_text"));
//			}else{
//				request.getRequestDispatcher("view/invalid_filtring.jsp").forward(request, response);
//				return;
//			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("filtredProducts", filtredProducts);
		request.getRequestDispatcher("view/filtred_products.jsp").forward(request, response);
		if (request.getSession().getAttribute("filtredProducts") != null) {
			request.getSession().removeAttribute("filtredProducts");
		}
	}

}

package Servlets;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import DB.CartHelper;
import DB.ProductHelper;
import DB.UserHelper;
import Model.Product;
/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
	private final CartHelper helper = new CartHelper();
	private final ProductHelper pHelper = new ProductHelper();
	private final UserHelper uHelper = new UserHelper();
	private static final long serialVersionUID = 1L;
	private final Gson gson = new GsonBuilder().create();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = (String) request.getParameter("username").split("&")[0];
		int userId = -1;
		try {
			userId = uHelper.getUserIdByUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().write(gson.toJson(helper.getCartForUser(userId)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String param = (String) request.getParameter("param").split("&")[0];
		String username = (String) request.getParameter("username").split("&")[0];
		System.out.println(param);
		int userId = -1;
		try {
			userId = uHelper.getUserIdByUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean b = false;
		if(param.compareToIgnoreCase("removeFromCart") == 0)
		{
			b = true;
			int prodId = Integer.parseInt(request.getParameter("prodId").split("&")[0]);
			Product p = null;
			try {
				p = pHelper.getProductById(prodId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			helper.removeItemFromCart(p, userId);
		}else if (param.compareToIgnoreCase("checkOutCart") == 0) {
			String address = (String) request.getParameter("address").split("&")[0];
			b = helper.checkoutCart(userId, address);
		}else if (param.compareToIgnoreCase("addItemToCart") == 0) {
			b = true;
			int prodId = Integer.parseInt(request.getParameter("prodId").split("&")[0]);
			Product p = null;
			try {
				p = pHelper.getProductById(prodId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int qty = Integer.parseInt(request.getParameter("qty").split("&")[0]);
			b = helper.addItemToCart(p, userId, qty);
		}
		//TODO if b == true, refresh the cart view page to load the updated cart, else alert that something was invalid
		response.getWriter().write(String.valueOf(b));
		
		
	}

}

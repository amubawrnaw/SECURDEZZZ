package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import DB.*;
import Model.Product;
import Model.ProductManager;
import Model.User;
/**
 * Servlet implementation class ProjectManagerServlet
 */
@WebServlet("/ProductManagerServlet")
public class ProductManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final ProductManagerHelper helper = new ProductManagerHelper();
    private final ProductHelper pHelper = new ProductHelper();
    private final Gson gson = new GsonBuilder().create();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = (String) request.getParameter("param").split("&")[0];
		System.out.println(param);
		if (param.compareToIgnoreCase("getByPM") == 0) 
		{
			String username = (String) request.getParameter("username").split("&")[0];
			username = helper.getProdNameByToken(username);
			Product[] products = null;
			System.out.println("getByPM");
			int managerId = -1;
			try {
				managerId = helper.getProductManagerIdByUsername(username);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			products = pHelper.getProductsByManagerId(managerId);
			response.getWriter().write(gson.toJson(products));
		} else if (param.compareToIgnoreCase("getAllPM") == 0){
			response.getWriter().write(gson.toJson(helper.getAllProductManagers()));
		}else
			response.getWriter().write("false");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	private int getProdManagerId(String username){
		int prodManagerId = -1;
		try {
			prodManagerId = helper.getProductManagerIdByUsername(username);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return prodManagerId;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = (String) request.getParameter("param").split("&")[0];
		//TODO As mentioned above, just 2 TODOs for emphasis
		System.out.println(param);
		boolean b = false;
		
		if(param.compareToIgnoreCase("restock") == 0)
		{
			String username = (String) request.getParameter("username").split("&")[0];
			username = helper.getProdNameByToken(username);
			int prodId = Integer.parseInt(request.getParameter("prodId").split("&")[0]);
			int quantity = Integer.parseInt(request.getParameter("qty").split("&")[0]);
			b = pHelper.restockProduct(prodId, getProdManagerId(username), quantity);
		}
		else if (param.compareToIgnoreCase("add") == 0)
		{
			String username = (String) request.getParameter("username").split("&")[0];
			username = helper.getProdNameByToken(username);
			String name = (String) request.getParameter("name").split("&")[0];
			double price = Double.parseDouble((String) request.getParameter("price").split("&")[0]);
			int quantity = Integer.parseInt(request.getParameter("qty").split("&")[0]);
			String imageLink = (String) request.getParameter("imgLink").split("&")[0];
			b = pHelper.addProduct(name, getProdManagerId(username), price, quantity, imageLink);
		}
		
		else if (param.compareToIgnoreCase("delete") == 0)
		{
			String username = (String) request.getParameter("username").split("&")[0];
			username = helper.getProdNameByToken(username);
			int prodId = Integer.parseInt(request.getParameter("prodId").split("&")[0]);
			b = pHelper.deleteProduct(prodId, getProdManagerId(username));
		}
		
		else if (param.compareToIgnoreCase("edit") == 0)
		{
			String username = (String) request.getParameter("username").split("&")[0];
			username = helper.getProdNameByToken(username);
			int prodId = Integer.parseInt(request.getParameter("prodId").split("&")[0]);
			String imageLink = null;
			String name = null;
			double price = -1;
			
			if(!(request.getParameter("imgLink").split("&")[0].equalsIgnoreCase("none"))){
				imageLink = (String) request.getParameter("imgLink").split("&")[0];
				System.out.println("Link set");
			}
			if(!(request.getParameter("price").split("&")[0].equalsIgnoreCase("none"))){
				price = Double.parseDouble((String) request.getParameter("price").split("&")[0]);
				System.out.println("Price set");
			}
			if(!(request.getParameter("name").split("&")[0].equalsIgnoreCase("none"))){
				name = (String) request.getParameter("name").split("&")[0];
				System.out.println("Name set");
			}
			if(price != -1){
				b = pHelper.editProductPrice(price, getProdManagerId(username), prodId);
				System.out.println("Price updated!");
			}if((!Objects.isNull(imageLink))){
				b = pHelper.editProductImage(imageLink, getProdManagerId(username), prodId);
				System.out.println("Image link updated!");
			}if((!Objects.isNull(name))){
				b = pHelper.editProductName(name, getProdManagerId(username), prodId);
				System.out.println("Product name updated!");
			}
		}
		
		else if (param.compareToIgnoreCase("register") == 0)
		{
			String email = (String) request.getParameter("email").split("&")[0];
			String username = (String) request.getParameter("username").split("&")[0];
			String pass = (String) request.getParameter("pass").split("&")[0];
			String storeName = (String) request.getParameter("storeName").split("&")[0];
			ProductManager pm = new ProductManager(username, storeName);
			UserHelper uHelper = new UserHelper();
			boolean takenByUser = false;
			try {
				if (uHelper.getUserByUsername(username) != null || uHelper.getAdminByUsername(username) != null){
					takenByUser = true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!takenByUser)
				try {
					b = helper.register(email, pm, pass);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					b = false;
					e.printStackTrace();
				}
		}else if (param.compareToIgnoreCase("ban") == 0){
			String user = (String) request.getParameter("user").split("&")[0];
			String adminUsername = (String) request.getParameter("admin").split("&")[0];
			adminUsername = helper.getProdNameByToken(adminUsername);
			String reason = (String) request.getParameter("reason").split("&")[0];
			String password = (String) request.getParameter("password").split("&")[0];
			boolean isBanned = helper.getBannedStatus(user);
			if(isBanned)
				helper.unBanPM(adminUsername, user, reason, password);
			else
				helper.banPM(adminUsername, user, reason, password);
		}
		response.getWriter().write(String.valueOf(b));
		
	}

}

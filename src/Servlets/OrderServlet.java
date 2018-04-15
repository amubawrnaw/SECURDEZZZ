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

import DB.DBConnection;
import DB.OrderHelper;
import DB.ProductManagerHelper;
import DB.UserHelper;
import Model.Order;
import Model.Order_Details;
import Model.Order_Status;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet(urlPatterns = {"/addOrderStatus", "/getOrderByUserId", "/getOrdersByProductManager"})
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Gson gson;
	private final OrderHelper oh = new OrderHelper();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderServlet() {
        super();
        gson = new GsonBuilder().create();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();

		switch(path){
		case "/addOrderStatus":
			addOrderStatus(request, response);
			break;
			
		case "/getOrderByUserId":
			try {
				getOrderByUserId(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case "/getOrdersByProductManager":
			getOrdersByProductManager(request, response);
			break;
		}
	}
	
	private void addOrderStatus(HttpServletRequest request, HttpServletResponse response){
		Order_Status os = new Order_Status();
		
		int detail_id = Integer.parseInt(request.getParameter("detail_id"));
		String status = request.getParameter("status");
		
		os.setDetail_id(detail_id);
		os.setStatus(status);
		
		oh.addOrderStatus(os);
	}
	
	private void getOrderByUserId(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		String username = request.getParameter("username");
		UserHelper uh = new UserHelper();
		username = uh.getUserIdByToken(username);
		
		int userId = uh.getUserIdByUsername(username);
		System.out.println("getting by userId");
		Order[] orders = oh.getOrdersByUserId(userId);
		//System.out.println(gson.toJson(orders));
		try {
			response.getWriter().write(gson.toJson(orders));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getOrdersByProductManager(HttpServletRequest request, HttpServletResponse response){
		String username = request.getParameter("user");
		ProductManagerHelper pmh = new ProductManagerHelper();
		username = pmh.getProdNameByToken(username);
		int managerId =  01;
		try {
			managerId = pmh.getProductManagerIdByUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Order_Details[] orders = oh.getOrdersByProductManager(managerId);
		
		try {
			response.getWriter().write(gson.toJson(orders));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

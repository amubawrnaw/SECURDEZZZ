package DB;

import java.sql.ResultSet;
import java.util.ArrayList;

import Model.*;

public class OrderHelper {
	private DBConnection dbc;
	
	public OrderHelper(){
		dbc = new DBConnection();
	}
	
	private Order[] getOrders(String query){
		ArrayList<Order> orders = new ArrayList<>();
		try{
			ResultSet rs = dbc.executeQuery(query);
			
			while(rs.next()){
				orders.add(Order.toOrder(rs));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return orders.toArray(new Order[orders.size()]);
	}
	
	
	public Order[] getOrdersByUserId(int userId){
		String query = "SELECT * FROM `order` WHERE u_id = " + userId;
		return getOrders(query);
	}
	
	public Order_Details[] getOrdersByProductManager(int pm){
		ArrayList<Order_Details> od = new ArrayList<>();
		String query = "SELECT od.detail_id, od.orderid, od.product_id, od.qty "
				+ "FROM order_details od, product p "
				+ "WHERE p.p_manager = " + pm +" "
				+ "AND p.prod_id = od.product_id";
		System.out.println(query);
		try{
			ResultSet rs = dbc.executeQuery(query);
			
			while(rs.next()){
				od.add(Order_Details.toOrderDetail(rs, dbc));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return od.toArray(new Order_Details[od.size()]);
	}
	
	public void addOrderStatus(Order_Status os){
		String query = "INSERT INTO order_status(date, status, detail_id) VALUES(CURDATE(),'"
				+ os.getStatus() + "',"
				+ os.getDetail_id() + ")";
		dbc.updateQuery(query);
	}
	
	
	
	
	
	
}

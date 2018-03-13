package Model;

import java.sql.ResultSet;
import java.util.ArrayList;

import DB.DBConnection;


public class Order {
	
	//order table
	private int order_id;
	private int user_id;
	private String dateCreated;
	private String address;
	//order details table
	private ArrayList<Order_Details> orders;
	
	public Order(){}
	
	public Order(int order_id, int user_id, String dateCreated, String address, ArrayList<Order_Details> orders) {
		super();
		this.order_id = order_id;
		this.user_id = user_id;
		this.dateCreated = dateCreated;
		this.address = address;
		this.orders = orders;
	}

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<Order_Details> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order_Details> orders) {
		this.orders = orders;
	}

	public static Order toOrder(ResultSet rs){
		DBConnection dbc = new DBConnection();
		Order o = null;
		try{
			o = new Order();
			o.setAddress(rs.getString("address"));
			o.setUser_id(rs.getInt("u_id"));
			o.setDateCreated(rs.getDate("date_created") + "");
			o.setOrder_id(rs.getInt("order_id"));
			
			String query = "SELECT * FROM securde.order_details WHERE orderid = " + o.getOrder_id();
			ResultSet r = dbc.executeQuery(query);
			ArrayList<Order_Details> od = new ArrayList<>();
			
			while(r.next()){
				od.add(Order_Details.toOrderDetail(r, dbc));
			}
			o.setOrders(od);
		}catch(Exception e){
			e.printStackTrace();
		}
		return o;
	}
}
















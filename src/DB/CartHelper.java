package DB;

import java.sql.*;
import java.util.ArrayList;

import Model.*;

//TODO import cart
public class CartHelper {
	
	private DBConnection dbc;
	public CartHelper(){
		dbc = new DBConnection();
	}
	
	public Cart[] getCartForUser(int userId) {
		String query = "SELECT * FROM cart WHERE user_id = " + userId;
		
		return getCartFromQuery(query);
	}
	
	public boolean checkoutCart(int userId, String address) {
		boolean success = false;
		String query = "DELETE FROM cart WHERE user_id = " + userId;
		String query2 = "INSERT INTO securde.order(u_id, date_created, address) VALUES("
				+ userId + ", " + "'" + 
				new java.sql.Date(System.currentTimeMillis())+ "'" 
				+ ", '" + address + "');";
		System.out.println(query2);
		String query3 = "SELECT MAX(order_id) FROM securde.order WHERE u_id = " + userId;
		String queryBalance = "SELECT credits FROM users WHERE user_id = " + userId;
		Cart[] carts = getCartForUser(userId);
		dbc.updateQuery(query2);
		int order_id;
		try{
			ResultSet rs = dbc.executeQuery(queryBalance);
			Double userBalance = 0.0;
			if(rs.next())
				userBalance = rs.getDouble("credits");
			
			rs = dbc.executeQuery(query3);
			order_id = 0;
			if(rs.next())
				order_id = rs.getInt("MAX(order_id)");
			
			Double cartTotal = 0.0;
			String queryPrice;
			
			for(Cart c : carts){
				queryPrice = "SELECT price FROM product WHERE prod_id = " + c.getPid();
				rs = dbc.executeQuery(queryPrice);
				double price = 0.0;
				if(rs.next())
					price = rs.getDouble("price");
				cartTotal += price * c.getQty();
			}
			
			if(cartTotal <= userBalance){
				String query4 = "SELECT MAX(detail_id) FROM order_details WHERE orderid = "+order_id;
				
				for(Cart c : carts){
					String query5 = "INSERT INTO order_details(orderid,product_id, qty) VALUES("
							+ order_id + ", "
							+ c.getPid() + ", "
							+ c.getQty() + ")";
					dbc.updateQuery(query5);
					
					rs = dbc.executeQuery(query4);
					int latestDetailId = 0;
					if(rs.next())
						latestDetailId = rs.getInt("MAX(detail_id)");
					
					String query6 = "INSERT INTO order_status(date,status,detail_id) VALUES(CURDATE(),"
							+ "'Order Placed', "
							+ latestDetailId + ")";
					
					dbc.updateQuery(query6);
				}
				String updateUserBalance = "UPDATE users SET credits = credits - " + cartTotal + " WHERE user_id = " + userId;
				dbc.updateQuery(query);
				dbc.updateQuery(updateUserBalance);
				success = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return success;
	}
	
	public boolean addItemToCart(Product p, int userId, int qty){
		boolean succ = false;
		String queryCheck = "SELECT * FROM cart WHERE user_id = " + userId + " AND p_id = " + p.getP_id();
		String updateStock = "";
		try{
			//get current stocks
			//check if qty is not mroe than the stock
			if(p.getQty()>=qty){
				//check if order alr exists
				ResultSet rs = dbc.executeQuery(queryCheck);
				if(rs.next()){
					Cart c = Cart.toCart(rs);
					int newqt = qty - c.getQty();
					//check if new qty is more than stock
					if(p.getQty()>newqt){
						String updateCart = "UPDATE cart SET qty = qty + " + newqt + " WHERE user_id = " + userId + " AND p_id = " + p.getP_id();
						updateStock = "UPDATE product SET qty = qty - " + newqt + " WHERE prod_id = " + p.getP_id();
						dbc.updateQuery(updateCart);
						succ = true;
					}
				}else{
					String query = "INSERT INTO cart(user_id, p_id, qty) VALUES("
							+ userId +","
							+ p.getP_id() + ","
							+ qty + ")";
					updateStock = "UPDATE product SET qty = " + (p.getQty() - qty) + " WHERE prod_id = " + p.getP_id();
					dbc.updateQuery(query);
					succ = true;
				}
				dbc.updateQuery(updateStock);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return succ;
	}
	
	public void removeItemFromCart(Product p, int userId){
		String query = "DELETE FROM cart WHERE p_id = " + p.getP_id() + " AND user_id = " + userId;
		
		dbc.updateQuery(query);
	}
	
	public Cart[] getCartFromQuery(String query) {
		Cart[] finalArr = null;
		ArrayList<Cart> tempArr = new ArrayList<>();
		try{
			ResultSet rs = dbc.executeQuery(query);
			while(rs.next()){
				tempArr.add(Cart.toCart(rs));
			}
			finalArr = tempArr.toArray(new Cart[tempArr.size()]);		
		}catch(Exception e){
			e.printStackTrace();
		}
		return finalArr;
	}
}
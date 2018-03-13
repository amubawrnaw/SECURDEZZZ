package Model;
import java.sql.ResultSet;
import java.util.ArrayList;

import DB.DBConnection;
import Model.Order_Status;

public class Order_Details{
		private int detail_id;
		private int order_id;
		private int product_id;
		private int qty;
		private ArrayList<Order_Status> status;
		
		public Order_Details(){
		}

		public Order_Details(int detail_id, int order_id, int product_id, int qty, ArrayList<Order_Status> status) {
			super();
			this.detail_id = detail_id;
			this.order_id = order_id;
			this.product_id = product_id;
			this.qty = qty;
			this.status = status;
		}

		public int getDetail_id() {
			return detail_id;
		}

		public void setDetail_id(int detail_id) {
			this.detail_id = detail_id;
		}

		public int getOrder_id() {
			return order_id;
		}

		public void setOrder_id(int order_id) {
			this.order_id = order_id;
		}

		public int getProduct_id() {
			return product_id;
		}

		public void setProduct_id(int product_id) {
			this.product_id = product_id;
		}

		public int getQty() {
			return qty;
		}

		public void setQty(int qty) {
			this.qty = qty;
		}

		public ArrayList<Order_Status> getStatus() {
			return status;
		}

		public void setStatus(ArrayList<Order_Status> status) {
			this.status = status;
		}
		
		public static Order_Details toOrderDetail(ResultSet rs, DBConnection dbc){
			Order_Details od = null;
			try{
				od = new Order_Details();
				od.setDetail_id(rs.getInt("detail_id"));
				od.setOrder_id(rs.getInt("orderid"));
				od.setProduct_id(rs.getInt("product_id"));
				od.setQty(rs.getInt("qty"));
				
				String query = "SELECT * FROM securde.order_status WHERE detail_id = " + od.getDetail_id();
				ArrayList<Order_Status> os = new ArrayList<>();
				ResultSet r = dbc.executeQuery(query);
				while(r.next()){
					os.add(Order_Status.toOrderStatus(r));
				}
					
				od.setStatus(os);
			}catch(Exception e){
				e.printStackTrace();
			}
			return od;
		}
		
	}
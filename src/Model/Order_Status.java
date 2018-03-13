package Model;

import java.sql.ResultSet;

import DB.DBConnection;

public class Order_Status{
	private int status_id;
	private String date;
	private String status;
	private int detail_id;
	public Order_Status(){}

	public Order_Status(int status_id, String date, String status, int detail_id) {
		super();
		this.status_id = status_id;
		this.date = date;
		this.status = status;
		this.detail_id = detail_id;
	}
	
	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(int i) {
		this.detail_id = i;
	}
	
	public static Order_Status toOrderStatus(ResultSet rs){
		Order_Status os = null;
		try{
			os = new Order_Status();
			os.setDate(rs.getDate("date") + "");
			os.setDetail_id(rs.getInt("detail_id"));
			os.setStatus(rs.getString("status"));
			os.setStatus_id(rs.getInt("status_id"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return os;
	}
	
	
}
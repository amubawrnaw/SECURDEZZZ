package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Cart {
	private int qty;
	private int pid;
	private int uid;
	
	public Cart(){}

	public Cart(int qty, int pid, int uid) {
		super();
		this.qty = qty;
		this.pid = pid;
		this.uid = uid;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}
	
	public static Cart toCart(ResultSet rs) throws SQLException{
		Cart c = null;
		c = new Cart();
		c.setPid(rs.getInt("p_id"));
		c.setUid(rs.getInt("user_id"));
		c.setQty(rs.getInt("qty"));
		return c;
	}
	
}

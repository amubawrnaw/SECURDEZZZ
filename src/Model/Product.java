package Model;

import java.sql.ResultSet;

public class Product {
	private String name;
	private int p_id;
	private Double price;
	private int manager;
	private String img_link;
	private int quantity;
	
	public Product(){}

	public Product(String name, int p_id, Double price, int manager, String img_link, int quantity) {
		super();
		this.name = name;
		this.p_id = p_id;
		this.price = price;
		this.manager = manager;
		this.img_link = img_link;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}

	public String getImg_link() {
		return img_link;
	}

	public void setImg_link(String img_link) {
		this.img_link = img_link;
	}
	
	public static Product toProduct(ResultSet rs){
		Product p = new Product();
		try{
			p.setImg_link(rs.getString("img_link"));
			p.setPrice(rs.getDouble("price"));
			p.setManager(rs.getInt("p_manager"));
			p.setName(rs.getString("prod_name"));
			p.setP_id(rs.getInt("prod_id"));
			p.setQty(rs.getInt("qty"));
		}catch(Exception e){
			e.printStackTrace();
		}
		return p;
	}

	public void setQty(int quantity) {
		// TODO Auto-generated method stub
		this.quantity = quantity;
	}
	
	public int getQty(){
		return quantity;
	}
}

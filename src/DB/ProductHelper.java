package DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Product;
import Model.ProductManager;

public class ProductHelper {
	private DBConnection dbc;
	public ProductHelper(){
		dbc = new DBConnection();
		dbc.getConnection();
	};
	
	private Product[] getProductArr(String query){
		ArrayList<Product> arr = new ArrayList<>();
		try{
			int i = 0;
			ResultSet rs = dbc.executeQuery(query);
			while(rs.next()){			
				arr.add(Product.toProduct(rs));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return arr.toArray(new Product[arr.size()]);
	}
	
	public boolean addProduct(String name, int manager, double price, int quantity, String imageLink){
		String query = "INSERT INTO product(prod_name, p_manager, price, qty, img_link) VALUES(?,?,?,?,?);";
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, name);
			pstmt.setInt(2, manager);
			pstmt.setDouble(3, price);
			pstmt.setInt(4, quantity);
			pstmt.setString(5, imageLink);
			
			
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("Product added!");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean restockProduct(int prodId, int manager, int quantity){
		String query = "UPDATE product SET qty = qty + ? WHERE prod_id = ? "
				+ "AND p_manager = ?";
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setInt(1, quantity);
			pstmt.setInt(2, prodId);
			pstmt.setInt(3, manager);
			
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("Product restocked by " + quantity);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean editProductName(String newName, int manager, int prodId){
		String query = "UPDATE product SET prod_name = ? WHERE p_manager = ? AND prod_id = ?";
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, newName);
			pstmt.setInt(2, manager);
			pstmt.setInt(3, prodId);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("Product name edited!");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean editProductPrice(double newPrice, int manager, int prodId){
		
		String query = "UPDATE product SET price = ? WHERE p_manager = ? AND prod_id = ?";
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setDouble(1, newPrice);
			pstmt.setInt(2, manager);
			pstmt.setInt(3, prodId);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("Product price edited!");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean editProductImage(String newImageLink, int manager, int prodId){
		String query = "UPDATE product SET img_link = ? WHERE p_manager = ? AND prod_id = ?";
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, newImageLink);
			pstmt.setInt(2, manager);
			pstmt.setInt(3, prodId);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("Product image link edited!");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteProduct(int id, int manager){
		String query = "UPDATE product SET removed = 1 WHERE p_manager = ? AND prod_id = ?";
		try{
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setInt(1, id);
			pstmt.setInt(2, manager);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("Product deleted!");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean readdProduct(int id, int manager){
		String query = "UPDATE product SET removed = 0 WHERE p_manager = ? AND prod_id = ?";
		try{
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setInt(1, id);
			pstmt.setInt(2, manager);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("Product deleted!");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	return true;
}
	public Product[] searchProducts(String searchName){
		String query = "SELECT * FROM product WHERE prod_name LIKE '%"+searchName+"%' AND removed = 0";
		return getProductArr(query);
	}
	
	public Product[] getProductsByManagerId(int prodManagerId){
		String query = "SELECT * FROM product WHERE p_manager = '"+prodManagerId+"' AND removed = 0";
		return getProductArr(query);
	}
	
	public Product getProductById(int id) throws SQLException{
		String query = "SELECT * FROM product WHERE prod_id = " + id + " AND removed = 0";
		ResultSet rs = dbc.executeQuery(query);
		Product p = null;
		if(rs.next())
			p = Product.toProduct(rs);
		return p;
	}
	
	public Product[] getProductsByPrice(int start, int end){
		String query = "SELECT * FROM product WHERE price >= " + start + " AND price <= " + end + " AND removed = 0";
		return getProductArr(query);
	}
	
	public Product[] getAllProducts(){
		String query = "SELECT * FROM product WHERE removed = 0";
		return getProductArr(query);
	}
}

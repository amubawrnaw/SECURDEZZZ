package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import Model.Product;
import Model.ProductManager;
import Model.User;
import Model.PasswordHasher;

public class ProductManagerHelper {
	private PasswordHasher ph;
	private DBConnection dbc;
	public ProductManagerHelper(){
		dbc = new DBConnection();
		ph = new PasswordHasher();
	}
	
	public int getProductManagerIdByUsername(String username) throws SQLException {
		String query = "SELECT prod_man_id FROM product_manager WHERE username = ?";
		System.out.println(username);
		ResultSet rs = null;
		int id = -1;
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				id = rs.getInt("prod_man_id");
			}
			pstmt.close();
			
			System.out.println("PM with username " + username + " found, returning id");
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}	
		return id;
	}
	
	public ProductManager getProductManagerByUsername(String username) throws SQLException{
		String query = "SELECT * FROM product_manager WHERE username = ?";
		ResultSet rs = null;
		ProductManager pm = null;
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if(rs.next()){
				pm = ProductManager.toProductManager(rs);
			}
			pstmt.close();
			
			System.out.println("PM with username " + username + " found");
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

		return pm;
	}
	
	public ProductManager login(String username, String password) throws SQLException {
		System.out.println("Logging in user " + username);
		String query = "SELECT * FROM product_manager WHERE username = ?";
		ResultSet rs = null;
		ProductManager pm = null;
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if(rs.next()){
				System.out.println(rs.getString("password"));
				if(ph.checkPassword(rs.getString("password"), password, getPMSalt(username)))
					pm = ProductManager.toProductManager(rs);
			}
			pstmt.close();
			
			System.out.println("User with username " + username + " found");
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return pm;
	}
	
	private ProductManager[] getProductManagerFromQuery(String query) {
			ProductManager[] finalArr = null;
			ArrayList<ProductManager> tempArr = new ArrayList<>();
			try{
				ResultSet rs = dbc.executeQuery(query);
				while(rs.next()){
					tempArr.add(ProductManager.toProductManager(rs));
				}
				
				finalArr = new ProductManager[tempArr.size()];
				
				for(int i = 0 ; i < tempArr.size(); i ++){
					finalArr[i] = tempArr.get(i);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return finalArr;
	}

	public boolean register(ProductManager pm, String password) throws SQLException {
		boolean regSuccess = false;
		
		ProductManager pmCheck = getProductManagerByUsername(pm.getUsername());
		
		if(pmCheck == null){
			System.out.println("Registration successful for user " + pm.getUsername());
			regSuccess = true;
			String query = "INSERT INTO product_manager(username, password, store_name, salt) " 
			+ "VALUES(?,?,?,?)";
			try{
				String salt = ph.generateSalt();
				PreparedStatement pstmt = dbc.createPreparedStatement(query);
				pstmt.setString(1, pm.getUsername());
				pstmt.setString(2, ph.hashPassword(password, salt));
				pstmt.setString(3, pm.getStoreName());
				pstmt.setString(4, salt);
				pstmt.executeUpdate();
				pstmt.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return regSuccess;
	}
	
	public String getPMSalt(String username){
		String query = "SELECT salt FROM product_manager WHERE username = ?";
		ResultSet rs = null;
		String salt = null;
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if(rs.next()){
				salt = rs.getString("salt");
			}
			pstmt.close();
			System.out.println("Getting salt for " + username);
		}catch(Exception e){
			e.printStackTrace();
			return salt;
		}
		return salt;
	}
}

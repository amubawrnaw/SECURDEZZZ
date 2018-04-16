package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import Model.Product;
import Model.ProductManager;
import Model.User;
import Model.EmailSender;
import Model.PasswordHasher;

public class ProductManagerHelper {
	private PasswordHasher ph;
	private DBConnection dbc;
	public ProductManagerHelper(){
		dbc = new DBConnection();
		ph = new PasswordHasher();
	}
	
	public ArrayList<ProductManager> getAllProductManagers(){
		System.out.println("Getting all product managers");
		String query = "SELECT * FROM product_manager";
		ResultSet rs = null;
		ArrayList<ProductManager> pmList = new ArrayList<ProductManager>();
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()){
				pmList.add(ProductManager.toProductManager(rs));
			}
			pstmt.close();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return pmList;
	}
	
	public boolean getBannedStatus(String user) {
		String query = "SELECT banned FROM product_manager WHERE username = ?";
		ResultSet rs = null;
		boolean status = false;
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, user);
			rs = pstmt.executeQuery();
			if(rs.next()){
				int banned = rs.getInt("banned");
				if(banned == 1)
					status = true;
			}
			pstmt.close();
			System.out.println("Getting status for " + user);
		}catch(Exception e){
			e.printStackTrace();
			return status;
		}
		return status;
	}
	
	public String getPMEmailByUsername(String username)throws SQLException{
		String query = "SELECT email FROM product_manager WHERE username = ?";
		System.out.println(username);
		ResultSet rs = null;
		String email = null;
		try{
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				email = rs.getString("email");
			}
			pstmt.close();
			
			System.out.println("PM with username " + username + " found, returning email");
		}catch(Exception e){
			e.printStackTrace();
			return email;
		}	
		return email;
	}
	
	public void banPM(String adminUsername, String userToBeBanned, String reason, String password) {
		System.out.println("Banning user " + userToBeBanned + " being banned by " + adminUsername);
		boolean adminExists = Objects.nonNull(loginAdmin(adminUsername, password));
		String query = "UPDATE product_manager SET banned = ? WHERE username = ?";
		if(adminExists){
			try{
				PreparedStatement pstmt = dbc.createPreparedStatement(query);
				pstmt.setInt(1, 1);
				pstmt.setString(2, userToBeBanned);
				pstmt.executeUpdate();
				pstmt.close();
				
				System.out.println("User " + userToBeBanned + " banned!");
			}catch(Exception e){
				e.printStackTrace();
				return;
			}
			String email = null;
			try {
				email = getPMEmailByUsername(userToBeBanned);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EmailSender es = new EmailSender();
			es.sendBanReason(email, reason);
		}
	}
	
	public void unBanPM(String adminUsername, String userToBeBanned, String reason, String password) {
		System.out.println("Unbanning user " + userToBeBanned + " being banned by " + adminUsername);
		boolean adminExists = Objects.nonNull(loginAdmin(adminUsername, password));
		String query = "UPDATE product_manager SET banned = ? WHERE username = ?";
		if(adminExists){
			try{
				PreparedStatement pstmt = dbc.createPreparedStatement(query);
				pstmt.setInt(1, 0);
				pstmt.setString(2, userToBeBanned);
				pstmt.executeUpdate();
				pstmt.close();
				
				System.out.println("User " + userToBeBanned + " unbanned!");
			}catch(Exception e){
				e.printStackTrace();
				return;
			}
			String email = null;
			try {
				email = getPMEmailByUsername(userToBeBanned);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EmailSender es = new EmailSender();
			es.sendunBanReason(email, reason);
		}
	}
	
	public String loginAdmin(String adminUsername, String password){
		String checkIfAdminExists = "SELECT * FROM admin WHERE username = ?";
		String adminUser = null;
		ResultSet rs = null;
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(checkIfAdminExists);
			
			pstmt.setString(1, adminUsername);
			rs = pstmt.executeQuery();
			if(rs.next()){
				System.out.println(rs.getString("password"));
				if(ph.checkPassword(rs.getString("password"), password, getAdminSalt(adminUsername)))
					adminUser = rs.getString("username");
			}
			pstmt.close();
			
			System.out.println("Admin with username " + adminUsername + " found");
		}catch(Exception e){
			e.printStackTrace();
			return adminUser;
		}
		return adminUser;
	}
	public String getAdminSalt(String username){
		String query = "SELECT salt FROM admin WHERE username = ?";
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

	public boolean register(String email, ProductManager pm, String password) throws SQLException {
		boolean regSuccess = false;
		
		ProductManager pmCheck = getProductManagerByUsername(pm.getUsername());
		
		if(pmCheck == null){
			System.out.println("Registration successful for user " + pm.getUsername());
			regSuccess = true;
			String query = "INSERT INTO product_manager(username, password, store_name, salt, email) " 
			+ "VALUES(?,?,?,?,?)";
			try{
				String salt = ph.generateSalt();
				PreparedStatement pstmt = dbc.createPreparedStatement(query);
				pstmt.setString(1, pm.getUsername());
				pstmt.setString(2, ph.hashPassword(password, salt));
				pstmt.setString(3, pm.getStoreName());
				pstmt.setString(4, salt);
				pstmt.setString(5, email);
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
	
	public String getProdNameByToken(String token){
		String query = "SELECT prod_man FROM tokens WHERE id_tokens = ?";
		System.out.println(token);
		String s = null;
		try{
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			pstmt.setString(1, token);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			s = rs.getString("prod_man");
			
			pstmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return s;
	}
	
	public String createProdManToken(String user){
		String query = "INSERT INTO tokens(id_tokens, prod_man) VALUES(?,?)";
		String t = null;
		try{
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			t = ph.generateToken();
			pstmt.setString(1, t);
			pstmt.setString(2, user);
			System.out.println("Token:" + t + " created!");
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e){
			t = null;
			e.printStackTrace();
		}
		return t;
	}
	
	public void removeProdManToken(String token){
		String query = "DELETE FROM tokens WHERE id_tokens = ?";
		try{
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1,token);
			pstmt.executeUpdate();
			pstmt.close();
			System.out.println("Salt:" + token + " deleted!");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

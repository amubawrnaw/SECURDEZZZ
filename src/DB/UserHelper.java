package DB;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;

import Model.ProductManager;
import Model.User;
import Model.EmailSender;
import Model.PasswordHasher;

public class UserHelper {
	private DBConnection dbc;
	private PasswordHasher ph;
	public UserHelper(){
		dbc = new DBConnection();
		dbc.getConnection();
		ph = new PasswordHasher();
	};
	
	public User getUserByUsername(String username) throws SQLException{	
		String query = "SELECT * FROM users WHERE username = ?";
		ResultSet rs = null;
		User u = null;
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if(rs.next()){
				u = User.toUser(rs);
			}
			pstmt.close();
			
			System.out.println("User with username " + username + " found");
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

		return u;
	}
	
	public User getUserByToken(String token){
		String query = "SELECT * FROM tokens WHERE id_tokens = ?";
		System.out.println(token);
		User u = null;
		try{
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			pstmt.setString(1, token);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			u = getUserByUsername(rs.getString("username"));
			
			pstmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return u;
	}
	
	public String createUserToken(String username){
		String query = "INSERT INTO tokens(id_tokens, username) VALUES(?,?)";
		String t = null;
		try{
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			t = ph.generateSalt();
			pstmt.setString(1, t);
			pstmt.setString(2, username);
			
			pstmt.executeUpdate();
			pstmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return t;
	}
	
	public int getUserIdByUsername(String username)throws SQLException{
		String query = "SELECT user_id FROM users WHERE username = ?";
		System.out.println(username);
		ResultSet rs = null;
		int id = -1;
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			
			if(rs.next()){
				id = rs.getInt("user_id");
			}
			pstmt.close();
			
			System.out.println("User with username " + username + " found, returning id");
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}	
		return id;
	}
	public String getUserEmailByUsername(String username)throws SQLException{
		String query = "SELECT email FROM users WHERE username = ?";
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
			
			System.out.println("User with username " + username + " found, returning email");
		}catch(Exception e){
			e.printStackTrace();
			return email;
		}	
		return email;
	}
	public User login(String username, String password) throws SQLException{
		System.out.println("Logging in user " + username);
		String query = "SELECT * FROM users WHERE username = ?";
		ResultSet rs = null;
		User u = null;
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if(rs.next()){
				System.out.println(rs.getString("password"));
				if(ph.checkPassword(rs.getString("password"), password, getUserSalt(username)))
					u = User.toUser(rs);
			}
			pstmt.close();
			
			System.out.println("User with username " + username + " found");
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return u;
	}
	
	public boolean requestPasswordEdit(String username){
		String query = "INSERT INTO passwordeditrequests(username, verification_code) VALUES(?,?)";
		String verificationCode = RandomStringUtils.randomAlphanumeric(5);
		System.out.println(verificationCode);
		try{
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2, verificationCode);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("User password edit request created!");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		String email = null;
		try {
			email = getUserEmailByUsername(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EmailSender es = new EmailSender();
		es.sendTo(email, verificationCode);
		return true;
	}
	
	public boolean confirmPasswordEdit(String username, String verificationCode){
		String query = "SELECT verification_code FROM passwordeditrequests WHERE username = ?";
		ResultSet rs = null;
		String trueCode = null;
		try{
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if(rs.next())
				trueCode = rs.getString("verification_code");
			pstmt.close();
			System.out.println("User password edit request being confirmed!");
			System.out.println(verificationCode + " " + trueCode);
			if(verificationCode.equals(trueCode)){
				String query2 = "DELETE FROM passwordeditrequests WHERE username = ?";
				try{
					PreparedStatement pstmt2 = dbc.createPreparedStatement(query2);
					pstmt2.setString(1, username);
					pstmt2.executeUpdate();
					System.out.println("Code has been used, deleting from DB");
				}catch(Exception e){
					e.printStackTrace();
					return false;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return verificationCode.equals(trueCode);
	}
	public boolean editPassword(String username, String newPassword){
		String query = "UPDATE users SET password = ? WHERE username = ?";
		try{
			String salt = getUserSalt(username);
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			String hashedPassword = ph.hashPassword(newPassword, salt);
			pstmt.setString(1, hashedPassword);
			pstmt.setString(2, username);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("User password edited for " + username + "!");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean editFirstName(String username, String newFname){
		String query = "UPDATE users SET fname = ? WHERE username = ?";
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, newFname);
			pstmt.setString(2, username);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("User First Name edited!");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean editLastName(String username, String newLname){
		String query = "UPDATE users SET lname = ? WHERE username = ?";
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, newLname);
			pstmt.setString(2, username);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("User First Name edited!");
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public double getCredits(String username) throws SQLException {
		String query = "SELECT credits FROM users WHERE username = ?";
		ResultSet rs = null;
		double val = 0;
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if(rs.next()){
				val = rs.getDouble("credits");
			}
			pstmt.close();
			System.out.println("Getting credits for " + username);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
		return val;
	}
	public void reloadCredits(String username, double amount) throws SQLException {
		String query = "UPDATE USERS SET credits = credits + ? WHERE username = ?";
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			pstmt.setDouble(1, amount);
			pstmt.setString(2, username);
			
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("Credits reloaded by " + amount);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean register(String email, User user, String password) throws SQLException{
		boolean regSuccess = false;
		
		User u = getUserByUsername(user.getUsername());
		
		if(u == null){
			System.out.println("Registration successful for user " + user.getUsername());
			regSuccess = true;
			String query = "INSERT INTO users(email, fname, lname, username, password, credits, salt) " 
			+ "VALUES(?,?,?,?,?,?,?)";
			try{
				String salt = ph.generateSalt();
				PreparedStatement pstmt = dbc.createPreparedStatement(query);
				pstmt.setString(1, email);
				pstmt.setString(2, user.getFname());
				pstmt.setString(3, user.getLname());
				pstmt.setString(4, user.getUsername());
				pstmt.setString(5, ph.hashPassword(password, salt));
				pstmt.setDouble(6, user.getCredits());
				pstmt.setString(7, salt);
				pstmt.executeUpdate();
				pstmt.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return regSuccess;
	}
	
	public String getUserSalt(String username){
		String query = "SELECT salt FROM users WHERE username = ?";
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

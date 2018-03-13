package DB;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import Model.ProductManager;
import Model.User;

public class UserHelper {
	private DBConnection dbc;
	public UserHelper(){
		dbc = new DBConnection();
		dbc.getConnection();
	};
	
	public User getUserByUsername(String username) throws SQLException{
		String query = "SELECT * FROM users WHERE username = '"+ username + "'";
		System.out.println(username);
		ResultSet rs = dbc.executeQuery(query);
		
		User u = null;
		if(rs.next()){
			u = User.toUser(rs);
		}
		return u;
	}
	
	public int getUserIdByUsername(String username)throws SQLException{
		String query = "SELECT user_id FROM users WHERE username = '"+ username + "'";
		System.out.println(username);
		ResultSet rs = dbc.executeQuery(query);
		
		int id = -1;
		if(rs.next()){
			id = rs.getInt("user_id");
		}
		return id;
	}
	public User login(String username, String password) throws SQLException{
		System.out.println("Logging in user " + username);
		String query = "SELECT * FROM users WHERE username = '"+ username + "' "
				+ "AND password = '" + password + "'";
		ResultSet rs = dbc.executeQuery(query);
		User u = null;
		if(rs.next()){
			u = User.toUser(rs);
		}
		return u;
	}
	
	public boolean editPassword(String username, String newPassword){
		String query = "UPDATE users SET password = ? WHERE username = ?";
		try{
			
			PreparedStatement pstmt = dbc.createPreparedStatement(query);
			
			pstmt.setString(1, newPassword);
			pstmt.setString(2, username);
			pstmt.executeUpdate();
			pstmt.close();
			
			System.out.println("User password edited!");
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
		String query = "SELECT credits FROM users WHERE username = '"+ username + "'";
		ResultSet rs = dbc.executeQuery(query);
		double val = 0;
		if(rs.next())
		{
			val = rs.getDouble("credits");
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
	
	public boolean register(User user, String password) throws SQLException{
		boolean regSuccess = false;
		
		User u = getUserByUsername(user.getUsername());
		
		if(u == null){
			System.out.println("Registration successful for user " + user.getUsername());
			regSuccess = true;
			String query = "INSERT INTO users(fname, lname, username, password, credits) VALUES("
					+ "'" + user.getFname() + "', "
					+ "'" + user.getLname() + "', "
					+ "'" + user.getUsername() + "', "
					+ "'" + password+ "', "
					+ "'" + user.getCredits() + "');";
			
			dbc.updateQuery(query);
		}
		return regSuccess;
	}
	
}

package DB;
import java.sql.*;

public class DBConnection {
	private String	driverName	= "com.mysql.jdbc.Driver";
	private String	url		= "jdbc:mysql://localhost:3306/";
	private String	database	= "securdb";
	private String	username	= "root";
	private String	password	= "1234";
	public Connection getConnection() {
		
		try {
			Class.forName(driverName);
			DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
			Connection con = DriverManager
					.getConnection(url + database, username, password);
			System.out.println("DBConnection::Connection SUCCESS");
			return con;
		} catch (Exception ex) {
			System.out.println("Exception Caught::Unable to connect to " + url + database);
                        ex.printStackTrace();
		}
		return null;
	}
        
        public ResultSet executeQuery(String s){
            try{
                Statement statement = getConnection().createStatement();
                ResultSet rs = statement.executeQuery(s);
                return rs;
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
        public void updateQuery(String s){
            try{
                Statement statement = getConnection().createStatement();
                statement.executeUpdate(s);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        public PreparedStatement createPreparedStatement(String query){
        	PreparedStatement ps = null;
        	try{
        		Connection conn = getConnection();
        		ps = conn.prepareStatement(query);
        		return ps;
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        	return ps;
        }
        
}

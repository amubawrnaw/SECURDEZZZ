package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import DB.*;
import Model.User;
import Model.Admin;
import Model.EmailSender;
import Model.ProductManager;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final UserHelper helper = new UserHelper();
    private final ProductManagerHelper pmHelper = new ProductManagerHelper();
    private final Gson gson = new GsonBuilder().create();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String param = (String) request.getParameter("param").split("&")[0];
		System.out.println(param);
		boolean b = false;
		
		if(param.compareToIgnoreCase("loggedin") == 0){
			Cookie cookie = request.getCookies()[0];
			System.out.println("yay");
			b = true;
			//add 3 weeks to life
			cookie.setMaxAge(cookie.getMaxAge() + 60*60*24*21);
		}else if(param.compareToIgnoreCase("login") == 0){
			String user = (String) request.getParameter("user").split("&")[0];
			String pass = (String) request.getParameter("pass").split("&")[0];
			//boolean remember = Boolean.parseBoolean((String)request.getParameter("remembered").split("&")[0]);
			try {
				if(helper.login(user, pass) != null){
					b = true;
					String t = helper.createUserToken(user);
					Cookie cookie = new Cookie("token", t);
					System.out.println("User " + user + " logged in");
					response.addCookie(cookie);
				}else if(pmHelper.login(user,pass) != null){
					b = true;
					String t = pmHelper.createProdManToken(user);
					Cookie cookie = new Cookie("token", t);
					
					System.out.println("PM " + user + " logged in");
					response.addCookie(cookie);
				}else{
					if(helper.loginAdmin(user, pass) != null)
					{
						b = true;
						String t = helper.createUserToken(user);
						Cookie cookie = new Cookie("token", t);
						response.addCookie(cookie);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(b);
			response.getWriter().write(String.valueOf(b));
		}else if(param.compareToIgnoreCase("user") == 0){
			System.out.println("Getting by user");
			String user = (String) request.getParameter("user").split("&")[0];
			User u = null;
			ProductManager pm = null;
			
			String temp = helper.getUserIdByToken(user);
			if(temp==null) temp = pmHelper.getProdNameByToken(user);
			user = temp;
			
			try {
				u = helper.getUserByUsername(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(u!=null){
				System.out.println("Im a User");
				response.getWriter().write(gson.toJson(u));
			}else {
				try {
					pm = pmHelper.getProductManagerByUsername(user);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (pm != null){
					System.out.println("Im a Product Manager");
					response.getWriter().write(gson.toJson(pm));
				} else {
					String adminUser = null;
					try {
						adminUser = helper.getAdminByUsername(user);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}if (adminUser != null) {
						System.out.println("I'm an admin!");
						Admin admin = new Admin(adminUser);
						response.getWriter().write(gson.toJson(admin));
					}
				}
			}
			
		}else if(param.compareToIgnoreCase("logout") == 0){
			
			
			Cookie[] cook = request.getCookies();
			for(Cookie c : cook){
				if(c.getName().equals("username")){
					c.setMaxAge(0);

					helper.removeUserToken(c.getValue());
					
					response.addCookie(c);
					break;
				}
			}
		}else if(param.compareToIgnoreCase("getCredits") == 0){
			String user = (String) request.getParameter("user").split("&")[0];
			String temp = helper.getUserIdByToken(user);
			if(temp==null) temp = pmHelper.getProdNameByToken(user);
			user = temp;
			double credits = 0.0;
			try {
				credits = helper.getCredits(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.getWriter().write(gson.toJson(credits));
		}else if (param.compareToIgnoreCase("requestToEdit") == 0){
			//TODO this checks if the verification code provided by the user is the same as the emailed one
			String verificationCode = (String) request.getParameter("code").split("&")[0];
			String username = (String) request.getParameter("user").split("&")[0];
			String temp = helper.getUserIdByToken(username);
			if(temp==null) temp = pmHelper.getProdNameByToken(username);
			username = temp;
			boolean isValidCode = helper.confirmPasswordEdit(username, verificationCode);
			System.out.println(isValidCode);
			response.getWriter().write(String.valueOf(isValidCode));
		}else if (param.compareToIgnoreCase("getAllUser") == 0){
			response.getWriter().write(gson.toJson(helper.getAllUsers()));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			String param = (String) request.getParameter("param").split("&")[0];
			System.out.println(param);
			if(param.compareToIgnoreCase("register") == 0){
				String email = (String) request.getParameter("user").split("&")[0];
				String userName = (String) request.getParameter("user").split("&")[0];
				String pass = (String) request.getParameter("pass").split("&")[0];
				String fName = (String) request.getParameter("fName").split("&")[0];
				String lName = (String) request.getParameter("lName").split("&")[0];
				User user = new User(userName, 0.0, fName, lName);
				boolean b = false;
				System.out.println("Registering user " + userName);
				try {
					b = helper.register(email, user, pass);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response.getWriter().write(String.valueOf(b));
			}else if(param.compareToIgnoreCase("reload") == 0){
				String userName = (String) request.getParameter("user").split("&")[0];
				userName = helper.getUserIdByToken(userName);
				double amount = Double.parseDouble(request.getParameter("amount").split("&")[0]);
				try {
					helper.reloadCredits(userName, amount);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if (param.compareToIgnoreCase("forgotrequest") == 0){
				//TODO doing this redirects to verification code page
				String username = (String) request.getParameter("user").split("&")[0];
				boolean b = false;
				try {
					if(helper.getUserByUsername(username) != null)
					{
						helper.requestPasswordEdit(username);
						Cookie cookie = new Cookie("username", username);
						response.addCookie(cookie);
						b = true;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response.getWriter().write(String.valueOf(b));
			}else if (param.compareToIgnoreCase("editPass") == 0){
				String username = (String) request.getParameter("user").split("&")[0];
				String password = (String) request.getParameter("password").split("&")[0];
				
				String temp = helper.getUserIdByToken(username);
				if(temp==null) temp = pmHelper.getProdNameByToken(username);
				username = temp;
				
				helper.editPassword(username, password);
				System.out.println("Password set");
			}else if (param.compareToIgnoreCase("edit") == 0){
				String username = (String) request.getParameter("user").split("&")[0];
				String newFname = null;
				String newLname = null;
				
				String temp = helper.getUserIdByToken(username);
				if(temp==null) temp = pmHelper.getProdNameByToken(username);
				username = temp;
				
				if(!(request.getParameter("newFname").split("&")[0].equalsIgnoreCase("none"))){
					newFname = (String) request.getParameter("newFname").split("&")[0];
					System.out.println("First name set");
				}
				if(!(request.getParameter("newLname").split("&")[0].equalsIgnoreCase("none"))){
					newLname = (String) request.getParameter("newLname").split("&")[0];
					System.out.println("Last name set");
				}
				
				if((!Objects.isNull(newFname))){
					helper.editFirstName(username, newFname);
					System.out.println("First name updated!");
				}if((!Objects.isNull(newLname))){
					helper.editLastName(username, newLname);
					System.out.println("Last name updated!");
				}
			}else if (param.compareToIgnoreCase("ban") == 0){
				String user = (String) request.getParameter("user").split("&")[0];
				String adminUsername = (String) request.getParameter("admin").split("&")[0];
				String reason = (String) request.getParameter("reason").split("&")[0];
				String password = (String) request.getParameter("password").split("&")[0];
				boolean isBanned = helper.getBannedStatus(user);
				if(isBanned)
					helper.unBanUser(adminUsername, user, reason, password);
				else
					helper.banUser(adminUsername, user, reason, password);
			}
	}

}

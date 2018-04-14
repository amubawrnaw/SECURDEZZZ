package Model;

import DB.UserHelper;

public class AdminPasswordMaker {
	public static void main(String args[]){
		PasswordHasher ph = new PasswordHasher();
		String salt = ph.generateSalt();
		System.out.println(salt);
		System.out.println(ph.hashPassword("PasswordToBeHashed", salt));
		
		UserHelper uh = new UserHelper();
		uh.unBanUser("jonnskiGod", "jonnski", "You okay now", "Password");
	}
}

package Model;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
public class PasswordHasher {

	public PasswordHasher(){}
	
	public String generateSalt(){
		return RandomStringUtils.randomAscii(20);
	}
	public String hashPassword(String password, String salt){
		return DigestUtils.sha256Hex(password + salt);
	}
	
	public boolean checkPassword(String passFromDb, String passFromUser, String salt){
		return passFromDb.equals(DigestUtils.sha256Hex(passFromUser + salt));
	}
}

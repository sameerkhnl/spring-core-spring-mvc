package guru.springframework.services.security;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by jt on 12/14/15.
 */
@Service
public class EncryptionServiceImpl implements EncryptionService {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public String encryptString(String input){
        return bCryptPasswordEncoder.encode(input);
    }

    public boolean checkPassword(String plainPassword, String encryptedPassword){
        return bCryptPasswordEncoder.matches(plainPassword, encryptedPassword);
    }
}

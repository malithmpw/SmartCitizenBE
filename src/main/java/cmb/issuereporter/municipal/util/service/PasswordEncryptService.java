package cmb.issuereporter.municipal.util.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PasswordEncryptService {

        public String  encryptedPassword(String passwrod)
        {
            String generatedPassword = null;
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(passwrod.getBytes());
                byte[] bytes = md.digest();
                StringBuilder sb = new StringBuilder();
                for(int i=0; i< bytes.length ;i++)
                {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
            return generatedPassword;
        }


}

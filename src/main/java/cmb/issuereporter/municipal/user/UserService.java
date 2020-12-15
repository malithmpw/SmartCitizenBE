package cmb.issuereporter.municipal.user;

import cmb.issuereporter.municipal.dto.ChangePasswordRequestDTO;
import cmb.issuereporter.municipal.dto.CustomError;
import cmb.issuereporter.municipal.dto.UserDTO;
import cmb.issuereporter.municipal.model.User;
import cmb.issuereporter.municipal.util.service.PasswordEncryptService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncryptService passwordEncryptService;

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    public ResponseEntity login(String phoneNo, String password){
        User user = userRepository.findByPhoneNo(phoneNo);
        if(user == null){
            LOGGER.info("Phone number Incorrect : " + phoneNo);
            return new ResponseEntity<>(new CustomError(3001,"Phone number Incorrect"), HttpStatus.NOT_FOUND);
        }else{
            if(password.equals(user.getPassword())){
                LOGGER.info("User Found  : " + phoneNo);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }else{
                LOGGER.info("Password Incorrect  : " + phoneNo);
                return new ResponseEntity<>(new CustomError(3001,"Password Incorrect"), HttpStatus.NOT_FOUND);
            }

        }
    }

    public ResponseEntity userRegistration(UserDTO userDTO) {
        if(checkPhoneNoAlreadyExist(userDTO.getPhoneNo())){
            LOGGER.info("User Registration : Phone Number Already Exist  : " + userDTO.getPhoneNo());
            return new ResponseEntity<>(new CustomError(3002,"Phone Number Already Exist"), HttpStatus.NOT_FOUND);
        }else {
            User newUser = new User();
            newUser.setEmail(userDTO.getEmail());
            newUser.setFirstName(userDTO.getFirstName());
            newUser.setLastName(userDTO.getLastName());
            newUser.setPassword(userDTO.getPassword());
            newUser.setPhoneNo(userDTO.getPhoneNo());
            newUser.setRole(userDTO.getRole());
            User user = userRepository.save(newUser);
            if (user == null) {
                LOGGER.info("User Registration : Fail  : " + userDTO.getPhoneNo());
                return new ResponseEntity<>(new CustomError(3003,"User Registration failed"), HttpStatus.NOT_FOUND);
            } else {
                LOGGER.info("User Registration : Success  : " + userDTO.getPhoneNo());
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }

    }

    public boolean checkPhoneNoAlreadyExist(String phoneNo){
        User user = userRepository.findByPhoneNo(phoneNo);
        if (user == null){
            return false;
        }else{
            return true;
        }
    }

    public User getUser(int id){
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    public ResponseEntity updateUser(UserDTO userDTO){
        User user = getUser(userDTO.getId());
        if(user != null){
            user.setRole(userDTO.getRole());
            user.setPassword(userDTO.getPassword());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user = userRepository.save(user);

            if(user != null){
                LOGGER.info("User Update : Success  : " + userDTO.getPhoneNo());
                return new ResponseEntity(user, HttpStatus.OK);
            }else{
                LOGGER.info("User Update : Fail  : " + userDTO.getPhoneNo());
                return new ResponseEntity(new CustomError(3004, "User Creation failed"), HttpStatus.NOT_FOUND);
            }
        }else{
            LOGGER.info("User Update : User Not Found  : " + userDTO.getPhoneNo());
            return new ResponseEntity(new CustomError(3001,"User Not Found"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity updateAdminUser(UserDTO userDTO){
        User user = getUser(userDTO.getId());
        if(user != null){
            user.setCategory(userDTO.getCategory());
            user.setRole(userDTO.getRole());
            if(!(userDTO.getPassword() == null || userDTO.getPassword() == "")){
                user.setPassword(passwordEncryptService.encryptedPassword(userDTO.getPassword()));
            }
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user = userRepository.save(user);

            if(user != null){
                LOGGER.info("User Update : Success  : " + userDTO.getPhoneNo());
                return new ResponseEntity(user, HttpStatus.OK);
            }else{
                LOGGER.info("User Update : Fail  : " + userDTO.getPhoneNo());
                return new ResponseEntity(new CustomError(3004, "User Creation failed"), HttpStatus.NOT_FOUND);
            }
        }else{
            LOGGER.info("User Update : User Not Found  : " + userDTO.getPhoneNo());
            return new ResponseEntity(new CustomError(3001,"User Not Found"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity adminUserRegistration(UserDTO userDTO) {
        if(checkPhoneNoAlreadyExist(userDTO.getPhoneNo())){
            LOGGER.info("User Registration : Phone Number Already Exist  : " + userDTO.getPhoneNo());
            return new ResponseEntity<>(new CustomError(3002,"Phone Number Already Exist"), HttpStatus.NOT_FOUND);
        }else {
            User newUser = new User();
            newUser.setEmail(userDTO.getEmail());
            newUser.setFirstName(userDTO.getFirstName());
            newUser.setLastName(userDTO.getLastName());
            newUser.setPassword(passwordEncryptService.encryptedPassword(userDTO.getPassword()));
            newUser.setPhoneNo(userDTO.getPhoneNo());
            newUser.setRole(userDTO.getRole());
            newUser.setCategory(userDTO.getCategory());
            User user = userRepository.save(newUser);
            if (user == null) {
                LOGGER.info("User Registration : Fail  : " + userDTO.getPhoneNo());
                return new ResponseEntity<>(new CustomError(3003,"User Registration failed"), HttpStatus.NOT_FOUND);
            } else {
                LOGGER.info("User Registration : Success  : " + userDTO.getPhoneNo());
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }

    }

    public ResponseEntity resetPasswordUser(ChangePasswordRequestDTO changePasswordRequestDTO){
        User user = userRepository.findByPhoneNo(changePasswordRequestDTO.getUserId());
        if(user != null){
            if(user.getPassword().equals(changePasswordRequestDTO.getOldPassword())){
                user.setPassword(changePasswordRequestDTO.getNewPassword());
                user = userRepository.save(user);
                LOGGER.info("Password reset : Success  : " + changePasswordRequestDTO.getUserId());
                return new ResponseEntity(user, HttpStatus.OK);
            }else{
                LOGGER.info("Password reset : Old Password not matched  : " + changePasswordRequestDTO.getUserId());
                return new ResponseEntity(new CustomError(3005,"Old Password not matched"), HttpStatus.NOT_FOUND);
            }
        }else{
            LOGGER.info("Password reset : User Not Found  : " + changePasswordRequestDTO.getUserId());
            return new ResponseEntity(new CustomError(3001,"User Not Found"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity deleteUser(UserDTO userDTO){
        User user = userRepository.findById(userDTO.getId()).orElse(null);
        if(user != null){
            userRepository.delete(user);
            LOGGER.info("User Delete : Success  : " + userDTO.getPhoneNo());
            return new ResponseEntity("Successfully Deleted User", HttpStatus.OK);
        }else{
            LOGGER.info("User Delete : User Not Found  : " + userDTO.getPhoneNo());
            return new ResponseEntity(new CustomError(3001,"User Not Found"), HttpStatus.NOT_FOUND);
        }

    }

    public List<User> getAdminList(String roleName) {
        LOGGER.info("Get Admin User List  " );
        return userRepository.getAdminUserList(roleName);
    }
}

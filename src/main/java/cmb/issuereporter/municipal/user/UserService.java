package cmb.issuereporter.municipal.user;

import cmb.issuereporter.municipal.dto.ChangePasswordRequestDTO;
import cmb.issuereporter.municipal.dto.CustomError;
import cmb.issuereporter.municipal.dto.UserDTO;
import cmb.issuereporter.municipal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity login(String phoneNo, String password){
        User user = userRepository.findByPhoneNoAndPassword(phoneNo, password);
        if(user == null){
            return new ResponseEntity<>(new CustomError(3001,"Phone number or Password Incorrect"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    public ResponseEntity userRegistration(UserDTO userDTO) {
        if(checkPhoneNoAlreadyExist(userDTO.getPhoneNo())){
            return new ResponseEntity<>(new CustomError(3002,"Phone Number Already Exist"), HttpStatus.OK);
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
                return new ResponseEntity<>(new CustomError(3003,"User Registration failed"), HttpStatus.OK);
            } else {
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
                return new ResponseEntity(user, HttpStatus.OK);
            }else{
                return new ResponseEntity(new CustomError(3004, "User Creation failed"), HttpStatus.OK);
            }
        }else{
            return new ResponseEntity(new CustomError(3001,"User Not Found"), HttpStatus.OK);
        }
    }

    public ResponseEntity resetPasswordUser(ChangePasswordRequestDTO changePasswordRequestDTO){
        User user = userRepository.findByPhoneNo(changePasswordRequestDTO.getUserId());
        if(user != null){
            if(user.getPassword().equals(changePasswordRequestDTO.getOldPassword())){
                user.setPassword(changePasswordRequestDTO.getNewPassword());
                user = userRepository.save(user);
                return new ResponseEntity(user, HttpStatus.OK);
            }else{
                return new ResponseEntity(new CustomError(3005,"Old Password not matched"), HttpStatus.OK);
            }
        }else{
            return new ResponseEntity(new CustomError(3001,"User Not Found"), HttpStatus.OK);
        }
    }

    public ResponseEntity deleteUser(UserDTO userDTO){
        User user = userRepository.findById(userDTO.getId()).orElse(null);
        if(user != null){
            userRepository.delete(user);
            return new ResponseEntity("Successfully Deleted User", HttpStatus.OK);
        }else{
            return new ResponseEntity(new CustomError(3001,"User Not Found"), HttpStatus.OK);
        }

    }
}

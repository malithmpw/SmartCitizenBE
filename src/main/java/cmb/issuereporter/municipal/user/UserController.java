package cmb.issuereporter.municipal.user;

import cmb.issuereporter.municipal.dto.ChangePasswordRequestDTO;
import cmb.issuereporter.municipal.dto.CustomError;
import cmb.issuereporter.municipal.dto.LoginRequestDTO;
import cmb.issuereporter.municipal.dto.UserDTO;
import cmb.issuereporter.municipal.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);
    @GetMapping("/test")
    public ResponseEntity test(){
        return new ResponseEntity("Automatic build trigger...  "+new Date(), HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO){
        LOGGER.info("User Login : " + loginRequestDTO.getPhoneNo());
        return userService.login(loginRequestDTO.getPhoneNo(), loginRequestDTO.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDTO userDTO){
        LOGGER.info("User Registration : " + userDTO.getPhoneNo());
         return userService.userRegistration(userDTO);
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO){
        LOGGER.info("User Update : " + userDTO.getPhoneNo());
        return userService.updateUser(userDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteUser(@RequestBody UserDTO userDTO){
        LOGGER.info("User Delete : " + userDTO.getPhoneNo());
        return userService.deleteUser(userDTO);
    }

    @PostMapping("/password/reset")
    public ResponseEntity passwordResetUser(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO){
        LOGGER.info("User Password reset : " + changePasswordRequestDTO.getUserId());
        return userService.resetPasswordUser(changePasswordRequestDTO);
    }
}

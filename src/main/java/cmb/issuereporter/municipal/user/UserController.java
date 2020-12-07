package cmb.issuereporter.municipal.user;

import cmb.issuereporter.municipal.dto.ChangePasswordRequestDTO;
import cmb.issuereporter.municipal.dto.CustomError;
import cmb.issuereporter.municipal.dto.LoginRequestDTO;
import cmb.issuereporter.municipal.dto.UserDTO;
import cmb.issuereporter.municipal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO){
        return userService.login(loginRequestDTO.getPhoneNo(), loginRequestDTO.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserDTO userDTO){
         return userService.userRegistration(userDTO);
    }

    @PostMapping("/update")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO){
        return userService.updateUser(userDTO);
    }

    @PostMapping("/delete")
    public ResponseEntity deleteUser(@RequestBody UserDTO userDTO){
        return userService.deleteUser(userDTO);
    }

    @PostMapping("/password/reset")
    public ResponseEntity passwordResetUser(@RequestBody ChangePasswordRequestDTO changePasswordRequestDTO){
        return userService.resetPasswordUser(changePasswordRequestDTO);
    }
}

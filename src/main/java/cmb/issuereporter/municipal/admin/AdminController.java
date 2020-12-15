package cmb.issuereporter.municipal.admin;

import cmb.issuereporter.municipal.dto.LoginRequestDTO;
import cmb.issuereporter.municipal.dto.UserDTO;
import cmb.issuereporter.municipal.user.UserService;
import cmb.issuereporter.municipal.util.service.UtilService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/api/v1/")
public class AdminController {

    @Autowired
    UtilService utilService;

    @Autowired
    UserService userService;

    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO loginRequestDTO){
        LOGGER.info("User Login : " + loginRequestDTO.getPhoneNo());
        return userService.login(loginRequestDTO.getPhoneNo(), loginRequestDTO.getPassword());
    }

    @GetMapping("/appData/{userId}")
    public ResponseEntity getAppData(@PathVariable("userId") Integer userId){
        LOGGER.info("Get App Data List : start" + userId);
        return utilService.getAppData(userId);
    }

    @PostMapping("/save")
    public ResponseEntity login(@RequestBody UserDTO userDTO){
        LOGGER.info("User Registration : " + userDTO.getPhoneNo());
        if(userDTO.getId() != null){
            return userService.updateAdminUser(userDTO);
        }else{
            return userService.adminUserRegistration(userDTO);
        }
    }
}

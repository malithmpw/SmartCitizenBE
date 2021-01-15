package cmb.issuereporter.municipal.util.service;

import cmb.issuereporter.municipal.dto.AppDataResponse;
import cmb.issuereporter.municipal.dto.UserDTO;
import cmb.issuereporter.municipal.model.Area;
import cmb.issuereporter.municipal.model.Category;
import cmb.issuereporter.municipal.model.Role;
import cmb.issuereporter.municipal.model.User;
import cmb.issuereporter.municipal.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UtilService {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    AreaService areaService;

    private static final Logger LOGGER = LogManager.getLogger(UtilService.class);
    public ResponseEntity getAppData(Integer userId){
        List<Role> roles = roleService.getAllRoleList();
        List<Category> categories= categoryService.getAllCategoryList();
        List<Area> areas = areaService.getAllAreaList();
        List<User> users = new ArrayList<>();
        List<UserDTO> userDTOS = new ArrayList<>();
        if(userId != null){
            User user = userService.getUser(userId);
            if(user.getRole() != null){

                users = userService.getAdminList("ADMIN");
                users.stream().forEach(userObj -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(userObj.getId());
                    userDTO.setEmail(userObj.getEmail());
                    userDTO.setFirstName(userObj.getFirstName());
                    userDTO.setLastName(userObj.getLastName());
                    userDTO.setPhoneNo(userObj.getPhoneNo());
                    userDTO.setRole(userObj.getRole());
                    userDTO.setCategory(userObj.getCategory());
                    userDTOS.add(userDTO);
                });
                LOGGER.info("Get App Data For Admin : " + userId);
            }
        }
        AppDataResponse appDataResponse = new AppDataResponse();
        appDataResponse.setAdminUserList(userDTOS);
        appDataResponse.setCategoryList(categories);
        appDataResponse.setRoleList(roles);
        appDataResponse.setAreas(areas);

        return new ResponseEntity(appDataResponse, HttpStatus.OK);
    }

    public Timestamp getTime(){
        LocalDateTime today = LocalDateTime.now();
        ZoneId lankaZoneId = ZoneId.of("Asia/Colombo");
        ZonedDateTime asiaZonedDateTime = today.atZone(lankaZoneId);
        return Timestamp.from(asiaZonedDateTime.toInstant());
    }



}

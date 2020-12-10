package cmb.issuereporter.municipal.util.service;

import cmb.issuereporter.municipal.dto.AppDataResponse;
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

import java.util.ArrayList;
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
        if(userId != null){
            User user = userService.getUser(userId);
            if(!(user.getRole() == null || user.getRole().getName().equals("USER"))){
               users = userService.getAdminList("ADMIN");
                LOGGER.info("Get App Data For Admin : " + userId);
            }
        }
        AppDataResponse appDataResponse = new AppDataResponse();
        appDataResponse.setAdminUserList(users);
        appDataResponse.setCategoryList(categories);
        appDataResponse.setRoleList(roles);
        appDataResponse.setAreas(areas);

        return new ResponseEntity(appDataResponse, HttpStatus.OK);
    }

}

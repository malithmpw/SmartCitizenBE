package cmb.issuereporter.municipal.util.controller;

import cmb.issuereporter.municipal.dto.RoleDTO;
import cmb.issuereporter.municipal.model.Role;
import cmb.issuereporter.municipal.util.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    private static final Logger LOGGER = LogManager.getLogger(RoleController.class);
    @GetMapping(value = "/all")
    public ResponseEntity getRoleList() {
        LOGGER.info("Get Role List : start" );
        List<Role> roleList = roleService.getAllRoleList();
        LOGGER.info("Get Role List : end" );
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity addRole(@RequestBody RoleDTO roleDTO){
        LOGGER.info("Save Role  : start" );
        return roleService.addRole(roleDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity deleteRole(@RequestBody RoleDTO roleDTO){
        LOGGER.info("Delete Role  : start" );
        return roleService.deleteRole(roleDTO);
    }
}

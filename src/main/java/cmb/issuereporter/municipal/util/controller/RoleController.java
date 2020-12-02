package cmb.issuereporter.municipal.util.controller;

import cmb.issuereporter.municipal.dto.RoleDTO;
import cmb.issuereporter.municipal.model.Role;
import cmb.issuereporter.municipal.util.service.RoleService;
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

    @GetMapping(value = "/all")
    public ResponseEntity getRoleList() {
        List<Role> roleList = roleService.getAllRoleList();
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity addCategory(@RequestBody RoleDTO roleDTO){
        return roleService.addRole(roleDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity deleteCategory(@RequestBody RoleDTO roleDTO){
        return roleService.deleteRole(roleDTO);
    }
}

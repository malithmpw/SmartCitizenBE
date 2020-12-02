package cmb.issuereporter.municipal.util.service;


import cmb.issuereporter.municipal.dto.CustomError;
import cmb.issuereporter.municipal.dto.RoleDTO;
import cmb.issuereporter.municipal.model.Category;
import cmb.issuereporter.municipal.model.Role;
import cmb.issuereporter.municipal.util.repsitory.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public List<Role> getAllRoleList(){
        List<Role> roles = roleRepository.findAll();
        return roles;
    }

    public Role findById(int id){
        Role role = roleRepository.findById(id).orElse(null);
        return role;
    }

    public ResponseEntity addRole(RoleDTO roleDTO){
        Role role = roleRepository.findByName(roleDTO.getName());
        if(role == null){
            Role newRole = new Role();
            newRole.setName(roleDTO.getName());
            newRole = roleRepository.save(newRole);
            if(newRole != null){
                return new ResponseEntity(newRole, HttpStatus.OK);
            }else{
                return new ResponseEntity(new CustomError(3004, "Role Creation failed"), HttpStatus.OK);
            }
        }else {
            return new ResponseEntity(new CustomError(3002, "Role Name Already Exist"), HttpStatus.OK);
        }
    }

    public ResponseEntity deleteRole(RoleDTO roleDTO) {
        Role role = roleRepository.findById(roleDTO.getId()).orElse(null);
        if(role != null){
            roleRepository.delete(role);
            return new ResponseEntity("Successfully Deleted Role", HttpStatus.OK);
        }else {
            return new ResponseEntity(new CustomError(3001, "Role Not Found"), HttpStatus.OK);
        }
    }
}

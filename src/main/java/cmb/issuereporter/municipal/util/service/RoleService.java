package cmb.issuereporter.municipal.util.service;


import cmb.issuereporter.municipal.dto.CustomError;
import cmb.issuereporter.municipal.dto.RoleDTO;
import cmb.issuereporter.municipal.model.Role;
import cmb.issuereporter.municipal.util.repsitory.RoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    private static final Logger LOGGER = LogManager.getLogger(RoleService.class);
    public List<Role> getAllRoleList(){
        List<Role> roles = roleRepository.findAll();
        LOGGER.info("Get Role List Service: List Size : " +roles.size());
        return roles;
    }

    public Role findById(int id){
        Role role = roleRepository.findById(id).orElse(null);
        return role;
    }

    public ResponseEntity addRole(RoleDTO roleDTO){
        Role role = roleRepository.findByName(roleDTO.getName());
        LOGGER.info("Save Role Service : Start : " + roleDTO.getName());
        if(role == null){
            Role newRole = new Role();
            newRole.setName(roleDTO.getName());
            newRole = roleRepository.save(newRole);
            if(newRole != null){
                LOGGER.info("Save Role Service : Success : " + roleDTO.getName());
                return new ResponseEntity(newRole, HttpStatus.OK);
            }else{
                LOGGER.info("Save Role Service : Role Creation failed : " + roleDTO.getName());
                return new ResponseEntity(new CustomError(3004, "Role Creation failed"), HttpStatus.NOT_FOUND);
            }
        }else {
            LOGGER.info("Save Role Service : Role Name Already Exist : " + roleDTO.getName());
            return new ResponseEntity(new CustomError(3002, "Role Name Already Exist"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity deleteRole(RoleDTO roleDTO) {
        Role role = roleRepository.findById(roleDTO.getId()).orElse(null);
        if(role != null){
            roleRepository.delete(role);
            LOGGER.info("Delete Role Service : Successfully Deleted Role : " + roleDTO.getName());
            return new ResponseEntity("Successfully Deleted Role", HttpStatus.OK);
        }else {
            LOGGER.info("Delete Role Service : Role Not Found : " + roleDTO.getName());
            return new ResponseEntity(new CustomError(3001, "Role Not Found"), HttpStatus.NOT_FOUND);
        }
    }
}

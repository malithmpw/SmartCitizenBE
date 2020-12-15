package cmb.issuereporter.municipal.dto;

import cmb.issuereporter.municipal.model.Category;
import cmb.issuereporter.municipal.model.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    private String password;
    private Role role;
    private Category category;
}

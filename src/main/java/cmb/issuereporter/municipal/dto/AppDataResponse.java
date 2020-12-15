package cmb.issuereporter.municipal.dto;

import cmb.issuereporter.municipal.model.Area;
import cmb.issuereporter.municipal.model.Category;
import cmb.issuereporter.municipal.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class AppDataResponse {
    private List<Role> roleList;

    private List<Category> categoryList;

    private List<UserDTO> adminUserList;

    private List<Area> areas;
}

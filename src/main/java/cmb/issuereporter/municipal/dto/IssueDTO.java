package cmb.issuereporter.municipal.dto;

import cmb.issuereporter.municipal.model.Area;
import cmb.issuereporter.municipal.model.Category;
import cmb.issuereporter.municipal.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.xml.transform.sax.SAXResult;
import java.util.Date;

@Getter
@Setter
public class IssueDTO {
    private Integer id;
    private String description;
    private String[] imageUrl;
    private String status;
    private Double lat;
    private Double lon;
    private Date createdDate;
    private Date updatedDate;
    private CategoryDTO category;
    private AreaDTO area;
    private UserDTO user;
    private UserDTO assignee;
    private UserDTO assignBy;
    private String[] imageToSave;
}

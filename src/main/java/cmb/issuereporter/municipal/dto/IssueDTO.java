package cmb.issuereporter.municipal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Colombo")
    private Date createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Colombo")
    private Date updatedDate;
    private CategoryDTO category;
    private AreaDTO area;
    private UserDTO user;
    private UserDTO assignee;
    private UserDTO assignBy;
    private String[] imageToSave;
}

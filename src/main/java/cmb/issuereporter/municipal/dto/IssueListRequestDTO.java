package cmb.issuereporter.municipal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueListRequestDTO {
    private int userId;

    private String status;

    private String startDate;

    private String endDate;

    private Integer areaId;

    private Integer categoryId;

    private Integer pageNo;

    private boolean allIssue;

}

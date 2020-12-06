package cmb.issuereporter.municipal.dto;

import cmb.issuereporter.municipal.model.Issue;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IssueListResponseDTO {
    private int pageCount;

    private long totalCount;

    private List<IssueDTO> issueList;
}

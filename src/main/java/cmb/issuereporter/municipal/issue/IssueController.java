package cmb.issuereporter.municipal.issue;


import cmb.issuereporter.municipal.dto.IssueDTO;
import cmb.issuereporter.municipal.dto.IssueListRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/issue")
public class IssueController {
    @Autowired
    IssueService issueService;

    @PostMapping(value = "/all")
    public ResponseEntity getIssueList(@RequestBody IssueListRequestDTO issueListRequestDTO) {
        return issueService.getIssueList(issueListRequestDTO);
    }

    @PostMapping(value = "/update")
    public ResponseEntity updateIssue(@RequestBody List<IssueDTO> issueDTOs){
        return issueService.updateIssue(issueDTOs);
    }

    @PostMapping(value = "/add")
    public ResponseEntity addIssue(@RequestBody IssueDTO issueDTO){
        return issueService.addIssue(issueDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity deleteIssue(@RequestBody IssueDTO issueDTO){
        return issueService.deleteIssue(issueDTO);
    }

}

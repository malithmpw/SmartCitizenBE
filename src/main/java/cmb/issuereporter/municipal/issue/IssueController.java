package cmb.issuereporter.municipal.issue;


import cmb.issuereporter.municipal.dto.IssueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/v1/issue")
public class IssueController {
    @Autowired
    IssueService issueService;

    @GetMapping(value = "/all")
    public ResponseEntity getRoleList() {
        return issueService.getIssueList();
    }

    @PostMapping(value = "/add")
    public ResponseEntity addCategory(@RequestBody IssueDTO issueDTO){
        return issueService.addIssue(issueDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity deleteCategory(@RequestBody IssueDTO issueDTO){
        return issueService.deleteIssue(issueDTO);
    }

}

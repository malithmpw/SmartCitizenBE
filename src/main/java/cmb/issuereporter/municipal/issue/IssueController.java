package cmb.issuereporter.municipal.issue;


import cmb.issuereporter.municipal.dto.IssueDTO;
import cmb.issuereporter.municipal.dto.IssueListRequestDTO;
import cmb.issuereporter.municipal.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/issue")
public class IssueController {
    @Autowired
    IssueService issueService;

    private static final Logger LOGGER = LogManager.getLogger(IssueController.class);

    @PostMapping(value = "/all")
    public ResponseEntity getIssueList(@RequestBody IssueListRequestDTO issueListRequestDTO) {
        LOGGER.info("Get Issue List : " + issueListRequestDTO.getUserId());
        return issueService.getIssueList(issueListRequestDTO);
    }

    @PostMapping(value = "/update")
    public ResponseEntity updateIssue(@RequestBody List<IssueDTO> issueDTOs){
        LOGGER.info("Update Issue List : " + issueDTOs.size());
        return issueService.updateIssue(issueDTOs);
    }

    @PostMapping(value = "/update/details")
    public ResponseEntity updateIssueDetails(@RequestBody List<IssueDTO> issueDTOs){
        LOGGER.info("Update Issue List : " + issueDTOs.size());
        return issueService.updateIssueDetail(issueDTOs);
    }

    @PostMapping(value = "/add")
    public ResponseEntity addIssue(@RequestBody IssueDTO issueDTO){
        LOGGER.info("Add Issue : " + issueDTO.getUser().getPhoneNo());
        return issueService.addIssue(issueDTO);
    }

    @PostMapping(value = "/delete")
    public ResponseEntity deleteIssue(@RequestBody IssueDTO issueDTO){
        LOGGER.info("Delete Issue : " + issueDTO.getUser().getPhoneNo());
        return issueService.deleteIssue(issueDTO);
    }

}

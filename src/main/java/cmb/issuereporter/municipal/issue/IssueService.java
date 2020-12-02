package cmb.issuereporter.municipal.issue;

import cmb.issuereporter.municipal.dto.CustomError;
import cmb.issuereporter.municipal.dto.IssueDTO;
import cmb.issuereporter.municipal.model.Issue;
import cmb.issuereporter.municipal.user.UserService;
import cmb.issuereporter.municipal.util.service.AreaService;
import cmb.issuereporter.municipal.util.service.CategoryService;
import cmb.issuereporter.municipal.util.service.ImageSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueService {

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    AreaService areaService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    ImageSaveService imageSaveService;

    public ResponseEntity addIssue(IssueDTO issueDTO){
            Issue issue = new Issue();
            issue.setDescription(issueDTO.getDescription());
            issue.setImageUrl(issueDTO.getImageUrl());
            issue.setStatus(issueDTO.getStatus());
            issue.setLat(issueDTO.getLat());
            issue.setLon(issueDTO.getLon());
            issue.setCreatedDate(issueDTO.getCreatedDate());
            issue.setUpdatedDate(issueDTO.getUpdatedDate());
            if(issueDTO.getUser() != null)
                issue.setUser(userService.getUser(issueDTO.getUser().getId()));
            if(issueDTO.getArea() != null)
                issue.setArea(areaService.findById(issueDTO.getArea().getId()));
            if(issueDTO.getAssignBy() != null)
                issue.setAssignBy(userService.getUser(issueDTO.getAssignBy().getId()));
            if(issueDTO.getAssignee() != null)
                issue.setAssignee(userService.getUser(issueDTO.getAssignee().getId()));
            if(issueDTO.getCategory() != null)
                issue.setCategory(categoryService.findById(issueDTO.getCategory().getId()));

            issue = issueRepository.save(issue);
            if(issue != null){
                return new ResponseEntity(issue, HttpStatus.OK);
            }else {
                return new ResponseEntity(new CustomError(3004, "Issue Creation failed"), HttpStatus.OK);
             }
    }

    public ResponseEntity updateIssue(IssueDTO issueDTO){
        Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);
        if(issue != null){
            issue.setDescription(issueDTO.getDescription());
            issue.setImageUrl(issueDTO.getImageUrl());
            issue.setStatus(issueDTO.getStatus());
            issue.setLat(issueDTO.getLat());
            issue.setLon(issueDTO.getLon());
            issue.setCreatedDate(issueDTO.getCreatedDate());
            issue.setUpdatedDate(issueDTO.getUpdatedDate());
            if(issueDTO.getUser() != null)
                issue.setUser(userService.getUser(issueDTO.getUser().getId()));
            if(issueDTO.getArea() != null)
                issue.setArea(areaService.findById(issueDTO.getArea().getId()));
            if(issueDTO.getAssignBy() != null)
                issue.setAssignBy(userService.getUser(issueDTO.getAssignBy().getId()));
            if(issueDTO.getAssignee() != null)
                issue.setAssignee(userService.getUser(issueDTO.getAssignee().getId()));
            if(issueDTO.getCategory() != null)
                issue.setCategory(categoryService.findById(issueDTO.getCategory().getId()));

            issue = issueRepository.save(issue);
            if(issue != null){
                return new ResponseEntity(issue, HttpStatus.OK);
            }else {
                return new ResponseEntity(new CustomError(3004, "Issue Update failed"), HttpStatus.OK);
            }
        }else{
            return new ResponseEntity(new CustomError(3001, "Issue Not Found"), HttpStatus.OK);
        }

    }


    public ResponseEntity deleteIssue(IssueDTO issueDTO) {
        Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);
        if(issue != null){
            issueRepository.delete(issue);
            return new ResponseEntity("Successfully Deleted Issue", HttpStatus.OK);
        }else {
            return new ResponseEntity(new CustomError(3001, "Issue Not Found"), HttpStatus.OK);
        }
    }


    public ResponseEntity getIssueList(){
        List<Issue> issueList = issueRepository.findAll();
        return new ResponseEntity(issueList, HttpStatus.OK);

     }
}

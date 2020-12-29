package cmb.issuereporter.municipal.issue;

import cmb.issuereporter.municipal.dto.*;
import cmb.issuereporter.municipal.model.Issue;
import cmb.issuereporter.municipal.model.User;
import cmb.issuereporter.municipal.user.UserService;
import cmb.issuereporter.municipal.util.service.AreaService;
import cmb.issuereporter.municipal.util.service.CategoryService;
import cmb.issuereporter.municipal.util.service.ImageSaveService;
import cmb.issuereporter.municipal.util.service.UtilService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public class IssueService {
    @Value("${page.item.count}")
    private int pageItemCount;

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

    @Autowired
    UtilService utilService;

    private static final Logger LOGGER = LogManager.getLogger(IssueService.class);
    public ResponseEntity addIssue(IssueDTO issueDTO){
            Issue issue = new Issue();
            issue.setDescription(issueDTO.getDescription());

            issue.setStatus(issueDTO.getStatus());
            issue.setLat(issueDTO.getLat());
            issue.setLon(issueDTO.getLon());
            issue.setCreatedDate(new Date());
            issue.setUpdatedDate(new Date());
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

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
            try {
                if(issueDTO.getImageToSave() != null) {
                    String[] imageArray = issueDTO.getImageToSave();
                    String[] imageLinks = new String[imageArray.length];
                    for (int k = 0; k < imageArray.length; k++) {
                        String imageName = formatter.format(date) + "-" + System.currentTimeMillis() + k;
                        imageLinks[k] = imageSaveService.saveImage(imageName, imageArray[k]);
                        LOGGER.info("Issue Image save : " + imageLinks[k]);
                    }
                    issue.setImageUrl(imageLinks);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            issue = issueRepository.save(issue);
            if(issue != null){
                LOGGER.info("Issue Save : Success ");
                return new ResponseEntity(issue, HttpStatus.OK);
            }else {
                LOGGER.info("Issue Save : Fail ");
                return new ResponseEntity(new CustomError(3004, "Issue Creation failed"), HttpStatus.NOT_FOUND);
             }
    }

    public ResponseEntity updateIssue(List<IssueDTO> issueDTOs){
        List<Issue> updatedIssues = new ArrayList<>();
        issueDTOs.stream().forEach(issueDTO -> {
            Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);
            if(issue != null) {
                issue.setDescription(issueDTO.getDescription());
                issue.setImageUrl(issueDTO.getImageUrl());
                issue.setStatus(issueDTO.getStatus());
                issue.setLat(issueDTO.getLat());
                issue.setLon(issueDTO.getLon());
                issue.setCreatedDate(issueDTO.getCreatedDate());
                issue.setUpdatedDate(issueDTO.getUpdatedDate());
                if (issueDTO.getUser() != null)
                    issue.setUser(userService.getUser(issueDTO.getUser().getId()));
                if (issueDTO.getArea() != null)
                    issue.setArea(areaService.findById(issueDTO.getArea().getId()));
                if (issueDTO.getAssignBy() != null)
                    issue.setAssignBy(userService.getUser(issueDTO.getAssignBy().getId()));
                if (issueDTO.getAssignee() != null)
                    issue.setAssignee(userService.getUser(issueDTO.getAssignee().getId()));
                if (issueDTO.getCategory() != null)
                    issue.setCategory(categoryService.findById(issueDTO.getCategory().getId()));

                if (issueDTO.getImageToSave() != null && issueDTO.getImageToSave().length > 0) {
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
                    try {
                        String[] imageArray = issueDTO.getImageToSave();
                        String[] imageLinks = new String[imageArray.length];
                        for (int k = 0; k < imageArray.length; k++) {
                            String imageName = formatter.format(date) + "-" + System.currentTimeMillis() + k;
                            imageLinks[k] = imageSaveService.saveImage(imageName, imageArray[k]);
                            LOGGER.info("Issue Image save : "+ imageLinks[k]);
                        }
                        issue.setImageUrl(imageLinks);
                    } catch (Exception e) {
                        LOGGER.info("Issue Image save : fail  ");
                        e.printStackTrace();
                    }
                }
                LOGGER.info("Issue Update : Success  ");
                updatedIssues.add(issueRepository.save(issue));
            }else {
                LOGGER.info("Issue Not found  "+ issueDTO.getId());
            }
        });
        return new ResponseEntity(updatedIssues, HttpStatus.OK);

    }


    public ResponseEntity deleteIssue(IssueDTO issueDTO) {
        Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);
        if(issue != null){
            issueRepository.delete(issue);
            LOGGER.info("Issue Delete : Success  "+ issueDTO.getId());
            return new ResponseEntity("Successfully Deleted Issue", HttpStatus.OK);
        }else {
            LOGGER.info("Issue Delete : Issue Not Found  "+ issueDTO.getId());
            return new ResponseEntity(new CustomError(3001, "Issue Not Found"), HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity getIssueList(IssueListRequestDTO issueListRequestDTO){
        Pageable sortedByDate =
                PageRequest.of(issueListRequestDTO.getPageNo(), pageItemCount, Sort.by("createdDate").descending());
        User user = userService.getUser(issueListRequestDTO.getUserId());
        Page<Issue> issueListPage = null;
        Date startDate = null;
        Date endDate = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            startDate = dateFormat.parse(issueListRequestDTO.getStartDate());
            endDate = dateFormat.parse(issueListRequestDTO.getEndDate());
        }catch (Exception e){
            e.printStackTrace();
        }
        if(user != null && startDate != null && endDate != null){
            if(user.getRole() == null || user.getRole().getName().equals("USER")){
                issueListPage = issueRepository.findIssue(issueListRequestDTO.getAreaId(), issueListRequestDTO.getCategoryId(), user.getId(), issueListRequestDTO.getStatus(), null, null, startDate, endDate,  sortedByDate);
                LOGGER.info("Issue Search by USER  ");
            }else if(user.getRole() != null && user.getRole().getName().equals("SUPER_USER")){
                issueListPage = issueRepository.findIssue(issueListRequestDTO.getAreaId(), issueListRequestDTO.getCategoryId(), null, issueListRequestDTO.getStatus(), null, null, startDate, endDate,  sortedByDate);
                LOGGER.info("Issue Search by SUPER_USER  ");
            }else if(user.getRole() != null && user.getRole().getName().equals("ADMIN")){
                issueListPage = issueRepository.findIssue(issueListRequestDTO.getAreaId(), issueListRequestDTO.getCategoryId(), null, issueListRequestDTO.getStatus(), null, null, startDate, endDate,  sortedByDate);
                LOGGER.info("Issue Search by ADMIN  ");
            }else if(user.getRole() != null && user.getRole().getName().equals("SUPER_ADMIN")){
                issueListPage = issueRepository.findIssue(issueListRequestDTO.getAreaId(), issueListRequestDTO.getCategoryId(), null, issueListRequestDTO.getStatus(), null, null, startDate, endDate,  sortedByDate);
                LOGGER.info("Issue Search by SUPER_ADMIN  ");
            }
        }
        IssueListResponseDTO issueListResponseDTO = new IssueListResponseDTO();
        List<IssueDTO> issueDTOList = new ArrayList<>();
        issueListPage.getContent().stream().forEach(issue ->{
            IssueDTO issueDTO = new IssueDTO();
            issueDTO.setId(issue.getId());
            issueDTO.setCreatedDate(issue.getCreatedDate());
            issueDTO.setUpdatedDate(issue.getUpdatedDate());
            issueDTO.setImageUrl(issue.getImageUrl());
            issueDTO.setDescription(issue.getDescription());
            issueDTO.setLat(issue.getLat());
            issueDTO.setLon(issue.getLon());
            issueDTO.setStatus(issue.getStatus());
            if(issue.getAssignee() != null){
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(issue.getAssignee().getEmail());
                userDTO.setFirstName(issue.getAssignee().getFirstName());
                userDTO.setLastName(issue.getAssignee().getLastName());
                userDTO.setId(issue.getAssignee().getId());
                userDTO.setPhoneNo(issue.getAssignee().getPhoneNo());
                userDTO.setRole(issue.getAssignee().getRole());
                issueDTO.setAssignee(userDTO);
            }
            if(issue.getAssignBy() != null){
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(issue.getAssignBy().getEmail());
                userDTO.setFirstName(issue.getAssignBy().getFirstName());
                userDTO.setLastName(issue.getAssignBy().getLastName());
                userDTO.setId(issue.getAssignBy().getId());
                userDTO.setPhoneNo(issue.getAssignBy().getPhoneNo());
                userDTO.setRole(issue.getAssignBy().getRole());
                issueDTO.setAssignBy(userDTO);
            }
            if(issue.getUser() != null){
                UserDTO userDTO = new UserDTO();
                userDTO.setEmail(issue.getUser().getEmail());
                userDTO.setFirstName(issue.getUser().getFirstName());
                userDTO.setLastName(issue.getUser().getLastName());
                userDTO.setId(issue.getUser().getId());
                userDTO.setPhoneNo(issue.getUser().getPhoneNo());
                userDTO.setRole(issue.getUser().getRole());
                issueDTO.setUser(userDTO);
            }
            if(issue.getCategory() != null){
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setId(issue.getCategory().getId());
                categoryDTO.setName(issue.getCategory().getName());
                categoryDTO.setDescription(issue.getCategory().getDescription());
                issueDTO.setCategory(categoryDTO);
            }
            if(issue.getArea() != null){
                AreaDTO areaDTO = new AreaDTO();
                areaDTO.setId(issue.getArea().getId());
                areaDTO.setName(issue.getArea().getName());
                issueDTO.setArea(areaDTO);
            }
            issueDTOList.add(issueDTO);
        });
        LOGGER.info("Issue Search result count  : "+ issueDTOList.size());
        issueListResponseDTO.setIssueList(issueDTOList);
        issueListResponseDTO.setPageCount(issueListPage.getTotalPages());
        issueListResponseDTO.setTotalCount(issueListPage.getTotalElements());

        return new ResponseEntity(issueListResponseDTO, HttpStatus.OK);
     }
}

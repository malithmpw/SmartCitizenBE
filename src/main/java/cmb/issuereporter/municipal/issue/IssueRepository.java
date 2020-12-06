package cmb.issuereporter.municipal.issue;

import cmb.issuereporter.municipal.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

    @Query("SELECT i FROM Issue i WHERE (:areaId is null or i.area.id = :areaId) and (:categoryId is null or i.category.id = :categoryId) and " +
            "(:userId is null or i.user.id = :userId) and (:status is null or i.status = :status) and (:userId is null or i.user.id = :userId) and " +
            "(:assigneeId is null or i.assignee.id = :assigneeId) and (:assignById is null or i.assignBy.id = :assignById) and i.createdDate BETWEEN :startDate AND :endDate")
    Page<Issue> findIssue(@Param("areaId") Integer areaId, @Param("categoryId") Integer categoryId, @Param("userId") Integer userId,
                          @Param("status") String status, @Param("assigneeId") Integer assigneeId, @Param("assignById") Integer assignById,
                          @Param("startDate") Date startDate, @Param("endDate") Date endDate,  Pageable pageable);
}

package cmb.issuereporter.municipal.user;

import cmb.issuereporter.municipal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByPhoneNoAndPassword(String phoneNo, String password);

    User findByPhoneNo(String phoneNo);

    @Query("SELECT u FROM User u WHERE u.role.name = ?1")
    List<User> getAdminUserList(String roleName);
}

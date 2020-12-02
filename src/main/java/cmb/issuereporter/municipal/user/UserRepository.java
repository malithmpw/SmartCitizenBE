package cmb.issuereporter.municipal.user;

import cmb.issuereporter.municipal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByPhoneNoAndPassword(String phoneNo, String password);

    User findByPhoneNo(String phoneNo);
}

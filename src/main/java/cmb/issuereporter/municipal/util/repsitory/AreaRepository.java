package cmb.issuereporter.municipal.util.repsitory;

import cmb.issuereporter.municipal.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
    Area findByName(String name);
}

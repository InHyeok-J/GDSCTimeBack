package seoultech.gdsc.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seoultech.gdsc.web.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}

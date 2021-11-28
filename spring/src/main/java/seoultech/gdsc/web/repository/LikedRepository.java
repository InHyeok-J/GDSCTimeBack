package seoultech.gdsc.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seoultech.gdsc.web.entity.Liked;

import java.util.Optional;

@Repository
public interface LikedRepository extends JpaRepository<Liked, Integer> {
    Optional<Liked> findByLikeCategoryAndRefIdAndUserId(int category,int refId,int userId);
}

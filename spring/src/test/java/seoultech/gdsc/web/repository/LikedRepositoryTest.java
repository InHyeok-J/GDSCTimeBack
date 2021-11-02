package seoultech.gdsc.web.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import seoultech.gdsc.web.entity.Liked;
import seoultech.gdsc.web.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
@Transactional
public class LikedRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LikedRepository likedRepository;

    private Liked liked;

    @Test
    public void createTest(){
        liked = new Liked();

        Optional<User> user1 = userRepository.findById(1);
        if(user1.isPresent()){
            System.out.println("user1 name : "+ user1.get().getName());
            liked.setRefId(2);
            liked.setUser(user1.get());
            liked.setLikeCategory(1);

            likedRepository.save(liked);
        }
        Optional<Liked> findLiked = likedRepository.findById(liked.getId());
        findLiked.ifPresent(liked->{
            Assertions.assertThat(liked.getRefId()).isEqualTo(2);
        });
    }

    @Test
    public void findTest(){
        Optional<Liked> findLiked = likedRepository.findById(2);
        findLiked.ifPresent(liked->{
            System.out.println(liked.getId());
            System.out.println(liked.getRefId());
            System.out.println(liked.getUser().getName());
        });
    }

    @Test
    public void updateTest(){
        Optional<Liked> findLiked = likedRepository.findById(2);
        findLiked.ifPresent(liked->{
            liked.setLikeCategory(5);
            likedRepository.save(liked);
        });
        Optional<Liked> newLiked = likedRepository.findById(2);
        newLiked.ifPresent(like ->{
            Assertions.assertThat(like.getLikeCategory()).isEqualTo(5);
        } );
    }

    @Test
    public void deleteTest(){
        Optional<Liked> findLiked = likedRepository.findById(2);
        findLiked.ifPresent(liked->{
            likedRepository.delete(liked);
        });
        Optional<Liked> reFindLiked = likedRepository.findById(2);
        if(reFindLiked.isPresent()){
            System.out.println("실패");
        }else System.out.println("성공");
    }
}

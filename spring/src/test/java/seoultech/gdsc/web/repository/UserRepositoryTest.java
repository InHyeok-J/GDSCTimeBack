package seoultech.gdsc.web.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import seoultech.gdsc.web.WebApplicationTests;
import seoultech.gdsc.web.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@SpringBootTest
public class UserRepositoryTest  extends WebApplicationTests {

    @Autowired
    private UserRepository userRepository;

    private  User newUser;
    @BeforeEach()
    private void beforeSaveUser(){
        System.out.println("모든 테스트 동작 전 실행");
        newUser = new User();
        this.newUser.setUserId("test1");
        this.newUser.setPassword("qwer1234");
        this.newUser.setEmail("ss112d86@gmail.com");
        this.newUser.setHp("010-2642-2713");
        this.newUser.setName("홍길동");
        this.newUser.setNickname("cocoding");
        this.newUser.setMajor("computerScience");

        this.userRepository.save(newUser);
    }

    @Test
    public  void createTest(){
        System.out.println("createTest시작");
        newUser = new User();
        this.newUser.setUserId("test");
        this.newUser.setPassword("qwer1234");
        this.newUser.setEmail("ss112d86@gmail.com");
        this.newUser.setHp("010-2642-2713");
        this.newUser.setName("조인혁");
        this.newUser.setNickname("cocoding");
        this.newUser.setMajor("computerScience");

        this.userRepository.save(newUser);
        Optional<User> findUser= userRepository.findById(newUser.getId());
        if(findUser.isPresent()){
            System.out.println(findUser.get().getName());
            Assertions.assertThat( findUser.get().getUserId()).isEqualTo("test");
        }else{
            System.out.println("creatTest fail");
        }
    }

    @Test
    public void findTest(){
        System.out.println("findTest시작");
        Optional<User> findUser = userRepository.findById(this.newUser.getId());
        findUser.ifPresent(user->{
                 Assertions.assertThat( user.getName()).isEqualTo("홍길동");
                System.out.println("findUserName: "+ user.getName());
        });
    }

    @Test
    public void updateTest(){
        System.out.println("updateTest시작");
        Optional<User> findUser = userRepository.findById(this.newUser.getId());

        findUser.ifPresent(user->{
            System.out.println("findUserName: "+ user.getName());
            user.setName("뉴홍길동");
            userRepository.save(user);
        });
        Optional<User> updatedUser = userRepository.findById(this.newUser.getId());
        updatedUser.ifPresent(user->{
            Assertions.assertThat( user.getName()).isEqualTo("뉴홍길동");
        });
    }

    @Test
    public void deleteTest(){
        System.out.println("deleteTest시작");
        Optional<User> findUser = userRepository.findById(this.newUser.getId());
        findUser.ifPresent(user->{
            userRepository.delete(user);
        });
        Optional<User> reFindUser = userRepository.findById(this.newUser.getId());
        if(reFindUser.isPresent()){
            System.out.println("실패");
        }else{
            System.out.println("성공");
        }
    }
}

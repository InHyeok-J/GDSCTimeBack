package seoultech.gdsc.web.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seoultech.gdsc.web.entity.BoardCategory;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
@Transactional
public class BoardCategoryRepositoryTest {
    @Autowired
    private BoardCategoryRepository boardCategoryRepository;

    private BoardCategory category;

    @Test
    public void createTest(){
        category = new BoardCategory();
        this.category.setCategoryName("무슨게시판?");

        boardCategoryRepository.save(category);
        Optional<BoardCategory> findCategory = boardCategoryRepository.findById(category.getId());
        findCategory.ifPresent(ca->{
            System.out.println(ca.getId());
            System.out.println(ca.getCategoryName());
            Assertions.assertThat(ca.getCategoryName()).isEqualTo("무슨게시판?");
        });
    }

    @Test
    public void findTest(){
        Optional<BoardCategory> findCategory = boardCategoryRepository.findById(1);
        if(findCategory.isPresent()){
            System.out.println("id :" + findCategory.get().getId()); // 1
            System.out.println("Name :" + findCategory.get().getCategoryName());//자유게시판
        } else{
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void updateTest(){
        Optional<BoardCategory> findCategory = boardCategoryRepository.findById(1);
        findCategory.ifPresent(ca->{
            ca.setCategoryName("비밀게시글");
            boardCategoryRepository.save(ca);
        });
        Optional<BoardCategory> newCategory = boardCategoryRepository.findById(1);
        newCategory.ifPresent(u->{
            Assertions.assertThat(u.getCategoryName()).isEqualTo("비밀게시글");
        });
    }

    @Test
    public void deleteTest(){
        Optional<BoardCategory> findCategory = boardCategoryRepository.findById(1);
        findCategory.ifPresent(ca->{
            boardCategoryRepository.delete(ca);
        });
        Optional<BoardCategory> newCategory = boardCategoryRepository.findById(1);
        if(newCategory.isPresent()){
            System.out.println("실패");
        }else{
            System.out.println("성공");
        }
    }
}

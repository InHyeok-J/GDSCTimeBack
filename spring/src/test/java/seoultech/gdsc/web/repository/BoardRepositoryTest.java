package seoultech.gdsc.web.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.BoardCategory;
import seoultech.gdsc.web.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
@Transactional
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardCategoryRepository boardCategoryRepository;

    private Board newBoad;


    @Test
    public void createTest(){
        newBoad = new Board();
//        User findUser = userRepository.findById(2)
//                .orElseThrow(IllegalArgumentException::new);
//        BoardCategory findCategory = boardCategoryRepository.findById(1)
//                .orElseThrow(IllegalArgumentException::new);
        Optional<User> findUser = userRepository.findById(2);
        Optional<BoardCategory> findCategory = boardCategoryRepository.findById(1);
        if(findUser.isPresent() && findCategory.isPresent()){
                System.out.println(findUser.get().getName());
                this.newBoad.setUser(findUser.get());
                this.newBoad.setTitle("첫게시글");
                this.newBoad.setContent("블라블라");
                this.newBoad.setCategory(findCategory.get());

        }
        this.boardRepository.save(newBoad);
        Optional<Board> findBoard = boardRepository.findById(newBoad.getId());
        findBoard.ifPresent(board->{
            System.out.println("게시글"+board.getTitle());
            Assertions.assertThat(board.getTitle()).isEqualTo("첫게시글");
        });
    }

    @Test
    public void findTest(){
        //id가 8인 board-> 위에서 생성한 보드 Commit함
        Optional<Board> findBoard = boardRepository.findById(1);
        if(findBoard.isPresent()){
            System.out.println("title:" + findBoard.get().getTitle());
            System.out.println("content:" + findBoard.get().getContent());
            Assertions.assertThat(findBoard.get().getTitle()).isEqualTo("첫게시글");
        } else{
            throw new IllegalArgumentException();
        }
    }

    @Test
    public void updateTest(){
        Optional<Board> findBoard = boardRepository.findById(1);
        findBoard.ifPresent(board->{
            board.setTitle("바꾼 게시글");
            boardRepository.save(board);
        });
        Optional<Board> newBoard = boardRepository.findById(1);
        newBoard.ifPresent(u->{
            Assertions.assertThat(u.getTitle()).isEqualTo("바꾼 게시글");
        });
    }

    @Test
    public void deleteTest(){
        Optional<Board> findBoard = boardRepository.findById(1);
        findBoard.ifPresent(board->{
            boardRepository.delete(board);
        });
        Optional<User> reFindBoard = userRepository.findById(1);
        if(reFindBoard.isPresent()){
            System.out.println("실패");
        }else{
            System.out.println("성공");
        }
    }

}

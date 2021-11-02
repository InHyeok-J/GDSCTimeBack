package seoultech.gdsc.web.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.Comment;
import seoultech.gdsc.web.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    private Comment newcomment;
    @Test
    public void createTest(){
        newcomment= new Comment();
        Optional<User> findUser = userRepository.findById(2);
        Optional<Board> findBoard = boardRepository.findById(1);
        if(findBoard.isPresent() && findUser.isPresent()){
            System.out.println(findUser.get().getName());
            newcomment.setBoard(findBoard.get());
            newcomment.setUser(findUser.get());
            newcomment.setContent("댓글");
        }
        commentRepository.save(newcomment);

        Optional<Comment> findComment = commentRepository.findById(newcomment.getId());
        findComment.ifPresent(comment -> {
            System.out.println("댓글 내용 : "+ comment.getContent());
            Assertions.assertThat(comment.getContent()).isEqualTo("댓글");
        });
    }

    @Test
    public void findTest(){
        Optional<Comment> findComment = commentRepository.findById(2);
        findComment.ifPresent(comment -> {
            System.out.println("댓글 내용 : "+ comment.getContent());
        });
    }
    @Test
    public void updateTest(){
        Optional<Comment> findComment = commentRepository.findById(2);
        findComment.ifPresent(comment -> {
            System.out.println("댓글 내용 : "+ comment.getContent());
            comment.setContent("내용바꾸기");
            commentRepository.save(comment);
        });
        Optional<Comment> newComment = commentRepository.findById(2);
        newComment.ifPresent(co->{
            Assertions.assertThat(co.getContent()).isEqualTo("내용바꾸기");
        });
    }

    @Test
    public void delteTest(){
        Optional<Comment> findComment = commentRepository.findById(2);
        findComment.ifPresent(comment -> {
            commentRepository.delete(comment);
        });
        Optional<Comment> reFindComment = commentRepository.findById(2);
        if(reFindComment.isPresent()) {
            System.out.println("실패");
        }else System.out.println("성공");
    }
}

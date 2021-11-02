package seoultech.gdsc.web.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import seoultech.gdsc.web.entity.Message;
import seoultech.gdsc.web.entity.User;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
@Transactional
public class MessageRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Message newMessage;

    @Test
    public void createTest(){
        newMessage = new Message();

        Optional<User> user1 = userRepository.findById(1);
        Optional<User> user2 = userRepository.findById(2);
        if(user1.isPresent() && user2.isPresent()){
            System.out.println("user1 name : "+ user1.get().getName());
            System.out.println("user2 name : "+ user2.get().getName());
            newMessage.setFrom(user1.get());
            newMessage.setTo(user2.get());
            newMessage.setContent("하이");

            messageRepository.save(newMessage);
        }
        Optional<Message> findMessage = messageRepository.findById(newMessage.getId());
        findMessage.ifPresent(msg->{
            System.out.println("내용:"+ msg.getContent());
            Assertions.assertThat(msg.getContent()).isEqualTo("하이");
        });
    }

    @Test
    public void  findTest(){
        Optional<Message> findMessage = messageRepository.findById(2);
        findMessage.ifPresent(msg->{
            System.out.println("내용:"+ msg.getContent());
        });
    }

    @Test
    public void updateTest(){
        Optional<Message> findMessage = messageRepository.findById(2);
        findMessage.ifPresent(msg->{
            System.out.println("내용:"+ msg.getContent());
            msg.setContent("내용바꾸기~");
            messageRepository.save(msg);
        });
        Optional<Message> newMessage = messageRepository.findById(2);
        newMessage.ifPresent(msg->{
            Assertions.assertThat(msg.getContent()).isEqualTo("내용바꾸기~");
        });
    }

    @Test
    public void deleteTest(){
        Optional<Message> findMessage = messageRepository.findById(2);
        findMessage.ifPresent(msg->{
            messageRepository.delete(msg);
        });
        Optional<Message> reFindMessage = messageRepository.findById(2);
        if(reFindMessage.isPresent()){
            System.out.println("fail");
        }else System.out.println("성공");
    }
}

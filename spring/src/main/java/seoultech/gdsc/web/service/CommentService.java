package seoultech.gdsc.web.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import seoultech.gdsc.web.dto.CommentDto;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.Comment;
import seoultech.gdsc.web.entity.User;
import seoultech.gdsc.web.repository.BoardRepository;
import seoultech.gdsc.web.repository.CommentRepository;
import seoultech.gdsc.web.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Boolean creatComment(int id, CommentDto.Request body){
        Optional<User> findUser = userRepository.findById(id);
        Optional<Board> findBoard = boardRepository.findById(body.getBoardId());
        if(!findUser.isPresent()) return false;
        if(!findBoard.isPresent()) return false;

        Comment comment = modelMapper.map(body, Comment.class);
        comment.setBoard(findBoard.get());
        comment.setUser(findUser.get());
        findBoard.get().setCommentNum(findBoard.get().getCommentNum()+1);

        boardRepository.save(findBoard.get());
        commentRepository.save(comment);
        return true;
    }

    public Optional<List<CommentDto.Response>> getComments(int id){
        Optional<Board> findBoard = boardRepository.findById(id);
        if(!findBoard.isPresent()) return Optional.empty();

        List<Comment> comments = commentRepository.findAllByBoardId(id);
        List<CommentDto.Response> response = comments.stream().map(item->{

            CommentDto.Response res = modelMapper.map(item, CommentDto.Response.class);
            res.setUserId(item.getUser().getId());
            boolean ownerCheck = findBoard.get().getUser() == item.getUser();
            res.setOwner(ownerCheck);
            //익명글이 아닌 방장은 실명(익명 댓글 작성 불가)
            if((!findBoard.get().getIsSecret()) && ownerCheck){
                res.setNickname(item.getUser().getNickname());
                res.setProfilePic(item.getUser().getProfilePic());
                return res;
            }else if(findBoard.get().getIsSecret()&&ownerCheck){
                //게시글이 익명이면 방장은 무조건 익명
                res.setNickname("익명");
                res.setProfilePic("익명이미지");
                return res;
            }
            // 위 조건외 익명글은 익명
            if(item.getIsSecret()){
                res.setNickname("익명");
                res.setProfilePic("익명이미지");
            }else{
                // 위 조건외 익명 글 아니면 실명
                res.setNickname(item.getUser().getNickname());
            }
            return res;
        }).collect(Collectors.toList());

        return Optional.of(response);
    }
}

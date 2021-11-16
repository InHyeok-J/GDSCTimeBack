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
            if(item.getIsSecret()){
                res.setNickname("익명");
                res.setProfilePic("익명이미지");
            }
            return res;
        }).collect(Collectors.toList());

        return Optional.of(response);
    }
}

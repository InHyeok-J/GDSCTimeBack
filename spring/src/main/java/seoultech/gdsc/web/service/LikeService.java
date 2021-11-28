package seoultech.gdsc.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seoultech.gdsc.web.dto.LikeDto;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.Comment;
import seoultech.gdsc.web.entity.Liked;
import seoultech.gdsc.web.repository.BoardRepository;
import seoultech.gdsc.web.repository.CommentRepository;
import seoultech.gdsc.web.repository.LikedRepository;
import seoultech.gdsc.web.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikedRepository likedRepository;
    private final UserRepository userRepository;

    public String like(int userId,LikeDto.Request body){
        Optional<Liked> existLike = likedRepository
                .findByLikeCategoryAndRefIdAndUserId(body.getCategoryId(), body.getRefId(),  userId);
        if(existLike.isPresent()){
            return "이미 좋아요를 했습니다.";
        }
        if(body.getCategoryId() == 1){
            Optional<Board> findBoard = boardRepository.findById(body.getRefId());
            if(findBoard.isEmpty()){
                return "존재하지 않는 게시글입니다";
            }

            Liked newLike = new Liked();
            newLike.setLikeCategory(body.getCategoryId());
            newLike.setRefId(body.getRefId());
            newLike.setUser(userRepository.findById(userId).get());

            findBoard.ifPresent(board->{
                board.setLikeNum(board.getLikeNum() + 1);
                if((board.getLikeNum()+1)>=10){
                    board.setIsHot(true);
                }
                boardRepository.save(board);
            });
            likedRepository.save(newLike);
            return "성공";
        }else if(body.getCategoryId() == 2){
            Optional<Comment> findComment = commentRepository.findById(body.getRefId());
            if(findComment.isEmpty()){
                return "존재하지 않는 게시글입니다";
            }
            Liked newLike = new Liked();
            newLike.setLikeCategory(body.getCategoryId());
            newLike.setRefId(body.getRefId());
            newLike.setUser(userRepository.findById(userId).get());
            findComment.ifPresent(comment -> {
                comment.setLikeNum(comment.getLikeNum()+1);
                commentRepository.save(comment);
            });
            likedRepository.save(newLike);
            return "성공";
        }
        return "잘못된 데이터 전송";
    }

}

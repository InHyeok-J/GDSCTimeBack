package seoultech.gdsc.web.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import seoultech.gdsc.web.dto.BoardDto;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.BoardCategory;
import seoultech.gdsc.web.entity.User;
import seoultech.gdsc.web.repository.BoardCategoryRepository;
import seoultech.gdsc.web.repository.BoardRepository;
import seoultech.gdsc.web.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCategoryRepository boardCategoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Boolean createBoard(int id, BoardDto.Request body){
        System.out.println(body.getCategoryId());
        System.out.println(body.getTitle());
        Optional<BoardCategory> fideCategory = boardCategoryRepository.findById(body.getCategoryId());
        if(!fideCategory.isPresent()){
            return false;
        }
        Optional<User> findUser = userRepository.findById(id);

        Board newBoard = new Board();
        newBoard.setTitle(body.getTitle());
        newBoard.setUser(findUser.get());
        newBoard.setContent(body.getContent());
        newBoard.setCategory(fideCategory.get());
        newBoard.setIsSecret(body.getIsSecret());
        boardRepository.save(newBoard);
        return true;
    }

    //카테고
    public Optional<List<BoardDto.ListResponse>> getBoardList(int id){
        Optional<BoardCategory> fideCategory = boardCategoryRepository.findById(id);
        if(!fideCategory.isPresent()){
            return Optional.empty();
        }
        List<Board> findBoard = boardRepository.findAllByCategoryIdOrderByIdDesc(id);
        List<BoardDto.ListResponse> response = findBoard.stream().map(item->{
            BoardDto.ListResponse res = modelMapper.map(item, BoardDto.ListResponse.class);
            res.setBoardCategoryId(item.getCategory().getId());

            return res;
        }).collect(Collectors.toList());

        return Optional.of(response);
    }

    //게시글 1개 조회
    public Optional<BoardDto.DetailResponse> getBoard(int id ){
        Optional<Board> findBoard = boardRepository.findById(id);
        if(!findBoard.isPresent()){
            return Optional.empty();
        }
        else{
            BoardDto.DetailResponse response =
                    modelMapper.map(findBoard.get(),BoardDto.DetailResponse.class);
            String nicknameChange =  findBoard.get().getIsSecret() ?
                    "익명" : findBoard.get().getUser().getNickname();
            response.setBoardCategoryId(findBoard.get().getCategory().getId());
            response.setNickname(nicknameChange);
            return Optional.of(response);
        }
    }

    public List<BoardDto.ListResponse> getMainBoard(){
        List<Board> findMainBoard = boardRepository.findMainBoard();
        List<BoardDto.ListResponse> response = findMainBoard.stream().map(board->{
            BoardDto.ListResponse res = modelMapper.map(board,BoardDto.ListResponse.class);

            res.setBoardCategoryId(board.getCategory().getId());
            return res;
        }).collect(Collectors.toList());
        return response;
    }

    public List<BoardDto.MainHotResponse>  getHotBoard(){
        List<Board> findBoard = boardRepository
                .findAllByIsHotTrueOrderByCreatedAtDesc(Pageable.ofSize(2));
        List<BoardDto.MainHotResponse> response = findBoard.stream().map(board->{
            BoardDto.MainHotResponse res = modelMapper.map(board, BoardDto.MainHotResponse.class);
            return res;
        }).collect(Collectors.toList());
        return response;
    }

    public List<BoardDto.RealtimeResponse> getRealTime(){
        LocalDateTime currentDate = LocalDateTime.now();
        List<Board> findBoard = boardRepository
                .findAllByIsHotTrueAndCreatedAtGreaterThanEqualOrderByCreatedAtDesc(
                        currentDate.minusDays(1));
        List<BoardDto.RealtimeResponse> responses = findBoard.stream()
                .sorted(Comparator.comparing((Board b)-> b.getCommentNum()+b.getLikeNum()).reversed())
                .limit(2)
                .map(board->{
            BoardDto.RealtimeResponse res = modelMapper.map(board, BoardDto.RealtimeResponse.class);
            String nicknameChange =  board.getIsSecret() ?
                            "익명" : board.getUser().getNickname();
            String profileStr = board.getIsSecret() ?
                            "익명이미지" : board.getUser().getProfilePic();
            res.setCategoryId(board.getCategory().getId());
            res.setNickname(nicknameChange);
            res.setProfilePic(profileStr);
            return res;
        }).collect(Collectors.toList());
        return responses;
    }

    public BoardDto.FilterResponse getFilterBoard(int id){
        List<Board> findHotBoard = boardRepository
                .findAllByCategoryIdAndIsHotTrueOrderByCreatedAtDesc(id,Pageable.ofSize(2));
        List<Board> findLastBoard = boardRepository
                .findAllByCategoryIdAndIsHotFalseOrderByCreatedAtDesc(id,Pageable.ofSize(2));
        BoardDto.FilterResponse response = new BoardDto.FilterResponse();

        response.setHotBoard(findHotBoard.stream().map(board->{
               BoardDto.ListResponse res = modelMapper.map(board,BoardDto.ListResponse.class);
               res.setBoardCategoryId(board.getCategory().getId());
               return res;
        }).collect(Collectors.toList()));

        response.setLastedBoard(findLastBoard.stream().map(board->{
            BoardDto.ListResponse res = modelMapper.map(board, BoardDto.ListResponse.class);
            res.setBoardCategoryId(board.getCategory().getId());
            return res;
        }).collect(Collectors.toList()));

        return response;
    }

}

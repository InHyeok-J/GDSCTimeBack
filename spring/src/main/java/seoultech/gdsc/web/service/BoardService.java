package seoultech.gdsc.web.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import seoultech.gdsc.web.dto.BoardDto;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.BoardCategory;
import seoultech.gdsc.web.entity.User;
import seoultech.gdsc.web.repository.BoardCategoryRepository;
import seoultech.gdsc.web.repository.BoardRepository;
import seoultech.gdsc.web.repository.UserRepository;

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
        List<Board> findBoard = boardRepository.findAllByCategoryId(id);
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
            response.setNickname(nicknameChange);
            return Optional.of(response);
        }
    }
}

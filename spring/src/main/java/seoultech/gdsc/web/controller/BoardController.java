package seoultech.gdsc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import seoultech.gdsc.web.dto.BoardDto;
import seoultech.gdsc.web.dto.CommentDto;
import seoultech.gdsc.web.response.BasicResponse;
import seoultech.gdsc.web.response.FailResponse;
import seoultech.gdsc.web.response.SuccessResponse;
import seoultech.gdsc.web.serializer.EmptyJsonResponse;
import seoultech.gdsc.web.service.BoardService;
import seoultech.gdsc.web.service.CommentService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {
    private  final BoardService boardService;
    private  final HttpSession httpSession;
    private final CommentService commentService;

    // 글 작성
    @PostMapping("")
    public ResponseEntity<BasicResponse> createBoard(@RequestBody() BoardDto.Request body) {
        int id = (int) httpSession.getAttribute("sessionId");
        Boolean result = boardService.createBoard(id, body);
        if (result) {
            return ResponseEntity.ok(new SuccessResponse<>(new EmptyJsonResponse()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new FailResponse<>("카테고리 조회실패", new EmptyJsonResponse()));
        }
    }

    // 글 하나 조회
    @GetMapping("/detail/{id}")
    public ResponseEntity<BasicResponse> getBoard(@PathVariable() int id){
        Optional<BoardDto.DetailResponse> board = boardService.getBoard(id);
        if(board.isPresent()){
            return ResponseEntity.ok(new SuccessResponse<>(board.get()));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new FailResponse<>("board 조회실패", new EmptyJsonResponse()));
        }
    }


    // 카테고리 글 전체 조회
    @GetMapping("")
    public ResponseEntity<BasicResponse> getBoardList(@RequestParam() int category){
        Optional<List<BoardDto.ListResponse>> boardList = boardService.getBoardList(category);
        if(boardList.isPresent()){
            return ResponseEntity.ok(new SuccessResponse<>(boardList.get()));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new FailResponse<>("잘못된 카테고리입니다.", new EmptyJsonResponse()));
        }
    }

    //  댓글작성
    @PostMapping("/comment")
    public ResponseEntity<BasicResponse> createComment(@RequestBody() CommentDto.Request body){
        int id = (int) httpSession.getAttribute("sessionId");
        Boolean result = commentService.creatComment(id,body);
        if(result){
            return ResponseEntity.ok(new SuccessResponse<>(new EmptyJsonResponse()));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new FailResponse<>("작성 실패", new EmptyJsonResponse()));
        }
    }

    //게시글의 댓글 조회
    @GetMapping("/{id}/comment")
    public ResponseEntity<BasicResponse> createComment(@PathVariable() int id){
        Optional<List<CommentDto.Response>> result = commentService.getComments(id);
        if(result.isPresent()){
            return ResponseEntity.ok(new SuccessResponse<>(result.get()));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new FailResponse<>("조회 실패", new EmptyJsonResponse()));
        }
    }

    @GetMapping("/main/myboard")
    public ResponseEntity<BasicResponse> getMyBoard(){
        List<BoardDto.ListResponse> result = boardService.getMainBoard();
        return ResponseEntity.ok(new SuccessResponse<>(result));
    }

    @GetMapping("/main/hot")
    public ResponseEntity<BasicResponse> getHotBoard(){
        List<BoardDto.MainHotResponse> result = boardService.getHotBoard();
        return ResponseEntity.ok(new SuccessResponse<>(result));
    }

    //실시간게시글 조회
    @GetMapping("/main/realtime")
    public ResponseEntity<BasicResponse> getRealTime(){
        List<BoardDto.RealtimeResponse> result = boardService.getRealTime();
        return ResponseEntity.ok(new SuccessResponse<>(result));
    }

    //프론트 board 페이지 진로/홍보용
    @GetMapping("/main/filter")
    public ResponseEntity<BasicResponse> getFilterBoard(@RequestParam() int category){
        BoardDto.FilterResponse result = boardService.getFilterBoard(category);
        return ResponseEntity.ok(new SuccessResponse<>(result));
    }
}

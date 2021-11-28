package seoultech.gdsc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import seoultech.gdsc.web.dto.LikeDto;
import seoultech.gdsc.web.response.BasicResponse;
import seoultech.gdsc.web.response.FailResponse;
import seoultech.gdsc.web.response.SuccessResponse;
import seoultech.gdsc.web.serializer.EmptyJsonResponse;
import seoultech.gdsc.web.service.LikeService;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {
    private final HttpSession httpSession;
    private final LikeService likeService;

    @PostMapping("")
    public ResponseEntity<BasicResponse> like(@RequestBody() LikeDto.Request body){
        int id = (int) httpSession.getAttribute("sessionId");
        String result = likeService.like(id,body);
        if(result.equals("성공")){
            return ResponseEntity.ok(new SuccessResponse<>(new EmptyJsonResponse()));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new FailResponse<>(result,body));
        }
    }
}

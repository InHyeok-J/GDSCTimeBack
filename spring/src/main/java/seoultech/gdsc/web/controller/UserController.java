package seoultech.gdsc.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import seoultech.gdsc.web.dto.UserDto;
import seoultech.gdsc.web.entity.User;
import seoultech.gdsc.web.response.BasicResponse;
import seoultech.gdsc.web.response.FailResponse;
import seoultech.gdsc.web.response.SuccessResponse;
import seoultech.gdsc.web.serializer.EmptyJsonResponse;
import seoultech.gdsc.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final HttpSession httpSession;

    public UserController(UserService userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    // 세션을 통한 유저 정보 조회
    @GetMapping("")
    public ResponseEntity<BasicResponse> getUserInfo(){
        Object id =   httpSession.getAttribute("sessionId");
        if(id==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new FailResponse<>("로그인 필요",new EmptyJsonResponse())
            );
        }
        Optional<UserDto.Response> user = userService.getUser((int)id);
        if(user.isPresent()){
            return ResponseEntity.ok(new SuccessResponse<>(user.get()));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new FailResponse<>("유저가 없습니다",new EmptyJsonResponse())
            );
        }// 위처럼 코딩하니깐 아래로 인텔리제이가 바꿔줌;;

    }

    // 유저 정보 수정
    @PatchMapping("")
    public ResponseEntity<BasicResponse> updateUser(@RequestBody() UserDto.UpdateRequest body){
        int id = (int)httpSession.getAttribute("sessionId");
        String result = userService.updateNickname(id, body);
        if(result.equals("성공")){
            return ResponseEntity.ok(
                   new SuccessResponse<>(new EmptyJsonResponse())
            );
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new FailResponse<>(result,body));
        }
    }

    // 유저 삭제
    @DeleteMapping("")
    public ResponseEntity<BasicResponse> deleteUser(){
        int id = (int)httpSession.getAttribute("sessionId");
        Boolean result = userService.deleteUser(id);
        return result ?
            ResponseEntity.ok(new SuccessResponse<>(new EmptyJsonResponse())) :
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new FailResponse<>("회원탈퇴실패", new EmptyJsonResponse()));
    }

    //회원가입
    @PostMapping("")
    public ResponseEntity<BasicResponse> signUp(@RequestBody() UserDto.SignUpRequest user){
        String message = userService.signUp(user);
        if(message.equals("ok")){
            return ResponseEntity.ok(new SuccessResponse<>(new EmptyJsonResponse()));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new FailResponse<>(message, new EmptyJsonResponse()));
        }
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<BasicResponse> login(@RequestBody() UserDto.LoginRequest data){
        Optional<User> user = userService.login(data);

        if(user.isPresent()){
            httpSession.setAttribute("sessionId",user.get().getId());
            return ResponseEntity.ok()
            .body(new SuccessResponse<>(new EmptyJsonResponse()));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new FailResponse<>("일치하지 않는 회원정보입니다.", new EmptyJsonResponse()));
        }
    }

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity<BasicResponse> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok()
                .body(new SuccessResponse<>(new EmptyJsonResponse()));
    };

}

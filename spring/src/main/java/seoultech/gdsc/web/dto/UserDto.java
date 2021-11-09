package seoultech.gdsc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDto {


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequest{
        private String userId;
        private String password;
        private String email;
        private  String name;
        private String nickname;
        private String major;
        private  String hp;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest{
        private String userId;
        private String password;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public  static class UpdateRequest{
        private String nickname;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private String userId;
        private String email;
        private String name;
        private String nickname;
        private String major;
        private String isAuth;
        private String profilePic;
    }

}

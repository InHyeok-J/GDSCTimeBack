package seoultech.gdsc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CommentDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private int boardId;
        private String content;
        private Boolean isSecret;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private int id;
        private String nickname;
        private String profilePic;
        private String content;
        private int likeNum;
        private int userId;
        private String createdAt;
    }
}

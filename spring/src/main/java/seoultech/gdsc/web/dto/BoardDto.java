package seoultech.gdsc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BoardDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private int categoryId;
        private String title;
        private String content;
        private Boolean isSecret;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ListResponse{
        private int id;
        private int boardCategoryId;
        private String title;
        private String content;
        private int likeNum;
        private int comment_num;
        private String createdAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailResponse{
        private int id;
        private int boardCategoryId;
        private String title;
        private String content;
        private String nickname;
        private String profilePic;
        private int likeNum;
        private int comment_num;
        private String createdAt;
    }

}

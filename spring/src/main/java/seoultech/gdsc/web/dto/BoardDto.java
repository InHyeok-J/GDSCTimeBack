package seoultech.gdsc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seoultech.gdsc.web.entity.Board;

import java.util.List;

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
        private int commentNum;
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
        private int commentNum;
        private String createdAt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MainHotResponse{
        private int id;
        private String title;
        private String content;
        private int likeNum;
        private int commentNum;
        private String createdAt;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RealtimeResponse{
        private int id ;
        private int categoryId;
        private String title;
        private String content;
        private int likeNum;
        private int commentNum;
        private String createdAt;
        private String nickname;
        private String profilePic;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FilterResponse{
        private List<ListResponse> hotBoard;
        private List<ListResponse> lastedBoard;
    }
}

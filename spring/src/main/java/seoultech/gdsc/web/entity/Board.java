package seoultech.gdsc.web.entity;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false ,columnDefinition = "TEXT")
    private String content;

    @ManyToOne(targetEntity = BoardCategory.class)
    private BoardCategory category;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isSecret = true;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int likeNum = 0;

    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private int commentNum = 0;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isHot = false;
}

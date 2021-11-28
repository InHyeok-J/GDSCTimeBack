package seoultech.gdsc.web.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.BoardCategory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    boolean existsBoardById(int id);
    List<Board> findAllByCategory(BoardCategory category);
    List<Board> findAllByCategoryIdOrderByIdDesc(int id);

    @Query("select board from Board board " +
            "where board.createdAt In(select max(board.createdAt)" +
            " from Board board group by board.category having board.category.id <= 6)")
    List<Board> findMainBoard();

    List<Board> findAllByIsHotTrueOrderByCreatedAtDesc(Pageable pageable);

    //진로 홍보 용 카테고리 아이디로 찾고 핫 게시물인 것 2개
    List<Board> findAllByCategoryIdAndIsHotTrueOrderByCreatedAtDesc(int id, Pageable pageable);

    //진로 홍보 용 카테고리 아이디로 찾고 최신글 2개
    List<Board> findAllByCategoryIdAndIsHotFalseOrderByCreatedAtDesc(int id,Pageable pageable);
    List<Board> findAllByIsHotTrueAndCreatedAtGreaterThanEqualOrderByCreatedAtDesc(LocalDateTime date);

}

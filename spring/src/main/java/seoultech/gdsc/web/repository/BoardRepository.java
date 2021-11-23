package seoultech.gdsc.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import seoultech.gdsc.web.entity.Board;
import seoultech.gdsc.web.entity.BoardCategory;

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
}

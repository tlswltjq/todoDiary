package tlswltjq.todoDiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tlswltjq.todoDiary.model.TodoEntity;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
//    @Query("SELECT t FROM TodoEntity t WHERE t.id = ?1")
//    List<TodoEntity> findByUserIdQuery(String userId);
    List<TodoEntity> findByUserId(String userId);

}

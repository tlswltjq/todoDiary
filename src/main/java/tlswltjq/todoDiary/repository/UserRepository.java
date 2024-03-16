package tlswltjq.todoDiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tlswltjq.todoDiary.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUsername(String username);

    Boolean existsByUsername(String username);
    UserEntity findByUsernameAndPassword(String username, String password);
}

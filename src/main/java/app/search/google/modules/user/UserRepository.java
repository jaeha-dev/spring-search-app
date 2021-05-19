package app.search.google.modules.user;

import app.search.google.modules.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Page<User> findAll(Pageable pageable);

    // 특정 검색어와 일치하는 모든 엔터티를 조회한다.
    Page<User> findByNameLike(Pageable pageable, String name);
    Page<User> findByUsernameLike(Pageable pageable, String name);

    // 특정 검색어를 포함하는 모든 엔터티를 조회한다.
    Page<User> findByNameContaining(Pageable pageable, String name);
    Page<User> findByUsernameContaining(Pageable pageable, String username);
}
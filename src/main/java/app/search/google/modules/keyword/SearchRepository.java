package app.search.google.modules.keyword;

import app.search.google.modules.keyword.domain.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<Search, Integer> {
    Optional<Search> findByKeywordAndHost(String keyword, String host);

    Page<Search> findAll(Pageable pageable);

    // 특정 검색어와 일치하는 모든 엔터티를 조회한다.
    Page<Search> findByHostLike(Pageable pageable, String host);
    Page<Search> findByKeywordLike(Pageable pageable, String keyword);

    // 특정 검색어를 포함하는 모든 엔터티를 조회한다.
    Page<Search> findByHostContaining(Pageable pageable, String host);
    Page<Search> findByKeywordContaining(Pageable pageable, String keyword);
}
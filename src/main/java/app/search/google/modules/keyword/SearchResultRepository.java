package app.search.google.modules.keyword;

import app.search.google.modules.keyword.domain.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchResultRepository extends JpaRepository<SearchResult, Integer> {
}
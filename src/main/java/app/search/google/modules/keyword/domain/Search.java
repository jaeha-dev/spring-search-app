package app.search.google.modules.keyword.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "search", uniqueConstraints = @UniqueConstraint(columnNames = { "keyword", "host"}))
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String keyword; // 검색어

    @Column
    private String host; // 호스트 이름

    @Column
    private Integer count; // 검색 횟수

    @Column
    private Integer hostCount; // 누적 검색수

    @OneToMany(mappedBy = "search", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SearchResult> searchResults = new ArrayList<>();

    @Builder
    public Search(String keyword, String host, Integer count, Integer hostCount, List<SearchResult> searchResults) {
        this.keyword = keyword;
        this.host = host;
        this.count = count;
        this.hostCount = hostCount;
        this.searchResults = searchResults;
    }

    // 연관 관계 메서드 (-> SearchResult 엔터티)
    public void addSearchResult(SearchResult searchResult) {
        this.searchResults.add(searchResult);

        searchResult.setSearch(this);
    }

    // 검색 횟수
    public void addCount(Integer count) {
        this.count += count;
    }

    // 누적 검색수
    public void addHostCount(Integer hostCount) {
        this.hostCount += hostCount;
    }
}
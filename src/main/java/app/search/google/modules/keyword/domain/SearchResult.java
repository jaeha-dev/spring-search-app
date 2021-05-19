package app.search.google.modules.keyword.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "search_result")
public class SearchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private String url; // 링크 (URL)

    @Column
    private String title; // 타이틀

    @Lob
    private String content; // 본문

    @Column
    private Integer frequency; // 검색어 출현수

    @ManyToOne(fetch = FetchType.LAZY)
    private Search search; // 검색 엔터티

    @Builder
    public SearchResult(String title, String content, String url, Integer frequency, Search search) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.frequency = frequency;
        this.search = search;
    }

    // 연관 관계 메서드 (-> Search 엔터티)
    public void setSearch(Search search) {
        // 이미 검색 엔터티와 연관 관계가 있을 경우, 관계를 끊는다. (하나의 검색 엔터티와 연관 관계를 갖도록 한다.)
        if (this.search != null) {
            this.search.getSearchResults().remove(this);
        }
        this.search = search;
        search.getSearchResults().add(this);
    }

    public void update(SearchResult searchResult) {
        this.frequency = searchResult.frequency;
    }
}
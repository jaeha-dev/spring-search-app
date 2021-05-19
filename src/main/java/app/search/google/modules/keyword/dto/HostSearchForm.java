package app.search.google.modules.keyword.dto;

import app.search.google.modules.keyword.domain.SearchOption;
import app.search.google.infra.domain.SortOption;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class HostSearchForm {
    private SortOption sortOption = SortOption.DESC; // or asc
    private SearchOption searchOption = SearchOption.ID;
    private String searchKeyword = "";

    @Builder
    public HostSearchForm(SortOption sortOption, SearchOption searchOption, String searchKeyword) {
        this.sortOption = sortOption;
        this.searchOption = searchOption;
        this.searchKeyword = searchKeyword;
    }
}
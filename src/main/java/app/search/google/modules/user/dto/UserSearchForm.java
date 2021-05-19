package app.search.google.modules.user.dto;

import app.search.google.infra.domain.SortOption;
import app.search.google.modules.user.domain.SearchOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchForm {
    private SortOption sortOption = SortOption.DESC; // or asc
    private SearchOption searchOption = SearchOption.ID;
    private String searchKeyword = "";

    @Builder
    public UserSearchForm(SortOption sortOption, SearchOption searchOption, String searchKeyword) {
        this.sortOption = sortOption;
        this.searchOption = searchOption;
        this.searchKeyword = searchKeyword;
    }
}
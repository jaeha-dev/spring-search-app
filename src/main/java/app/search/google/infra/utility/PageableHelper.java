package app.search.google.infra.utility;

import app.search.google.modules.keyword.domain.SearchOption;
import app.search.google.infra.domain.SortOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component
public class PageableHelper {

    public Pageable modify(Pageable pageable, String sortOption, String searchOption) {
        if (ObjectUtils.isEmpty(sortOption)) {
            sortOption = SortOption.DESC.getKey(); // 없을 경우, 기본 값 지정
        }
        if (ObjectUtils.isEmpty(searchOption)) {
            searchOption = SearchOption.ID.getKey(); // 없을 경우, 기본 값 지정
        }

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(),
                // searchOption 값은 빈 문자열 또는 널 값이 될 수 없다.
                Sort.by(Sort.Direction.fromString(sortOption), searchOption)
        );

        return pageable;
    }
}
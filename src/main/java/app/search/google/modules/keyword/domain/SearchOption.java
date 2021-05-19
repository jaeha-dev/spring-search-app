package app.search.google.modules.keyword.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchOption {
    ID("id", "번호"),
    KEYWORD("keyword", "검색어"),
    HOST("host", "호스트 이름");

    private final String key;
    private final String value;

    public static SearchOption fromByKey(String key) {
        if (key.equalsIgnoreCase("keyword")) {
            return KEYWORD;
        } else if (key.equalsIgnoreCase("host")) {
            return HOST;
        } else {
            return ID;
        }
    }
}
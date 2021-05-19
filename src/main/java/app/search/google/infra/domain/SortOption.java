package app.search.google.infra.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortOption {
    DESC("desc", "내림차순"),
    ASC("asc", "오름차순");

    private final String key;
    private final String value;

    public static SortOption fromByKey(String key) {
        if (key.equalsIgnoreCase("desc")) {
            return DESC;
        }
        return ASC;
    }
}
package app.search.google.modules.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchOption {
    ID("id", "번호"),
    USERNAME("username", "계정 ID"),
    NAME("name", "사용자 이름");

    private final String key;
    private final String value;

    public static SearchOption fromByKey(String key) {
        if (key.equalsIgnoreCase("username")) {
            return USERNAME;
        } else if (key.equalsIgnoreCase("name")) {
            return NAME;
        } else {
            return ID;
        }
    }
}
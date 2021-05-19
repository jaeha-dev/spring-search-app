package app.search.google.infra.utility;

import app.search.google.modules.user.domain.SearchOption;
import app.search.google.infra.domain.SortOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToEnumConverter {
    public static class SortOptionConverter implements Converter<String, SortOption> {
        public SortOption convert(String key) {
            log.info("SortOptionConverter: {}", key);
            return SortOption.fromByKey(key);
        }
    }

    public static class UserSearchOptionConverter implements Converter<String, SearchOption> {
        public SearchOption convert(String key) {
            log.info("UserSearchOptionConverter: {}", key);
            return SearchOption.fromByKey(key);
        }
    }

    public static class KeywordSearchOptionConverter implements Converter<String, app.search.google.modules.keyword.domain.SearchOption> {
        public app.search.google.modules.keyword.domain.SearchOption convert(String key) {
            log.info("KeywordSearchOptionConverter: {}", key);
            return app.search.google.modules.keyword.domain.SearchOption.fromByKey(key);
        }
    }
}
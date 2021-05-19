package app.search.google.modules.keyword.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class KeywordHelper {

    // 검색어 출현수 및 누적 검색수를 계산한다.
    public int countKeyword(String target, String keyword) {
        int count = 0;
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(target);
        for (int i = 0; matcher.find(i); i = matcher.end()) {
            count++;
        }

        return count;
    }
}
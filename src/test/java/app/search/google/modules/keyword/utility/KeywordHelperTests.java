package app.search.google.modules.keyword.utility;

import app.search.google.modules.keyword.utility.KeywordHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KeywordHelperTests {

    @InjectMocks
    private KeywordHelper keywordHelper;

    @Test
    @DisplayName("호스트 이름 출현수 계산 테스트")
    public void countHostTest() {
        // Given
        int total = 0;
        String host = "www.jobkorea.co.kr";
        List<String> targets = new ArrayList<>() {{
            add("https://www.jobkorea.co.kr/");
            add("http://company.jobkorea.co.kr/");
            add("https://www.jobkorea.co.kr/recruit/home");
            add("https://www.youtube.com/watch?v=1ZjTWt8vHoE");
            add("https://www.jobkorea.co.kr/service/user/tool/spellcheck");
            add("http://company.jobkorea.co.kr/Service/default.asp");
            add("http://company.jobkorea.co.kr/company/default.asp");
            add("https://m.jobkorea.co.kr/Login/Login.asp");
        }};

        // When
        for (String target : targets) {
            total += keywordHelper.countKeyword(target, host);
        }

        // Then (host 값이 targets 리스트에서 3회 검색된다.)
        assertThat(total).isEqualTo(3);
    }

    @Test // https://stackoverflow.com/questions/2163045/how-to-remove-line-breaks-from-a-file-in-java
    @DisplayName("개행 문자 삭제 테스트")
    public void removeNewLineCharTest() {
        // Given
        String target = "문자열\n문자열, 문자열, \n문자열, 문자열, 문자열\n.";
        System.out.println("Before: " + target);

        // When, Then (\n 문자를 공백으로 치환한다.)
        String result = target.replaceAll("\\R+", "");
        System.out.println("After: " + result);
    }

    @Test
    @DisplayName("검색어 출현수 계산 테스트")
    public void countFrequencyTest() {
        // Given
        String keyword = "잡코리아";
        String target = "잡코리아 - No.1 일자리, 채용, 구인구직, 취업사이트 대한민국 no.1 취업사이트, 잡코리아에서 수백만개의 일자리 및 채용 정보와 기업 정보를 확인해보세요.";
        // When
        int frequency = keywordHelper.countKeyword(target, keyword);

        // Then (keyword 값이 target 문자열에서 2회 검색된다.)
        assertThat(frequency).isEqualTo(2);
    }
}
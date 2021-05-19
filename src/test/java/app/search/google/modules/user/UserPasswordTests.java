package app.search.google.modules.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserPasswordTests {

    @Test // https://stackoverflow.com/questions/3064423/how-to-convert-an-array-to-a-set-in-java/3064447
    @DisplayName("비밀번호 중복 문자 찾기")
    public void passwordTest() {
        String[] targetOne = "@abc123".split("");
        Set<String> setOne = new HashSet<>(Arrays.asList(targetOne));
        if (targetOne.length == setOne.size()) {
            System.out.println("중복 없음");
        } else {
            System.out.println("중복 있음");
        }

        String[] targetTwo = "@abc1223".split("");
        // java.lang.IllegalArgumentException: duplicate element 2
        // Set<String> setTwo = Set.of(targetTwo);
        Set<String> setTwo = new HashSet<>(Arrays.asList(targetTwo));
        if (targetTwo.length == setTwo.size()) {
            System.out.println("중복 없음");
        } else {
            System.out.println("중복 있음");
        }
    }
}
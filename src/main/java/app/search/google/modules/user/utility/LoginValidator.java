package app.search.google.modules.user.utility;

import app.search.google.modules.user.dto.LoginForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class LoginValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginForm loginForm = (LoginForm) target;

        // 계정 비밀번호 중복 문자 검사
        String[] passwordArray = loginForm.getPassword().split("");
        Set<String> passwordSet = new HashSet<>(Arrays.asList(passwordArray));

        if (passwordArray.length != passwordSet.size()) {
            errors.rejectValue("password", "duplicated.password", new Object[]{loginForm.getPassword()}, "계정 비밀번호에 동일한 문자를 사용할 수 없습니다.");
        }
    }
}
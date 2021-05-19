package app.search.google.modules.user.utility;

import app.search.google.modules.user.UserRepository;
import app.search.google.modules.user.dto.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class JoinValidator implements Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinForm joinForm = (JoinForm) target;

        // 계정 ID 중복 검사
        if (userRepository.existsByUsername(joinForm.getUsername())) {
            errors.rejectValue("username", "existed.username", new Object[]{joinForm.getUsername()}, "이미 등록된 계정 ID 입니다.");
        }

        // 계정 비밀번호 중복 문자 검사
        String[] passwordArray = joinForm.getPassword().split("");
        Set<String> passwordSet = new HashSet<>(Arrays.asList(passwordArray));

        if (passwordArray.length != passwordSet.size()) {
            errors.rejectValue("password", "duplicated.password", new Object[]{joinForm.getPassword()}, "계정 비밀번호에 동일한 문자를 사용할 수 없습니다.");
        }
    }
}
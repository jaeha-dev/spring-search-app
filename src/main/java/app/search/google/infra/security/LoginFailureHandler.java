package app.search.google.infra.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final String DEFAULT_LOGIN_FAILURE_URL = "/users/login?error";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String title = "로그인 실패";
        String message = "";

        if (e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException) {
            message = "계정 ID 또는 비밀번호를 확인해주세요.";
        } else if (e instanceof UsernameNotFoundException) {
            message = "등록되지 않은 계정 ID입니다.";
        } else {
            message = "서버 오류로 인해 로그인 할 수 없습니다.";
        }

        request.setAttribute("title", title);
        request.setAttribute("message", message);
        request.getRequestDispatcher(DEFAULT_LOGIN_FAILURE_URL).forward(request, response);
    }
}
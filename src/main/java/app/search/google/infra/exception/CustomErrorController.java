package app.search.google.infra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Whitelabel Error Page를 커스텀 페이지로 변경한다.
        model.addAttribute("message", "해당 요청을 처리할 수 없습니다.");

        return "/components/errors/errorPage";
    }

    @Override
    public String getErrorPath() {
        return "";
    }
}
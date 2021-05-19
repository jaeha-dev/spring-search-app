package app.search.google.infra.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        // http://dveamer.github.io/backend/HowToUseSlf4j.html
        log.error("Global: {}", "Exception", e);

        // HttpStatus 값이 있을 때, 기본 경로(/error/4xx, 5xx.html)로 가므로
        // HttpStatus 값을 제외해서 커스텀 페이지로 포워딩한다. (빠르게 메뉴 버튼을 클릭할 수 있도록)
        // (상태 코드 값은 전달하지 않고 메시지만 전달한다.)
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/components/errors/errorPage");
        mav.addObject("message", "해당 요청을 처리할 수 없습니다.");

        return mav;
    }

//    ModelAndView 대신 View 파일 경로를 지정할 수 있다.
//    @ExceptionHandler(Exception.class)
//    public String handleException(Exception e) {
//        log.error(e.getMessage());
//
//        return "/components/errors/errorPage";
//    }
}
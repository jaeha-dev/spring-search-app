package app.search.google.modules.user;

import app.search.google.modules.user.domain.User;
import app.search.google.modules.user.dto.JoinForm;
import app.search.google.modules.user.dto.LoginForm;
import app.search.google.modules.user.dto.UserSearchForm;
import app.search.google.modules.user.utility.JoinValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final JoinValidator joinValidator;

    @GetMapping(value = {"/users/join"})
    public String joinPage(Model model) {
        model.addAttribute("joinForm", new JoinForm());

        return "joinPage";
    }

    @PostMapping(value = {"/users/join"})
    public String join(@Valid JoinForm joinForm, BindingResult bindingResult, RedirectAttributes attributes) {
        joinValidator.validate(joinForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "joinPage";
        } else {
            userService.joinUser(joinForm);
        }

        attributes.addFlashAttribute("title", "계정 등록 성공");
        attributes.addFlashAttribute("message", "등록된 계정 정보로 로그인 할 수 있습니다.");

        return "redirect:/users/login";
    }

    @RequestMapping(value = {"/users/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());

        return "loginPage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = {"/users/profile"})
    public String profilePage(User me) {
        return "components/users/profilePage";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = {"/admin/users"})
    public String userListPage(@PageableDefault Pageable pageable, @ModelAttribute UserSearchForm form, Model model) {
        Page<User> results = userService.getUserList(pageable, form);
        model.addAttribute("results", results);
        model.addAttribute("url", "/admin/users");
        model.addAttribute("sortOption", form.getSortOption());
        model.addAttribute("searchOption", form.getSearchOption());
        model.addAttribute("searchKeyword", form.getSearchKeyword());

        return "components/users/listPage";
    }

    @PostMapping(value = {"/users/exists/{username}"})
    public @ResponseBody Boolean existsUsername(@PathVariable("username") String username) {
        return userService.existsByUsername(username);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = {"/admin/users/{id}"})
    public @ResponseBody Boolean removeUser(@PathVariable("id") Integer id) {
        return userService.removeUser(id);
    }
}
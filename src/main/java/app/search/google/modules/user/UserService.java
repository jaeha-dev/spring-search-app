package app.search.google.modules.user;

import app.search.google.modules.user.domain.Role;
import app.search.google.modules.user.domain.RoleType;
import app.search.google.modules.user.domain.SearchOption;
import app.search.google.modules.user.domain.User;
import app.search.google.modules.user.dto.JoinForm;
import app.search.google.modules.user.dto.UserSearchForm;
import app.search.google.infra.utility.PageableHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final PageableHelper pageableHelper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void joinUser(JoinForm joinForm) {
        Role role = roleRepository.findByName(RoleType.ROLE_USER)
                .orElseThrow(() -> new IllegalArgumentException("해당 계정 권한이 없습니다."));

        User user = User.builder()
                .username(joinForm.getUsername())
                .password(passwordEncoder.encode(joinForm.getPassword()))
                .name(joinForm.getName())
                .roles(Collections.singleton(role))
                .build();

        userRepository.save(user);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional
    public Boolean removeUser(Integer id) {
        if (!userRepository.existsById(id)) {
            return false;
        } else {
            userRepository.deleteById(id);
            return true;
        }
    }

    public Page<User> getUserList(Pageable pageable, UserSearchForm form) {
        Page<User> users;
        pageable = pageableHelper.modify(pageable, form.getSortOption().getKey(), form.getSearchOption().getKey());
        String searchKeyword = form.getSearchKeyword();

        if (ObjectUtils.isEmpty(searchKeyword)) {
            users = userRepository.findAll(pageable);
        } else {
            switch (form.getSearchOption()) {
                case USERNAME:
                    users = userRepository.findByUsernameLike(pageable, searchKeyword);
                    break;
                case NAME:
                    users = userRepository.findByNameLike(pageable, searchKeyword);
                    break;
                default:
                    users = userRepository.findAll(pageable);
                    break;
            }
        }

        return users;
    }
}
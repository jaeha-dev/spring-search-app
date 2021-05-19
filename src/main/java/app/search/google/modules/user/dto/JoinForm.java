package app.search.google.modules.user.dto;

import app.search.google.modules.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class JoinForm {

    @Pattern(regexp = "^[a-z]{2,10}$", message = "계정 ID 규칙을 확인해주세요.")
    private String username;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,10}$", message = "계정 비밀번호 규칙을 확인해주세요.")
    private String password;

    @Pattern(regexp = "^[가-힣]{2,5}$", message = "사용자 이름 규칙을 확인해주세요.")
    private String name;

    @Builder
    public JoinForm(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .name(name)
                .build();
    }
}
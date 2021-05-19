package app.search.google;

import app.search.google.modules.user.RoleRepository;
import app.search.google.modules.user.UserRepository;
import app.search.google.modules.user.domain.Role;
import app.search.google.modules.user.domain.RoleType;
import app.search.google.modules.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createDummyUsers();
	}

	// 더미 계정 생성용 데이터
	private void createDummyUsers() {
		Role adminRole = Role.builder().name(RoleType.ROLE_ADMIN).build();
		Role userRole = Role.builder().name(RoleType.ROLE_USER).build();
		roleRepository.save(adminRole);
		roleRepository.save(userRole);

		Set<Role> role = new HashSet<>();
		role.add(adminRole);
		userRepository.save(User.builder()
				.username("admin")
				.password("{bcrypt}$2a$10$lT11yzUb1E/AYtz6PBy5neHc78k7xJ7GzHxiYJiCuCQVFJogMQg3q") // @abcd1234
				.name("관리자")
				.roles(role)
				.build());

		List<User> admins = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);
			admins.add(User.builder()
					.username("admin" + i)
					.password("{bcrypt}$2a$10$lT11yzUb1E/AYtz6PBy5neHc78k7xJ7GzHxiYJiCuCQVFJogMQg3q") // @abcd1234
					.name("관리자" + i)
					.roles(roles)
					.build());
		}
		userRepository.saveAll(admins);

		List<User> users = new ArrayList<>();
		for (int i = 1; i <= 140; i++) {
			Set<Role> roles = new HashSet<>();
			roles.add(userRole);
			users.add(User.builder()
					.username("user" + i)
					.password("{bcrypt}$2a$10$lT11yzUb1E/AYtz6PBy5neHc78k7xJ7GzHxiYJiCuCQVFJogMQg3q") // @abcd1234
					.name("사용자" + i)
					.roles(roles)
					.build());
		}
		userRepository.saveAll(users);
	}
}
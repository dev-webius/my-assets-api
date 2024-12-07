package net.webius.myassets.global.auth.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @DisplayName("UserRepository 테스트")
public class UserRepositoryTest {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test @DisplayName("existsByUsername 파라미터가 null 인 경우")
    public void existsByUsernameIsNull() {
        assertThat(userRepository.existsByUsername(null)).isFalse();
    }

    @Test @DisplayName("findByUsername 파라미터가 null 인 경우")
    public void findByUsernameIsNull() {
        assertThat(userRepository.findByUsername(null).isEmpty()).isTrue();
    }
}

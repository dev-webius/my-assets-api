package net.webius.myassets.global.auth.service;

import lombok.RequiredArgsConstructor;
import net.webius.myassets.global.auth.exception.PasswordMismatchException;
import net.webius.myassets.global.auth.exception.UsernameAlreadyUsingException;
import net.webius.myassets.global.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class UserDataService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void verifyPassword(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new PasswordMismatchException(password);
        }
    }

    public void verifyUniqueUsername(String username) throws UsernameAlreadyUsingException {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyUsingException(username);
        }
    }
}

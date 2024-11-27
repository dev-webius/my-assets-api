package net.webius.myassets.global.auth.service;

import lombok.RequiredArgsConstructor;
import net.webius.myassets.global.auth.domain.UserRole;
import net.webius.myassets.global.auth.domain.UserStatus;
import net.webius.myassets.global.auth.dto.SignupReq;
import net.webius.myassets.global.auth.entity.UserAuthEntity;
import net.webius.myassets.global.auth.entity.UserEntity;
import net.webius.myassets.global.auth.repository.UserAuthRepository;
import net.webius.myassets.global.auth.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;
    private final UserDataService userDataService;
    private final UserAuthRepository userAuthRepository;

    @Transactional
    public void signup(SignupReq signupReq) {
        // Username 중복 검증
        userDataService.verifyUniqueUsername(signupReq.getUsername());

        // 회원가입
        createUserEntity(signupReq);
    }

    private void createUserEntity(SignupReq signupReq) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(signupReq.getUsername());
        userEntity.setBirthday(signupReq.getBirthday());
        userEntity.setAuth(createUserAuthEntity(signupReq));
        userEntity.setRole(UserRole.USER); // 기본 사용자 권한
        userEntity.setStatus(UserStatus.VERIFIED); // 기본 인증된 상태
        userRepository.save(userEntity);
    }

    private UserAuthEntity createUserAuthEntity(SignupReq signupReq) {
        UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setHash(userDataService.encodePassword(signupReq.getPassword()));
        return userAuthRepository.save(userAuthEntity);
    }
}

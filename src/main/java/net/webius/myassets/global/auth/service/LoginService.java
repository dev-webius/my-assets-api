package net.webius.myassets.global.auth.service;

import net.webius.myassets.global.auth.dto.LoginReq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {
    private final UserDataService userDataService;

    public LoginService(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @Transactional
    public void login(LoginReq loginReq) {
        // 사용자 조회
        var user = userDataService.findByUsername(loginReq.getUsername());

        // 비밀번호 검증
        userDataService.verifyPassword(loginReq.getPassword(), user.getAuth().getHash());
    }
}

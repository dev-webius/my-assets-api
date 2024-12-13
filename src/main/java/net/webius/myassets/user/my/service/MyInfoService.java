package net.webius.myassets.user.my.service;

import lombok.RequiredArgsConstructor;
import net.webius.myassets.global.auth.repository.UserRepository;
import net.webius.myassets.global.auth.service.UserEntityUserDetailsService;
import net.webius.myassets.user.my.dto.MyInfoApplicationReq;
import net.webius.myassets.user.my.dto.MyInfoRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class MyInfoService {
    private final UserEntityUserDetailsService userEntityUserDetailsService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public MyInfoRes getMyInfo() {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();

        MyInfoRes myInfoRes = new MyInfoRes();
        myInfoRes.setUsername(userEntity.getUsername());
        myInfoRes.setBirthday(userEntity.getBirthday());
        myInfoRes.setStatus(userEntity.getStatus().name().toLowerCase());
        myInfoRes.setUpdatedAt(userEntity.getUpdatedAt());

        return myInfoRes;
    }

    @Transactional
    public void applyMyInfo(MyInfoApplicationReq myInfoApplicationReq) {
        var userEntity = userEntityUserDetailsService.getUserDetails().getUserEntity();

        userEntity.setBirthday(myInfoApplicationReq.getBirthday());
        userRepository.save(userEntity);
    }
}

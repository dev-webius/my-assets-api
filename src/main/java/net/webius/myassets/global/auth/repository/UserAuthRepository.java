package net.webius.myassets.global.auth.repository;

import net.webius.myassets.global.auth.entity.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<UserAuthEntity, Long> {
}

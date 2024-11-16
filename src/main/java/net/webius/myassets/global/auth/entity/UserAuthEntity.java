package net.webius.myassets.global.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.LocalDateTime;

@Entity(name = "user_auth")
@Getter @Setter
public class UserAuthEntity {
    @Id @JoinColumn @OneToOne
    private UserEntity user;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 64)
    private String salt;

    @Column(nullable = false) @CurrentTimestamp(source = SourceType.VM)
    private LocalDateTime updatedAt;
}

package net.webius.myassets.global.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.global.auth.domain.UserRole;
import net.webius.myassets.global.auth.domain.UserStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "user")
@Getter @Setter
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private UserStatus status = UserStatus.REQUESTED;

    @Column(nullable = false) @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false) @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column
    private LocalDateTime deletedAt;
}

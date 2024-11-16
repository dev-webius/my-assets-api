package net.webius.myassets.user.asset.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.global.auth.entity.UserEntity;

@Entity(name = "user_asset")
@Getter @Setter
public class UserAssetEntity {
    @Id @JoinColumn @OneToOne
    private UserEntity user;

    @Column(nullable = false)
    private String orderBy;
}

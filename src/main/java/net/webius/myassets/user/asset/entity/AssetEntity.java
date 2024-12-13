package net.webius.myassets.user.asset.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.global.auth.entity.UserEntity;
import net.webius.myassets.user.asset.domain.AssetType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "asset")
@Getter @Setter
public class AssetEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @JoinColumn(nullable = false) @ManyToOne
    private UserEntity user;

    @Column(nullable = false)
    private AssetType type;

    @Column(nullable = false)
    private Long amount = 0L;

    @JoinColumn(nullable = false) @ManyToOne
    private AssetProviderEntity assetProvider;

    @Column(nullable = false, length = 20)
    private String assetProviderCode;

    @Column(nullable = false)
    private String alias;

    @Column(nullable = false)
    private String memo;

    @Column @CreationTimestamp
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column @UpdateTimestamp
    private LocalDateTime updatedAt = LocalDateTime.now();
}

package net.webius.myassets.user.asset.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "user_asset_statistics")
@Getter @Setter
public class UserAssetStatisticsEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long plannedAmount;

    @Column(nullable = false)
    private Long usedAmount;

    @Column(nullable = false)
    private Long marginAmount;

    @Column(nullable = false) @CreationTimestamp
    private LocalDateTime createdAt;
}

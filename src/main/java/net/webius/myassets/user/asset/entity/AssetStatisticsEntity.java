package net.webius.myassets.user.asset.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "asset_statistics")
@Getter @Setter
public class AssetStatisticsEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long assetId;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private LocalDateTime dateAt;

    @Column(nullable = false) @CreationTimestamp
    private LocalDateTime createdAt;
}

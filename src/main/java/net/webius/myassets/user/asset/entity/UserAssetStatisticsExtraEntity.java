package net.webius.myassets.user.asset.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "user_asset_statistics_extra")
@Getter @Setter
public class UserAssetStatisticsExtraEntity {
    @Id @JoinColumn @OneToOne
    private UserAssetStatisticsEntity userAssetStatistics;

    @Column(nullable = false, length = 1000)
    private String memo;
}

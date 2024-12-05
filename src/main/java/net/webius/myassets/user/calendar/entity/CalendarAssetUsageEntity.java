package net.webius.myassets.user.calendar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.user.asset.domain.AssetUsageType;
import net.webius.myassets.user.asset.entity.AssetEntity;

@Entity(name = "calendar_asset_usage")
@Getter @Setter
public class CalendarAssetUsageEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false) @ManyToOne
    private CalendarDefinitionEntity calendarDefinition;

    @JoinColumn(nullable = false) @ManyToOne
    private AssetEntity asset;

    @Column(nullable = false)
    private AssetUsageType type;

    @Column(nullable = false)
    private Long amount;
}

package net.webius.myassets.user.asset.dto;

import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.user.asset.domain.AssetType;
import net.webius.myassets.user.asset.domain.AssetProvider;

import java.time.LocalDateTime;

@Getter @Setter
public class AssetDetailRes {
    private AssetType type;
    private AssetProvider provider;
    private Long amount;
    private String alias;
    private String memo;
    private LocalDateTime updatedAt;
}

package net.webius.myassets.user.asset.domain;

import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.user.asset.entity.AssetEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter @Setter
public class Asset {
    private UUID id;
    private AssetType type;
    private Long amount;
    private String provider;
    private String alias;

    public static Asset fromEntity(AssetEntity entity) {
        var asset = new Asset();
        asset.setId(entity.getUuid());
        asset.setType(entity.getType());
        asset.setAmount(entity.getAmount());
        asset.setProvider(entity.getAssetProvider().getName());
        asset.setAlias(entity.getAlias());
        return asset;
    }

    public static List<Asset> fromEntities(List<AssetEntity> entities) {
        return entities.stream().map(Asset::fromEntity).collect(Collectors.toList());
    }
}

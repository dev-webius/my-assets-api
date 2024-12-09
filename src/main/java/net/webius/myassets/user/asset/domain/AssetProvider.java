package net.webius.myassets.user.asset.domain;

import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.user.asset.entity.AssetProviderEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class AssetProvider {
    private String code;
    private String name;

    public static AssetProvider fromEntity(AssetProviderEntity entity) {
        var provider = new AssetProvider();
        provider.setCode(entity.getCode());
        provider.setName(entity.getName());
        return provider;
    }

    public static List<AssetProvider> fromEntities(List<AssetProviderEntity> entities) {
        return entities.stream().map(AssetProvider::fromEntity).collect(Collectors.toList());
    }
}

package net.webius.myassets.user.asset.dto;

import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.user.asset.domain.AssetProvider;

import java.util.List;

@Getter @Setter
public class AssetCreationOptionsRes {
    private List<AssetProvider> providers;
}

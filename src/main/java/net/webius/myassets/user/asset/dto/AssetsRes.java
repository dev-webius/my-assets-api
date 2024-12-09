package net.webius.myassets.user.asset.dto;

import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.user.asset.domain.Asset;

import java.util.List;

@Getter @Setter
public class AssetsRes {
    private long totalCount;
    private List<Asset> assets;
}

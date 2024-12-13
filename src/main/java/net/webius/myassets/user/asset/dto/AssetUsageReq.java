package net.webius.myassets.user.asset.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AssetUsageReq {
    @NotNull
    private Long amount;

    private LocalDateTime dateAt = LocalDateTime.now();
}

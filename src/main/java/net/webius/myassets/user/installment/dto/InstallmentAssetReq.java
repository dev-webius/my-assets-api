package net.webius.myassets.user.installment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class InstallmentAssetReq {
    @NotNull
    private UUID assetId;
}

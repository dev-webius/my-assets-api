package net.webius.myassets.user.asset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AssetApplicationReq {
    @NotBlank @Size(max = 20)
    private String providerCode;

    @NotBlank @Size(max = 255)
    private String alias;

    @NotNull @Size(max = 255)
    private String memo;
}

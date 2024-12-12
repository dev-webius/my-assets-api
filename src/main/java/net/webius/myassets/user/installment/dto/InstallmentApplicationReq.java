package net.webius.myassets.user.installment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InstallmentApplicationReq {
    @NotNull @Size(max = 255)
    private String memo;
}

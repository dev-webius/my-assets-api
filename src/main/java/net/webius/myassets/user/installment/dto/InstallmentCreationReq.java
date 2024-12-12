package net.webius.myassets.user.installment.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter @Setter
public class InstallmentCreationReq {
    @NotNull
    private UUID assetId;

    @NotEmpty @Size(max = 50)
    private String name;

    @NotNull
    private Long amount;

    @NotNull @PastOrPresent
    private LocalDate paidAt;

    @NotNull @Size(min = 1)
    private Long totalMonth;

    @NotNull
    private Boolean isInterestFee;

    @NotNull
    private String memo;
}

package net.webius.myassets.user.installment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class InstallmentDetailRes {
    private String name;
    private Long amount;
    private Long currentMonth;
    private Long totalMonth;
    private Long fee;
    private Boolean isInterestFree;
    private String memo;
    private LocalDate paidAt;
    private LocalDate beginAt;
    private LocalDate endAt;
}

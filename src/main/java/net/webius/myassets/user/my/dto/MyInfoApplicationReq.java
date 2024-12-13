package net.webius.myassets.user.my.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class MyInfoApplicationReq {
    @NotNull
    @PastOrPresent
    private LocalDate birthday;
}

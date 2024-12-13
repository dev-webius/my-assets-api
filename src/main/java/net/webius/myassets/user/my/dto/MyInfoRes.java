package net.webius.myassets.user.my.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class MyInfoRes {
    private String username;
    private LocalDate birthday;
    private String status;
    private LocalDateTime updatedAt;
}

package net.webius.myassets.user.calendar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "calendar_definition")
@Getter @Setter
public class CalendarDefinitionEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private LocalDateTime beginAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    @Column(nullable = false)
    private Boolean isAllDay;

    @Column(nullable = false, length = 1000)
    private String memo;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Long totalUsageAmount;

    @Column(nullable = false)
    private Integer totalUsageCount;

    @Column(nullable = false)
    private Boolean isAutomatically;

    @Column(nullable = false) @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false) @UpdateTimestamp
    private LocalDateTime updatedAt;
}

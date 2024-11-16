package net.webius.myassets.user.calendar.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import net.webius.myassets.user.calendar.domain.RecurrenceFrequencyType;
import net.webius.myassets.user.calendar.domain.RecurrenceUntilType;

import java.time.LocalDate;

@Entity(name = "calendar_recurrence")
@Getter @Setter
public class CalendarRecurrenceEntity {
    @Id @JoinColumn @OneToOne
    private CalendarDefinitionEntity calendarDefinition;

    @Column(nullable = false)
    private RecurrenceFrequencyType frequency;

    @Column
    private Integer frequencyInterval;

    @Column
    private Integer frequencyDaysOfWeek;

    @Column
    private LocalDate frequencySpecificDay;

    @Column(nullable = false)
    private RecurrenceUntilType until;

    @Column
    private Integer untilCount;

    @Column
    private LocalDate untilEndAt;
}

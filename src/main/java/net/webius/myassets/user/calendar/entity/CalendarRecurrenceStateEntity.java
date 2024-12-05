package net.webius.myassets.user.calendar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "calendar_recurrence_state")
@Getter @Setter
public class CalendarRecurrenceStateEntity {
    @Id @JoinColumn(name = "calendar_recurrence_id") @OneToOne
    private CalendarRecurrenceEntity calendarRecurrence;

    @Column(nullable = false)
    private Integer countOf;
}

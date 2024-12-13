package net.webius.myassets.user.calendar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "calendar")
@Getter @Setter
public class CalendarEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @JoinColumn(nullable = false) @ManyToOne
    private CalendarDefinitionEntity calendarDefinition;

    @Column(nullable = false)
    private Integer createdIndex;
}

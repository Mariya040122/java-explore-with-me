package ru.practicum.explorewithme.request.model;

import lombok.*;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.request.RequestStatus;
import ru.practicum.explorewithme.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "requests")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @JoinColumn(name = "event")
    @ManyToOne(fetch = FetchType.EAGER)
    private Event event;


    @JoinColumn(name = "userid")
    @ManyToOne(fetch = FetchType.EAGER)
    User requester;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    RequestStatus status;
}

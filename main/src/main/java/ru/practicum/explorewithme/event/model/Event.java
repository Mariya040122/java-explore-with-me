package ru.practicum.explorewithme.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.explorewithme.State;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.request.model.Request;
import ru.practicum.explorewithme.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.explorewithme.Constants.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; //индивидуальный номер события

    @Column(name = "annotation", nullable = false)
    private String annotation; // краткое описание

    @JoinColumn(name = "category")
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category; // категория

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    @Column(name = "createdon", nullable = false)
    private LocalDateTime createdOn; //дата и время создания события

    @Column(name = "description", nullable = false)
    private String description; //полное описание события

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    @Column(name = "eventdate", nullable = false)
    private LocalDateTime eventDate; // дата и время на которые намечено событие


    @JoinColumn(name = "initiator")
    @ManyToOne(fetch = FetchType.EAGER)
    private User initiator; // пользователь (краткая информация)

    @Column(name = "latitude", nullable = false)
    private float latitude;

    @Column(name = "longitude", nullable = false)
    private float longitude;

    @Column(name = "paid", nullable = false)
    private Boolean paid; //нужно ли оплачивать участие

    @Column(name = "participantlimit")
    private Integer participantLimit; //ограничение на количество участников. Значение 0 - означает отсутствие ограничения

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    @Column(name = "publishedon", nullable = true)
    private LocalDateTime publishedOn; // дата и время публикации события

    @Column(name = "requestmoderation", nullable = false)
    private Boolean requestModeration; //нужна ли пре-модерация заявок на участие

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state; //список состояний жизненного цикла события

    @Column(name = "title", nullable = false)
    private String title; //заголовок

    @Column(name = "compilation")
    private Long compilation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    private List<Request> requests;

    @Transient
    private Long views = 0L;
}

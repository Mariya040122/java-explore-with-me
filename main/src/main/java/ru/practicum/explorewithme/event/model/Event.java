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

import static ru.practicum.explorewithme.Constants.DT_PATTERN;

@Getter
@Setter
@ToString
@Entity
@Table(name = "events")
public class Event {

    @Column(name = "annotation", nullable = false)
    String annotation; // краткое описание

    @JoinColumn(name = "category")
    @ManyToOne(fetch = FetchType.EAGER)
    Category category; // категория

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    @Column(name = "createdon", nullable = false)
    LocalDateTime createdOn; //дата и время создания события

    @Column(name = "description", nullable = false)
    String description; //полное описание события

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    @Column(name = "eventdate", nullable = false)
    LocalDateTime eventDate; // дата и время на которые намечено событие

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; //индивидуальный номер события

    @JoinColumn(name = "initiator")
    @ManyToOne(fetch = FetchType.EAGER)
    User initiator; // пользователь (краткая информация)

    @Column(name = "latitude", nullable = false)
    private float latitude;

    @Column(name = "longitude", nullable = false)
    private float longitude;

    @Column(name = "paid", nullable = false)
    Boolean paid; //нужно ли оплачивать участие

    @Column(name = "participantlimit")
    Integer participantLimit; //ограничение на количество участников. Значение 0 - означает отсутствие ограничения

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    @Column(name = "publishedon", nullable = true)
    LocalDateTime publishedOn; // дата и время публикации события

    @Column(name = "requestmoderation", nullable = false)
    Boolean requestModeration; //нужна ли пре-модерация заявок на участие

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    State state; //список состояний жизненного цикла события

    @Column(name = "title", nullable = false)
    String title; //заголовок

    @Column(name = "compilation")
    Long compilation;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    List<Request> requests;

    @Transient
    Long views = 0L;
}

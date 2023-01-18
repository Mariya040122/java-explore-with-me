package ru.practicum.explorewithme.comment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.DT_PATTERN;

@Getter
@Setter
@ToString
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // идентификатор комментария

    @Column(name = "text", nullable = false)
    private String text; // текст комментария

    @JoinColumn(name = "userid")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user; // пользователь

    @JoinColumn(name = "event")
    @ManyToOne(fetch = FetchType.EAGER)
    private Event event; // событие

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    @Column(name = "postedon", nullable = false)
    private LocalDateTime postedOn; // дата и время публикации

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false; // отметка об удалении
}

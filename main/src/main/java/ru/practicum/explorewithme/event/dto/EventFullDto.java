package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.State;
import ru.practicum.explorewithme.event.Location;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.user.model.User;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {

    private Long id; //индивидуальный номер события
    private String annotation; // краткое описание
    private Category category; // категория
    private long confirmedRequests = 0; //количество одобренных заявок на участие в данном событии
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    private LocalDateTime createdOn; //дата и время создания события
    private String description; //полное описание события
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    private LocalDateTime eventDate; // дата и время на которые намечено событие
    private User initiator; // пользователь (краткая информация)
    private Location location; //широта и долгота места проведения события
    private Boolean paid; //нужно ли оплачивать участие
    private int participantLimit; //ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    private LocalDateTime publishedOn; // дата и время публикации события
    private Boolean requestModeration; //нужна ли пре-модерация заявок на участие
    private State state; //список состояний жизненного цикла события
    private String title; //заголовок
    private long views = 0; //количество просмотрев события
}

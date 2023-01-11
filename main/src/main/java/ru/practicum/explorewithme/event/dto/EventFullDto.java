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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {

    String annotation; // краткое описание
    Category category; // категория
    long confirmedRequests = 0; //количество одобренных заявок на участие в данном событии
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn; //дата и время создания события
    String description; //полное описание события
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate; // дата и время на которые намечено событие
    Long id; //индивидуальный номер события
    User initiator; // пользователь (краткая информация)
    Location location; //широта и долгота места проведения события
    Boolean paid; //нужно ли оплачивать участие
    int participantLimit; //ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn; // дата и время публикации события
    Boolean requestModeration; //нужна ли пре-модерация заявок на участие
    State state; //список состояний жизненного цикла события
    String title; //заголовок
    long views = 0; //количество просмотрев события
}

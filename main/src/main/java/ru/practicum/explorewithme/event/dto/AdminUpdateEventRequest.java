package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.event.Location;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.DT_PATTERN;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminUpdateEventRequest {

    String annotation; // Новая аннотация
    Long category; // Новая категория
    String description; // Новое описание
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    LocalDateTime eventDate; // Новые дата и время на которые намечено событие.
    Location location; // Широта и долгота места проведения события
    Boolean paid; // Новое значение флага о платности мероприятия
    Integer participantLimit; // Новый лимит пользователей
    Boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    String title; // Новый заголовок
}

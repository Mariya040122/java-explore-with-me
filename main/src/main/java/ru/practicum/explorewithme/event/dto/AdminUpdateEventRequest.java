package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.event.Location;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminUpdateEventRequest {

    private String annotation; // Новая аннотация
    private Long category; // Новая категория
    private String description; // Новое описание
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    private LocalDateTime eventDate; // Новые дата и время на которые намечено событие.
    private Location location; // Широта и долгота места проведения события
    private Boolean paid; // Новое значение флага о платности мероприятия
    private Integer participantLimit; // Новый лимит пользователей
    private Boolean requestModeration; // Нужна ли пре-модерация заявок на участие
    private String title; // Новый заголовок
}

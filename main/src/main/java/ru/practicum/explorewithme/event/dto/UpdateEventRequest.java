package ru.practicum.explorewithme.event.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.explorewithme.StartDateConstraint;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateEventRequest {

    @Size(min = 20, max = 2000)
    String annotation; //Новая аннотация
    @Positive
    Long category; //Новая категория
    @Size(min = 20, max = 7000)
    String description; //Новое описание
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @StartDateConstraint
    LocalDateTime eventDate; //Новые дата и время на которые намечено событие.
    @NotNull
    @Positive
    Long eventId; ////Событие
    Boolean paid; // Новое значение флага о платности мероприятия
    @PositiveOrZero
    Integer participantLimit; //Новый лимит пользователей
    @Size(min = 3, max = 120)
    String title; //Новый заголовок
}

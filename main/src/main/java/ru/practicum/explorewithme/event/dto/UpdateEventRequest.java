package ru.practicum.explorewithme.event.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.explorewithme.StartDateConstraint;

import static ru.practicum.explorewithme.Constants.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateEventRequest {

    @NotNull
    @Positive
    private Long eventId; ////Событие
    @Size(min = 20, max = 2000)
    private String annotation; //Новая аннотация
    @Positive
    private Long category; //Новая категория
    @Size(min = 20, max = 7000)
    private String description; //Новое описание
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    @StartDateConstraint
    private LocalDateTime eventDate; //Новые дата и время на которые намечено событие.
    private Boolean paid; // Новое значение флага о платности мероприятия
    @PositiveOrZero
    private Integer participantLimit; //Новый лимит пользователей
    @Size(min = 3, max = 120)
    private String title; //Новый заголовок
}

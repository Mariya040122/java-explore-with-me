package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.StartDateConstraint;
import ru.practicum.explorewithme.event.Location;


import javax.validation.constraints.*;
import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    @NotNull
    @Size(min = 20, max = 2000)
    private String annotation; // краткое описание
    @NotNull
    @Positive
    private Long category; // категория
    @NotNull
    @Size(min = 20, max = 7000)
    private String description; //полное описание события
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    @NotNull
    @StartDateConstraint
    private LocalDateTime eventDate; // дата и время на которые намечено событие
    @NotNull
    private Location location; //широта и долгота места проведения события
    private Boolean paid = false; //нужно ли оплачивать участие
    private Integer participantLimit = 0; //ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private Boolean requestModeration = true; //нужна ли пре-модерация заявок на участие
    @NotNull
    @Size(min = 3, max = 120)
    private String title; //заголовок

}

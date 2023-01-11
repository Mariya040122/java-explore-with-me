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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    @NotNull
    @Size(min = 20, max = 2000)
    String annotation; // краткое описание
    @NotNull
    @Positive
    Long category; // категория
    @NotNull
    @Size(min = 20, max = 7000)
    String description; //полное описание события
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    @StartDateConstraint
    LocalDateTime eventDate; // дата и время на которые намечено событие
    @NotNull
    Location location; //широта и долгота места проведения события
    Boolean paid = false; //нужно ли оплачивать участие
    Integer participantLimit = 0; //ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    Boolean requestModeration = true; //нужна ли пре-модерация заявок на участие
    @NotNull
    @Size(min = 3, max = 120)
    String title; //заголовок

}

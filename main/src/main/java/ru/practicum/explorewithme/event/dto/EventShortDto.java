package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.user.model.User;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.DT_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

    String annotation; // краткое описание
    Category category; // категория
    long confirmedRequests; //количество одобренных заявок на участие в данном событии
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    LocalDateTime eventDate; // дата и время на которые намечено событие
    private Long id; //индивидуальный номер события
    User initiator; // пользователь (краткая информация)
    Boolean paid; //нужно ли оплачивать участие
    String title; //заголовок
    long views; //количество просмотрев события
}

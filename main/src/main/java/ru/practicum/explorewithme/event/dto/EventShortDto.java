package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.user.model.User;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

    private Long id; //индивидуальный номер события
    private String annotation; // краткое описание
    private Category category; // категория
    private long confirmedRequests; //количество одобренных заявок на участие в данном событии
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    private LocalDateTime eventDate; // дата и время на которые намечено событие
    private User initiator; // пользователь (краткая информация)
    private Boolean paid; //нужно ли оплачивать участие
    private String title; //заголовок
    private long views; //количество просмотрев события
}

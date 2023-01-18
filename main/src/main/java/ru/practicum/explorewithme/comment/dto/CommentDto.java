package ru.practicum.explorewithme.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.user.dto.UserDto;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.DT_PATTERN;

@Getter
@Setter
public class CommentDto {

    private Long id; // идентификатор комментария
    private String text; // текст комментария
    private UserDto user; // пользователь
    private EventShortDto event; // событие
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    private LocalDateTime postedOn; // дата и время публикации
}

package ru.practicum.explorewithme.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.request.RequestStatus;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {

    private Long id; // Идентификатор заявки
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    private LocalDateTime created; //Дата и время создания заявки
    private Long event; //Идентификатор события
    private Long requester; // Идентификатор пользователя, отправившего заявку
    private RequestStatus status; // Статус заявки
}

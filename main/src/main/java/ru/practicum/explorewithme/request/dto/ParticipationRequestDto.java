package ru.practicum.explorewithme.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.request.RequestStatus;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.DT_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    LocalDateTime created; //Дата и время создания заявки
    Long event; //Идентификатор события
    Long id; // Идентификатор заявки
    Long requester; // Идентификатор пользователя, отправившего заявку
    RequestStatus status; // Статус заявки
}

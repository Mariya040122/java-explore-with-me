package ru.practicum.explorewithme.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.DT_PATTERN;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {

    Long id; //Идентификатор записи
    String app; //Идентификатор сервиса для которого записывается информация
    String uri; //URI для которого был осуществлен запрос
    String ip; //IP-адрес пользователя, осуществившего запрос
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    LocalDateTime timestamp; //Дата и время, когда был совершен запрос к эндпоинту
}

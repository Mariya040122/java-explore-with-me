package ru.practicum.explorewithme.stats.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static ru.practicum.explorewithme.Constants.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {

    private Long id; //Идентификатор записи
    private String app; //Идентификатор сервиса для которого записывается информация
    private String uri; //URI для которого был осуществлен запрос
    private String ip; //IP-адрес пользователя, осуществившего запрос
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DT_PATTERN)
    private LocalDateTime timestamp; //Дата и время, когда был совершен запрос к эндпоинту
}

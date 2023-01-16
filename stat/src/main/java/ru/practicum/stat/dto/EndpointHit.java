package ru.practicum.stat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {

    private Long id; //Идентификатор записи
    private String app; //Идентификатор сервиса для которого записывается информация
    private String uri; //URI для которого был осуществлен запрос
    private String ip; //IP-адрес пользователя, осуществившего запрос
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp; //Дата и время, когда был совершен запрос к эндпоинту
}

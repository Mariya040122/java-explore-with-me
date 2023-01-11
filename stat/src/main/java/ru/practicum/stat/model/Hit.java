package ru.practicum.stat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "stats")
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; //Идентификатор записи
    @Column(name = "app", nullable = false)
    String app; //Идентификатор сервиса для которого записывается информация
    @Column(name = "uri", nullable = false)
    String uri; //URI для которого был осуществлен запрос
    @Column(name = "ip", nullable = false)
    String ip; //IP-адрес пользователя, осуществившего запрос
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "timestamp", nullable = false)
    LocalDateTime timestamp; //Дата и время, когда был совершен запрос к эндпоинту
}

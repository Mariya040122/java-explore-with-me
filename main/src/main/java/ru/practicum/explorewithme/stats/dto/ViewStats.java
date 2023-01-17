package ru.practicum.explorewithme.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ViewStats {

    private String app;//Название сервиса
    private String uri;//URI сервиса
    private Long hits;//Количество просмотров
}

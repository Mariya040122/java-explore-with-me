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
    String app;//Название сервиса
    String uri;//URI сервиса
    Long hits;//Количество просмотров
}

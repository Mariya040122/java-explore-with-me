package ru.practicum.explorewithme.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserShortDto {
    Long id; //уникальный идентификатор пользователя
    String name; //имя или логин пользователя
}

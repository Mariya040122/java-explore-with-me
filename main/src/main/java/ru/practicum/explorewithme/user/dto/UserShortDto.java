package ru.practicum.explorewithme.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserShortDto {

    private Long id; //уникальный идентификатор пользователя
    private String name; //имя или логин пользователя
}

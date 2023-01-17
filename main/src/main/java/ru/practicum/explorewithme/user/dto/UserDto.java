package ru.practicum.explorewithme.user.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id; //уникальный идентификатор пользователя
    private String name; //имя или логин пользователя
    private String email; //адрес электронной почты
}

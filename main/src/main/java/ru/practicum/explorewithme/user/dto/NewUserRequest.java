package ru.practicum.explorewithme.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {

    @NotNull
    @Email
    private String email; //почтовый адрес
    @NotNull
    @NotBlank
    private String name; //имя
}

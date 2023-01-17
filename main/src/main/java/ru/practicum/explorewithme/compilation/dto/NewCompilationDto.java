package ru.practicum.explorewithme.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    private Set<Long> events; //список событий входящих в подборку
    private Boolean pinned = false; //закреплена ли подборка на главной странице сайта
    @NotNull
    @NotBlank
    private String title; //заголовок подборки

}

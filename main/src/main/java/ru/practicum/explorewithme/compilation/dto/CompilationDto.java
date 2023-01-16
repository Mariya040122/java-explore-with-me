package ru.practicum.explorewithme.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.event.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private Long id; //идентификатор
    private List<EventShortDto> events; // Список событий входящих в подборку
    private Boolean pinned; //закреплена ли подборка на главной странице сайта
    private String title; //заголовок подборки

}

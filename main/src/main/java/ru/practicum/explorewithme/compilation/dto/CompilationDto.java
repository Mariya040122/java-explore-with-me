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

    List<EventShortDto> events; // Список событий входящих в подборку
    Long id; //идентификатор
    Boolean pinned; //закреплена ли подборка на главной странице сайта
    String title; //заголовок подборки

}

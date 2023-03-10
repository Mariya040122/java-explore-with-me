package ru.practicum.explorewithme.publicController;

import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.event.SortEventEnum;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface PublicService {


    List<Event> findAllEvents(String text, Long[] categories, Boolean paid,
                              LocalDateTime rangeStart, LocalDateTime rangeEnd,
                              Boolean onlyAvailable, SortEventEnum sort, int from, int size);


    Event findEvent(long eventId) throws NotFoundException;


    List<CompilationDto> findAllCompilations(boolean pinned, int from, int size);


    CompilationDto findCompilation(long compId) throws NotFoundException;


    List<CategoryDto> findAllCategories(int from, int size);


    CategoryDto findCategory(long catId) throws NotFoundException;

}

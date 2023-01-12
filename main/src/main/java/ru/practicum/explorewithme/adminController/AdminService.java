package ru.practicum.explorewithme.adminController;


import ru.practicum.explorewithme.State;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.event.dto.AdminUpdateEventRequest;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.user.dto.NewUserRequest;
import ru.practicum.explorewithme.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminService {

    public List<EventFullDto> eventSearch(Long[] users, State[] states, Long[] categories,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);


    EventFullDto editEvent(long eventId, AdminUpdateEventRequest updateEventRequest);


    EventFullDto publishEvent(long eventId);


    EventFullDto rejectEvent(long eventId);


    CategoryDto editCategory(CategoryDto categoryDto);


    CategoryDto createCategory(NewCategoryDto newCategory);


    void deleteCategory(long categoryId);


    List<UserDto> usersSearch(Long[] users, int from, int size);


    UserDto createUser(NewUserRequest newUser);


    void deleteUser(long userId);


    CompilationDto createCompilation(NewCompilationDto newCompilation);


    void deleteCompilation(long compilationId);


    void deleteEventInCompilation(long compilationId, long eventId);


    void addEventToCompilation(long compilationId, long eventId);


    void unpinCompilation(long compilationId);


    void pinCompilation(long compilationId);

}

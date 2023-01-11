package ru.practicum.explorewithme.adminController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.State;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.event.dto.AdminUpdateEventRequest;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.dto.NewUserRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/events")
    public List<EventFullDto> eventSearch(@RequestParam(name = "users", required = false) Long[] users,
                                          @RequestParam(name = "states", required = false) State[] states,
                                          @RequestParam(name = "categories", required = false) Long[] categories,
                                          @RequestParam(name = "rangeStart", required = false)
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                          @RequestParam(name = "rangeEnd", required = false)
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                          @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                          @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        log.info("Получен запрос на поиск события");
        return adminService.eventSearch(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/events/{eventId}")
    public EventFullDto editEvent(@PathVariable long eventId,
                                  @RequestBody AdminUpdateEventRequest updateEventRequest) {
        log.info("Получен запрос на редактирование события");
        return adminService.editEvent(eventId, updateEventRequest);
    }

    @PatchMapping("/events/{eventId}/publish")
    public EventFullDto publishEvent(@PathVariable long eventId) {
        log.info("Получен запрос на публикацию события");
        return adminService.publishEvent(eventId);
    }

    @PatchMapping("/events/{eventId}/reject")
    public EventFullDto rejectEvent(@PathVariable long eventId) {
        log.info("Получен запрос на отклонение события");
        return adminService.rejectEvent(eventId);
    }

    @PatchMapping("/categories")
    public CategoryDto editCategory(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Получен запрос изменение категории");
        return adminService.editCategory(categoryDto);
    }

    @PostMapping("/categories")
    public CategoryDto createCategory(@Valid @RequestBody NewCategoryDto newCategory) {
        log.info("Получен запрос добавление новой категории");
        return adminService.createCategory(newCategory);
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategory(@PathVariable long catId) {
        log.info("Получен запрос удаление категории");
        adminService.deleteCategory(catId);
    }

    @GetMapping("/users")
    public List<UserDto> usersSearch(@RequestParam(name = "ids", required = false) Long[] users,
                                     @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                     @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        log.info("Получен запрос на получение информации о пользователях");
        return adminService.usersSearch(users, from, size);
    }

    @PostMapping("/users")
    public UserDto createUser(@Valid @RequestBody NewUserRequest newUser) {
        log.info("Получен запрос добавление нового пользователя");
        return adminService.createUser(newUser);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("Получен запрос удаление пользователя");
        adminService.deleteUser(userId);
    }

    @PostMapping("/compilations")
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto newCompilation) {
        log.info("Получен запрос добавление новой подборки");
        return adminService.createCompilation(newCompilation);
    }

    @DeleteMapping("/compilations/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        log.info("Получен запрос удаление подборки");
        adminService.deleteCompilation(compId);
    }

    @DeleteMapping("/compilations/{compId}/events/{eventId}")
    public void deleteEventInCompilation(@PathVariable long compId,
                                         @PathVariable long eventId) {
        log.info("Получен запрос удаление событий из подборки");
        adminService.deleteEventInCompilation(compId, eventId);
    }

    @PatchMapping("/compilations/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable long compId,
                                      @PathVariable long eventId) {
        log.info("Получен запрос на добавление события в подборку");
        adminService.addEventToCompilation(compId, eventId);
    }

    @DeleteMapping("/compilations/{compId}/pin")
    public void unpinCompilation(@PathVariable long compId) {
        log.info("Получен запрос на открепление подборки");
        adminService.unpinCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}/pin")
    public void pinCompilation(@PathVariable long compId) {
        log.info("Получен запрос на закрепление подборки");
        adminService.pinCompilation(compId);
    }


}
package ru.practicum.explorewithme.publicController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.event.SortEventEnum;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.stats.StatService;
import ru.practicum.explorewithme.stats.dto.EndpointHit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.practicum.explorewithme.Constants.*;


@Slf4j
@RestController
@RequestMapping
public class PublicController {

    private final PublicService publicService;
    private final StatService statService;


    @Autowired
    public PublicController(PublicService publicService, StatService statService) {
        this.publicService = publicService;
        this.statService = statService;
    }

    @GetMapping("/events")
    public List<EventShortDto> findAllEvents(@RequestParam(name = "text", required = false) String text,
                                             @RequestParam(name = "categories", required = false) Long[] categories,
                                             @RequestParam(name = "paid", required = false) Boolean paid,
                                             @RequestParam(name = "rangeStart", required = false)
                                             @DateTimeFormat(pattern = DT_PATTERN) LocalDateTime rangeStart,
                                             @RequestParam(name = "rangeEnd", required = false)
                                             @DateTimeFormat(pattern = DT_PATTERN) LocalDateTime rangeEnd,
                                             @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                             @RequestParam(name = "sort", required = false) SortEventEnum sort,
                                             @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                             @RequestParam(name = "size", defaultValue = "10") @Positive int size,
                                             HttpServletRequest request) {
        log.info("Получен запрос на вывод данных о всех мероприятиях");
        List<Event> results = publicService.findAllEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        statService.postStat(new EndpointHit(0L, "ewm-main-service", request.getRequestURI(),
                request.getRemoteAddr(), LocalDateTime.now()));
        return statService.getEventsShortDto(results, sort == SortEventEnum.VIEWS);
    }


    @GetMapping("/events/{eventId}")
    public EventFullDto findEvent(@PathVariable long eventId, HttpServletRequest request) {
        log.info("Получен запрос на получение информации о конкретном мероприятии");
        Event result = publicService.findEvent(eventId);
        statService.postStat(new EndpointHit(0L, "ewm-main-service", request.getRequestURI(),
                request.getRemoteAddr(), LocalDateTime.now()));
        return statService.getEventsFullDto(new ArrayList<>(Arrays.asList(result)), false).get(0);
    }

    @GetMapping("/compilations")
    public List<CompilationDto> findAllCompilations(@RequestParam(name = "pinned", defaultValue = "false") boolean pinned,
                                                    @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                                    @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        log.info("Получен запрос на вывод данных о всех подорках");
        return publicService.findAllCompilations(pinned, from, size);
    }


    @GetMapping("/compilations/{compId}")
    public CompilationDto findCompilation(@PathVariable long compId) {
        log.info("Получен запрос на получение информации о конкретной подборке");
        return publicService.findCompilation(compId);
    }

    @GetMapping("/categories")
    public List<CategoryDto> findAllCategories(@RequestParam(name = "from", defaultValue = "0") @PositiveOrZero int from,
                                               @RequestParam(name = "size", defaultValue = "10") @Positive int size) {
        log.info("Получен запрос на вывод данных о всех категориях");
        return publicService.findAllCategories(from, size);
    }


    @GetMapping("/categories/{catId}")
    public CategoryDto findCategory(@PathVariable long catId) {
        log.info("Получен запрос на получение информации о конкретной категории");
        return publicService.findCategory(catId);
    }


}

package ru.practicum.stat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stat.dto.EndpointHit;
import ru.practicum.stat.dto.ViewStats;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping
public class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("/hit")
    public void addStats(@Valid @RequestBody EndpointHit endpointHit) {
        log.info("Получен запрос информация о запросе к эндпоинту");
        statsService.addStats(endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam(name = "start") String start,
                                    @RequestParam(name = "end") String end,
                                    @RequestParam(name = "uris", required = false) String[] uris,
                                    @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        log.info("Получен запрос на получение статистики");
        return statsService.getStats(start, end, uris, unique);
    }
}


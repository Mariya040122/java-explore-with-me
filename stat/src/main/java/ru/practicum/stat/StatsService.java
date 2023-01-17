package ru.practicum.stat;

import ru.practicum.stat.dto.EndpointHit;
import ru.practicum.stat.dto.ViewStats;

import java.util.List;

public interface StatsService {
    void addStats(EndpointHit endpointHit);

    List<ViewStats> getStats(String start, String end, String[] uris, Boolean unique);
}

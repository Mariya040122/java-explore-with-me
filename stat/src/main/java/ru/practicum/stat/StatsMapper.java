package ru.practicum.stat;

import ru.practicum.stat.dto.EndpointHit;
import ru.practicum.stat.dto.ViewStats;
import ru.practicum.stat.model.Hit;
import ru.practicum.stat.model.Stat;

public class StatsMapper {
    public static Hit fromEndpointHit(EndpointHit endpointHit) {
        Hit hit = new Hit();
        hit.setId(endpointHit.getId());
        hit.setApp(endpointHit.getApp());
        hit.setUri(endpointHit.getUri());
        hit.setIp(endpointHit.getIp());
        return hit;
    }

    public static ViewStats fromStat(Stat stat) {
        return new ViewStats(stat.getApp(), stat.getUri(), stat.getHits().longValue());
    }
}

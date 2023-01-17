package ru.practicum.stat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.stat.dto.EndpointHit;
import ru.practicum.stat.dto.ViewStats;
import ru.practicum.stat.model.Hit;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatsServiceImpl implements StatsService {
    @Autowired
    private final HitRepository hitRepository;

    public StatsServiceImpl(HitRepository hitRepository) {
        this.hitRepository = hitRepository;
    }

    @Override
    public void addStats(EndpointHit endpointHit) {
        Hit hit = StatsMapper.fromEndpointHit(endpointHit);
        hit.setTimestamp(LocalDateTime.now());
        hitRepository.save(hit);
    }

    @Override
    public List<ViewStats> getStats(String start, String end, String[] uris, Boolean unique) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startRange = LocalDateTime.parse(java.net.URLDecoder.decode(start, StandardCharsets.UTF_8),
                formatter);
        LocalDateTime endRange = LocalDateTime.parse(java.net.URLDecoder.decode(end, StandardCharsets.UTF_8),
                formatter);
        return hitRepository.getStats(startRange, endRange, Arrays.asList(uris), unique).stream()
                .map(StatsMapper::fromStat)
                .collect(Collectors.toList());
    }
}

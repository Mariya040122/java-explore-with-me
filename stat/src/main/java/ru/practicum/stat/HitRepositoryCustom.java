package ru.practicum.stat;

import ru.practicum.stat.model.Stat;

import java.time.LocalDateTime;
import java.util.List;

public interface HitRepositoryCustom {
    List<Stat> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}

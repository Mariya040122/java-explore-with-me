package ru.practicum.explorewithme.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.explorewithme.State;
import ru.practicum.explorewithme.event.model.Event;

import java.time.LocalDateTime;

public interface EventRepositoryCustom {
    Page<Event> findPublicEvents(String text, Long[] categories, Boolean paid,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                 Boolean onlyAvailable, SortEventEnum sort,
                                 Pageable pageable);

    Page<Event> findAdminEvents(Long[] users, State[] states, Long[] categories,
                                LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                Pageable pageable);
}

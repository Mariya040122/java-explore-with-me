package ru.practicum.explorewithme.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.user.model.User;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {

    Page<Event> findAllByInitiator(User user, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * FROM events as e WHERE e.id IN (:eventIds)")
    List<Event> findEventsInList(@Param("eventIds") List<Long> eventIds);
}

package ru.practicum.explorewithme.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.request.model.Request;
import ru.practicum.explorewithme.user.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByEvent(Event event);

    List<Request> findAllByRequester(User requester);

    Optional<Request> findRequestByRequesterAndEvent(User requester, Event event);

    long countAllByEventAndStatus(Event event, RequestStatus status);

    @Modifying
    @Query("update Request req set req.status = :newStatus where req.event = :eventId and req.status = :oldStatus")
    void updateStatus(@Param(value = "eventId") long eventId,
                      @Param(value = "oldStatus") RequestStatus oldStatus,
                      @Param(value = "newStatus") RequestStatus newStatus);
}

package ru.practicum.explorewithme.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.practicum.explorewithme.State;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.request.RequestStatus;
import ru.practicum.explorewithme.request.model.Request;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventRepositoryCustomImpl implements EventRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Event> findPublicEvents(String text, Long[] categories, Boolean paid,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        Boolean onlyAvailable, SortEventEnum sort,
                                        Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(event.get("state"), State.PUBLISHED));
        if (text != null) {
            Expression<String> textLiteral = cb.literal("%" + text + "%");
            predicates.add(
                    cb.or(
                            cb.like(cb.lower(event.get("annotation")), cb.lower(textLiteral)),
                            cb.like(cb.lower(event.get("description")), cb.lower(textLiteral))
                    ));
        }
        if (categories != null) {
            Expression<Long> cats = event.get("category");
            predicates.add(cats.in(new ArrayList<>(Arrays.asList(categories))));
        }
        if (paid != null) {
            predicates.add(cb.equal(event.get("paid"), paid));
        }
        if (rangeStart == null || rangeEnd == null) {
            predicates.add(cb.greaterThan(event.get("eventDate"), LocalDateTime.now()));
        } else {
            predicates.add(cb.between(event.get("eventDate"), rangeStart, rangeEnd));
        }
        Subquery<Long> subQuery = query.subquery(Long.class);
        Root subQueryRoot = subQuery.from(Request.class);
        Predicate subQueryPredicate = cb.and(
                cb.equal(event.get("id"), subQueryRoot.get("event")),
                cb.equal(subQueryRoot.get("status"), RequestStatus.CONFIRMED)
        );
        subQuery.select(cb.count(subQueryRoot.get("id"))).where(subQueryPredicate);
        if (onlyAvailable) {
            predicates.add(
                    cb.or(
                            cb.equal(event.get("participantLimit"), 0),
                            cb.gt(event.get("participantLimit"), subQuery)
                    )
            );
        }
        if (sort == SortEventEnum.EVENT_DATE) {
            query.orderBy(cb.asc(event.get("eventDate")));
        }
        query.select(event)
                .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return pageResults(entityManager.createQuery(query), pageable);
    }

    @Override
    public Page<Event> findAdminEvents(Long[] users, State[] states, Long[] categories,
                                       LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                       Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = cb.createQuery(Event.class);
        Root<Event> event = query.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();
        if (users != null) {
            Expression<Long> usr = event.get("initiator");
            predicates.add(usr.in(new ArrayList<>(Arrays.asList(users))));
        }
        if (states != null) {
            Expression<String> sts = event.get("state");
            predicates.add(sts.in(new ArrayList<>(Arrays.asList(states))));
        }
        if (categories != null) {
            Expression<Long> cats = event.get("category");
            predicates.add(cats.in(new ArrayList<>(Arrays.asList(categories))));
        }
        if (rangeStart == null || rangeEnd == null) {
            predicates.add(cb.greaterThan(event.get("eventDate"), LocalDateTime.now()));
        } else {
            predicates.add(cb.between(event.get("eventDate"), rangeStart, rangeEnd));
        }
        query.select(event)
                .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return pageResults(entityManager.createQuery(query), pageable);
    }

    private Page<Event> pageResults(Query resultQuery, Pageable pageable) {
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        resultQuery.setFirstResult(pageNumber);
        resultQuery.setMaxResults(pageSize);
        List<Event> result = resultQuery.getResultList();
        int size = result.size();
        return new PageImpl<>(result, pageable, size);
    }
}

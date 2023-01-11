package ru.practicum.stat;

import ru.practicum.stat.model.Stat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class HitRepositoryCustomImpl implements HitRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Stat> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        String queryString = "select 0 as id, app, uri, count(" + (unique ? "distinct" : "") + " ip) as hits " +
                "from stats  " +
                "where " +
                ((uris != null) ? "uri in (:uris) and " : " ") +
                "timestamp > :start and timestamp < :end " +
                "group by app, uri";

        Query query = entityManager.createNativeQuery(queryString, "StatResult");
        if (uris != null) {
            query.setParameter("uris", uris);
        }
        query.setParameter("start", start);
        query.setParameter("end", end);
        @SuppressWarnings("unchecked")
        List<Stat> results = query.getResultList();
        return results;
    }
}

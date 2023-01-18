package ru.practicum.explorewithme.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.practicum.explorewithme.comment.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Comment> findComments(String text, Long[] users, Long[] events,
                                      boolean includeDeleted, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = cb.createQuery(Comment.class);
        Root<Comment> comment = query.from(Comment.class);

        List<Predicate> predicates = new ArrayList<>();
        if (text != null) {
            Expression<String> textLiteral = cb.literal("%" + text + "%");
            predicates.add(
                    cb.like(cb.lower(comment.get("text")), cb.lower(textLiteral))
            );
        }
        if (users != null) {
            Expression<Long> userIds = comment.get("userId");
            predicates.add(userIds.in(new ArrayList<>(Arrays.asList(users))));
        }
        if (events != null) {
            Expression<Long> eventIds = comment.get("event");
            predicates.add(eventIds.in(new ArrayList<>(Arrays.asList(events))));
        }
        if (!includeDeleted) {
            predicates.add(cb.equal(comment.get("deleted"), false));
        }
        query.orderBy(cb.asc(comment.get("postedOn")));
        query.select(comment)
                .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        Query resultQuery = entityManager.createQuery(query);
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        resultQuery.setFirstResult(pageNumber);
        resultQuery.setMaxResults(pageSize);
        List<Comment> result = resultQuery.getResultList();
        int size = result.size();
        return new PageImpl<>(result, pageable, size);
    }
}

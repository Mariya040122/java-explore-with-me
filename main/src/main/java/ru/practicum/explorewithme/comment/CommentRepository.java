package ru.practicum.explorewithme.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.OffsetPageRequest;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.user.model.User;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
    Optional<Comment> findByUserAndEventAndDeleted(User user, Event event, Boolean deleted);

    Page<Comment> findAllByEventAndDeleted(Event event, Boolean deleted, Pageable pageable);

    Page<Comment> findAllByUserAndDeleted(User user, Boolean deleted, Pageable pageable);
}

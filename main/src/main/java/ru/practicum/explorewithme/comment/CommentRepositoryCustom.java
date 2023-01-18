package ru.practicum.explorewithme.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.explorewithme.comment.model.Comment;

public interface CommentRepositoryCustom {
    Page<Comment> findComments(String text, Long[] users, Long[] events,
                               boolean includeDeleted, Pageable pageable);
}

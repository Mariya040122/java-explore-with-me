package ru.practicum.explorewithme.exceptions;

public class NotFoundCommentException extends NotFoundException {
    public NotFoundCommentException(Long id) {
        super(String.format("Комментарий с id=%d не найден", id));
    }
}

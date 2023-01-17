package ru.practicum.explorewithme.exceptions;

public class NotFoundUserException extends NotFoundException {

    public NotFoundUserException(Long id) {
        super(String.format("Пользователь с id=%d не найден", id));
    }
}

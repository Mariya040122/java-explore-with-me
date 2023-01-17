package ru.practicum.explorewithme.exceptions;

public class NotFoundRequestException extends NotFoundException {

    public NotFoundRequestException(Long id) {
        super(String.format("Запрос с id=%d не найден", id));
    }
}

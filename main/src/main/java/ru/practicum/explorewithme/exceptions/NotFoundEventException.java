package ru.practicum.explorewithme.exceptions;

public class NotFoundEventException extends NotFoundException {

    public NotFoundEventException(Long id) {
        super(String.format("Событие с id=%d не найдено", id));
    }
}

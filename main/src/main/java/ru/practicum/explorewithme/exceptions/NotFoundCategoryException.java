package ru.practicum.explorewithme.exceptions;

public class NotFoundCategoryException extends NotFoundException {

    public NotFoundCategoryException(Long id) {
        super(String.format("Категория с id=%d не найдена", id));
    }
}

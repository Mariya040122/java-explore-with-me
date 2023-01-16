package ru.practicum.explorewithme.exceptions;

public class NotFoundCompilationException extends NotFoundException{

    public NotFoundCompilationException(Long id) {
        super(String.format("Подборка с id=%d не найдена", id));
    }
}

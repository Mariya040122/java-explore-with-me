package ru.practicum.explorewithme.event.dto;

import ru.practicum.explorewithme.event.model.Event;

import java.time.LocalDateTime;

public class EventRequestDto {


    String annotation; //Новая аннотация

    int category; //Новая категория

    String description; //Новое описание

    LocalDateTime eventDate; //Новые дата и время на которые намечено событие.

    Event event; //Событие

    Long id;//Идентификатор

    Boolean paid; // Новое значение флага о платности мероприятия

    int participantLimit; //Новый лимит пользователей

    String title; //Новый заголовок
}

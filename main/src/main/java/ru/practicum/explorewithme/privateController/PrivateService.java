package ru.practicum.explorewithme.privateController;


import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.NewCommentDto;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventRequest;
import ru.practicum.explorewithme.exceptions.BadRequestException;
import ru.practicum.explorewithme.exceptions.ForbiddenException;
import ru.practicum.explorewithme.exceptions.NotFoundException;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;


import java.util.List;

public interface PrivateService {


    List<EventShortDto> getEventsByUser(long userId, int from, int size) throws NotFoundException;


    EventFullDto updateEvent(long userId, UpdateEventRequest updateEvent) throws NotFoundException, ForbiddenException;


    EventFullDto addEvent(long userId, NewEventDto newEvent) throws NotFoundException;


    EventFullDto getFullEventByUser(long userId, long eventId) throws NotFoundException;


    EventFullDto cancellationEventByUser(long userId, long eventId) throws NotFoundException, ForbiddenException;


    List<ParticipationRequestDto> getRequestFullByUser(long userId, long eventId) throws NotFoundException, ForbiddenException;


    ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId) throws NotFoundException, BadRequestException, ForbiddenException;


    ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId) throws NotFoundException, BadRequestException, ForbiddenException;


    List<ParticipationRequestDto> getUserRequests(long userId) throws NotFoundException;


    ParticipationRequestDto createRequest(long userId, long eventId) throws NotFoundException, BadRequestException, ForbiddenException;


    ParticipationRequestDto cancelRequest(long userId, long requestId) throws NotFoundException, ForbiddenException;

    CommentDto postComment(long userId, long eventId, NewCommentDto newComment);

    List<CommentDto> getCommentsByEvent(long userId, long eventId, int from, int size);

    List<CommentDto> getCommentsByUser(long userId, int from, int size);

    void deleteComment(long userId, long commentId);
}

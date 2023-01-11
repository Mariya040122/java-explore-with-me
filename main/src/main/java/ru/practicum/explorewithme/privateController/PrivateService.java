package ru.practicum.explorewithme.privateController;


import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventRequest;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;


import java.util.List;

public interface PrivateService {


    List<EventShortDto> getEventsByUser(long userId, int from, int size);


    EventFullDto updateEvent(long userId, UpdateEventRequest updateEvent);


    EventFullDto addEvent(long userId, NewEventDto newEvent);


    EventFullDto getFullEventByUser(long userId, long eventId);


    EventFullDto cancellationEventByUser(long userId, long eventId);


    List<ParticipationRequestDto> getRequestFullByUser(long userId, long eventId);


    ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId);


    ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId);


    List<ParticipationRequestDto> getUserRequests(long userId);


    ParticipationRequestDto createRequest(long userId, long eventId);


    ParticipationRequestDto cancelRequest(long userId, long requestId);
}

package ru.practicum.explorewithme.privateController;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventRequest;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@Slf4j
@RestController
@RequestMapping
public class PrivateController {

    private final PrivateService privateService;

    @Autowired
    public PrivateController(PrivateService privateService) {
        this.privateService = privateService;
    }


    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getEventsByUser(@PathVariable long userId,
                                               @RequestParam(name = "from", defaultValue = "0")
                                               @PositiveOrZero int from,
                                               @RequestParam(name = "size", defaultValue = "10")
                                               @Positive int size) {
        log.info("Получен запрос на получение событий, добавленных текущим пользователем");
        return privateService.getEventsByUser(userId, from, size);
    }


    @PatchMapping("/users/{userId}/events")
    public EventFullDto updateEvent(@PathVariable long userId,
                                    @RequestBody @Valid UpdateEventRequest updateEvent) {
        log.info("Получен запрос на изменение события, добавленных текущим пользователем");
        return privateService.updateEvent(userId, updateEvent);
    }


    @PostMapping("/users/{userId}/events")
    public EventFullDto addEvent(@PathVariable long userId,
                                 @RequestBody @Valid NewEventDto newEvent) {
        log.info("Получен запрос на добавление события");
        return privateService.addEvent(userId, newEvent);
    }


    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getFullEventByUser(@PathVariable long userId,
                                           @PathVariable long eventId) {
        log.info("Получен запрос на получение полной информации о событии добавленном текущим пользователем");
        return privateService.getFullEventByUser(userId, eventId);
    }


    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto cancellationEventByUser(@PathVariable long userId,
                                                @PathVariable long eventId) {
        log.info("Получен запрос на отмену события, добавленных текущим пользователем");
        return privateService.cancellationEventByUser(userId, eventId);
    }


    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestFullByUser(@PathVariable long userId,
                                                              @PathVariable long eventId) {
        log.info("Получен запрос на получение информации о запосах на участие в событии текущего пользователя");
        return privateService.getRequestFullByUser(userId, eventId);
    }


    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto confirmRequest(@PathVariable long userId,
                                                  @PathVariable long eventId,
                                                  @PathVariable long reqId) {
        log.info("Получен запрос на подтверждение чужой заявки на участие в событии текущего пользователя");
        return privateService.confirmRequest(userId, eventId, reqId);
    }


    @PatchMapping("/users/{userId}/events/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto rejectRequest(@PathVariable long userId,
                                                 @PathVariable long eventId,
                                                 @PathVariable long reqId) {
        log.info("Получен запрос на откланение чужой заявки на участие в событии текущего пользователя");
        return privateService.rejectRequest(userId, eventId, reqId);
    }


    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable long userId) {
        log.info("Получен запрос на получение информации о заявках текущего пользователя на участие в чужих событиях");
        return privateService.getUserRequests(userId);
    }


    @PostMapping("/users/{userId}/requests")
    public ParticipationRequestDto createRequest(@PathVariable long userId,
                                                 @RequestParam(name = "eventId", required = true)
                                                 @Positive long eventId) {
        log.info("Получен запрос на добавление запроса о текущего пользователя на участие в событии");
        return privateService.createRequest(userId, eventId);
    }


    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable long userId,
                                                 @PathVariable long requestId) {
        log.info("Получен запрос отмену своего участия в событии");
        return privateService.cancelRequest(userId, requestId);
    }

}

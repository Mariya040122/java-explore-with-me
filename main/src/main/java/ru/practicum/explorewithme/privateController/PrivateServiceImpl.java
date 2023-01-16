package ru.practicum.explorewithme.privateController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.OffsetPageRequest;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.compilation.CompilationRepository;
import ru.practicum.explorewithme.event.EventMapper;
import ru.practicum.explorewithme.event.EventRepository;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.category.CategoryRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.dto.UpdateEventRequest;
import ru.practicum.explorewithme.exceptions.*;
import ru.practicum.explorewithme.request.RequestMapper;
import ru.practicum.explorewithme.request.RequestRepository;
import ru.practicum.explorewithme.request.RequestStatus;
import ru.practicum.explorewithme.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.request.model.Request;
import ru.practicum.explorewithme.user.UserRepository;
import ru.practicum.explorewithme.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.State.*;

@Service
@Transactional(readOnly = true)
public class PrivateServiceImpl implements PrivateService {

    @Autowired
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;


    public PrivateServiceImpl(UserRepository userRepository,
                              EventRepository eventRepository,
                              CompilationRepository compilationRepository,
                              RequestRepository requestRepository,
                              CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.compilationRepository = compilationRepository;
        this.requestRepository = requestRepository;
        this.categoryRepository = categoryRepository;

    }

    @Override
    public List<EventShortDto> getEventsByUser(long userId, int from, int size) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException(userId));
        List<Event> events = eventRepository.findAllByInitiator(user, new OffsetPageRequest(from, size,
                Sort.by("id").ascending())).getContent();
        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(long userId, UpdateEventRequest updateEvent)
            throws NotFoundException, ForbiddenException {
        Event event = eventRepository.findById(updateEvent.getEventId())
                .orElseThrow(() -> new NotFoundEventException(updateEvent.getEventId()));
        if (event.getInitiator().getId() != userId) {
            throw new ForbiddenException("Пользователю данное действие запрещено");
        }
        if (event.getState() == PUBLISHED) {
            throw new ForbiddenException("Редактировать опубликованное событие запрещено");
        }
        if (updateEvent.getEventDate() != null) {
            event.setEventDate(updateEvent.getEventDate());
        }
        if (updateEvent.getAnnotation() != null) {
            event.setAnnotation(updateEvent.getAnnotation());
        }
        if (updateEvent.getCategory() != null) {
            Category newCategory = categoryRepository.findById(updateEvent.getCategory())
                    .orElseThrow(() -> new NotFoundCategoryException(updateEvent.getCategory()));
            event.setCategory(newCategory);
        }
        if (updateEvent.getDescription() != null) {
            event.setDescription(updateEvent.getDescription());
        }
        if (updateEvent.getPaid() != null) {
            event.setPaid(updateEvent.getPaid());
        }
        if (updateEvent.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEvent.getParticipantLimit());
        }
        if (updateEvent.getTitle() != null) {
            event.setTitle(updateEvent.getTitle());
        }
        event.setState(PENDING);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }


    @Override
    @Transactional
    public EventFullDto addEvent(long userId, NewEventDto newEvent) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException(userId));
        Category category = categoryRepository.findById(newEvent.getCategory()).orElseThrow();
        Event event = EventMapper.fromNewEventDto(newEvent);
        event.setInitiator(user);
        event.setCategory(category);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(PENDING);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto getFullEventByUser(long userId, long eventId) throws NotFoundException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
        if (event.getInitiator().getId() == userId) {
            return EventMapper.toEventFullDto(event);
        } else return null;
    }

    @Override
    @Transactional
    public EventFullDto cancellationEventByUser(long userId, long eventId)
            throws NotFoundException, ForbiddenException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
        if (event.getInitiator().getId() == userId) {
            if (event.getState() == PENDING) {
                event.setState(CANCELED);
                return EventMapper.toEventFullDto(eventRepository.save(event));
            } else throw new ForbiddenException("Отменить можно только событие в ожидании публикации");
        } else throw new ForbiddenException("Пользователю данное действие запрещено");
    }


    @Override
    public List<ParticipationRequestDto> getRequestFullByUser(long userId, long eventId)
            throws NotFoundException, ForbiddenException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
        if (event.getInitiator().getId() == userId) {
            List<Request> requests = requestRepository.findAllByEvent(event);
            return requests.stream()
                    .map(RequestMapper::toParticipationRequestDto)
                    .collect(Collectors.toList());
        } else throw new ForbiddenException("Пользователю данное действие запрещено");
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmRequest(long userId, long eventId, long reqId)
            throws NotFoundException, BadRequestException, ForbiddenException {
        checkRequest(userId, eventId, reqId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
        Request request = requestRepository.findById(reqId).orElseThrow(() -> new NotFoundRequestException(reqId));
        if (request.getStatus() != RequestStatus.PENDING)
            throw new BadRequestException("Статус запроса должен быть В ОЖИДАНИИ");
        if (event.getParticipantLimit() == 0) {
            request.setStatus(RequestStatus.CONFIRMED);
            return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
        } else {
            long approvedAmount = requestRepository.countAllByEventAndStatus(event, RequestStatus.CONFIRMED);
            if (approvedAmount < event.getParticipantLimit()) {
                request.setStatus(RequestStatus.CONFIRMED);
                requestRepository.save(request);
                if ((event.getParticipantLimit() - approvedAmount) == 1) {
                    requestRepository.updateStatus(eventId, RequestStatus.PENDING, RequestStatus.REJECTED);
                }
                return RequestMapper.toParticipationRequestDto(request);
            } else throw new ForbiddenException("Лимит участников исчерпан");
        }
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectRequest(long userId, long eventId, long reqId)
            throws NotFoundException, BadRequestException, ForbiddenException {
        checkRequest(userId, eventId, reqId);
        Request request = requestRepository.findById(reqId).orElseThrow(() -> new NotFoundRequestException(reqId));
        if (request.getStatus() != RequestStatus.PENDING)
            throw new BadRequestException("Статус запроса должен быть В ОЖИДАНИИ");
        request.setStatus(RequestStatus.REJECTED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    private void checkRequest (long userId, long eventId, long reqId)
            throws NotFoundException, BadRequestException, ForbiddenException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
        if (event.getState() != PUBLISHED) throw new BadRequestException("Статус события должен быть В ОЖИДАНИИ");
        if (event.getInitiator().getId() != userId)
            throw new ForbiddenException("Пользователю данное действие запрещено");
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(long userId) throws NotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException(userId));
        return requestRepository.findAllByRequester(user).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(long userId, long eventId)
            throws NotFoundException, BadRequestException, ForbiddenException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundUserException(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
        if (event.getParticipantLimit() == requestRepository.countAllByEventAndStatus(event, RequestStatus.CONFIRMED)) {
            throw new ForbiddenException("Лимит участников исчерпан");
        }
        if (event.getState() != PUBLISHED) throw new BadRequestException("Статус события должен быть В ОЖИДАНИИ");
        if (event.getInitiator().getId() == userId)
            throw new ForbiddenException("Пользователю данное действие запрещено");
        Optional<Request> requestExist = requestRepository.findRequestByRequesterAndEvent(user, event);
        if (requestExist.isPresent()) throw new BadRequestException("Запрос на участие в событии уже создан");
        RequestStatus status = RequestStatus.PENDING;
        if (!event.getRequestModeration()) {
            status = RequestStatus.CONFIRMED;
        }
        Request request = new Request(null, LocalDateTime.now(), event, user, status);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(long userId, long requestId)
            throws NotFoundException, ForbiddenException {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundRequestException(requestId));
        if (request.getRequester().getId() != userId)
            throw new ForbiddenException("Пользователю данное действие запрещено");
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }
}

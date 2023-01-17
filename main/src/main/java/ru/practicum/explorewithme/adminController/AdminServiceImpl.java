package ru.practicum.explorewithme.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.OffsetPageRequest;
import ru.practicum.explorewithme.State;
import ru.practicum.explorewithme.category.CategoryMapper;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.compilation.CompilationMapper;
import ru.practicum.explorewithme.compilation.CompilationRepository;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.event.EventMapper;
import ru.practicum.explorewithme.event.EventRepository;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.event.dto.AdminUpdateEventRequest;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.category.dto.NewCategoryDto;
import ru.practicum.explorewithme.category.CategoryRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.exceptions.*;
import ru.practicum.explorewithme.request.RequestRepository;
import ru.practicum.explorewithme.user.UserMapper;
import ru.practicum.explorewithme.user.UserRepository;
import ru.practicum.explorewithme.user.dto.NewUserRequest;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;


    public AdminServiceImpl(UserRepository userRepository,
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
    public List<EventFullDto> eventSearch(Long[] users, State[] states, Long[] categories,
                                          LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        List<Event> foundEvents = eventRepository.findAdminEvents(users, states, categories, rangeStart, rangeEnd,
                new OffsetPageRequest(from, size, Sort.unsorted())).getContent();
        return foundEvents.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto editEvent(long eventId, AdminUpdateEventRequest updateEventRequest) throws NotFoundException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
        if (updateEventRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventRequest.getAnnotation());
        }
        if (updateEventRequest.getCategory() != null) {
            event.setCategory(categoryRepository.findById(updateEventRequest.getCategory())
                    .orElseThrow(() -> new NotFoundCategoryException(updateEventRequest.getCategory())));
        }
        if (updateEventRequest.getDescription() != null) {
            event.setDescription(updateEventRequest.getDescription());
        }
        if (updateEventRequest.getEventDate() != null) {
            event.setEventDate(updateEventRequest.getEventDate());
        }
        if (updateEventRequest.getLocation() != null) {
            event.setLongitude(updateEventRequest.getLocation().getLon());
            event.setLatitude(updateEventRequest.getLocation().getLat());
        }
        if (updateEventRequest.getPaid() != null) {
            event.setPaid(updateEventRequest.getPaid());
        }
        if (updateEventRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventRequest.getParticipantLimit());
        }
        if (updateEventRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventRequest.getRequestModeration());
        }
        if (updateEventRequest.getTitle() != null) {
            event.setTitle(updateEventRequest.getTitle());
        }
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(long eventId) throws NotFoundException, BadRequestException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
        if (event.getState() != State.PENDING) throw new BadRequestException("Статус события должен быть В ОЖИДАНИИ");
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1L))) {
            throw new BadRequestException("Время начала события должно быть не ранее, " +
                    "чем через 1 час от текущего момента");
        }
        event.setState(State.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto rejectEvent(long eventId) throws NotFoundException, BadRequestException {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
        if (event.getState() != State.PENDING) throw new BadRequestException("Статус события должен быть В ОЖИДАНИИ");;
        event.setState(State.CANCELED);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public CategoryDto editCategory(CategoryDto categoryDto) throws NotFoundException {
        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundCategoryException(categoryDto.getId()));
        category.setName(categoryDto.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDto createCategory(NewCategoryDto newCategory) {
        Category category = new Category(0L, newCategory.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<UserDto> usersSearch(Long[] users, int from, int size) {
        if (users != null) {
            return userRepository.findUsersInList(new ArrayList<>(Arrays.asList(users))).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findAll(new OffsetPageRequest(from, size, Sort.by("id").ascending()))
                    .getContent().stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public UserDto createUser(NewUserRequest newUser) {
        User user = UserMapper.fromNewUserRequest(newUser);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public CompilationDto createCompilation(NewCompilationDto newCompilation) {
        Compilation compilation = CompilationMapper.fromNewCompilationDto(newCompilation);
        List<Event> events = new ArrayList<>();
        if (newCompilation.getEvents() != null && !newCompilation.getEvents().isEmpty()) {
            events = eventRepository.findEventsInList(new ArrayList<>(newCompilation.getEvents()));
        }
        compilation.setEvents(events);
        compilationRepository.save(compilation);
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    @Transactional
    public void deleteCompilation(long compilationId) {
        compilationRepository.deleteById(compilationId);
    }

    @Override
    @Transactional
    public void deleteEventInCompilation(long compilationId, long eventId) throws NotFoundException {
        Compilation compilation = compilationRepository.findById(compilationId).orElseThrow();
        if (compilation.getEvents().stream()
                .filter(c -> c.getId() == eventId)
                .collect(Collectors.toList())
                .isEmpty()) {
            throw new NotFoundEventException(eventId);
        }
        Event event = eventRepository.findById(eventId).orElseThrow();
        event.setCompilation(null);
        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void addEventToCompilation(long compilationId, long eventId) throws NotFoundException {
        Compilation compilation = compilationRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundCompilationException(compilationId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundEventException(eventId));
        event.setCompilation(compilationId);
        eventRepository.save(event);
    }

    @Override
    @Transactional
    public void unpinCompilation(long compilationId) {
        Compilation compilation = compilationRepository.findById(compilationId).orElseThrow();
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public void pinCompilation(long compilationId) {
        Compilation compilation = compilationRepository.findById(compilationId).orElseThrow();
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }
}

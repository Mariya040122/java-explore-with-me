package ru.practicum.explorewithme.publicController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.OffsetPageRequest;
import ru.practicum.explorewithme.category.CategoryMapper;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.compilation.CompilationMapper;
import ru.practicum.explorewithme.compilation.CompilationRepository;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.event.EventRepository;
import ru.practicum.explorewithme.category.dto.CategoryDto;
import ru.practicum.explorewithme.event.SortEventEnum;
import ru.practicum.explorewithme.category.CategoryRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.request.RequestRepository;
import ru.practicum.explorewithme.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicServiceImpl implements PublicService {
    @Autowired
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;


    public PublicServiceImpl(UserRepository userRepository,
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
    public List<Event> findAllEvents(String text, Long[] categories, Boolean paid,
                                     LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                     Boolean onlyAvailable, SortEventEnum sort, int from, int size) {
        List<Event> foundEvents = eventRepository.findPublicEvents(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, new OffsetPageRequest(from, size, Sort.unsorted())).getContent();
        return foundEvents;
    }

    @Override
    public Event findEvent(long eventId) {
        return eventRepository.findById(eventId).orElseThrow();
    }

    @Override
    public List<CompilationDto> findAllCompilations(boolean pinned, int from, int size) {
        List<Compilation> compilations = compilationRepository.findAllByPinned(pinned, new OffsetPageRequest(from, size,
                Sort.by("id").ascending())).getContent();
        return compilations.stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto findCompilation(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow();
        return CompilationMapper.toCompilationDto(compilation);
    }

    @Override
    public List<CategoryDto> findAllCategories(int from, int size) {
        List<Category> categories = categoryRepository.findAll(new OffsetPageRequest(from, size,
                Sort.by("id").ascending())).getContent();
        return categories.stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findCategory(long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow();
        return CategoryMapper.toCategoryDto(category);
    }
}

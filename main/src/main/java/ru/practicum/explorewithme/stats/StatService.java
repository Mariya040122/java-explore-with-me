package ru.practicum.explorewithme.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explorewithme.event.EventMapper;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.stats.dto.EndpointHit;
import ru.practicum.explorewithme.stats.dto.ViewStats;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatService {
    protected final RestTemplate rest;

    @Autowired
    public StatService(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        this.rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public List<ViewStats> findStat(Map<String, Object> parameters) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, defaultHeaders());

        ResponseEntity<List<ViewStats>> statServerResponse;
        try {
            statServerResponse = rest.exchange("/stats?start={start}&end={end}&uris={uris}", HttpMethod.GET, requestEntity,
                    new ParameterizedTypeReference<>() {
                    }, parameters);
        } catch (HttpStatusCodeException e) {
            return null;
        }
        if (statServerResponse.getStatusCode().is2xxSuccessful()) {
            return statServerResponse.getBody();//Arrays.asList(statServerResponse.getBody());
        } else return null;
    }

    public List<EventShortDto> getEventsShortDto(List<Event> events, boolean sortByViews) {
        return getEventFullInfo(events, sortByViews).stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    public List<EventFullDto> getEventsFullDto(List<Event> events, boolean sortByViews) {
        return getEventFullInfo(events, sortByViews).stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }


    public List<Event> getEventFullInfo(List<Event> events, boolean sortByViews) {
        List<String> uris = events.stream()
                .map(this::convertToUri)
                .collect(Collectors.toList());
        Map<String, Object> parameters = Map.of(
                "start", java.net.URLEncoder.encode("2020-01-01 00:00:00", StandardCharsets.UTF_8),
                "end", java.net.URLEncoder.encode("2100-01-01 00:00:00", StandardCharsets.UTF_8),
                "uris", uris.toArray(String[]::new)
        );
        List<ViewStats> viewStats = findStat(parameters);
        if (viewStats == null) {
            return events;
        }
        for (Event ev : events) {
            String uri = "/events/" + ev.getId();
            Optional<ViewStats> stat = viewStats.stream()
                    .filter(v -> v.getUri().equals(uri))
                    .findFirst();
            if (stat.isPresent()) {
                ev.setViews(stat.get().getHits());
            }
        }
        if (sortByViews) {
            List<Event> sortedCopy = new ArrayList(events);
            sortedCopy.sort(Comparator.comparing(Event::getViews).reversed());
            return sortedCopy;
        }
        return events;
    }

    private String convertToUri(Event id) {
        return "/events/" + id.getId();
    }

    public ResponseEntity<Object> postStat(EndpointHit body) {
        HttpEntity<EndpointHit> requestEntity = new HttpEntity<>(body, defaultHeaders());

        ResponseEntity<Object> statServerResponse;
        try {
            statServerResponse = rest.exchange("/hit", HttpMethod.POST, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        if (statServerResponse.getStatusCode().is2xxSuccessful()) {
            return statServerResponse;
        }
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(statServerResponse.getStatusCode());
        if (statServerResponse.hasBody()) {
            return responseBuilder.body(statServerResponse.getBody());
        }
        return responseBuilder.build();
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}

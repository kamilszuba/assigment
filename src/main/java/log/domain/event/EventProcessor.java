package log.domain.event;

import log.domain.LogPart;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static log.domain.event.Event.fromLogs;

@AllArgsConstructor
public class EventProcessor {

    private static final Map<String, LogPart> LOG_CACHE = new HashMap<>();

    private final EventRepository eventRepository;

    public void process(LogPart logPart) {

        if (itIsSecondPartOfEvent(logPart)) {
            createEvent(logPart);
        } else {
            cache(logPart);
        }
    }

    private void createEvent(LogPart newLogPart) {

        LogPart oldLogPart = LOG_CACHE.remove(newLogPart.getId());
        Event event = fromLogs(oldLogPart, newLogPart);

        eventRepository.save(event);
    }

    private boolean itIsSecondPartOfEvent(LogPart logPart) {
        return LOG_CACHE.containsKey(logPart.getId());
    }

    private void cache(LogPart logPart) {
        LOG_CACHE.put(logPart.getId(), logPart);
    }
}

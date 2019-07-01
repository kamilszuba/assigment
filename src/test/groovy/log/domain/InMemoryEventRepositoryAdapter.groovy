package log.domain

import log.domain.event.Event
import log.domain.event.EventRepository

class InMemoryEventRepositoryAdapter implements EventRepository {

    private final Set<Event> database = new HashSet<>()

    @Override
    void save(Event event) {
        database.add(event)
    }
}

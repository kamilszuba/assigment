package log.domain.event;

public interface EventRepository {
    void save(Event event);
}

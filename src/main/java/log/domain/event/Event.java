package log.domain.event;

import log.domain.LogPart;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;

import static java.time.Duration.between;
import static java.time.Instant.ofEpochMilli;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode
@Getter
@ToString
public class Event {

    private static final long ALERT_BORDER_IN_MILLIS = 4;

    private String id;

    private Long duration;

    private String host;

    private boolean alert;

    static Event fromLogs(LogPart startLogPart, LogPart endLogPart) {

        Instant firstTimestamp = ofEpochMilli(startLogPart.getTimestamp());
        Instant secondTimestamp = ofEpochMilli(endLogPart.getTimestamp());

        long duration = between(firstTimestamp, secondTimestamp)
                .abs()
                .toMillis();

        return new Event(startLogPart.getId(), duration, startLogPart.getHost(), isAlert(duration));
    }

    private static boolean isAlert(long duration) {
        return duration > ALERT_BORDER_IN_MILLIS;
    }
}

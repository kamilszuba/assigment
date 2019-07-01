package log.domain;

import com.google.gson.Gson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static log.domain.Constants.MAX_STRING_LENGTH;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
public class LogPart {

    private final static Gson GSON = new Gson();

    @NotBlank
    @Size(max = MAX_STRING_LENGTH)
    private String id;

    @NotNull
    private State state;

    @Size(max = MAX_STRING_LENGTH)
    private String host;

    private Type type;

    @NotNull
    private Long timestamp;

    static LogPart fromJson(String json) {
        return GSON.fromJson(json, LogPart.class);
    }
}

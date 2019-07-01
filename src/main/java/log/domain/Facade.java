package log.domain;

import lombok.AllArgsConstructor;

import java.io.IOException;

import static lombok.AccessLevel.PACKAGE;

@AllArgsConstructor(access = PACKAGE)
public class Facade {

    private final ParseLogsUseCase parseLogsUseCase;

    public void parse(FilePath filePath) throws IOException {
        parseLogsUseCase.parse(filePath);
    }
}

package log.domain;

import log.domain.event.EventProcessor;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@AllArgsConstructor
class ParseLogsUseCase {

    private final LogValidator validator;
    private final EventProcessor eventProcessor;

    void parse(FilePath logFile) throws IOException {
        checkFilePath(logFile);

        createStreamFromFile(logFile)
                .map(LogPart::fromJson)
                .map(validator::validate)
                .forEach(eventProcessor::process);
    }

    private void checkFilePath(FilePath logFile) {
        checkArgument(nonNull(logFile) && isNotBlank(logFile.getPath()));
    }

    private Stream<String> createStreamFromFile(FilePath filePath) throws IOException {
        return Files.lines(Paths.get(filePath.getPath()))
                .parallel();
    }
}

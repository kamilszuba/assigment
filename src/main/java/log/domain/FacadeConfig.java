package log.domain;

import log.domain.event.EventProcessor;
import log.domain.event.EventRepository;

import javax.validation.ValidatorFactory;


public class FacadeConfig {

    public Facade logFacade(EventRepository eventRepository, ValidatorFactory validatorFactory) {
        return new Facade(
                new ParseLogsUseCase(
                        new LogValidator(validatorFactory),
                        new EventProcessor(eventRepository)
                )
        );
    }
}

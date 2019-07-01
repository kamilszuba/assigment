package log.domain;

import lombok.AllArgsConstructor;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@AllArgsConstructor
class LogValidator {

    private final ValidatorFactory validationFactory;

    LogPart validate(LogPart logPart) {

        Validator validator = validationFactory.getValidator();
        Set<ConstraintViolation<LogPart>> constraintViolations = validator.validate(logPart);

        if (constraintViolations.size() > 0) {
            throw new IllegalArgumentException("Log entry with id: " + logPart.getId() + " data is not sufficient to parse!");
        }

        return logPart;
    }
}

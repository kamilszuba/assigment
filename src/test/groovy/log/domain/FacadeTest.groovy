package log.domain


import spock.lang.Specification

import javax.validation.Validation

class FacadeTest extends Specification {

    final def logFacade = new FacadeConfig().logFacade(new InMemoryEventRepositoryAdapter(), Validation.buildDefaultValidatorFactory())

    def "file should be parsed correctly cause input data is valid"() {
        given:
            def fileWithValidData = new FilePath(getTestFileDirectoryPath() + "valid_data_with_6_events.txt")
        when:
            logFacade.parse(fileWithValidData)
        then:
            dataAreParsedCorrectly()
    }

    def "parsing should fail due to null id"() {
        given:
            def file = new FilePath(getTestFileDirectoryPath() + "invalid_data_null_id.txt")
        when:
            logFacade.parse(file)
        then:
            IllegalArgumentException exception = thrown()
            exception.message.contains('Log entry with id: ')
    }

    def "parsing should fail due to null state"() {
        given:
            def file = new FilePath(getTestFileDirectoryPath() + "invalid_data_null_state.txt")
        when:
            logFacade.parse(file)
        then:
            IllegalArgumentException exception = thrown()
            exception.message.contains('Log entry with id: ')
    }

    def "parsing should fail due to null timestamp"() {
        given:
            def file = new FilePath(getTestFileDirectoryPath() + "invalid_data_null_timestamp.txt")
        when:
            logFacade.parse(file)
        then:
            IllegalArgumentException exception = thrown()
            exception.message.contains('Log entry with id: ')
    }

    def "file should be parsed with event with alert"() {
        given:
            def fileWithValidData = new FilePath(getTestFileDirectoryPath() + "valid_data_with_alert.txt")
        when:
            logFacade.parse(fileWithValidData)
        then:
            thereIsEventWithAlert()
    }

    def "file should be parsed with event without alert"() {
        given:
            def fileWithValidData = new FilePath(getTestFileDirectoryPath() + "valid_data_without_alert.txt")
        when:
            logFacade.parse(fileWithValidData)
        then:
            thereIsEventWithoutAlert()
    }

    private def thereIsEventWithAlert() {
        logFacade.parseLogsUseCase.eventProcessor.eventRepository.database.find { it.alert }
    }

    private def thereIsEventWithoutAlert() {
        logFacade.parseLogsUseCase.eventProcessor.eventRepository.database.find { !it.alert }
    }


    private void dataAreParsedCorrectly() {
        def events = logFacade.parseLogsUseCase.eventProcessor.eventRepository.database.findAll()
        assert events.size() == 6
    }

    private static String getTestFileDirectoryPath() {
        System.getProperty("user.dir") + "/src/test/resources/files/"
    }
}

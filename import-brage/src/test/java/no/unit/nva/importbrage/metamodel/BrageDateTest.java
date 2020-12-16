package no.unit.nva.importbrage.metamodel;

import no.unit.nva.importbrage.metamodel.exceptions.IllegalDateConversionException;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidPublicationDateException;
import no.unit.nva.importbrage.metamodel.exceptions.InvalidTimestampException;
import no.unit.nva.importbrage.metamodel.types.DateType;
import no.unit.nva.model.PublicationDate;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static no.unit.nva.importbrage.metamodel.BrageDate.DATE_ELEMENT_DELIMITER;
import static no.unit.nva.importbrage.metamodel.exceptions.IllegalDateConversionException.MESSAGE_TEMPLATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BrageDateTest {

    public static final String NOT_RELEVANT = "not relevant";

    private static Stream<Arguments> getInstantDateLists() {
        return Stream.of(
                Arguments.of("2020-03-06T01:21:03.101Z")
        );
    }

    @ParameterizedTest(name = "Publication dates with structure {0} are valid")
    @MethodSource("getPublicationDateLists")
    void asPublicationDateReturnsPublicationDateWhenInputIsValid(List<String> value) throws
            InvalidPublicationDateException, IllegalDateConversionException {
        var actual = new BrageDate(DateType.EMBARGO_END_DATE, String.join(DATE_ELEMENT_DELIMITER, value))
                .asPublicationDate();
        var expected = getExpectedPublicationDate(value);
        assertThat(actual, equalTo(expected));
    }

    @ParameterizedTest(name = "Instants with structure {0} are valid")
    @MethodSource("getInstantDateLists")
    void asInstantReturnsPublicationDateWhenInputIsValid(String value) throws IllegalDateConversionException,
            InvalidTimestampException {
        var actual = new BrageDate(DateType.ACCESSIONED, value).asInstant();
        var expected = Instant.parse(value);
        assertThat(actual, equalTo(expected));
    }

    @ParameterizedTest(name = "Publication dates with type {0} are invalid")
    @MethodSource("getDateTypeNotString")
    void asPublicationDateThrowsIllegalDateConversionExceptionWhenDateTypeIsInvalid(DateType type) {
        Executable executable = () -> new BrageDate(type, NOT_RELEVANT).asPublicationDate();
        var exception = assertThrows(IllegalDateConversionException.class, executable);
        var expectedMessage = String.format(MESSAGE_TEMPLATE, NOT_RELEVANT, type);
        assertThat(exception.getMessage(), equalTo(expectedMessage));
    }

    @ParameterizedTest(name = "Publication dates with type {0} are invalid")
    @MethodSource("getDateTypeNotTimestamp")
    void asInstantThrowsIllegalDateConversionExceptionWhenDateTypeIsInvalid(DateType type) {
        Executable executable = () -> new BrageDate(type, NOT_RELEVANT).asInstant();
        var exception = assertThrows(IllegalDateConversionException.class, executable);
        var expectedMessage = String.format(MESSAGE_TEMPLATE, NOT_RELEVANT, type);
        assertThat(exception.getMessage(), equalTo(expectedMessage));
    }

    @ParameterizedTest(name = "Publication dates with structure {0} are invalid")
    @ValueSource(strings = {"nonsense", "2000-123-11", "2000-01-123", "2000-1", "2000-12-", "--", "-10-01"})
    void asPublicationDateThrowsInvalidPublicationDateExceptionWhenInputIsInvalid(String candidate) {
        DateType dateType = DateType.ISSUED;
        Executable executable = () -> new BrageDate(dateType, candidate).asPublicationDate();
        var exception = assertThrows(InvalidPublicationDateException.class, executable);
        var expectedMessage = String.format(InvalidPublicationDateException.MESSAGE_TEMPLATE, dateType, candidate);
        assertThat(exception.getMessage(), equalTo(expectedMessage));
    }

    @ParameterizedTest(name = "Publication dates with structure {0} are invalid")
    @ValueSource(strings = {"nonsense", "2000-123-11", "2000-01-123", "2000-1", "2000-12-", "--", "-10-01"})
    void asInstantThrowsInvalidPublicationDateExceptionWhenInputIsInvalid(String candidate) {
        DateType dateType = DateType.ACCESSIONED;
        Executable executable = () -> new BrageDate(dateType, candidate).asInstant();
        var exception = assertThrows(InvalidTimestampException.class, executable);
        var expectedMessage = String.format(InvalidTimestampException.MESSAGE_TEMPLATE, dateType, candidate);
        assertThat(exception.getMessage(), equalTo(expectedMessage));
    }

    private PublicationDate getExpectedPublicationDate(List<String> value) {
        var month = value.size() > 1 ? value.get(1) : null;
        var day = value.size() > 2 ? value.get(2) : null;
        return new PublicationDate.Builder()
                .withYear(value.get(0))
                .withMonth(month)
                .withDay(day)
                .build();
    }

    private static Stream<Arguments> getDateTypeNotString() {
        return Arrays.stream(DateType.values()).sequential()
                .filter(BrageDateTest::isNotADateStringType)
                .map(Arguments::of);
    }

    private static Stream<Arguments> getDateTypeNotTimestamp() {
        return Arrays.stream(DateType.values()).sequential()
                .filter(BrageDateTest::isNotATimestampType)
                .map(Arguments::of);
    }

    private static boolean isNotADateStringType(DateType type) {
        return !type.getValueType().equals(DateType.DateValueType.STRING);
    }

    private static Stream<Arguments> getPublicationDateLists() {
        return Stream.of(
                Arguments.of(List.of("2000")),
                Arguments.of(List.of("2000", "02")),
                Arguments.of(List.of("2000", "02", "01"))
        );
    }

    private static boolean isNotATimestampType(DateType type) {
        return !type.getValueType().equals(DateType.DateValueType.TIMESTAMP);
    }
}
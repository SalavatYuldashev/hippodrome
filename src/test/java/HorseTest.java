import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {


    private final Horse testHorse = new Horse("Jerry", 77.0, 333.0);
    private final Horse testHorseWithoutDistance = new Horse("Sugar", 88.0);


    @Test
    @DisplayName("Должен вернуть IllegalArgumentException если имя null")
    void returnIllegalArgumentExceptionWhenNameIsNull() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> new Horse(null, 13., 23.),
                        "Ошибка при передаче null в конструктор вместо имя.");
        assertEquals("Name cannot be null.", ex.getMessage());

    }

    @ParameterizedTest(name = "input=''{0}'' → IllegalArgumentException")
    @ValueSource(strings = {"", " ", "    ", "\t", "\n"})
    @DisplayName("Should throw IllegalArgumentException if name is empty")
    void returnIllegalArgumentExceptionIfNameIsEmpty(String input) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Horse(input, 12., 23.),
                "Ошибка при передаче пустой строки вместо имя.");
        assertEquals("Name cannot be blank.", ex.getMessage());

    }

    @Test
    @DisplayName("Should throw IllegalArgumentException if speed is negative.")
    void returnIllegalArgumentExceptionIfSpeedIsNegative() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Horse("Bill", -3, 11.0),
                "Exception when speed is negative.");
        assertEquals("Speed cannot be negative.", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException if distance is negative.")
    void returnIllegalArgumentExceptionIfDistanceIsNegative() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Horse("Sara", 23.0, -34.0),
                "Exception when distance is negative.");
        assertEquals("Distance cannot be negative.", ex.getMessage());
    }

    @Test
    @DisplayName("Should return correct name.")
    void isCorrectGetName() {
        assertEquals("Jerry", testHorse.getName(), "Exception when getName() is wrong");
    }

    @Test
    @DisplayName("Should return correct speed.")
    void isCorrectGetSpeed() {
        assertEquals(77.0, testHorse.getSpeed(), "Exception when getSpeed() is wrong");
    }

    @Test
    @DisplayName("Should return correct distance.")
    void isCorrectGetDistance() {
        assertEquals(333.0, testHorse.getDistance(), "Exception when getDistance() is wrong");
    }

    @Test
    @DisplayName("Should return 0 if object created without distance.")
    void returnZeroIfObjectCreatedWithoutDistance() {
        assertEquals(0, testHorseWithoutDistance.getDistance(), "Exception when getDistance() isn't 0");
    }

    @Test
    void moveShouldCallGetRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {

            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            testHorse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9), Mockito.atLeastOnce());
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {77.0, 32.0})
    void isMoveReturnCorrectValue(Double speed) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {

            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.5);
            Horse testBill = new Horse("Bill", speed);
            testBill.move();
            assertEquals(speed * 0.5, testBill.getDistance());
        }

    }

}
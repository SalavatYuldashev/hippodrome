import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.stream.IntStream;
import java.util.stream.Collectors;


class HippodromeTest {


    public static List<Horse> initialHorses(int countOfHorses) {
        List<Horse> horses = new ArrayList<>(countOfHorses);
        for (int i = 0; i < countOfHorses; i++) {
            String name = "Horse " + (i + 1);
            double speed = ThreadLocalRandom.current().nextDouble(5.0, 30.0);
            double distance = ThreadLocalRandom.current().nextDouble(1.0, 50.0);
            horses.add(new Horse(name, speed, distance));
        }
        return horses;

    }

    List<Horse> listOfThirtyHorses = initialHorses(30);
    Hippodrome testHippodromeOnThirtyHorses = new Hippodrome(listOfThirtyHorses);


    @Test
    @DisplayName("Should return IllegalArgumentException if horses is null.")
    void returnIllegalArgumentExceptionWhenHorsesNull() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> {
                    new Hippodrome(null);
                });
        assertEquals("Horses cannot be null.", ex.getMessage());
    }

    @ParameterizedTest(name = "input=''{0}'' → IllegalArgumentException")
    @EmptySource
    @DisplayName("Should return IllegalArgumentException if horses is empty.")
    void returnIllegalArgumentExceptionWhenHorsesEmpty(List<Horse> horses) {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals("Horses cannot be empty.", ex.getMessage());

    }

    @Test
    void shouldGetCorrectHorses() {
        assertEquals(listOfThirtyHorses, testHippodromeOnThirtyHorses.getHorses());
    }

    @Test
    void moveShouldInvokeMoveOnEachHorse() {
        List<Horse> horseSpies = IntStream.rangeClosed(1, 50)
                .mapToObj(i -> spy(new Horse("Horse" + i, 5.0, 10.0)))
                .collect(Collectors.toList());
        Hippodrome hippodromeOnSpy = new Hippodrome(horseSpies);
        hippodromeOnSpy.move();
        for (Horse horseSpy : horseSpies) {
            verify(horseSpy, times(1)).move();
        }

    }

    @Test
    void getWinnerShouldReturnHorseWithTheMostOfDistance() {

        assertEquals(listOfThirtyHorses.stream().max(Comparator.comparing(Horse::getDistance)).orElse(null),
                testHippodromeOnThirtyHorses.getWinner());

    }
}
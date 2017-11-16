package de.data_experts.jdk9_collections;

import de.data_experts.jdk9_collections.junit5.FastTest;
import de.data_experts.jdk9_collections.junit5.RepeatedFastTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

class JUnit5FeatureTest {


    @BeforeEach
    void init(TestInfo testInfo) {
        System.out.println(testInfo.getTestClass().get());
        System.out.println(testInfo.getDisplayName());
    }

    @Test
    void testTimeoutWithoutForcedEndErfolgreich() {
        Assertions.assertTimeout(Duration.ofMillis(1000), () -> findAllPrimes(1000).forEach(System.out::println), "Ermittlung aller Primzahlen bis 1000 nicht in einer Sekunde möglich.");
    }


    @Test
    void testTimeoutWithoutForcedEnd() {
        Assertions.assertTimeout(Duration.ofMillis(1000), () -> findAllPrimes(100000).forEach(System.out::println), "Ermittlung aller Primzahlen bis 100000 nicht in einer Sekunde möglich.");
    }

    // Wieso ist dieser Test grün, der darüber aber nicht?!
    @Test
    void testGetAllPrimesWithoutError() {
        Assertions.assertTimeout(Duration.ofMillis(1000), () -> findAllPrimes(100000).peek(System.out::println), "Ermittlung aller Primzahlen bis 100000 nicht in einer Sekunde möglich.");
    }

    @Test
    void testTimeoutWithForcedEnd() {
        Assertions.assertTimeoutPreemptively(Duration.ofMillis(1000), () -> findAllPrimes(1000000).forEach(System.out::println), "Ermittlung aller Primzahlen bis 1000000 nicht in einer Sekunde erfolgreich.");
    }

    @FastTest
    void testVeryFast() {
        Assertions.assertTrue(2 + 3 == 5);
    }

    @RepeatedFastTest
    void testRepeatedVeryFast() {
        Assertions.assertTrue(2 + 3 == 5);
    }

    @RepeatedTest(10)
    void testRepeated() {
        Assertions.assertTrue(2 + 3 == 5);
    }


    @ParameterizedTest
    @ValueSource(longs = {5L, 31L, 11L, 109L})
    void testIsPrimeImperative(long prime) {
        Assertions.assertTrue(isPrimeImperative(prime));
    }

    @ParameterizedTest
    @ValueSource(longs = {5L, 31L, 11L, 109L})
    void testIsPrimeDeclarative(long prime) {
        Assertions.assertTrue(isPrimeDeclarative(prime));
    }

    private LongStream findAllPrimes(long until) {
        return LongStream.range(2, until)
                .filter(JUnit5FeatureTest::isPrimeDeclarative);
    }

    private static boolean isPrimeImperative(long number) {
        assert number > 1;
        for (long divisor = 2; divisor < number; divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPrimeDeclarative(long number) {
        assert number > 1;
        return LongStream.range(2, number).noneMatch(divisor -> number % divisor == 0);
    }


    @Test
    void getAllMatchingPersons() {
        PersonProvider.getPersons().stream()
                .filter(p -> p.getGroesse() > 185)
                .filter(p -> p.getAlter() < 60)
                .forEach(System.out::println);
    }

    @ParameterizedTest
    @CsvSource({"62, 175", "17, 165", "36, 175", "89, 190"})
    void getFirstMatchingPerson(int mindestAlter, int mindestGroesse) {
        PersonProvider.getPersons().stream()
                .filter(p -> p.getGroesse() >= mindestGroesse)
                .filter(p -> p.getAlter() >= mindestAlter)
                .findFirst()
                .ifPresentOrElse(System.out::println, () -> System.out.println("Nix gefunden!"));
    }

    @ParameterizedTest
    @ArgumentsSource(MyArgumentsProvider.class)
    void getFirstMatchingPersonWithArgProvider(int mindestAlter, int mindestGroesse) {
        PersonProvider.getPersons().stream()
                .filter(p -> p.getGroesse() >= mindestGroesse)
                .filter(p -> p.getAlter() >= mindestAlter)
                .findFirst()
                .ifPresentOrElse(System.out::println, () -> System.out.println("Nix gefunden!"));
    }

    static class MyArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(Arguments.of(62, 175), Arguments.of(17, 165), Arguments.of(36, 175), Arguments.of(89, 190));
        }
    }


    @ParameterizedTest
    @ValueSource(strings = {"01.01.2015", "31.03.1958", "16.02.1984"})
    void getAllPersonsOlderThen(@JavaTimeConversionPattern("dd.MM.yyyy") LocalDate maxGeburtsdatum) {
        PersonProvider.getPersons().stream()
                .filter(p -> p.getGeburtstag().isBefore(maxGeburtsdatum))
                .forEach(System.out::println);
    }


    @TestFactory
    Stream<DynamicTest> generateTestsWithSomePrimes() {

        // Generates random positive integers between 0 and 100 until
        // a number evenly divisible by 7 is encountered.
        Iterator<Long> inputGenerator = findAllPrimes(1000).iterator();

        // Generates display names like: input:5, input:37, input:85, etc.
        Function<Long, String> displayNameGenerator = (input) -> "Prime:" + input;

        // Executes tests based on the current input value.
        ThrowingConsumer<Long> testExecutor = (prime) -> Assertions.assertTrue(isPrimeImperative(prime));

        // Returns a stream of dynamic tests.
        return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
    }

    @TestFactory
    Stream<DynamicTest> generateTestsWithRandomAge() {

        // Generates random positive integers between 0 and 100 until
        // a number evenly divisible by 7 is encountered.
        Iterator<Integer> inputGenerator = new RandomAgeIterator(10);

        // Generates display names like: input:5, input:37, input:85, etc.
        Function<Integer, String> displayNameGenerator = (input) -> "Alter:" + input;

        // Executes tests based on the current input value.
        ThrowingConsumer<Integer> testExecutor = (alter) -> PersonProvider.getPersons().stream().filter(p -> p.getAlter() <= alter).forEach(System.out::println);

        // Returns a stream of dynamic tests.
        return DynamicTest.stream(inputGenerator, displayNameGenerator, testExecutor);
    }

    private static class RandomAgeIterator implements Iterator<Integer> {

        final Random random = new Random();
        final int numberOfValues;
        int current = 0;

        public RandomAgeIterator(int numberOfValues) {
            this.numberOfValues = numberOfValues;
        }

        @Override
        public boolean hasNext() {
            return current++ < numberOfValues;
        }

        @Override
        public Integer next() {
            return random.nextInt(100);
        }
    }

}

package de.data_experts.reactive;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

class ReactiveTest {

    @Test
    void testReactiveWithSlowSubscriberAndSmallPool() {
        // wir erzeugen Back Pressure, denn wir überlasten den Subscriber, also muss der Publisher reagieren
        // wir cachen nur 5 elemente
        SubmissionPublisher<Long> publisher = new SubmissionPublisher<>(ForkJoinPool.commonPool(), 5);
        publisher.subscribe(new MySubscriber(true));
        PrimeFinder.findAllPrimes(2, 10000).forEach(prime -> {
            publisher.submit(prime);
            System.out.println("Got prime: " + prime);
        });
        publisher.close();
    }

    @Test
    void testReactiveWithSlowSubscriber() {
        // default cache size wir verwendet: 256 Elemente
        SubmissionPublisher<Long> publisher = new SubmissionPublisher<>();
        publisher.subscribe(new MySubscriber(true));
        PrimeFinder.findAllPrimes(2, 10000).forEach(prime -> {
            publisher.submit(prime);
            System.out.println("Got prime: " + prime);
        });
        publisher.close();
    }

    @Test
    void testReactiveWithSlowPublisher() {
        SubmissionPublisher<Long> publisher = new SubmissionPublisher<>();
        publisher.subscribe(new MySubscriber(false));
        PrimeFinder.findAllPrimesVerySlow(10000).forEach(prime -> {
            publisher.submit(prime);
            System.out.println("Got prime: " + prime);
        });
        publisher.close();
    }

    private static final long START_POINT = 1000000;

    private static final long END_POINT = 1010000;

    @Test
    void testPrimesReactive() {
        final SubmissionPublisher<Long> publisher = new SubmissionPublisher<>();
        publisher.subscribe(new MySubscriber(false));
        PrimeFinder.findAllPrimes(START_POINT, END_POINT).forEach(publisher::submit);
        publisher.close();
    }

    @Test
    void testPrimesReactiveWithSearchingSubscriber() {
        final SubmissionPublisher<Long> publisher = new SubmissionPublisher<>();
        publisher.subscribe(new MyPrimeFinderSubscriber());
        LongStream.range(START_POINT, END_POINT).forEach(publisher::submit);
        publisher.close();
    }

    @Test
    void testPrimesFindClassicWay() {
        for (long number = START_POINT; number <= END_POINT; number++) {
            if (PrimeFinder.isPrimeImperative(number)) {
                System.out.println("PRIME FOUND Classic: " + number);
            }
        }
    }


    @Test
    void testFibonacciReactiveWithCalculatingSubscriber() {
        final SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        publisher.subscribe(new MyFibonacciCalculatingSubscriber());
        IntStream.range(1, 51).forEach(publisher::submit);
        publisher.close();
    }

    @Test
    void testFibonacciCalculateClassicWay() {
        IntStream.range(1, 51).forEach(number -> System.out.println("Fib for " + number + " = " + FibCalculator.fib(number)));
    }

}

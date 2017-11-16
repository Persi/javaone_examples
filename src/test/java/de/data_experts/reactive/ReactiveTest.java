package de.data_experts.reactive;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

class ReactiveTest {

    private static final long START_POINT = 1000000;

    private static final long END_POINT = 1010000;

    @Test
    void testReactiveWithSlowSubscriberAndSmallPool() throws ExecutionException, InterruptedException {
        // wir erzeugen Back Pressure, denn wir überlasten den Subscriber, also muss der Publisher reagieren
        // wir cachen nur 5 elemente
        CompletableFuture future = new CompletableFuture();
        SubmissionPublisher<Long> publisher = new SubmissionPublisher<>(ForkJoinPool.commonPool(), 5);
        publisher.subscribe(new MySubscriber(true, future));
        PrimeFinder.findAllPrimes(2, 100).forEach(prime -> {
            publisher.offer(prime, (subscriber, element) -> {
                System.out.println("Item rejected:" + element);
                // force retry once!
                return true;
            });
        });
        publisher.close();
        future.get();
    }

    @Test
    void testReactiveWithSlowPublisher() throws InterruptedException, ExecutionException {
        SubmissionPublisher<Long> publisher = new SubmissionPublisher<>();
        CompletableFuture future = new CompletableFuture();
        publisher.subscribe(new MySubscriber(false, future));
        PrimeFinder.findAllPrimesVerySlow(10000).forEach(publisher::submit);
        publisher.close();
        future.get();
    }

    @Test
    void testPrimesReactive() throws InterruptedException, ExecutionException {
        final SubmissionPublisher<Long> publisher = new SubmissionPublisher<>();
        CompletableFuture future = new CompletableFuture();
        publisher.subscribe(new MySubscriber(false, future));
        PrimeFinder.findAllPrimes(START_POINT, END_POINT).forEach(publisher::submit);
        publisher.close();
        future.get();
    }

    @Test
    void testPrimesReactiveWithSearchingSubscriber() throws InterruptedException, ExecutionException {
        final SubmissionPublisher<Long> publisher = new SubmissionPublisher<>();
        CompletableFuture future = new CompletableFuture();
        publisher.subscribe(new MyPrimeFinderSubscriber(future));
        LongStream.range(START_POINT, END_POINT).forEach(publisher::submit);
        publisher.close();
        future.get();
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
    void testFibonacciReactiveWithCalculatingSubscriber() throws InterruptedException, ExecutionException {
        final SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();
        CompletableFuture future = new CompletableFuture();
        publisher.subscribe(new MyFibonacciCalculatingSubscriber(future));
        IntStream.range(1, 51).forEach(publisher::submit);
        publisher.close();
        future.get();
    }

    @Test
    void testFibonacciCalculateClassicWay() {
        IntStream.range(1, 51).forEach(number -> System.out.println("Fib for " + number + " = " + FibCalculator.fib(number)));
    }

}

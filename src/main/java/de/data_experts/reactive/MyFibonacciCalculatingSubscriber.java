package de.data_experts.reactive;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

class MyFibonacciCalculatingSubscriber implements Flow.Subscriber<Integer> {
    private Flow.Subscription subscription;

    private static final int NUMBER_OF_REQUEST_ITEMS = 1;

    private final CompletableFuture future;

    MyFibonacciCalculatingSubscriber(CompletableFuture future) {
        this.future = future;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(NUMBER_OF_REQUEST_ITEMS); // Long.MAX_VALUE entspricht unbegrenzt vielen Objekten
    }

    @Override
    public void onNext(Integer item) {
        long fib = FibCalculator.fib(item);
        System.out.println("Fib for " + item + " = " + String.valueOf(fib));
        subscription.request(NUMBER_OF_REQUEST_ITEMS); // Long.MAX_VALUE entspricht unbegrenzt vielen Objekten
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Done");
        future.complete(new Object());
    }
}
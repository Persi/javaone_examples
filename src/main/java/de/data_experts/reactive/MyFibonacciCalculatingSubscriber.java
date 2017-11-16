package de.data_experts.reactive;

import java.util.concurrent.Flow;

class MyFibonacciCalculatingSubscriber implements Flow.Subscriber<Integer> {
    private Flow.Subscription subscription;

    private static final int NUMBER_OF_REQUEST_ITEMS = 1;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(NUMBER_OF_REQUEST_ITEMS); // Long.MAX_VALUE entspricht unbegrenzt vielen Objekten
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("Fib for " + item + " = " + FibCalculator.fib(item));
        subscription.request(NUMBER_OF_REQUEST_ITEMS); // Long.MAX_VALUE entspricht unbegrenzt vielen Objekten
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Done");
    }
}
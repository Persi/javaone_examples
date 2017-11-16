package de.data_experts.reactive;

import java.util.concurrent.Flow;

class MyPrimeFinderSubscriber implements Flow.Subscriber<Long> {
    private Flow.Subscription subscription;

    private static final int NUMBER_OF_REQUEST_ITEMS = 1000;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(NUMBER_OF_REQUEST_ITEMS); // Long.MAX_VALUE entspricht unbegrenzt vielen Objekten
    }

    @Override
    public void onNext(Long item) {
        if (PrimeFinder.isPrimeImperative(item)) {
            System.out.println("PRIME FOUND: " + item);
            // do some crazy stuff here!
        }
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

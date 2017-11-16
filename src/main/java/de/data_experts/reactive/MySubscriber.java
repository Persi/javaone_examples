
package de.data_experts.reactive;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Flow;

class MySubscriber implements Flow.Subscriber<Long> {
    private Flow.Subscription subscription;

    private static final int NUMBER_OF_REQUEST_ITEMS = 100;

    private final boolean verySlow;

    private final CompletableFuture future;

    MySubscriber(boolean verySlow, CompletableFuture future) {
        this.verySlow = verySlow;
        this.future = future;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(NUMBER_OF_REQUEST_ITEMS); // Long.MAX_VALUE entspricht unbegrenzt vielen Objekten
    }

    @Override
    public void onNext(Long item) {
        if (verySlow) {
            // producer much faster then consumer!
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        System.out.println("Received : " + item);
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
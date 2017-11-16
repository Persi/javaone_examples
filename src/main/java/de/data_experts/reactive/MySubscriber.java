
package de.data_experts.reactive;

import java.util.concurrent.Flow;

class MySubscriber implements Flow.Subscriber<Long> {
    private Flow.Subscription subscription;

    private static final int NUMBER_OF_REQUEST_ITEMS = 1000;

    private final boolean verySlow;

    public MySubscriber(boolean verySlow) {
        this.verySlow = verySlow;
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
    }
}
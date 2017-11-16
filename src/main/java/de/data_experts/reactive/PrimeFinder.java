package de.data_experts.reactive;

import java.util.stream.LongStream;

class PrimeFinder {

    // Komplexität O(n)
    static LongStream findAllPrimes(long from, long until) {
        return LongStream.range(from, until)
                .filter(PrimeFinder::isPrimeImperative);
    }

    static LongStream findAllPrimesVerySlow(long until) {
        return LongStream.range(2, until)
                .filter(PrimeFinder::isPrimeDeclarativeVerySlow);
    }

    static boolean isPrimeImperative(long number) {
        assert number > 1;
        for (long divisor = 2; divisor < number; divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    static boolean isPrimeDeclarative(long number) {
        assert number > 1;
        return LongStream.range(2, number).noneMatch(divisor -> number % divisor == 0);
    }

    static boolean isPrimeDeclarativeVerySlow(long number) {
        // producer much slower then consumer!
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
        return PrimeFinder.isPrimeDeclarative(number);
    }
}

package de.data_experts.reactive;

class FibCalculator {

    // Komplexität O(2^n)
    static long fib(int number) {
        assert number >= 1;
        if (number <= 2) {
            return 1L;
        }
        return fib(number - 1) + fib(number - 2);
    }
}

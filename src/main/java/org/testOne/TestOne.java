package org.testOne;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class TestOne {
    public static void main(String[] args) {
        /**
         * 1. Создать список из 1_000 рандомных чисел от 1 до 1_000_000
         */

        List<Integer> numbers = new Random().ints(1000, 0, 1_000_000).
                boxed().collect(Collectors.toList());

        /**
         * 1.1 Найти максимальное
         */

        int max = numbers.stream().max(Integer::compare).get();
        System.out.println("это max - " + max);

        /**
         * 2.2 Все числа, большие, чем 500_000, умножить на 5, отнять от них 150 и просуммировать
         */

        Optional<Integer> sum = numbers.stream().filter(a -> a > 5_00_000).map(number -> number * 5 -150).reduce((x, y)->x+y);
        System.out.println(sum.get());

        /**
         * 2.3 Найти количество чисел, квадрат которых меньше, чем 100_000
         */

        long count = numbers.stream().filter(n -> n*n < 100_000).count();
        System.out.println(count);


    }
}

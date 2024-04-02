
package edu.ucr.cs.cs167.cchua032;

import java.util.function.Function;

/**
 * Main class with specified functionalities.
 */
public class App
{
    public static void main( String[] args )
    {
        if (args.length < 3) {
            System.out.println("Error: At least three parameters expected, from, to, and base.");
            return;
        }
        //Part 6
        int from = Integer.parseInt(args[0]);
        int to = Integer.parseInt(args[1]);
        String baseString = args[2];

        String[] baseParts;
        Function<Integer, Boolean> filter;

        if (baseString.contains(",")) {
            baseParts = baseString.split(",");
            filter = combineWithAnd(createFilters(baseParts));
        } else {
            baseParts = baseString.split("v");
            filter = combineWithOr(createFilters(baseParts));
        }

        /*
        #Part 2
        if (base == 2) {
            printEvenNumbers(from, to);
        } else if (base == 3) {
            printNumbersDivisibleByThree(from, to);
        }
        #Part 3
        Function<Integer, Boolean> filter;
        if (base == 2) {
            filter = new IsEven();
        } else if (base == 3) {
            filter = new IsDivisibleByThree();
        } else {
            System.out.println("Invalid base parameter.");
            return;
        }
        #Part 4
        Function<Integer, Boolean> filter;
        switch (base) {
            case 2:
                filter = new IsEven();
                break;
            case 3:
                filter = new IsDivisibleByThree();
                break;
            case 5:
                filter = new Function<Integer, Boolean>() {
                    @Override
                    public Boolean apply(Integer x) {
                        return x % 5 == 0;
                    }
                };
                break;
            case 10:
                filter = x -> x % 10 == 0;
                break;
            default:
                System.out.println("Invalid base parameter.");
                return;
        }
        #Part 5
        Function<Integer, Boolean> divisibleByBase = x -> x % base == 0;

        printNumbers(from, to, divisibleByBase);

        // Uncomment the following line to test the compilation issue
        // base = 0;
        */
        printNumbers(from, to, filter);
    }

    public static Function<Integer, Boolean>[] createFilters(String[] bases) {
        Function<Integer, Boolean>[] filters = new Function[bases.length];
        for (int i = 0; i < bases.length; i++) {
            int base = Integer.parseInt(bases[i]);
            filters[i] = x -> x % base == 0;
        }
        return filters;
    }

    public static Function<Integer, Boolean> combineWithAnd(Function<Integer, Boolean> ... filters) {
        return x -> {
            for (Function<Integer, Boolean> filter : filters) {
                if (!filter.apply(x)) {
                    return false;
                }
            }
            return true;
        };
    }

    public static Function<Integer, Boolean> combineWithOr(Function<Integer, Boolean> ... filters) {
        return x -> {
            for (Function<Integer, Boolean> filter : filters) {
                if (filter.apply(x)) {
                    return true;
                }
            }
            return false;
        };
    }

    public static void printNumbers(int from, int to, Function<Integer, Boolean> filter) {
        System.out.printf("Printing numbers in the range [%d,%d]\n", from, to);
        for (int i = from; i <= to; i++) {
            if (filter.apply(i)) {
                System.out.println(i);
            }
        }
    }

    // Part 1: Original functions (now commented out)
    /*
    public static void printEvenNumbers(int from, int to) {
        System.out.printf("Printing numbers in the range [%d,%d]\n", from, to);
        for (int i = from; i <= to; i++) {
            if (i % 2 == 0) {
                System.out.println(i);
            }
        }
    }
    public static void printNumbersDivisibleByThree(int from, int to) {
        System.out.printf("Printing numbers in the range [%d,%d]\n", from, to);
        for (int i = from; i <= to; i++) {
            if (i % 3 == 0) {
                System.out.println(i);
            }
        }
    }*/
    static class IsEven implements Function<Integer, Boolean> {
        @Override
        public Boolean apply(Integer x) {
            return x % 2 == 0;
        }
    }

    static class IsDivisibleByThree implements Function<Integer, Boolean> {
        @Override
        public Boolean apply(Integer x) {
            return x % 3 == 0;
        }
    }
}

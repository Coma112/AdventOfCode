package org.example;

import java.util.*;
import java.util.stream.IntStream;

public class Test {
    public static void main(String[] args) {
        Scanner file = new Scanner(Objects.requireNonNull(Test.class.getClassLoader().getResourceAsStream("input.txt")));

        char[] charArray = file.nextLine().toCharArray();
        Map<String, List<String>> nodes = new HashMap<>();

        file.nextLine();

        while (file.hasNextLine()) {
            String string = file.nextLine();
            String[] parts = string.split(" = ");
            String key = parts[0];
            String[] values = parts[1].split("\\(|, |\\)");
            nodes.put(key, Arrays.asList(values[1], values[2]));
        }

        String current = "AAA";
        List<String> allCurrent = new ArrayList<>(nodes.keySet());
        allCurrent.removeIf(e -> !e.endsWith("A"));

        int[] zLoop = new int[allCurrent.size()];
        int steps = performSteps(charArray, nodes, current);
        int allSteps = performAllSteps(zLoop, allCurrent, nodes, charArray);

        System.out.printf("Lépés a ZZZ-hez: %s\nÖsszes lépés a **Z-hez %s\n", steps, lcmArr(zLoop, 0, zLoop.length));
    }

    private static int performSteps(char[] charArray, Map<String, List<String>> nodes, String current) {
        int steps = 0;
        while (!current.equals("ZZZ")) current = nodes
                .get(current)
                .get(charArray[steps++ % charArray.length] == 'L' ? 0 : 1);
        return steps;
    }

    private static int performAllSteps(int[] zLoop, List<String> allCurrent, Map<String, List<String>> nodes, char[] lr) {
        int allSteps = 0;
        while (!IntStream
                .of(zLoop)
                .allMatch(i -> i > 0)) {

            for (int i = 0; i < allCurrent.size(); i++) {
                if (allCurrent
                        .get(i)
                        .endsWith("Z")) zLoop[i] = allSteps;

                allCurrent
                        .set(i, nodes.get(allCurrent.get(i))
                                .get(lr[allSteps % lr.length] == 'L' ? 0 : 1));
            }

            allSteps++;
        }

        return allSteps;
    }

    public static long lcmArr(int[] array, int start, int end) {
        if (end - start == 1) return lcm(array[start], array[end - 1]);
        return lcm(array[start], lcmArr(array, start + 1, end));
    }

    public static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    public static long gcd(long a, long b) {
        if (a < b) return gcd(b, a);
        if (a % b == 0) return b;
        return gcd(b, a % b);
    }
}


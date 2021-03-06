package org.skywind.one;

import org.skywind.util.Numbers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.LongFunction;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Triangle, square, pentagonal, hexagonal, heptagonal, and octagonal numbers are all figurate (polygonal)
 * numbers and are generated by the following formulae:
 * <p>
 * Triangle	 	    P3,n=n(n+1)/2	 	1, 3, 6, 10, 15, ...
 * Square	 	    P4,n=n2	 	        1, 4, 9, 16, 25, ...
 * Pentagonal	 	P5,n=n(3n−1)/2	 	1, 5, 12, 22, 35, ...
 * Hexagonal	 	P6,n=n(2n−1)	 	1, 6, 15, 28, 45, ...
 * Heptagonal	 	P7,n=n(5n−3)/2	 	1, 7, 18, 34, 55, ...
 * Octagonal	 	P8,n=n(3n−2)	 	1, 8, 21, 40, 65, ...
 * <p>
 * The ordered set of three 4-digit numbers: 8128, 2882, 8281, has three interesting properties.
 * <p>
 * The set is cyclic, in that the last two digits of each number is the first two digits of the next number
 * (including the last number with the first).
 * Each polygonal type: triangle (P3,127=8128), square (P4,91=8281), and pentagonal (P5,44=2882),
 * is represented by a different number in the set.
 * This is the only set of 4-digit numbers with this property.
 * Find the sum of the only ordered set of six cyclic 4-digit numbers for which each polygonal type:
 * triangle, square, pentagonal, hexagonal, heptagonal, and octagonal, is represented by a different number in the set.
 * <p>
 * Author: Sergey Saiyan sergey.sova42@gmail.com
 * Created at 04/06/2017.
 */
public class P061 {

    public static void main(String[] args) {
        Map<Long, LongFunction<Long>> functions = Map.of(
                3L, Numbers::getTriangle,
                4L, Numbers::getSquare,
                5L, Numbers::getPentagonal,
                6L, Numbers::getHexagonal,
                7L, Numbers::getHeptagonal,
                8L, Numbers::getOctagonal
        );

        Map<Long, long[]> key2Numbers = functions.keySet().stream().collect(Collectors.toMap(
                k -> k,
                k -> Numbers.generate(functions.get(k)).filter(x -> x > 999 && x % 100 > 9).takeWhile(x -> x < 10000).toArray()
        ));

        for (Map.Entry<Long, long[]> e1 : key2Numbers.entrySet()) {
            for (long v1 : e1.getValue()) {
                for (Map.Entry<Long, long[]> e2 : exclude(key2Numbers.entrySet(), asList(e1))) {
                    for (long v2 : Arrays.stream(e2.getValue()).filter(v2 -> isCyclic(v1, v2)).toArray()) {
                        for (Map.Entry<Long, long[]> e3 : exclude(key2Numbers.entrySet(), asList(e1, e2))) {
                            for (long v3 : Arrays.stream(e3.getValue()).filter(v3 -> isCyclic(v2, v3)).toArray()) {
                                for (Map.Entry<Long, long[]> e4 : exclude(key2Numbers.entrySet(), asList(e1, e2, e3))) {
                                    for (long v4 : Arrays.stream(e4.getValue()).filter(v4 -> isCyclic(v3, v4)).toArray()) {
                                        for (Map.Entry<Long, long[]> e5 : exclude(key2Numbers.entrySet(), asList(e1, e2, e3, e4))) {
                                            for (long v5 : Arrays.stream(e5.getValue()).filter(v5 -> isCyclic(v4, v5)).toArray()) {
                                                for (Map.Entry<Long, long[]> e6 : exclude(key2Numbers.entrySet(), asList(e1, e2, e3, e4, e5))) {
                                                    for (long v6 : Arrays.stream(e6.getValue()).filter(v6 -> isCyclic(v5, v6)).toArray()) {
                                                        if (isCyclic(v6, v1)) {
                                                            System.out.println(v1 + v2 + v3 + v4 + v5 + v6);
                                                            return;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static List<Map.Entry<Long, long[]>> exclude(Set<Map.Entry<Long, long[]>> set, List<Map.Entry<Long, long[]>> toExclude) {
        List<Long> excludes = toExclude.stream().mapToLong(Map.Entry::getKey).boxed().collect(Collectors.toList());
        return set.stream().filter(e -> !excludes.contains(e.getKey())).collect(Collectors.toList());
    }

    private static boolean isCyclic(long n1, long n2) {
        return n1 % 100 == n2 / 100;
    }
}

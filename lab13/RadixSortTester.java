import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RadixSortTester {
    private static String[] stringArray = {"abc", "gg", "sdsd", "sdas", "a", "ab", "s",
            "was", "be", "water", "melon", "interview"};
    private static String[] stringArrayExpected = {"a", "ab", "abc", "be", "gg", "interview", "melon", "s",
            "sdas", "sdsd", "was", "water"};

    @Test
    public void testRadixSortLSD() {
        String[] sortedStringArray = RadixSort.sort(stringArray);
//            for (String s : sortedStringArray) {
//                System.out.println(s);
//            }
        assertEquals(stringArrayExpected, sortedStringArray);
    }

    @Test
    public void testRadixSortMSD() {
        String[] sortedStringArray2 = RadixSort.sortMSD(stringArray);
        for (String s : sortedStringArray2) {
            System.out.println(s);
        }
        assertEquals(stringArrayExpected, sortedStringArray2);
    }
}

import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RadixSortTester {
    private static String[] stringArray = {"abc", "gg", "sdsd", "sdas", "a", "ab", "s",
            "was", "be", "water", "melon", "interview"};
    private static String[] stringArrayExpected = {"a", "ab", "abc", "be", "gg", "interview", "melon", "s",
            "sdas", "sdsd", "was", "water"};

    private static String[] stringArray2 = {"Y", " "};
    private static String[] stringArrayExpected2 = {" ", "Y"};
    @Test
    public void testRadixSortLSD() {
        String[] sortedStringArray = RadixSort.sort(stringArray);
//            for (String s : sortedStringArray) {
//                System.out.println(s);
//            }
        assertEquals(stringArrayExpected, sortedStringArray);
    }

    @Test
    public void testRadixSortLSD2() {
        String[] sortedStringArray2 = RadixSort.sort(stringArray2);
        for (String s : sortedStringArray2) {
            System.out.println(s);
        }
        System.out.println("Y " + "_");
        assertEquals(stringArrayExpected2, sortedStringArray2);
    }

//    @Test
//    public void testRadixSortMSD() {
//        String[] sortedStringArray2 = RadixSort.sortMSD(stringArray);
//        for (String s : sortedStringArray2) {
//            System.out.println(s);
//        }
//        assertEquals(stringArrayExpected, sortedStringArray2);
//    }
}

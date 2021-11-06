import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
//    public static String[] sort(String[] asciis) {
//        int maxLen = 0;
//        for (String s : asciis) {
//            if (s.length() > maxLen) {
//                maxLen = s.length();
//            }
//        }
//
//        String[] res = Arrays.copyOf(asciis, asciis.length);
//        for (int i = maxLen - 1; i >= 0; i--) {
//            sortHelperLSD(res, i);
//        }
//        return res;
//    }
//
//    /**
//     * LSD helper method that performs a destructive counting sort the array of
//     * Strings based off characters at a specific index.
//     * @param asciis Input array of Strings
//     * @param index The position to sort the Strings on.
//     */
//    private static void sortHelperLSD(String[] asciis, int index) {
//        // Optional LSD helper method for required LSD radix sort
//        int R = 256;
//        int[] counts = new int[R + 1];
//        for (String item : asciis) {
//            int c = charAtOrMinChar(index, item);
//            counts[c]++;
//        }
//
//        int[] starts = new int[R + 1];
//        int pos = 0;
//        for (int i = 0; i < R + 1; i++) {
//            starts[i] = pos;
//            pos += counts[i];
//        }
//
//        String[] sorted = new String[asciis.length];
//        for (int i = 0; i < asciis.length; i++) {
//            String item = asciis[i];
//            int c = charAtOrMinChar(index, item);
//            int place = starts[c];
//            sorted[place] = item;
//            starts[c]++;
//        }
//
//        for (int i = 0; i < asciis.length; i++) {
//            asciis[i] = sorted[i];
//        }
//    }
//
//    private static int charAtOrMinChar(int index, String item) {
//        if (index < item.length() && index >= 0) {
//            return item.charAt(index) + 1;
//        } else {
//            return 0;
//        }
//    }
    private static int maxLength = Integer.MIN_VALUE;
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        // make a copy of the original asciis to keep non-destructive
        String[] arrCopy = new String[asciis.length];
        System.arraycopy(asciis, 0, arrCopy, 0, asciis.length);

        // find the max length of all strings in the asciis
        for (String ascii : asciis) {
            maxLength = Math.max(ascii.length(), maxLength);
        }

        for (int d = 0; d < maxLength; d++) {
            sortHelperLSD(arrCopy, maxLength - d - 1);
        }
        return arrCopy;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        // make a new asciis in which pad every shorter strings with '_'
        String[] asciisPadded = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            if (asciis[i].length() < maxLength) {
                asciisPadded[i] = pad(asciis[i], maxLength - asciis[i].length());
            } else {
                asciisPadded[i] = asciis[i];
            }
        }

        // gather all the counts for each value
        int[] counts = new int[257];
        for (String i : asciisPadded) {
            counts[i.charAt(index)]++;
        }

        // calculate start position
        int[] starts = new int[257];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        String[] sorted = new String[asciis.length];
        for (int i = 0; i < asciis.length; i += 1) {
            String item = asciisPadded[i];
            int place = starts[item.charAt(index)];
            sorted[place] = asciis[i];  // remember to use the original asciis without '_'
            starts[item.charAt(index)] += 1;
        }
        System.arraycopy(sorted, 0, asciis, 0, sorted.length);
    }

    /**
     * pad the string s with '_' at the back end,
     * for example pad("2", 2) will return "2__"
     * @param s the string to be padded
     * @param numberOfPad the number of '_'
     * @return padded string
     */
    private static String pad(String s, int numberOfPad) {
        for (int i = 0; i < numberOfPad; i++) {
            s += '_';
        }
        return s;
    }

//    /**
//     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
//     * Destructive method that changes the passed in array, asciis.
//     *
//     * @param asciis String[] to be sorted
//     * @param start int for where to start sorting in this method (includes String at start)
//     * @param end int for where to end sorting in this method (does not include String at end)
//     * @param index the index of the character the method is currently sorting on
//     *
//     **/
//    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
//        // Optional MSD helper method for optional MSD radix sort
//        if (index >= maxLength || start >= end + 1) { return; }
//        // make a new asciis in which pad every shorter strings with '_'
//        String[] asciisPadded = new String[end - start];
//        for (int i = 0; i < asciisPadded.length; i += 1) {
//            if (asciis[start + i].length() < maxLength) {
//                asciisPadded[i] = pad(asciis[start + i], maxLength - asciis[start + i].length());
//            } else {
//                asciisPadded[i] = asciis[start + i];
//            }
//        }
//
//        // gather all the counts for each value
//        int[] counts = new int[256];
//        for (String i : asciisPadded) {
//            counts[i.charAt(index)]++;
//        }
//
//        // calculate start position
//        int[] starts = new int[256];
//        int pos = 0;
//        for (int i = 0; i < starts.length; i += 1) {
//            starts[i] = pos;
//            pos += counts[i];
//        }
//        int[] startsCopy = new int[256];
//        System.arraycopy(starts, 0, startsCopy, 0, starts.length);
//
//        String[] sorted = new String[asciisPadded.length];
//        for (int i = 0; i < asciisPadded.length; i += 1) {
//            String item = asciisPadded[i];
//            int place = starts[item.charAt(index)];
//            sorted[place] = asciis[start + i];
//            starts[item.charAt(index)] += 1;
//        }
//        // copy the sorted part to the original asciis
//        System.arraycopy(sorted, 0, asciis, start, sorted.length);
//
//        // use recursion to sort strings in every sorted subarray of asciis
//        for (int i = 0; i < counts.length; i += 1) {
//            if (counts[i] != 0) {
//                sortHelperMSD(asciis, start + startsCopy[i], start + startsCopy[i] + counts[i], index + 1);
//            }
//        }
//    }
//
//    public static String[] sortMSD(String[] asciis) {
//        for (String ascii : asciis) {
//            maxLength = Math.max(ascii.length(), maxLength);
//        }
//        sortHelperMSD(asciis, 0, asciis.length, 0);
//        return asciis;
//    }
}

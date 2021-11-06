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
        // gather all the counts for each value
        int[] counts = new int[257];
        for (String i : asciis) {
            counts[getCharAsciiFromIndex(i, index)]++;
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
            String item = asciis[i];
            int place = starts[getCharAsciiFromIndex(item, index)];
            sorted[place] = asciis[i];
            starts[getCharAsciiFromIndex(item, index)] += 1;
        }
        System.arraycopy(sorted, 0, asciis, 0, sorted.length);
    }

    // get ascii code of the char in the index position of String s,
    // if index is out of length, return 0 to make sure it's before every char
    private static int getCharAsciiFromIndex(String s, int index) {
        if (index < s.length()) {
            return s.charAt(index) + 1; // original ascii codes start at 0, so add 1
        } else { return 0; }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        if (index >= maxLength || start >= end + 1) { return; }
        String[] asciisSelect = new String[end - start];
        System.arraycopy(asciis, start + 0, asciisSelect, 0, asciisSelect.length);

        // gather all the counts for each value
        int[] counts = new int[257];
        for (String i : asciisSelect) {
            counts[getCharAsciiFromIndex(i, index)]++;
        }

        // calculate start position
        int[] starts = new int[257];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }
        int[] startsCopy = new int[257];
        System.arraycopy(starts, 0, startsCopy, 0, starts.length);

        String[] sorted = new String[asciisSelect.length];
        for (int i = 0; i < asciisSelect.length; i += 1) {
            String item = asciisSelect[i];
            int place = starts[getCharAsciiFromIndex(item, index)];
            sorted[place] = asciisSelect[i];
            starts[getCharAsciiFromIndex(item, index)] += 1;
        }
        // copy the sorted part to the original asciis
        System.arraycopy(sorted, 0, asciis, start, sorted.length);

        // use recursion to sort strings in every sorted subarray of asciis
        for (int i = 0; i < counts.length; i += 1) {
            if (counts[i] != 0) {
                sortHelperMSD(asciis, start + startsCopy[i], start + startsCopy[i] + counts[i], index + 1);
            }
        }
    }

    public static String[] sortMSD(String[] asciis) {
        for (String ascii : asciis) {
            maxLength = Math.max(ascii.length(), maxLength);
        }
        sortHelperMSD(asciis, 0, asciis.length, 0);
        return asciis;
    }
}

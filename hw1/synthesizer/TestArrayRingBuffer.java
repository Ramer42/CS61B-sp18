package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        //ArrayRingBuffer arb = new ArrayRingBuffer(10);
        ArrayRingBuffer x = new ArrayRingBuffer(4);
        x.enqueue(33.1); // 33.1 null null  null
        x.enqueue(44.8); // 33.1 44.8 null  null
        x.enqueue(62.3); // 33.1 44.8 62.3  null
        x.enqueue(-3.4); // 33.1 44.8 62.3 -3.4
//        x.enqueue(-3.4);
        assertEquals(33.1, x.dequeue());     // 44.8 62.3 -3.4  null (returns 33.1)
        assertEquals(44.8, x.dequeue());
        assertEquals(62.3, x.peek());
        x.enqueue(6.6);
    }

    @Test
    public void testIterator() {
        //ArrayRingBuffer arb = new ArrayRingBuffer(10);
        ArrayRingBuffer<Double> x = new ArrayRingBuffer<>(4);
        x.enqueue(7.7); // 7.7 null null  null
        x.enqueue(33.1); // 7.7 33.1 null  null
        x.dequeue();        // null 33.1 null  null
        x.enqueue(44.8); // null 33.1 44.8 null
        x.enqueue(62.3); // null 33.1 44.8 62.3
        x.enqueue(-3.4); // -3.4 33.1 44.8 62.3
        double[] numbers = new double[]{33.1, 44.8, 62.3, -3.4};
        int i = 0;
        for (double item : x) {
            System.out.println(item);
            assertEquals(numbers[i], item, 0.0);
            i++;
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 

import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testArray() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> aq1 = new ArrayDequeSolution<>();
        String operationSequenceString = "\n";

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                sad1.addLast(i);
                aq1.addLast(i);
                operationSequenceString += "addLast(" + i + ")\n";
                assertEquals(operationSequenceString, sad1.get(i), aq1.get(i));
            } else {
                sad1.addFirst(i);
                aq1.addFirst(i);
                operationSequenceString += "addFirst(" + i + ")\n";
                assertEquals(operationSequenceString, sad1.get(0), aq1.get(0));
            }
        }
        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                Integer removeItem1 = sad1.removeLast();
                Integer removeItem2 = aq1.removeLast();
                operationSequenceString += "removeLast():" + "\n";
                assertEquals(operationSequenceString, removeItem2, removeItem1);
            } else {
                Integer removeItem1 = sad1.removeFirst();
                Integer removeItem2 = aq1.removeFirst();
                operationSequenceString += "removeFirst():" + "\n";
                assertEquals(operationSequenceString, removeItem2, removeItem1);
            }
        }
    }
}

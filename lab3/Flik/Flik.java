import static org.junit.Assert.*;

import org.junit.Test;
/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        return a == b;
    }

    @Test
    public void test(){
        assertTrue(isSameNumber(4, 4));
    }
}

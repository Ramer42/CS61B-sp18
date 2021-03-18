import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    @Test
    public void testEqualChars() {
        CharacterComparator offByOne = new OffByN(4);
        assertFalse(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('h', 'l'));
        assertFalse(offByOne.equalChars('&', '%'));
    }
}

/** The OffByN constructor should return an object whose equalChars
 *  method returns true for characters that are off by N  */
public class OffByN implements CharacterComparator{
    private int offN;
    public OffByN(int N) {
        offN = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return x - y == offN || x - y == -offN;
    }
}

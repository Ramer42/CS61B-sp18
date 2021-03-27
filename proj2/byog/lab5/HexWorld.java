package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;

    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Fills a line of tiles of type ts, start with position (x,y), the length is num.
     */
    public static void drawTiles(TETile[][] tiles, int x, int y, int num, TETile ts) {
        for (int i = 0; i < num; i++) {
            tiles[x + i][y] = ts;
        }
    }

    /** add a Hexagon to the TETile, start with position (x,y), the size of Hexagon is s
     * and filled with the ts type of tile. x,y is the bottom left point of the shape, I see
     * the Hexagon as rectangle, so the start point is actually not "in" the Hexagon
     * @param tiles
     * @param x
     * @param y
     * @param s
     * @param ts
     */
    public static void addHexagon(TETile[][] tiles, int x, int y, int s, TETile ts) {
        for (int i = 0; i < s; i++) {
            drawTiles(tiles, x + s - i - 1, y + i, s + 2 * i, ts);
        }
        for (int i = 0; i < s; i++) {
            drawTiles(tiles, x + i, y + s + i, s + 2 * (s - i - 1), ts);
        }
    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.WATER;
            default: return Tileset.WATER;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] HexTiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                HexTiles[x][y] = Tileset.NOTHING;
            }
        }

//        addHexagon(HexTiles, 0, 0, 3, Tileset.WALL);
//        addHexagon(HexTiles, 20, 15, 5, Tileset.WALL);

//        for (int i = 0; i < 3; i++) {
//            addHexagon(HexTiles, 0, 6 + i * 6, 3, randomTile());
//        }
//        for (int i = 0; i < 4; i++) {
//            addHexagon(HexTiles, 5, 3 + i * 6, 3, randomTile());
//        }
//        for (int i = 0; i < 5; i++) {
//            addHexagon(HexTiles, 10, 0 + i * 6, 3, randomTile());
//        }

        for (int i = 0; i < 5; i++) {
            int c = 2 - Math.abs(2 - i);
            for (int j = 0; j < 3 + c; j++) {
                addHexagon(HexTiles, 5 * i, 3 * (2 - c) + j * 6, 3, randomTile());
            }
        }

        ter.renderFrame(HexTiles);
    }
}

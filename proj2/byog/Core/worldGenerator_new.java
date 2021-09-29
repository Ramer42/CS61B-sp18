package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class worldGenerator_new{
    private int WIDTH ;
    private int HEIGHT;
    private long SEED;
    private Random RANDOM;

    public worldGenerator_new(int WIDTH, int HEIGHT, int SEED) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.SEED = SEED;
        this.RANDOM = new Random(SEED);
    }

    public TETile[][] generate() {
        // initialize tiles
        TETile[][] WorldTiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                WorldTiles[x][y] = Tileset.NOTHING;
            }
        }

        // generate rooms
//        int numberOfRooms = 5 + RANDOM.nextInt(15);
        int numberOfRooms = 30;
        System.out.println(numberOfRooms);
        int[][] roomsInfo = new int[numberOfRooms][4];
//        int numberOfPixel = WIDTH * HEIGHT;
        int count = 0;
        for (int i = 0; i < numberOfRooms; i++) {
            int xOfThisRoom = RANDOM.nextInt(WIDTH - 2);
            int yOfThisRoom = RANDOM.nextInt(HEIGHT - 2);
            int widthOfThisRoom = RANDOM.nextInt(12) + 3;
            int heightOfThisRoom = RANDOM.nextInt(12) + 3;
            int[] thisRoomInfo = new int[]{xOfThisRoom, yOfThisRoom, widthOfThisRoom, heightOfThisRoom};
            if (checkFitFrame(thisRoomInfo)) {
                roomsInfo[count] = thisRoomInfo;
                count += 1;
            }
//            if (checkFitFrame(thisRoomInfo)) {
//                roomsInfo[count] = thisRoomInfo;
//                count += 1;
//            }
        }
        System.out.println(count);
        for (int i = 0; i < count; i++) {
            int x = roomsInfo[i][0];
            int y = roomsInfo[i][1];
            int width = roomsInfo[i][2];
            int height = roomsInfo[i][3];
            WorldTiles = drawRectangle(WorldTiles, x, y, width, height, Tileset.WALL);
        }
        return WorldTiles;
    }

    private TETile[][] drawRectangle(TETile[][] tiles, int x, int y, int WIDTH, int HEIGHT, TETile ts) {
        tiles = drawHorizontalTiles(tiles, x, y, WIDTH, ts);
        tiles = drawHorizontalTiles(tiles, x, y + HEIGHT - 1, WIDTH, ts);
        tiles = drawVerticalTiles(tiles, x, y + 1, HEIGHT - 2, ts);
        tiles = drawVerticalTiles(tiles, x + WIDTH - 1, y + 1, HEIGHT - 2, ts);
        return tiles;
    }

    public TETile[][] drawHorizontalTiles(TETile[][] tiles, int x, int y, int num, TETile ts) {
        for (int i = 0; i < num; i++) {
            tiles[x + i][y] = ts;
        }
        return tiles;
    }

    public TETile[][] drawVerticalTiles(TETile[][] tiles, int x, int y, int num, TETile ts) {
        for (int i = 0; i < num; i++) {
            tiles[x][y + i] = ts;
        }
        return tiles;
    }

    private boolean checkFit(int[] RoomInfo1, int[] RoomInfo2) {
        int x1 = RoomInfo1[0];
        int y1 = RoomInfo1[1];
        int width1 = RoomInfo1[2];
        int height1 = RoomInfo1[3];
        int x2 = RoomInfo2[0];
        int y2 = RoomInfo2[1];
        int width2 = RoomInfo2[2];
        int height2 = RoomInfo2[3];
        if ((isInRange(x1, x2, x2 + width2 -1) || isInRange(x1 + width1 - 1, x2, x2 + width2 - 1))
                && (isInRange(y1, y2, y2 + height2 - 1) || isInRange(y1 + height1 - 1, y2, y2 + height2 - 1))) {
            return false;
        }
        return true;
    }

    public boolean isInRange(int x, int a, int b) {
        return x >= a && x <= b;
    }

    private boolean checkFitFrame(int[] RoomInfo) {
        int x = RoomInfo[0];
        int y = RoomInfo[1];
        int width = RoomInfo[2];
        int height = RoomInfo[3];
        return !(x + width > this.WIDTH || y + height > this.HEIGHT);
    }

    private boolean checkFitAll(int[][] roomsInfo, int[] thisRoomInfo, int count) {
        for (int i = 0; i < count; i++) {
            if (!(checkFit(roomsInfo[i], thisRoomInfo) && checkFit(thisRoomInfo, roomsInfo[i]))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int WIDTH = 80;
        int HEIGHT = 40;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        worldGenerator_new wg = new worldGenerator_new(WIDTH, HEIGHT,556);
        TETile[][] Tiles = wg.generate();

        ter.renderFrame(Tiles);
    }
}

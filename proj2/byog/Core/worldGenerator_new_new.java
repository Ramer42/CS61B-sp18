package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class worldGenerator_new_new {
    private int WIDTH ;
    private int HEIGHT;
    private long SEED;
    private Random RANDOM;

    public worldGenerator_new_new (int WIDTH, int HEIGHT, int SEED) {
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
        int numberOfRooms = 50;
        System.out.println(numberOfRooms);
        int[][] roomsInfo = new int[numberOfRooms][4];
//        int numberOfPixel = WIDTH * HEIGHT;
        int count = 0;
        for (int i = 0; i < numberOfRooms; i++) {
            int xOfThisRoom = RANDOM.nextInt(WIDTH - 4) + 2;
            int yOfThisRoom = RANDOM.nextInt(HEIGHT - 4) + 2;
            int widthOfThisRoom = RANDOM.nextInt(10) + 3;
            int heightOfThisRoom = RANDOM.nextInt(10) + 3;
            int[] thisRoomInfo = new int[]{xOfThisRoom, yOfThisRoom, widthOfThisRoom, heightOfThisRoom};
            if ((checkFitAll(roomsInfo, thisRoomInfo, count) || count == 0) && checkFitFrame(thisRoomInfo)) {
                roomsInfo[count] = thisRoomInfo;
                count += 1;
            }
//            if (checkFitFrame(thisRoomInfo)) {
//                roomsInfo[count] = thisRoomInfo;
//                count += 1;
//            }
        }
        System.out.println(count);
        /**
         * Draw every room.
         */
        for (int i = 0; i < count; i++) {
            int x = roomsInfo[i][0];
            int y = roomsInfo[i][1];
            int width = roomsInfo[i][2];
            int height = roomsInfo[i][3];
            WorldTiles = drawRectangle(WorldTiles, x, y, width, height, Tileset.FLOOR);
            /**
             * Connect every room with hallways.
             */
            if (i != 0) {
                WorldTiles = drawHallway(WorldTiles, x, y, roomsInfo[i-1][0], roomsInfo[i-1][1], Tileset.FLOOR);
            }
        }
        /**
         * Enclose all of the rooms and hallways with walls.
         */
        WorldTiles = drawWalls(WorldTiles, Tileset.WALL);
//        if (WorldTiles[0][0] != Tileset.NOTHING) {
//            System.out.println("Nothing");
//        }
//        if (WorldTiles[20][0] != Tileset.NOTHING) {
//            System.out.println("Nothing");
//        }
//        WorldTiles = drawHallway(WorldTiles, 10, 10, 30, 35, Tileset.WALL);
//        WorldTiles = drawHallway(WorldTiles, 34, 10, 10, 35, Tileset.WALL);
//        WorldTiles[30][35] = Tileset.FLOWER;
        return WorldTiles;
    }

    private TETile[][] drawRectangle(TETile[][] tiles, int x, int y, int WIDTH, int HEIGHT, TETile ts) {
        for (int i = 0; i < HEIGHT; i++) {
            tiles = drawHorizontalTiles(tiles, x, y + i, WIDTH, ts);
        }
        return tiles;
    }

    private TETile[][] drawHorizontalTiles(TETile[][] tiles, int x, int y, int num, TETile ts) {
        for (int i = 0; i < num; i++) {
            tiles[x + i][y] = ts;
        }
        return tiles;
    }

    private TETile[][] drawVerticalTiles(TETile[][] tiles, int x, int y, int num, TETile ts) {
        for (int i = 0; i < num; i++) {
            tiles[x][y + i] = ts;
        }
        return tiles;
    }

    private TETile[][] drawHallway(TETile[][] tiles, int xs, int ys, int xe, int ye, TETile ts) {
        if (xs <= xe) {
            tiles = drawHorizontalTiles(tiles, xs, ys, xe-xs+1, ts);
            if (ys <= ye) {
                tiles = drawVerticalTiles(tiles, xe, ys, ye-ys+1, ts);
            }
            else {
                tiles = drawVerticalTiles(tiles, xe, ye, ys-ye+1, ts);
            }
        }
        else {
            tiles = drawHorizontalTiles(tiles, xe, ye, xs-xe+1, ts);
            if (ys <= ye) {
                tiles = drawVerticalTiles(tiles, xs, ys, ye-ys+1, ts);
            }
            else {
                tiles = drawVerticalTiles(tiles, xs, ye, ys-ye+1, ts);
            }
        }

        return tiles;
    }

    private TETile[][] drawWalls(TETile[][] tiles, TETile ts) {
//        TETile[][] tiles_extanded = new TETile[WIDTH + 2][HEIGHT + 2];
//        for (int x = 0; x < WIDTH; x += 1) {
//            for (int y = 0; y < HEIGHT; y += 1) {
//                tiles_extanded[x + 1][y + 1] = tiles[x][y];
//            }
//        }
//        System.out.println(tiles_extanded[0][0]);

        for (int x = 0; x < WIDTH - 2; x += 1) {
            for (int y = 0; y < HEIGHT - 2; y += 1) {
                if (tiles[x + 1][y + 1] != Tileset.NOTHING && tiles[x + 1][y + 1] != null) {
                    continue;
                }
                for1:
                for (int i = 0; i < 3; i += 1) {
                    for (int j = 0; j < 3; j += 1) {
                        if (tiles[x + i][y + j] == Tileset.FLOOR || tiles[x + i][y + j] == Tileset.SAND)  {
                            tiles[x + 1][y + 1] = ts;
                            break for1;
                        }
                    }
                }
            }
        }

//        for (int x = 0; x < WIDTH; x += 1) {
//            for (int y = 0; y < HEIGHT; y += 1) {
//                tiles[x][y] = tiles_extanded[x + 1][y + 1];
//            }
//        }
        return tiles;
    }

//    private boolean checkFit(int[] RoomInfo1, int[] RoomInfo2) {
//        int x1 = RoomInfo1[0];
//        int y1 = RoomInfo1[1];
//        int width1 = RoomInfo1[2];
//        int height1 = RoomInfo1[3];
//        int x2 = RoomInfo2[0];
//        int y2 = RoomInfo2[1];
//        int width2 = RoomInfo2[2];
//        int height2 = RoomInfo2[3];
//        if ((isInRange(x1, x2, x2 + width2 -1) || isInRange(x1 + width1 - 1, x2, x2 + width2 - 1))
//                && (isInRange(y1, y2, y2 + height2 - 1) || isInRange(y1 + height1 - 1, y2, y2 + height2 - 1))) {
//            return false;
//        }
//        return true;
//    }

    private boolean checkFit(int[] RoomInfo1, int[] RoomInfo2) {
        int x1 = RoomInfo1[0];
        int y1 = RoomInfo1[1];
        int width1 = RoomInfo1[2];
        int height1 = RoomInfo1[3];
        int x2 = x1 + width1;
        int y2 = y1 + height1;
        int x3 = RoomInfo2[0];
        int y3 = RoomInfo2[1];
        int width2 = RoomInfo2[2];
        int height2 = RoomInfo2[3];
        int x4 = x3 + width2;
        int y4 = y3 + height2;

        int m_x = Math.max(x1, x3);
        int m_y = Math.max(y1, y3);
        int n_x = Math.min(x2, x4);
        int n_y = Math.min(y2, y4);

        if (m_x < n_x && m_y < n_y) {
            return false;
        }
        return true;
    }

    private boolean isInRange(int x, int a, int b) {
        return x >= a && x <= b;
    }

    private boolean checkFitFrame(int[] RoomInfo) {
        int x = RoomInfo[0];
        int y = RoomInfo[1];
        int width = RoomInfo[2];
        int height = RoomInfo[3];
        return !(x + width > this.WIDTH - 2 || y + height > this.HEIGHT - 2);
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

        worldGenerator_new_new wg = new worldGenerator_new_new(WIDTH, HEIGHT, 222);
        TETile[][] Tiles = wg.generate();

        ter.renderFrame(Tiles);
    }
}

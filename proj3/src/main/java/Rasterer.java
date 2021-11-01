import java.util.HashMap;
import java.util.Map;
import java.awt.Rectangle;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    public static final double ROOT_ULLAT = 37.892195547244356, ROOT_ULLON = -122.2998046875,
            ROOT_LRLAT = 37.82280243352756, ROOT_LRLON = -122.2119140625;
    public static final int TILE_SIZE = 256, MAX_LEVEL = 7;

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        boolean query_success = true;
        double lrlon = params.get("lrlon");
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlat = params.get("lrlat");
        double h = params.get("h");
        double w = params.get("w");
        if (ullon > lrlon || ullat < lrlat) {
            query_success = false;
            results.put("query_success", query_success);
            return results;
        }
        double root_lon_width  = ROOT_LRLON - ROOT_ULLON;
        double root_lat_height  = ROOT_LRLAT - ROOT_ULLAT;
        double LonDPP = (lrlon - ullon) / w;
        int depth = (int)Math.ceil(log(2, root_lon_width / LonDPP) - 8);
        if (depth > MAX_LEVEL) {
            depth = MAX_LEVEL;
        }
        double tile_lon_width = root_lon_width / Math.pow(2, depth);
        double tile_lat_height = root_lat_height / Math.pow(2, depth);
        int raster_ul_x = (int)Math.floor((ullon - ROOT_ULLON) / tile_lon_width);
        int raster_ul_y = (int)Math.floor((ullat - ROOT_ULLAT) / tile_lat_height);
        int raster_lr_x = (int)Math.floor((lrlon - ROOT_ULLON) / tile_lon_width);
        int raster_lr_y = (int)Math.floor((lrlat - ROOT_ULLAT) / tile_lat_height);

        if (checkQueryPosition(raster_ul_x, raster_ul_y, raster_lr_x, raster_lr_y, depth)) {
            Rectangle inter = getIntersection(raster_ul_x, raster_ul_y, raster_lr_x, raster_lr_y, depth);
            raster_ul_x = inter.x;
            raster_ul_y = inter.y;
            raster_lr_x = inter.x + inter.width - 1;
            raster_lr_y = inter.y + inter.height - 1;
        } else {
            query_success = false;
            results.put("query_success", query_success);
            return results;
        }

        double raster_ul_lon = raster_ul_x * tile_lon_width + ROOT_ULLON;
        double raster_ul_lat = raster_ul_y * tile_lat_height + ROOT_ULLAT;
        double raster_lr_lon = (raster_lr_x + 1) * tile_lon_width + ROOT_ULLON;
        double raster_lr_lat = (raster_lr_y + 1) * tile_lat_height + ROOT_ULLAT;
        String[][] render_grid = getRenderGrid(raster_ul_x, raster_ul_y, raster_lr_x, raster_lr_y, depth);
        results.put("depth", depth);
        results.put("render_grid", render_grid);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("query_success", query_success);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
//        System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
//                           + "your browser.");
        return results;
    }

    private double log(int basement, double n){
        return Math.log(n) / Math.log(basement);
    }

    private static String[][] getRenderGrid(int raster_ul_x, int raster_ul_y, int raster_lr_x, int raster_lr_y, int depth) {
        int width = raster_lr_x - raster_ul_x + 1;
        int height = raster_lr_y - raster_ul_y + 1;
        String[][] render_grid = new String[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                String fs = String.format("d%s_x%s_y%s.png", depth, raster_ul_x + j, raster_ul_y + i);
                render_grid[i][j] = fs;
            }
        }
        return render_grid;
    }

    private boolean checkQueryPosition(int raster_ul_x, int raster_ul_y, int raster_lr_x, int raster_lr_y, int depth) {
        int m_x = Math.max(raster_ul_x, 0);
        int m_y = Math.max(raster_ul_y, 0);
        int n_x = Math.min(raster_lr_x, (int)Math.pow(2, depth) - 1);
        int n_y = Math.min(raster_lr_y, (int)Math.pow(2, depth) - 1);

        if (m_x <= n_x && m_y <= n_y) {
            return true;
        }
        return false;
    }

    private Rectangle getIntersection(int raster_ul_x, int raster_ul_y, int raster_lr_x, int raster_lr_y, int depth) {
        Rectangle re1 = new Rectangle(raster_ul_x, raster_ul_y, raster_lr_x - raster_ul_x + 1, raster_lr_y - raster_ul_y + 1);
        Rectangle re2 = new Rectangle(0, 0, (int)Math.pow(2, depth), (int)Math.pow(2, depth));
        return re1.intersection(re2);
    }

    public static void main(String[] args) {
        String[][] render_grid = getRenderGrid(0,0,2,2,2);
        for (String[] sl : render_grid) {
            for (String s : sl) {
                System.out.println(s);
            }
        }
    }

}

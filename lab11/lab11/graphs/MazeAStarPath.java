package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;
import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    private class NodeComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer v1, Integer v2) {
            return distTo[v1] + h(v1) - (distTo[v2] + h(v2));
        }
    }

    private MinPQ<Integer> pq = new MinPQ<>(new NodeComparator());

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        int v;
        pq.insert(s);
        while (true) {
            v = pq.delMin();
            marked[v] = true;
            announce();

            if (v == t) {
                return;
            }
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    pq.insert(w);
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}


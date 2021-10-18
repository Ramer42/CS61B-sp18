package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s = 0;
    private boolean cycleFound = false;
    // there should be no edges connecting the part of the graph that doesn’t contain a cycle,
    // so create a temp list to save the original edgeTo info, when find the cycle, copy the needed
    // edges from edgeToTemp to edgeTo then update the maze.
    public int[] edgeToTemp;
    private Maze maze;


    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        distTo[s] = 0;
        edgeToTemp = new int[maze.V()];
        edgeToTemp[s] = s;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(s);
    }

    // Helper methods go here
    private void dfs(int v) {
        marked[v] = true;
        announce();

        for (int w : maze.adj(v)) {
            // check if there is an adjacent w such that w is already visited
            // and w is not parent of v, then there is a cycle in graph.
            if (marked[w] && w != edgeToTemp[v]) {
                cycleFound = true;
                // copy the edges in the cycle from edgeToTemp to edgeTo then update the maze
                edgeTo[w] = v;
                while (edgeToTemp[v] != w) {
                    edgeTo[v] = edgeToTemp[v];
                    v = edgeToTemp[v];
                }
                edgeTo[v] = edgeToTemp[v];
                announce();
                return;
            }
            if (!marked[w]) {
                edgeToTemp[w] = v;
                distTo[w] = distTo[v] + 1;
                dfs(w);
                if (cycleFound) {
                    return;
                }
            }
        }
    }
}


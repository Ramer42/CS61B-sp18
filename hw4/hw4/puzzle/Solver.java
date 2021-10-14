package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private class NodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode sn1, SearchNode sn2) {
            return sn1.distanceFromInit + sn1.estimatedDistanceToGoalSN
                    - (sn2.distanceFromInit + sn2.estimatedDistanceToGoalSN);
        }
    }

    private MinPQ<SearchNode> pq = new MinPQ<>(new NodeComparator());
    private int moves;
    private SearchNode minSearchNode;

    /** Constructor which solves the puzzle, computing
     * everything necessary for moves() and solution() to
     * not have to solve the problem again. Solves the
     * puzzle using the A* algorithm. Assumes a solution exists. */
    public Solver(WorldState initial) {
        //insert an “initial search node” into the priority queue
        SearchNode initialNode = new SearchNode(initial, 0, null);
        pq.insert(initialNode);
        //Remove the search node with minimum priority
        minSearchNode = pq.delMin();
        while (!minSearchNode.worldState.isGoal()) {
            for (WorldState ws : minSearchNode.worldState.neighbors()) {
                //critical optimization : checks that no enqueued WorldState is its own grandparent
                if (minSearchNode.previousNode == null || !ws.equals(minSearchNode.previousNode.worldState)) {
                    pq.insert(new SearchNode(ws,minSearchNode.distanceFromInit + 1, minSearchNode));
                }
            }
            minSearchNode = pq.delMin();
        }
        moves = minSearchNode.distanceFromInit;
    }

    private class SearchNode{
        WorldState worldState;
        int distanceFromInit;
        SearchNode previousNode;
        //second optimization : save estimatedDistanceToGoal of WorldState in an instance variable
        int estimatedDistanceToGoalSN;

        public SearchNode(WorldState ws, int dfi, SearchNode psn) {
            worldState = ws;
            distanceFromInit = dfi;
            previousNode = psn;
            estimatedDistanceToGoalSN = ws.estimatedDistanceToGoal();
        }
    }

    /** Returns the minimum number of moves to solve the puzzle starting
     * at the initial WorldState. */
    int moves() { return moves; }

    /** Returns a sequence of WorldStates from the initial WorldState
     * to the solution. */
    public Iterable<WorldState> solution() {
        Stack<WorldState> stack = new Stack<>();
        while (minSearchNode != null) {
            stack.push(minSearchNode.worldState);
            minSearchNode = minSearchNode.previousNode;
        }
        return stack;
    }
}

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        PriorityQueue<SearchNode> pq = new PriorityQueue<>(new NodeComparator());
        SearchNode minSearchNode;
        long stNodeId = g.closest(stlon, stlat);
        long destNodeId = g.closest(destlon, destlat);
        Map<Long, Double> distTo = new LinkedHashMap<>();

        //insert an “initial search node” into the priority queue
        SearchNode initialSearchNode = new SearchNode(g, stNodeId, destNodeId, 0, null);
        pq.add(initialSearchNode);
        distTo.put(stNodeId, 0.0);
        //Remove the search node with minimum priority
        minSearchNode = pq.poll();
//        int j = 0;

        while (minSearchNode.nodeId != minSearchNode.destNodeId) {
//            if (j < 20) {
//                System.out.println("This node:" + minSearchNode.nodeId);
//            }
//            j++;
            for (long adjId : g.adjacent(minSearchNode.nodeId)) {
                if (!distTo.containsKey(adjId)) {
                    distTo.put(adjId, 200.0);
                }
                double distToAdj = minSearchNode.distanceFromStart + g.distance(minSearchNode.nodeId, adjId);
                //critical optimization : checks that no enqueued Node is its own grandparent
                if (minSearchNode.previousSearchNode == null ||
                        (adjId != minSearchNode.previousSearchNode.nodeId &&
                        distToAdj < distTo.get(adjId))) {
                    pq.add(new SearchNode(g, adjId, destNodeId, distToAdj, minSearchNode));
                    distTo.put(adjId, distToAdj);
//                    if (j < 20) {
//                        System.out.println("adj:" + adjId);
//                    }
                }
            }
            minSearchNode = pq.poll();
        }

//        Queue<Long> queue = new LinkedList<>();
//        SearchNode thisSearchNode = minSearchNode;
//        while (thisSearchNode != null) {
//            queue.add(thisSearchNode.nodeId);
//            thisSearchNode = thisSearchNode.previousSearchNode;
//        }
        Stack<Long> stack = new Stack<>();
        SearchNode thisSearchNode = minSearchNode;
        while (thisSearchNode != null) {
            stack.push(thisSearchNode.nodeId);
            thisSearchNode = thisSearchNode.previousSearchNode;
        }
        int size = stack.size();
        LinkedList<Long> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(i, stack.pop());
        }
        return list; // FIXME
    }

    private static class SearchNode{
//        GraphDB.Node node;
        long nodeId;
        long destNodeId;
        double distanceFromStart;
        SearchNode previousSearchNode;
        //second optimization : save estimatedDistanceToGoal of WorldState in an instance variable
        double estimatedDistanceToDest;

        public SearchNode(GraphDB g, long nodeId, long destNodeId, double dfs, SearchNode psn) {
            this.nodeId = nodeId;
            this.destNodeId = destNodeId;
            this.distanceFromStart = dfs;
            this.previousSearchNode = psn;
            this.estimatedDistanceToDest = g.distance(nodeId, destNodeId);
        }
    }

    private static class NodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode sn1, SearchNode sn2) {
            int c = 0;
            if (sn1.distanceFromStart + sn1.estimatedDistanceToDest < sn2.distanceFromStart + sn2.estimatedDistanceToDest) {
                c = -1;
            } else if (sn1.distanceFromStart + sn1.estimatedDistanceToDest > sn2.distanceFromStart + sn2.estimatedDistanceToDest) {
                c = 1;
            }
            return c;
//            int c = (int)((sn1.distanceFromStart + sn1.estimatedDistanceToDest
//                    - (sn2.distanceFromStart + sn2.estimatedDistanceToDest)) * Math.pow(10, 8));
//            return c;
        }
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigationDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        List<NavigationDirection> listOfNavigationDirection = new LinkedList<>();
        HashSet<HashSet<Long>> connectionSet;
        NavigationDirection nd = new NavigationDirection();
        long prevNodeId = 0;
        long prevFindWayId = 0;
        String prevFindWayName = "";
        int i = 0;
        String wayName = "";
        boolean findWay = false;
        boolean changeWay = true;
        HashSet<Long> cs;
        double prevBearing = 0.0;
        for (long currentNodeId : route) {
            findWay = false;
            changeWay = true;
            if (i == 0) {
                prevNodeId = currentNodeId;
                i++;
                continue;
            }
            cs = new HashSet<>();
            cs.add(currentNodeId);
            cs.add(prevNodeId);


            if (i > 1) {
                connectionSet = g.getConnections(prevFindWayId);
//                System.out.println("prevFindWayId: " + prevFindWayId);
                for (HashSet<Long> connection : connectionSet) {
                    if (connection.equals(cs)) {
                        wayName = g.getWayName(prevFindWayId);
                        findWay = true;
                        changeWay = false;
                        break;
                    }
                }
            }

            for (long wayId : g.ways()) {
                if (findWay) { break;}
                connectionSet = g.getConnections(wayId);
                for (HashSet<Long> connection : connectionSet) {
                    if (connection.equals(cs)) {
                        wayName = g.getWayName(wayId);
                        findWay = true;
                        if (!(wayName == null && prevFindWayName == null) && Objects.equals(wayName, prevFindWayName)) {
                            changeWay = false;
                        }
//                        if (wayName != null) {
//                            System.out.println(prevFindWayName);
//                            System.out.println(wayName);
//                        }
                        prevFindWayName = wayName;
                        prevFindWayId = wayId;
                        if (changeWay) {
                            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
                            System.out.println("the way has been changed");
//                            System.out.println("newFindWayId: " + wayId);
                        }
                        break;
                    }
                }
            }

            System.out.println("wayName: " + wayName);
            if (i == 1) {
                nd = new NavigationDirection();
                prevFindWayName = wayName;
                if (wayName == null) {
                    nd.way = NavigationDirection.UNKNOWN_ROAD;
                } else {
                    nd.way = wayName;
                }
                nd.direction = 0;
                nd.distance += g.distance(prevNodeId, currentNodeId);
                if (i == route.size() - 1) {
                    listOfNavigationDirection.add(nd);
                }
            } else if (changeWay) {
                listOfNavigationDirection.add(nd);
                nd = new NavigationDirection();
                double bearing = prevBearing - g.bearing(prevNodeId, currentNodeId);
                nd.direction = chooseDirection(-bearing);
                nd.distance = g.distance(prevNodeId, currentNodeId);
                if (wayName == null) {
//                    nd.way = NavigationDirection.UNKNOWN_ROAD;
                    nd.way = "";
                } else {
                    nd.way = wayName;
                }
                if (i == route.size() - 1) {
                    listOfNavigationDirection.add(nd);
                }
            } else {
                nd.distance += g.distance(prevNodeId, currentNodeId);
                if (i == route.size() - 1) {
                    listOfNavigationDirection.add(nd);
                }
            }

            prevBearing = g.bearing(prevNodeId, currentNodeId);
            prevNodeId = currentNodeId;
            i++;
        }
        return listOfNavigationDirection; // FIXME
    }

    private static int chooseDirection(double bearing) {
        double absBearing = Math.abs(bearing);
        if (absBearing > 180) {
            absBearing = 360 - absBearing;
            bearing *= -1;
        }
        if (absBearing <= 15) {
            return 1;
        } else if (absBearing <= 30) {
            if (bearing < 0) {
                return 2;
            } else {
                return 3;
            }
        } else if (absBearing <= 100) {
            if (bearing < 0) {
                return 5;
            } else {
                return 4;
            }
        } else if (bearing < -100) {
            return 6;
        } else {
            return 7;
        }
    }

    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}

package comp5017_CW2;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class NetworkUtils implements INetworkUtils{
    /**
     * @param network -- the network
     * @param index   -- index of start station
     * @return list of station indexes visited in breadth-first search from index
     * @pre index is a valid station index
     */
    @Override
    public ListInt breadthFirstSearch(Network network, int index) {
        int size = network.getNumStations();
        QueueInt q = new QueueInt(size);
        ListInt ls = new ListInt(size);
        q.addToBack(index);

        while (q.getSize() > 0) {
            int s = q.removeFromFront();
            if (!ls.contains(s)) {
                ls.append(s);
                for (int i = 0; i != size; i++) {
                    if (network.getDistance(i,s) != network.NO_LINK && !ls.contains(i)) {
                        q.addToBack(i);
                    }
                }
            }
        }
        return ls;
    }

    /**
     * @param network -- the network
     * @param index   -- index of start station
     * @return list of station indexes visited in depth-first search from index
     * @pre index is a valid station index
     */
    @Override
    public ListInt depthFirstSearch(Network network, int index) {
        int size = network.getNumStations();
        StackInt s = new StackInt(size);
        ListInt ls = new ListInt(size);
        s.push(index);

        while (s.getSize() > 0) {
            int t = s.pop();
            if (!ls.contains(t)) {
                ls.append(t);
                for (int i = 0; i != size; i++) {
                    if (network.getDistance(i,t) != network.NO_LINK && !ls.contains(i)) {
                        s.push(i);
                    }
                }
            }
        }
        return ls;
    }

    /**
     * @param network         -- the network
     * @param startIndex -- index of start station
     * @param endIndex   -- index of start station
     * @return list of station indexes in the shortest path between startIndex and endIndex
     * @pre startIndex and endIndex are valid station indexes
     * @pre startIndex != endIndex
     * @post number of iterations displayed (logging)
     */
    @Override
    public ListInt dijkstraPath(Network network, int startIndex, int endIndex) {
        class Node {
            double g;
            Node back;
            public Node() {
                g = network.NO_LINK;
                back = null;
            }
        }
        int size = network.getNumStations();
        Node[] nodes = new Node[size];
        SetInt closed = new SetInt(size);
        SetInt open = new SetInt(size);
        ListInt ls = new ListInt(size);
        double value = Double.MIN_VALUE;
        int index = -1;

        for (int i = 0; i < size; i++) {
            open.include(i);
            nodes[i] = new Node();
        }
        nodes[startIndex].g = 0;
        while (!closed.contains(endIndex)) {
            for (int i : open.set) {
                if (nodes[i].g > value) {
                    value = nodes[i].g;
                    index = i;
                }
            }
            open.exclude(index);
            closed.include(index);

            if (index != endIndex) {
                for (int n = 0; n != size; n++) {
                    double distance = network.getDistance(n, index);
                    if (distance != network.NO_LINK && open.contains(n)) {
                        double sum = nodes[index].g + distance;
                        if (sum < nodes[n].g) {
                            nodes[n].g = sum;
                            nodes[n].back = nodes[index];
                        }
                    }
                }
            }
        }
        int i = endIndex;
        while (i != startIndex) {
            ls.append(i);
            i = Arrays.binarySearch(nodes, nodes[i].back);
        }
        ls.append(startIndex);

        return ls;
    }

    /**
     * @param network    -- the network
     * @param startIndex -- index of start station
     * @param endIndex   -- index of start station
     * @return list of station indexes in the shortest path between startIndex and endIndex
     * @pre startIndex and endIndex are valid station indexes
     * @pre startIndex != endIndex
     * @post number of iterations displayed (logging)
     */
    @Override
    public ListInt aStarPath(Network network, int startIndex, int endIndex) {
        return null;
    }
}

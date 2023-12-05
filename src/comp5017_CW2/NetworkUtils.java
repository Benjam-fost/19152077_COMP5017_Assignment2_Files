package comp5017_CW2;

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
        assert network != null && startIndex != endIndex : "Invalid argument";
        class Node {
            double g;
            int back;
            public Node() {
                g = network.NO_LINK;
                back = -1;
            }
        }
        int size = network.getNumStations();
        Node[] nodes = new Node[size];
        SetInt closed = new SetInt(size);
        SetInt open = new SetInt(size);
        ListInt ls = new ListInt(size);
        int whileCount = 0;

        for (int i = 0; i < size; i++) {
            open.include(i);
            nodes[i] = new Node();
        }
        nodes[startIndex].g = 0;
        while (!closed.contains(endIndex)) {
            whileCount++;
            int index = -1;
            double x = network.NO_LINK;
            int count = -1;
            for (int i : open.set) {
                count++;
                if (count < open.getSize() && nodes[i].g != network.NO_LINK && nodes[i].g < x) {
                    x = nodes[i].g;
                    index = i;
                }
            }
            assert index != -1 : "Lowest g-value node not found";
            open.exclude(index);
            closed.include(index);

            if (index != endIndex) {
                for (int n = 0; n != size; n++) {
                    double distance = network.getDistance(n, index);
                    if (distance != network.NO_LINK && open.contains(n)) {
                        double sum = nodes[index].g + distance;
                        if (sum < nodes[n].g) {
                            nodes[n].g = sum;
                            nodes[n].back = index;
                        }
                    }
                }
            }
        }
        int i = endIndex, j = 0;
        int[] list = new int[size];
        while (i != startIndex) {
            list[j] = i;
            j++;
            i = nodes[i].back;
        }
        list[j] = startIndex;

        for (int k = j; k >= 0; k--) {
            ls.append(list[k]);
        }
        System.out.println("While loop iterations: " + whileCount);
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
        class Node {
            double g;
            double f;
            int back;
            public Node() {
                g = network.NO_LINK;
                back = -1;
            }
        }
        int size = network.getNumStations();
        Node[] nodes = new Node[size];
        SetInt closed = new SetInt(size);
        SetInt open = new SetInt(size);
        ListInt ls = new ListInt(size);
        int whileCount = 0;

        for (int i = 0; i < size; i++) {
            open.include(i);
            nodes[i] = new Node();
            double x0 = network.getStationInfo(i).getxPos();
            double y0 = network.getStationInfo(i).getyPos();
            double x1 = network.getStationInfo(endIndex).getxPos();
            double y1 = network.getStationInfo(endIndex).getyPos();
            nodes[i].f = network.pythagoras(x0,y0,x1,y1);
        }
        nodes[startIndex].g = 0;
        while (!closed.contains(endIndex)) {
            whileCount++;
            int index = -1;
            double x = network.NO_LINK;
            int count = -1;
            for (int i : open.set) {
                count++;
                if (count < open.getSize() && nodes[i].g != network.NO_LINK && nodes[i].f < x) {
                    x = nodes[i].f;
                    index = i;
                }
            }
            assert index != -1 : "Lowest g-value node not found";
            open.exclude(index);
            closed.include(index);

            if (index != endIndex) {
                for (int n = 0; n != size; n++) {
                    double distance = network.getDistance(n, index);
                    if (distance != network.NO_LINK && open.contains(n)) {
                        double sum = nodes[index].g + distance;
                        if (sum < nodes[n].g) {
                            nodes[n].g = sum;
                            nodes[n].f += sum;
                            nodes[n].back = index;
                        }
                    }
                }
            }
        }
        int i = endIndex, j = 0;
        int[] list = new int[size];
        while (i != startIndex) {
            list[j] = i;
            j++;
            i = nodes[i].back;
        }
        list[j] = startIndex;

        for (int k = j; k >= 0; k--) {
            ls.append(list[k]);
        }
        System.out.println("While loop iterations: " + whileCount);
        return ls;
    }
}

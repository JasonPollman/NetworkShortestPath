// Jason James Pollman
// ITCS-2215-001
// Programming Project #3
// Version 3

import java.util.*;

public class EdgeComparator implements Comparator<Edge> {
    @Override
    public int compare(Edge first, Edge second) {
        return first.dest.name.compareTo(second.dest.name);
    }
}
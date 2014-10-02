// Jason James Pollman
// ITCS-2215-001
// Programming Project #3
// Version 3

/**
 * Represents a directed edge in a directed graph.
 */
public class Edge {

	public static final int INFINITY = Integer.MAX_VALUE;

	public Vertex dest;
	public float dist;
	public boolean status; // true = "up", false = "down."

	public Edge(Vertex dest, float dist) {
		this.dest = dest;
		this.dist = dist;
		this.status = true;
	}
}
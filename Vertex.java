// Jason James Pollman
// ITCS-2215-001
// Programming Project #3
// Version 3

import java.util.*;

/**
 * Represents a vertex in a directed graph.
 */
class Vertex implements Comparable<Vertex> {

	public static final int INFINITY = Integer.MAX_VALUE;

  public String name;          // Vertex identifier.
  public boolean visited;      // Has vertex been visited?
  public List<Edge> adj;       // Adjacent vertice edges.
  public Vertex prev;          // Previous vertex on shortest path.
  public float dist;           // Path distance.
  public boolean status;       // true = "up", false = "down."

  /**
   * Constructor
   * Sets the vertex's name and instantiates as new Linked List for it's adjacent vertices.
   */
  public Vertex(String name) {
    this.name = name;
    adj = new LinkedList<Edge>();
    reset();
  }

  /**
   * Resets the vertex
   */
  public void reset() {
    dist = Vertex.INFINITY;
    visited = false;
    prev = null;
    status = true;
  }

  @Override
  public int compareTo(Vertex other) {
    if(dist < other.dist) {
      return -1;
    }

    if(dist > other.dist) {
      return 1;
    }

    return 0;
  }
}
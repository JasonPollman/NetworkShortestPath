// Jason James Pollman
// ITCS-2215-001
// Programming Project #3
// Version 3

import java.util.*;

/**
 * Represents a directed graph.
 */
public class Graph {
	
  // The actual iterable that holds the vertex elements.
  public Map<String,Vertex> vertexMap = new TreeMap<String,Vertex>();


  /**
   * Adds a new edge to the graph.
   */
  public int addEdge(String sourceName, String destName, float weight) {

    // Get (or create, if null) the vertex with name sourceName
    Vertex v = getVertex(sourceName);

    // Get (or create, if null) the vertex with name destName
    Vertex w = getVertex(destName);

    Edge e = getEdge(sourceName, destName);
    if(getEdge(sourceName, destName) == null) {
      // Create the edge from sourceName => destName, with distance weight.
      e = new Edge(w, weight);

      // Add the edge to the source's adjacency list.
      v.adj.add(e);

      // Signal that we created a *new* edge.
      return 1;
    }
    else {
      e.dist = weight;

      // Signal that we just modified a *current* edge.
      return 0;
    }

  } // End of addEdge method.


  /**
   * Deletes an edge from the graph.
   */
  public boolean deleteEdge(String sourceName, String destName) {

      Vertex v = getVertex(sourceName);

      boolean flagRemoved = false;
      for(Edge e : v.adj) {
        if(e.dest.name.equals(destName)) {
          v.adj.remove(e);
          flagRemoved = true;
          break;
        }
      }

      return (flagRemoved) ? true : false;

  } // End of deleteEdge method.


  /**
   * Toggles as vertex's up/down status.
   */
  public boolean vertexToggleStatus(String vertex) {

      Vertex v = getVertex(vertex, true);
      
      if(v == null) { return false; }

      v.status = !v.status;
      return true;

  } // End of vertexToggleStatus method.


  /**
   * Toggles an edge's up/down status.
   */
  public boolean edgeToggleStatus(String source, String dest) {

      Edge e = getEdge(source, dest);
      if(e == null) { 
        return false;
      }
      else {
        e.status = !e.status;
        return true;
      }

  } // End of edgeToggleStatus method.


  /**
   * Toggles an edge's up/down status.
   */
  public Edge getEdge(String source, String dest) {

      Vertex v = getVertex(source, true);
      Vertex w = getVertex(source, true);

      if(v == null || w == null) { return null; }

      for(Edge e : v.adj) {
        if(e.dest.name.equals(dest)) {
          return e;
        }
      }

      return null;

  } // End of getEdge method.


  /**
   * If vertexName is not present, add it to the vertexMap.
   * In either case, return the vertex object.
   */
  public Vertex getVertex(String vertexName) {
    // Attempt to grab the vertex from the list of vertices.
    vertexName = vertexName.toLowerCase();
    Vertex v = vertexMap.get(vertexName);

    // Create a new vertex, if vertex vertexName does not exist.
    if(v == null) {
      
      v = new Vertex(vertexName);
      vertexMap.put(vertexName, v);

      vertexName = vertexName.substring(0, 1).toUpperCase() + vertexName.substring(1);
      System.out.println("Vertex " + vertexName + " has been created.");
    }

    return v;

  } // End of getVertex method.


  /**
   * Override, that does not create a new vertex if vertex is not found.
   */
  public Vertex getVertex(String vertexName, boolean noCreation) {

    if(noCreation == false) {
      Vertex v = getVertex(vertexName);
      return v;
    }

    // Attempt to grab the vertex from the list of vertices.
    vertexName = vertexName.toLowerCase();
    Vertex v = vertexMap.get(vertexName);

    return v;

  } // End of getVertex method.


  /**
   * Initializes the vertex output info prior to running
   * any shortest path algorithm.
   */
  private void clearAll() {
    for(Vertex v : vertexMap.values())
      v.reset();

  } // End of clearAll method.


  private void printPath(Vertex dest) {

    if(dest.prev != null) {
      printPath(dest.prev);
      System.out.print(" to ");
    }

    System.out.print(dest.name);
  }

  /**
   * Prints the vertices and their respective edges.
   */
  public void printGraph() {

    if(vertexMap == null || vertexMap.size() <= 0) {
      System.out.println("The network is currently empty, there are no vertices or edges.");
    }
    else {
      for(Vertex v : vertexMap.values()) {
        String vertexName = v.name.substring(0, 1).toUpperCase() + v.name.substring(1);
        System.out.print(vertexName);

        // Indicate that the vertex is down.
        if(v.status == false) {
          System.out.print(" DOWN");
        }

        Collections.sort(v.adj, new EdgeComparator());

        for(Edge e : v.adj) {
          String edgeName = e.dest.name.substring(0, 1).toUpperCase() + e.dest.name.substring(1);
          System.out.print("\n  " + edgeName + " " + e.dist);
          if(!e.status) { System.out.print(" DOWN"); }
        }
        System.out.print("\n");
      }
    }

  } // End of printGraph method.


  /**
   * Implementation of Dijkstra's algoright using Priority Queue (Min Binary Heap).
   */
  public List<String> dijkstra(Vertex source, Vertex dest) {

    PriorityQueue<Vertex> q = new PriorityQueue<Vertex>();
    List<String> paths = new ArrayList<String>();

    for(Vertex v : vertexMap.values()) {
      v.dist = Edge.INFINITY;
      v.visited = false;
      v.prev = null;
    }

    source.dist = 0;
    q.add(source);

    while(!q.isEmpty()) {

      // Find vertex in q with shortest distance.
      Vertex u = source;
      float min = (float) Vertex.INFINITY;
      for(Vertex s : q) {
        if(s.dist <= min && s.status == true && s.visited == false) { // Find the vertex in q with the minimum distance from u and that is "up."
          min = s.dist;
          u = s;
        }
      }

      if(u.name.equals(dest.name)) { break; }

      q.remove();
      u.visited = true;

      for(Edge e : u.adj) {
        if(e.status != false) {
          float alt = u.dist + e.dist;

          if(alt < e.dest.dist && e.dest.visited == false) {
            e.dest.dist = alt;
            e.dest.prev = u;
            q.add(e.dest);
          }
        }
      }
    } // End of while loop


    // Prepend each vertex's name to the path list.
    Vertex k = dest;
    while(k.prev != null) {
      paths.add(0, k.name);
      k = k.prev;
    }

    // Prepend source to path list.
    paths.add(0, k.name);

    Vertex x = getVertex(dest.name, true);

    // Tack on the distance.
    paths.add(Float.toString(x.dist));

    return paths;

  } // End of dijkstra method.

  /**
   * This series of methods uses recursion to print the vertices reachable by each independent vertex.
   * It has a asymptotic time complexity of O(|V|+|E|).
   * That is, because the first method iterates through the total number of vertices (|V|) in the foreach loop,
   * then the second overwritten method recursively calls each vertex's adjacent vertices.
   * However, as a vertex's adjacent vertices are traversed, they are added to a list, preventing them from
   * being traversed more than once by the vertex in the original (non-overridden) method.
   */
  public void reachable() {

    int verticesDown = 0;

    for(Vertex v : vertexMap.values()) {
      if(v.status != false) {
        String vertexName = v.name.substring(0, 1).toUpperCase() + v.name.substring(1);
        System.out.println(vertexName);
        
        ArrayList<String> list = reachable(v, new ArrayList<String>(), new ArrayList<String>());

        // Sort the list so it's in alphabetical order.
        Collections.sort(list);

        // Print the "reachable" vertices.
        if(!list.isEmpty()) {
          for(String s : list) {
            System.out.println("  " + s);
          }
        }
        else {
          System.out.println("  No reachable vertices!");
        }
      }
      else {
        verticesDown++;
      }
    }

    if(verticesDown == vertexMap.size()) {
      System.out.print("All vertices are down, therefore nothing is reachable.");
    }

  } // End of reachable method.

  public ArrayList<String> reachable(Vertex v, ArrayList<String> visited, ArrayList<String> list) {

    visited.add(v.name);
    for(Edge e : v.adj) {
      if(!(visited.contains(e.dest.name) || e.dest.status == false) && e.status != false) {
        String destName = e.dest.name.substring(0, 1).toUpperCase() + e.dest.name.substring(1);
        list.add(destName);
        reachable(e.dest, visited, list);
      }
    }

    return list;
  
  } // End of reachable method.

} // End of Graph class.


/**
 * Simple exception handler for the Graph class.
 */
class GraphException extends RuntimeException
{
  public GraphException(String msg) {
    super(msg);
  }
}
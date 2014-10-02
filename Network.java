// Jason James Pollman
// ITCS-2215-001
// Programming Project #3
// Version 3

import java.io.*;
import java.util.*;
import java.text.*;

public class Network {

	private Graph g = new Graph();
	private String networkFileLocation;

	public void reset(String networkFileLocation) throws IOException {
		this.networkFileLocation = networkFileLocation;
		
		File file = new File(networkFileLocation);
		if(!file.exists()) {
			System.out.println("The specified network file location does not exist!");
			System.out.println("Starting with an empty network. Currently there are no nodes or paths.");
		}
		else {
			g = new Graph();
			Scanner input = new Scanner(file);
			
			while(input.hasNext()) {
				String line = input.nextLine();

				String[] tokens = line.split(" ");

				if(tokens.length < 3) {
					System.out.println("Skipping ill formatted line.");
				}
				else {

					String source = tokens[0];
					String dest		= tokens[1];
					float weight	= Float.parseFloat(tokens[2]);

					// Graph should be undirected.
					g.addEdge(source, dest, weight);
					g.addEdge(dest, source, weight);
				}

			} // End of while loop.

		} // End of if statement.

	}

	public Network(String networkFileLocation) throws IOException {
		reset(networkFileLocation);
	
	} // End of constructor.

	public Network() {
		System.out.println("Starting with an empty network. Currently there are no nodes or paths, \nas a network file was not defined.");

	} // End of constructor.

	public void addEdge(String tailVertex, String headVertex, float transitTime) {

		if(transitTime <= 0) {
			// Dijkstra's only works on graphs with non-negative edge weights.
      System.out.println("Transit time between network nodes must be greator than zero!\nNo edge created.");
      return;
    }

		int status = g.addEdge(tailVertex, headVertex, transitTime);

		tailVertex = tailVertex.substring(0, 1).toUpperCase() + tailVertex.substring(1);
		headVertex = headVertex.substring(0, 1).toUpperCase() + headVertex.substring(1);

		if(status == 1) {
			System.out.println("Edge from " + tailVertex + " to " + headVertex + " successfully added.");
		}
		else {
			System.out.println("Edge from " + tailVertex + " to " + headVertex + " successfully updated.");
		}
	
	} // End of addEdge method.


	public void deleteEdge(String tailVertex, String headVertex) { 
		boolean removed = g.deleteEdge(tailVertex, headVertex);

		tailVertex = tailVertex.substring(0, 1).toUpperCase() + tailVertex.substring(1);
		headVertex = headVertex.substring(0, 1).toUpperCase() + headVertex.substring(1);

		if(removed) {
			System.out.println("Edge from " + tailVertex + " to " + headVertex + " has been successfully deleted.");
		}
		else {
			System.out.println("Edge from " + tailVertex + " to " + headVertex + " could not be deleted, because it does not exist.");
		}
	
	} // End of deleteEdge method.


	public void addVertex(String vertex) {
		if(g.getVertex(vertex, true) != null) {
			vertex = vertex.substring(0, 1).toUpperCase() + vertex.substring(1);
			System.out.println("Vertex " + vertex + " already exists and cannot be overwritten.");
		}
		else {
			g.getVertex(vertex);
			vertex = vertex.substring(0, 1).toUpperCase() + vertex.substring(1);
		}
	} // End of addVertex method.


	public void deleteVertex(String vertex) {

		for(Iterator<String> i = g.vertexMap.keySet().iterator(); i.hasNext();) {
			String v = i.next();
			if(g.vertexMap.get(v).name.equals(vertex)) {

				for(Vertex w : g.vertexMap.values()) {
					for(Edge e : w.adj) {
						if(e.dest.name.equals(vertex)) {
							g.getVertex(w.name, true).adj.remove(e);
							break;
						}
					}
				}

				g.vertexMap.remove(v);

				vertex = vertex.substring(0, 1).toUpperCase() + vertex.substring(1);
				System.out.println("Vertex " + vertex + " and all edges to it have been deleted.");
				return;
			}
		}

		System.out.println("Vertex " + vertex + " does not exist, and therefore cannot be deleted.");

	} // End of deleteVertex method.


	public void vertexDown(String vertex) {

		if(g.getVertex(vertex, true) != null && g.getVertex(vertex, true).status == false) {
			System.out.println("Vertex " + vertex + " is already disabled.");
			return;
		}

		boolean statusChanged = g.vertexToggleStatus(vertex);
		vertex = vertex.substring(0, 1).toUpperCase() + vertex.substring(1);

		if(statusChanged) {
			System.out.println("Vertex " + vertex + " has been disabled.");
		}
		else {
			System.out.println("Vertex " + vertex + " does not exist.");
		}

	} // End of vertexDown method.

	public void vertexUp(String vertex) {

		if(g.getVertex(vertex, true) != null && g.getVertex(vertex, true).status == true) {
			System.out.println("Vertex " + vertex + " is already enabled.");
			return;
		}

		boolean statusChanged = g.vertexToggleStatus(vertex);
		vertex = vertex.substring(0, 1).toUpperCase() + vertex.substring(1);

		if(statusChanged) {
			System.out.println("Vertex " + vertex + " has been enabled.");
		}
		else {
			System.out.println("Vertex " + vertex + " does not exist.");
		}
	
	} // End of vertexUp method.

	public void edgeDown(String tailVertex, String headVertex) {

		Edge e = g.getEdge(tailVertex, headVertex);

		boolean statusChanged = g.edgeToggleStatus(tailVertex, headVertex);

		tailVertex = tailVertex.substring(0, 1).toUpperCase() + tailVertex.substring(1);
		headVertex = headVertex.substring(0, 1).toUpperCase() + headVertex.substring(1);

		if(e != null && e.status == true) {
			System.out.println("The edge from " + tailVertex + " to " + headVertex + " is already disabled.");
		}
		else if(!statusChanged) {
			System.out.println("The edge from " + tailVertex + " to " + headVertex + " does not exist.");
		}
		else {
			System.out.println("The edge from " + tailVertex + " to " + headVertex + " has been disabled.");
		}

	} // End of edgeDown method.

	public void edgeUp(String tailVertex, String headVertex) {

		Edge e = g.getEdge(tailVertex, headVertex);

		boolean statusChanged = g.edgeToggleStatus(tailVertex, headVertex);

		tailVertex = tailVertex.substring(0, 1).toUpperCase() + tailVertex.substring(1);
		headVertex = headVertex.substring(0, 1).toUpperCase() + headVertex.substring(1);

		if(e != null && e.status == false) {
			System.out.println("The edge from " + tailVertex + " to " + headVertex + " is already enabled.");
		}
		else if(!statusChanged) {
			System.out.println("The edge from " + tailVertex + " to " + headVertex + " does not exist.");
		}
		else {
			System.out.println("The edge from " + tailVertex + " to " + headVertex + " has been enabled.");
		}

	} // End of edgeDown method.

	public void path(String fromVertex, String toVertex) {
		Vertex v = g.getVertex(fromVertex, true);
    Vertex w = g.getVertex(toVertex, true);

    if(v == null) {
      // Signal that one of the vertices does not exist.
      fromVertex = fromVertex.substring(0, 1).toUpperCase() + fromVertex.substring(1);
      System.out.println("Vertex " + fromVertex + " does not exist!\nNo path computed.");
      return;
    }

    if(w == null) {
      // Signal that one of the vertices does not exist.
      toVertex = toVertex.substring(0, 1).toUpperCase() + toVertex.substring(1);
      System.out.println("Vertex " + toVertex + " does not exist!\nNo path computed.");
      return;
    }

    if(v.status == false) {
    	v.name = v.name .substring(0, 1).toUpperCase() + v.name.substring(1);
    	System.out.println("Vertex " + v.name + " is down and therefore you cannot traverse from it!");
    	return;
    }

    if(w.status == false) {
    	w.name = w.name .substring(0, 1).toUpperCase() + w.name.substring(1);
    	System.out.println("Vertex " + w.name + " is down and is unreachable!");
    	return;
    }

    List<String> resultsList = g.dijkstra(v, w);
    String[] results = new String[resultsList.size()];
    resultsList.toArray(results);

    if(Float.parseFloat(results[results.length-1]) == (float) Vertex.INFINITY) {
    	toVertex = toVertex.substring(0, 1).toUpperCase() + toVertex.substring(1);
    	fromVertex = fromVertex.substring(0, 1).toUpperCase() + fromVertex.substring(1);
    	System.out.println("The path from " + fromVertex + " to " + toVertex + " is unreachable (most likely an edge is down).");
    	return;
    }

    // If no results, then the path is not reachable.
    if(results == null) {
    	System.out.println("Path from vertex " + fromVertex + " to vertex " + toVertex + " is unreachable.");
    	return;
    }
		
		for(int i = 0; i < results.length; i++) {
			results[i] = results[i].substring(0, 1).toUpperCase() + results[i].substring(1);
			if(i != results.length - 1) {
				if(i == 0) {
					System.out.print("(Source) ");
				}
				
				if(i == results.length - 2) {
					System.out.print("(Destination) ");
				}
				
				System.out.print(results[i] + " ");

				if(i != results.length - 2) {
					System.out.print(" => ");
				}
			}
			else {
				DecimalFormat formatter = new DecimalFormat( "#,###,###,##0.00" );
				String totalTime = formatter.format(Float.parseFloat(results[i]));
				System.out.print("- " + totalTime);
			}
		}
	} // End of path method.

	
	public void print() { g.printGraph();	}


	public void clearAll() {
		g.vertexMap.clear();
		System.out.println("The network had been cleared.");
	
	} // End of clearAll method.


	public void reachable() { g.reachable(); }


	public void allVerticesUp() {
		for(Vertex v : g.vertexMap.values()) {
			v.status = true;
		}

		System.out.println("All vertices have been enabled.");
		
	} // End of allVerticesUp method.


	public void allVerticesDown() {
		for(Vertex v : g.vertexMap.values()) {
			v.status = false;
		}

		System.out.println("All vertices have been disabled.");
		
	} // End of allVerticesDown method.


	public void allEdgesUp() {
		for(Vertex v : g.vertexMap.values()) {
			for(Edge e : v.adj) {
				e.status = true;
			}
		}

		System.out.println("All edges have been enabled.");
		
	} // End of allEdgesUp method.


	public void allEdgesDown() {
		for(Vertex v : g.vertexMap.values()) {
			for(Edge e : v.adj) {
				e.status = false;
			}
		}

		System.out.println("All edges have been disabled.");
		
	} // End of allEdgesDown method.

} // End of Network class.
Jason James Pollman
ITCS-2215-001
Programming Project #3
Version 3

------------------------------------------------------------ README ------------------------------------------------------------
Run NetworkDriver.java with the file location of the network setup text file as an argument, although this is not required
and you can run the program with a "blank" network, adding the vertices and edges yourself.

Example:
> java NetworkDriver network.txt


To exit the program enter either 'quit' or 'q' into the command prompt.

Example:
> Enter a command: quit


----------------------------------------------------- COMPILER INFORMATION -----------------------------------------------------
java version "1.7.0_45"
Java(TM) SE Runtime Environment (build 1.7.0_45-b18)
Java HotSpot(TM) Client VM (build 24.45-b08, mixed mode, sharing)


------------------------------------------------------------- FILES ------------------------------------------------------------
This package contains the following files:

Edge.java
  - A class that acts as the graph's edges.

EdgeComparator.java
  - Allows Edge objects to be compared, to see if they have the same destination vertex (compares destination vertex's names). 

Graph.java
  - The graph class. Computes all functions on the network graph, such as adding and removing edges and vertices.

Network.java
  - An supplement to the Graph class. Mostly used for output so that Graph.java can be used for other purposes, if desired. 

NetworkDriver.java
  - Contains the main class that drives the program. 

Vertex.java
  - A class for Vertex objects, which act as vertices in the graph.


------------------------------------------------------- LIST OF COMMANDS -------------------------------------------------------
The program accepts the following commands. They are listed here in the format "commandName argument1 argument2..." The program
will prompt the user for commands as the program begins automatically.

quit (or q)
  - Exits the program.

print 
  - Prints the network graph with each vertex and it's adjacent vertices.

path fromVertex toVertex
  - Computes Dijkstra's algorithm for the fromVertex to the sourceVertex and print the shortest network path's traversal and
    it's distance.

addedge tailVertex headVertex transitTime
  - Adds an edge from tailVertex to headVertex with weight transitTime

deleteedge tailVertex headVertex
  - Removes the edge from tailVertex to headVertex

addvertex vertexName
  - Adds a vertex with name vertexName

deletevertex vertexName
  - Deletes the vertex vertexName and all paths to and from it.

vertexdown vertexName
  - Sets vertexName as disabled.

vertexup vertexname
  - Sets vertexName as enabled.

edgedown tailVertex headVertex
  - Sets the edge from tailVertex to headVertex as disabled.

edgeup tailVertex headVertex
  - Sets the edge from tailVertex to headVertex as enabled.

clearall
  - Clears all vertices from the network, also clearing all edges.

reachable
  - Prints each vertex and all vertices it can reach by graph traversal.

allverticesdown
  - Sets all vertices as disabled.

allverticesup
  - Sets all vertices as enabled.

alledgesdown
  - Sets all edges as disabled.

alledgesup
  - Sets all edges as enabled.

reset
  - Resets the network to its original state (when initially loaded).


-------------------------------------------------------- PROGRAM DESIGN --------------------------------------------------------
The program was designed to keep all independent classes separate for easy readability.

The graph program from Quiz #5 was used as a base, and then *heavily* modified to fit the needs of this particular application.
The graph, defined by the Graph class, utilizes vertices and edges, defined by their respective classes: Vertex and Edge.

Edges are objects that belong to a Vertex and have a destination Vertex. The weight of an edge is noted by the property 'dist'.
The edge can be labeled as 'up' or 'down' (enabled or disabled), changing the behavior of the graph and the shortest distance
between vertices.

Vertices are objects that contain edges from one to another. They have as properties, a name, a list of adjacent vertices (edges),
and like edges, a status which indicates whether or not the vertex is enabled or disabled. 

When a user prompts the program with the command 'path' and provides arguments with the source vertex and the destination vertex,
Dijkstra's algorithm is computed on the source vertex until it reaches the destination vertex, discovering the shortest path from
the source to the destination.


-------------------------------------------------------- DATA STRUCTURES -------------------------------------------------------
The network is represented as a weighted and directed graph that can hold edges with non-negative weights. The methods, such as
adding vertices and edges are performed from within the Graph class, and supplemented with visual output by the Network class.

The shortest path between 2 vertices is computed using Dijkstra's Algorithm, implemented with a min-heap priority queue.

Most other information that needed a data structure utilized ArrayLists for simple computation. No primitive arrays were used.


---------------------------------------------------------- WHAT WORKS ----------------------------------------------------------
The implementation of Dijkstra's works well, as it terminates computation as soon as the destination vertex is found. However,
this could have been implemented in a manner that the algorithm wouldn't have to be run every time the 'path' command was ran
by storing the results of a full Dijkstra computation on all vertices, and then only re-running the algorithm when a network change 
is made (adding a vertex or edge, etc.).

Also, I feel that the Network & Graph classes could have been condensed into a single class, or perhaps Network could have
extended the Graph class for simplicity's sake.


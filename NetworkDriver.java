// Jason James Pollman
// ITCS-2215-001
// Programming Project #3
// Version 3

import java.io.*;
import java.util.*;

class NetworkDriver {

  private static Network network;
  private static String networkFileLocation;

	public static void main(String[] args) throws IOException {

		if(args.length > 0) {
      networkFileLocation = args[0];
      network = new Network(args[0]);
    }
    else {
      network = new Network();
    }

		String cmd = "";
		Scanner input = new Scanner(System.in);

		while(!cmd.equals("quit") && !cmd.equals("q")) {

      System.out.print("\nEnter a command: ");

      cmd = input.nextLine();
      cmd = cmd.trim().toLowerCase();
      
      // No need to run code if user is quitting.
      if(!cmd.equals("quit") && !cmd.equals("q")) {

        String[] cmdArr = cmd.split(" ");

        switch(cmdArr[0]) {

          // Prints the network graph
          case "print":
            network.print();
            break;

          // Prints the path from the fromVertex to the toVertex
          case "path":
            if(NetworkDriver.checkNumArgs(cmdArr, 2)) { network.path(cmdArr[1], cmdArr[2]); } // path(fromVertex, toVertex)
            break;

          // Adds an edge from tailVertex to headVertex with a weight of transitTime
          case "addedge":

            if(NetworkDriver.checkNumArgs(cmdArr, 3)) {

              // Parse the third parameter as a float.
              try {
                float transitTime = Float.parseFloat(cmdArr[3]);
                network.addEdge(cmdArr[1], cmdArr[2], transitTime); // addEdge(tailVertex, headVertex, transitTime)
              }
              catch(NumberFormatException e) {
                System.out.println(cmdArr[0] + "'s thrid argument must a number!");
              }

            } 
            break;

          // Removes the edge from tailVertex to headVertex
          case "deleteedge":
            if(NetworkDriver.checkNumArgs(cmdArr, 2)) { network.deleteEdge(cmdArr[1], cmdArr[2]); } // deleteEdge(tailVertex, headVertex)
            break;

          // Add a vertex
          case "addvertex":
            if(NetworkDriver.checkNumArgs(cmdArr, 1)) { network.addVertex(cmdArr[1]); } // addVertex(vertexName)
            break;

          // Delete a vertex
          case "deletevertex":
            if(NetworkDriver.checkNumArgs(cmdArr, 1)) { network.deleteVertex(cmdArr[1]); } // deleteVertex(vertexName)
            break;

          // Marks a vertex as "down" (disabled).
          case "vertexdown":
            if(NetworkDriver.checkNumArgs(cmdArr, 1)) { network.vertexDown(cmdArr[1]); } // vertexDown(vertex)
            break;

          // Marks a vertex as "up" (enabled).
          case "vertexup":
            if(NetworkDriver.checkNumArgs(cmdArr, 1)) { network.vertexUp(cmdArr[1]); } // vertexUp(vertex)
            break;

          // Marks an edge as "down" (disabled).
          case "edgedown":
            if(NetworkDriver.checkNumArgs(cmdArr, 2)) { network.edgeDown(cmdArr[1], cmdArr[2]); } // edgeDown(tailVertex, headVertex)
            break;

          // Marks an edge as "up" (enabled).
          case "edgeup":
            if(NetworkDriver.checkNumArgs(cmdArr, 2)) { network.edgeUp(cmdArr[1], cmdArr[2]); } // edgeUp(tailVertex, headVertex)
            break;

          // Clears the entire network.
          case "clearall":
            if(NetworkDriver.checkNumArgs(cmdArr, 0)) { network.clearAll(); }
            break;

          // Prints each vertice that a vertex can reach.
          case "reachable":
            if(NetworkDriver.checkNumArgs(cmdArr, 0)) { network.reachable(); }
            break;

          // Sets all vertices as enabled.
          case "allverticesup":
            if(NetworkDriver.checkNumArgs(cmdArr, 0)) { network.allVerticesUp(); }
            break;

          // Sets all vertices as disabled.
          case "allverticesdown":
            if(NetworkDriver.checkNumArgs(cmdArr, 0)) { network.allVerticesDown(); }
            break;

          // Sets all edges as enabled.
          case "alledgesup":
            if(NetworkDriver.checkNumArgs(cmdArr, 0)) { network.allEdgesUp(); }
            break;

          // Sets all edges as enabled.
          case "alledgesdown":
            if(NetworkDriver.checkNumArgs(cmdArr, 0)) { network.allEdgesDown(); }
            break;

          // Resets the network to its original state (when initially loaded).
          case "reset":
            if(NetworkDriver.checkNumArgs(cmdArr, 0)) { network.reset(networkFileLocation); }
            System.out.println("\nThe network has been reset.");
            break;

          default:
            System.out.println("Command not found.");

	      } // End switch block.

	    } // End of if statment.

	      System.out.print("\n");

   	} // End while loop.

  } // End of main method.


  /**
   * Check that the input array contains the correct number of arugments for the method
   * to be called.
   */
  private static boolean checkNumArgs(String[] cmd, int args) {
    if(cmd.length != (args + 1)) {
      System.out.println("Command " + cmd[0] + " requires exactly " + args + " argument(s).");
      return false;
    }

    return true;
  }

} // End of NetworkDriver class.
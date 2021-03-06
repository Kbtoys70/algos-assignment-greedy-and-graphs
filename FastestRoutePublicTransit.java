/**
 * Public Transit
 * Author: Kevin Balderrama and Carolyn Yao
 * Does this compile? Y
 */

/**
 * This class contains solutions to the Public, Public Transit problem in the
 * shortestTimeToTravelTo method. There is an existing implementation of a
 * shortest-paths algorithm. As it is, you can run this class and get the solutions
 * from the existing shortest-path algorithm.
 */
public class FastestRoutePublicTransit {

  /**
   * The algorithm that could solve for shortest travel time from a station S
   * to a station T given various tables of information about each edge (u,v)
   *
   * @param S the s th vertex/station in the transit map, start From
   * @param T the t th vertex/station in the transit map, end at
   * @param startTime the start time in terms of number of minutes from 5:30am
   * @param lengths lengths[u][v] The time it takes for a train to get between two adjacent stations u and v
   * @param first first[u][v] The time of the first train that stops at u on its way to v, int in minutes from 5:30am
   * @param freq freq[u][v] How frequently is the train that stops at u on its way to v
   * @return shortest travel time between S and T
   */
  public int myShortestTravelTime(
    int S,
    int T,
    int startTime,
    int[][] lengths,
    int[][] first,
    int[][] freq
  ) {
	// Your code along with comments here.
	  int output = Integer.MAX_VALUE;
	  //handle delay for wait onfirst train
	  int[][] initialDelay = add(lengths, first);
	  int[] times = optimize(initialDelay, S);
	  printShortestTimes(times);
	  output = times[T];
	  
    // Your code along with comments here. Feel free to borrow code from any
    // of the existing method. You can also make new helper methods.
    return output;
  }

  /**
   * Finds the vertex with the minimum time from the source that has not been
   * processed yet.
   * @param times The shortest times from the source
   * @param processed boolean array tells you which vertices have been fully processed
   * @return the index of the vertex that is next vertex to process
   */
  public int findNextToProcess(int[] times, Boolean[] processed) {
    int min = Integer.MAX_VALUE;
    int minIndex = -1;

    for (int i = 0; i < times.length; i++) {
      if (processed[i] == false && times[i] <= min) {
        min = times[i];
        minIndex = i;
      }
    }
    return minIndex;
  }

  public void printShortestTimes(int times[]) {
    System.out.println("Vertex Distances (time) from Source");
    for (int i = 0; i < times.length; i++)
        System.out.println((char)(65+i) + ": " + times[i] + " minutes");
  }

  /**
   * Given an adjacency matrix of a graph, implements
   * @param graph The connected, directed graph in an adjacency matrix where
   *              if graph[i][j] != 0 there is an edge with the weight graph[i][j]
   * @param source The starting vertex
   */
  public void shortestTime(int graph[][], int source) {
    int numVertices = graph[0].length;

    // This is the array where we'll store all the final shortest times
    int[] times = new int[numVertices];

    // processed[i] will true if vertex i's shortest time is already finalized
    Boolean[] processed = new Boolean[numVertices];

    // Initialize all distances as INFINITE and processed[] as false
    for (int v = 0; v < numVertices; v++) {
      times[v] = Integer.MAX_VALUE;
      processed[v] = false;
    }

    // Distance of source vertex from itself is always 0
    times[source] = 0;

    // Find shortest path to all the vertices
    for (int count = 0; count < numVertices - 1 ; count++) {
      // Pick the minimum distance vertex from the set of vertices not yet processed.
      // u is always equal to source in first iteration.
      // Mark u as processed.
      int u = findNextToProcess(times, processed);
      processed[u] = true;

      // Update time value of all the adjacent vertices of the picked vertex.
      for (int v = 0; v < numVertices; v++) {
        // Update time[v] only if is not processed yet, there is an edge from u to v,
        // and total weight of path from source to v through u is smaller than current value of time[v]
        if (!processed[v] && graph[u][v]!=0 && times[u] != Integer.MAX_VALUE && times[u]+graph[u][v] < times[v]) {
          times[v] = times[u] + graph[u][v];
        }
      }
    }

    printShortestTimes(times);
  }
  public int[][] add(int[][] graph1, int[][] graph2)
  {
	  int[][] output = new int[graph1.length][graph1[0].length];
	  for(int i=0;i<graph1.length-1;i++)
	  {
		  for(int j=0;j<graph1[0].length-1;j++)
		  {
			  output[i][j] = graph1[i][j] + graph2[i][j];
		  }
	  }
	  return output;
  }
  public int[] optimize(int[][] graph, int source)
  {
	  //straight copy of shortest time, needed Dijksta's
	  //straight copy of shortest time, needed array
	  int numVertices = graph[0].length;
	  int[] times = new int[numVertices];
	  Boolean[] processed = new Boolean[numVertices];
	  for (int v = 0; v < numVertices; v++){
	      times[v] = Integer.MAX_VALUE;
	      processed[v] = false;
	  }
	  times[source] = 0;
	  for (int count = 0; count < numVertices - 1 ; count++) {
	      int u = findNextToProcess(times, processed);
	      processed[u] = true;
	      for (int v = 0; v < numVertices; v++) {
	        if (!processed[v] && graph[u][v]!=0 && times[u] != Integer.MAX_VALUE && times[u]+graph[u][v] < times[v]) {
	          times[v] = times[u] + graph[u][v];
	        }
	      }
	  }
	  return times;
  }
  
  public static void main (String[] args) {
    /* length(e) 
    int lengthTimeGraph[][] = new int[][]{
      {0,  4, 0,  0,  0,  0, 0,  8, 0},
      {4,  0, 8,  0,  0,  0, 0, 11, 0},
      {0,  8, 0,  7,  0,  4, 0,  0, 2},
      {0,  0, 7,  0,  9, 14, 0,  0, 0},
      {0,  0, 0,  9,  0, 10, 0,  0, 0},
      {0,  0, 4, 14, 10,  0, 2,  0, 0},
      {0,  0, 0,  0,  0,  2, 0,  1, 6},
      {8, 11, 0,  0,  0,  0, 1,  0, 7},
      {0,  0, 2,  0,  0,  0, 6,  7, 0}
    };
    FastestRoutePublicTransit t = new FastestRoutePublicTransit();
    t.shortestTime(lengthTimeGraph, 0);*/

    // You can create a test case for your implemented method for extra credit below
    int lengthTimeGraph1[][] = new int[][]{
        {0,  4, 0,  0,  0,  0, 0,  8, 0},
        {4,  0, 8,  0,  0,  0, 0, 11, 0},
        {0,  8, 0,  7,  0,  4, 0,  0, 2},
        {0,  0, 7,  0,  9, 14, 0,  0, 0},
        {0,  0, 0,  9,  0, 10, 0,  0, 0},
        {0,  0, 4, 14, 10,  0, 2,  0, 0},
        {0,  0, 0,  0,  0,  2, 0,  1, 6},
        {8, 11, 0,  0,  0,  0, 1,  0, 7},
        {0,  0, 2,  0,  0,  0, 6,  7, 0}
      };
      int firstTimeGraph1[][] = new int[][]{
    	  {0,  4, 0,  0,  0,  0, 0,  8, 0},
          {4,  0, 8,  0,  0,  0, 0, 11, 0},
          {0,  8, 0,  7,  0,  4, 0,  0, 2},
          {0,  0, 7,  0,  9, 14, 0,  0, 0},
          {0,  0, 0,  9,  0, 10, 0,  0, 0},
          {0,  0, 4, 14, 10,  0, 2,  0, 0},
          {0,  0, 0,  0,  0,  2, 0,  1, 6},
          {8, 11, 0,  0,  0,  0, 1,  0, 7},
          {0,  0, 2,  0,  0,  0, 6,  7, 0}
        };
        int frequencyTimeGraph1[][] = new int[][]{
        	{0,  4, 0,  0,  0,  0, 0,  8, 0},
            {4,  0, 8,  0,  0,  0, 0, 11, 0},
            {0,  8, 0,  7,  0,  4, 0,  0, 2},
            {0,  0, 7,  0,  9, 14, 0,  0, 0},
            {0,  0, 0,  9,  0, 10, 0,  0, 0},
            {0,  0, 4, 14, 10,  0, 2,  0, 0},
            {0,  0, 0,  0,  0,  2, 0,  1, 6},
            {8, 11, 0,  0,  0,  0, 1,  0, 7},
            {0,  0, 2,  0,  0,  0, 6,  7, 0}
          };
          FastestRoutePublicTransit t1 = new FastestRoutePublicTransit();
          System.out.println( t1.myShortestTravelTime(0, 7, 100, lengthTimeGraph1, firstTimeGraph1, frequencyTimeGraph1));
    
    
  }
}

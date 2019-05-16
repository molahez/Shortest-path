package eg.edu.alexu.csd.filestructure.graphs.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.management.RuntimeErrorException;

import eg.edu.alexu.csd.filestructure.graphs.IGraph;

public class Graph implements IGraph {

	String[] l1;
	ArrayList<Integer> vertices = new ArrayList<Integer>();
	ArrayList<Integer> neighbours = new ArrayList<Integer>();
	double infinity = Double.POSITIVE_INFINITY;
	Graphe graphe = null;
	int[][] graph;
	int numOfVertices = 0;
	ArrayList<Integer> verticesOrdered = new ArrayList<Integer>();
	int[] dist;

	class Graphe {
		// A class to represent a weighted edge in graph
		class Edge {
			int src, dest, weight;

			Edge() {
				src = dest = weight = 0;
			}
		};

		int V, E;
		Edge edge[];

		// Creates a graph with V vertices and E edges
		Graphe(int v, int e) {
			V = v;
			E = e;
			edge = new Edge[e];
			for (int i = 0; i < e; ++i)
				edge[i] = new Edge();
		}

	}

	@Override
	public void readGraph(File file) {
		String line = null;
		FileReader fr = null;
		if (file == null) {

		}
		int linecounter = 0;
		try {
			fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				if (linecounter == 0) {
					l1 = line.split(" ");
					graphe = new Graphe(Integer.parseInt(l1[0]), Integer.parseInt(l1[1]));
					linecounter++;

					// For Dijkstra
					graph = new int [Integer.parseInt(l1[0])][Integer.parseInt(l1[0])];
					numOfVertices = Integer.parseInt(l1[0]);
				} else {
					String[] l2 = line.split(" ");
					graphe.edge[linecounter - 1].src = Integer.parseInt(l2[0]);
					graphe.edge[linecounter - 1].dest = Integer.parseInt(l2[1]);
					graphe.edge[linecounter - 1].weight = Integer.parseInt(l2[2]);
					linecounter++;

					// For Dijkstra
					graph[Integer.parseInt(l2[0])][Integer.parseInt(l2[1])] = Integer.parseInt(l2[2]);

				}
			}
			if ((linecounter - 1) < size()) {
				throw new RuntimeErrorException(null);

			}
			br.close();
			fr.close();

		} catch (FileNotFoundException e1) {
			throw new RuntimeErrorException(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return Integer.parseInt(l1[1]);
	}

	@Override
	public ArrayList<Integer> getVertices() {
		for (int i = 0; i < Integer.parseInt(l1[0]); i++) {
			vertices.add(i);
		}
		return vertices;
	}

	@Override
	public ArrayList<Integer> getNeighbors(int v) {
		ArrayList<Integer> req = new ArrayList<Integer>();
		for (int i = 0; i < graphe.E; i++) {
			if (v == graphe.edge[i].src) {
				req.add(graphe.edge[i].dest);
			}
		}
		return req;
	}

	@Override
	public void runDijkstra(int src, int[] distances) {
		Boolean visitedArr[] = new Boolean[numOfVertices];

		for (int i = 0; i < numOfVertices; i++)
        {
			distances[i] = (Integer.MAX_VALUE)/2;
            visitedArr[i] = false;
        }

		distances[src] = 0;

		for (int count = 0; count < numOfVertices -  1; count++)
        {

            int u = getMinAfterItr(distances, visitedArr);


            visitedArr[u] = true;

            for (int v = 0; v < numOfVertices; v++)


                if (!visitedArr[v] && graph[u][v]!=0 &&
                		distances[u] != (Integer.MAX_VALUE)/2 &&
                	    distances[u]+graph[u][v] < distances[v])
                	distances[v] = distances[u] + graph[u][v];
        }
		dist = new int[numOfVertices];
		dist = distances;

	}

	@Override
	public ArrayList<Integer> getDijkstraProcessedOrder() {
		   int min = dist[0];

	       for (int j = 0; j < numOfVertices; j ++) {
	    	   for(int i = 0; i < dist.length; i++)
		       {
		            if(min > dist[i])
		            {
		                min = dist[i];
		                verticesOrdered.add(i);
		                dist[i] = (Integer.MAX_VALUE)/2;
		            }
		        }
	       }
	       return verticesOrdered;
	}

	@Override
	public boolean runBellmanFord(int src, int[] distances) {
		boolean flag = true;
		for (int node = 0; node < Integer.parseInt(l1[0]); node++) {
			distances[node] = (Integer.MAX_VALUE)/2;
		}

		distances[src] = 0;

		for (int node = 0; node < (Integer.parseInt(l1[0])); node++) {
			for (int j = 0; j < (Integer.parseInt(l1[1])); j++) {
				int u = graphe.edge[j].src;
				int v = graphe.edge[j].dest;
				int weight = graphe.edge[j].weight;
				if (distances[u] != (Integer.MAX_VALUE)/2 && distances[u] + weight < distances[v])
					distances[v] = distances[u] + weight;

			}
		}

		for (int j = 0; j < (Integer.parseInt(l1[1])); ++j) {
			int u = graphe.edge[j].src;
			int v = graphe.edge[j].dest;
			int weight = graphe.edge[j].weight;
			if (distances[u] != (Integer.MAX_VALUE)/2 && distances[u] + weight < distances[v]) {
				flag = false;
				break;
			}
		}
		if (!flag) {
			return false;
		} else {
			return true;

		}
	}

	private int getMinAfterItr (int dist[], Boolean visitedArr[])
    {
        // Initialize minimum value
        int min = (Integer.MAX_VALUE)/2 , min_index=-1;

        for (int v = 0; v < numOfVertices; v++)
            if (visitedArr[v] == false && dist[v] <= min)
            {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

}

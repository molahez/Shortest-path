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

	Graphe graphe = null;

	static class Edge {
		int source;
		int destination;
		int weight;

		public Edge(int source, int destination, int weight) {
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}
	}

	static class Graphe {
		int vertices;
		LinkedList<Edge>[] adjacencylist;

		Graphe(int vertices) {
			this.vertices = vertices;
			adjacencylist = new LinkedList[vertices];
			// initialize adjacency lists for all the vertices
			for (int i = 0; i < vertices; i++) {
				adjacencylist[i] = new LinkedList<>();
			}
		}

		public void addEgde(int source, int destination, int weight) {
			Edge edge = new Edge(source, destination, weight);
			adjacencylist[source].addFirst(edge); // for directed graph
		}

		/*public void printGraph() {
			for (int i = 0; i < vertices; i++) {
				LinkedList<Edge> list = adjacencylist[i];
				for (int j = 0; j < list.size(); j++) {
					System.out.println("vertex-" + i + " is connected to " + list.get(j).destination + " with weight "
							+ list.get(j).weight);
				}
			}
		}*/
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
					graphe = new Graphe(Integer.parseInt(l1[0]));
					linecounter++;
				} else {
					String[] l2 = line.split(" ");
					graphe.addEgde(Integer.parseInt(l2[0]), Integer.parseInt(l2[1]), Integer.parseInt(l2[2]));
					linecounter++;

				}
			}
			if((linecounter-1)< size()) {
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
		for(int i = 0;i<Integer.parseInt(l1[0]);i++) {
			vertices.add(i);
		}
		return vertices;
	}

	@Override
	public ArrayList<Integer> getNeighbors(int v) {
		LinkedList<Edge> required = graphe.adjacencylist[v];
		ArrayList<Integer> req = new ArrayList<Integer>();
		for(int i =0;i < required.size();i++) {
			req.add(required.get(i).destination);
		}
		
		return req;
	}

	@Override
	public void runDijkstra(int src, int[] distances) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Integer> getDijkstraProcessedOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean runBellmanFord(int src, int[] distances) {
		// TODO Auto-generated method stub
		return false;
	}

}

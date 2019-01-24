// javac Maze.java StdDraw.java
// java Maze maze16.txt
// Garrett Ewens
// CS2300
// November 12, 2018
import java.io.*;
import java.util.*;


class Data
{
    // dimension of maze
	public int n;
    // wall indicator for cell i, j
    public boolean[][] north;
    public boolean[][] east;
    public boolean[][] south;
    public boolean[][] west;

    public Data(String filename) {
    	readMaze(filename);
    }

    public void readMaze(String filename) {
		try {
			File file = new File(filename);
			BufferedReader buf = new BufferedReader(new FileReader(file));

			String text = buf.readLine();
			n = Integer.parseInt(text);

			north = new boolean[n+2][n+2];
        	east  = new boolean[n+2][n+2];
        	south = new boolean[n+2][n+2];
        	west  = new boolean[n+2][n+2];

			while ((text = buf.readLine()) != null) {
				String[] tokens = text.split(" ");
				int x = Integer.parseInt(tokens[0]);
				int y = Integer.parseInt(tokens[1]);
				north[x][y] = (tokens[2].equals("1") ? true : false);
				east[x][y]  = (tokens[3].equals("1") ? true : false);
				south[x][y] = (tokens[4].equals("1") ? true : false);
				west[x][y]  = (tokens[5].equals("1") ? true : false);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

public class Maze
{
    // dimension of maze
	private int n;
    private Data data;

    public Maze(Data data) {
    	this.data = data;
        this.n = data.n;
        StdDraw.setXscale(0, n+2);
        StdDraw.setYscale(0, n+2);
    }


    public void drawDot(int x, int y, String color) {

    	float size = (float)0.25;
    	if (color.equals("RED")) {
    		StdDraw.setPenColor(StdDraw.RED);
    		size = (float)0.375;
    	}
   		else if (color.equals("BLUE"))
    		StdDraw.setPenColor(StdDraw.BLUE);
    	else if (color.equals("GRAY"))
    		StdDraw.setPenColor(StdDraw.GRAY);
    	else
    		StdDraw.setPenColor(StdDraw.BLACK);

        StdDraw.filledCircle(x + 0.5, y + 0.5, size);
        StdDraw.show();
        StdDraw.pause(200);
        //try{System.in.read();}
        //catch(Exception e){}
    }


	// draw the maze
    public void draw() {

        StdDraw.setPenColor(StdDraw.BLACK);
        for (int x=1; x <= n; x++) {
            for (int y=1; y <= n; y++) {
                if (data.south[x][y]) StdDraw.line(x, y, x+1, y);
                if (data.north[x][y]) StdDraw.line(x, y+1, x+1, y+1);
                if (data.west[x][y])  StdDraw.line(x, y, x, y+1);
                if (data.east[x][y])  StdDraw.line(x+1, y, x+1, y+1);
            }
        }
        StdDraw.show();

        drawDot(n, 1, "RED");
        drawDot(1, n, "RED");
    }

	public class Node {
		private int x;
		private int y;
		private int index;
		private double counter;

		Node(int x, int y, int index) {
			this.x = x;
			this.y = y;
			this.index = index;
			this.counter = Double.POSITIVE_INFINITY;
		}

		public void setCounter(double counter) {
			this.counter = counter;
		}
	}

	public class Graph {
		int V;
		LinkedList<Node> adjListArray[];

		Graph(int V) {
			this.V = V;
			adjListArray = new LinkedList[V];
			for (int i = 0; i < V; i++) {
				adjListArray[i] = new LinkedList<Node>();
			}
		}
	}

	public void BFS(Graph graph) {
		double count = 0;
		int s = ((n - 1) * n);
		boolean visited[] = new boolean[graph.V];
		Queue<Node> queue = new LinkedList<Node>();
		visited[s] = true;
		graph.adjListArray[s].getFirst().setCounter(count);
		queue.add(graph.adjListArray[s].getFirst());
		while (queue.size() != 0) {
			count = queue.peek().counter + 1;
			s = queue.poll().index;
			if (s == graph.adjListArray[n-1].getFirst().index) {
				System.out.println("The shortest distance is: " + (int)graph.adjListArray[n-1].getFirst().counter);
				solveGraph(graph);
				return;
			}

			ListIterator<Node> i = graph.adjListArray[s].listIterator();
			while (i.hasNext()) {
				int cur = i.next().index;
				if (!visited[cur]) {
					visited[cur] = true;
					graph.adjListArray[cur].getFirst().setCounter(count);
					queue.add(graph.adjListArray[cur].getFirst());
				}
			}
		}
	}

	public void solveGraph(Graph graph) {
		int count = 0;
		int start = ((n - 1) * n);
		int end = (int)graph.adjListArray[n-1].getFirst().counter;
		int cursor = n - 1;
		int solution[] = new int[end + 1];
		solution[count] = graph.adjListArray[n-1].getFirst().index;
		int temp = end;
		while (cursor != start) {
			ListIterator<Node> i = graph.adjListArray[cursor].listIterator();
			while (i.hasNext()) {
				int cur = i.next().index;
				if (graph.adjListArray[cur].getFirst().counter + 1 == temp) {
					count++;
					solution[count] = graph.adjListArray[cur].getFirst().index;
					temp--;
					cursor = cur;
				}
			}
		}
		for (int i = (int)graph.adjListArray[n-1].getFirst().counter; i >= 0; i--) {
			int coordinate = solution[i];
			this.drawDot(graph.adjListArray[coordinate].getFirst().x, graph.adjListArray[coordinate].getFirst().y, "BLUE");
		}
	}

	public void addEdge(Graph graph, int x1, int y1, int x2, int y2) {
		Node from = new Node(x2, y2, (((y2 - 1) * n) + (x2 - 1)));
		graph.adjListArray[(((y1 - 1) * n) + (x1 - 1))].add(from);
	}
/*
	// Used for seeing if the Adjacency Linked List was built properly
	public void printGraph(Graph graph) {
		for (int i = 0; i < graph.V; i++) {
			System.out.println("Adjacency list of vertex " + i);
			System.out.print("head");
			ListIterator<Node> listIterator = graph.adjListArray[i].listIterator();
			while (listIterator.hasNext()) {
				System.out.print(" -> " + listIterator.next().index);
			}
			System.out.println();
			System.out.println();
		}
	}
*/
	public void solve() {
		Graph graph = new Graph(n * n);
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				Node current = new Node(i, j, (((j - 1) * n) + (i - 1)));
				graph.adjListArray[(((j - 1) * n) + (i - 1))].add(current);
				if (!this.data.north[i][j]) addEdge(graph, i, j, i, j + 1);
				if (!this.data.south[i][j]) addEdge(graph, i, j, i, j - 1);
				if (!this.data.east[i][j]) addEdge(graph, i, j, i + 1, j);
				if (!this.data.west[i][j]) addEdge(graph, i, j, i - 1, j);
			}
		}
		//printGraph(graph);
		BFS(graph);
	}
	static public void main (String args[]) {

		if (args.length == 1) {
			Data data = new Data(args[0]);
			Maze maze = new Maze(data);

			maze.draw();
			maze.solve();
		}
		else
			System.out.println("Please provide an input file ...");
	}
}



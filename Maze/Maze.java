// Garrett Ewens
// Data Structures
// September 9, 2018
//javac Maze.java StdDraw.java
//java Maze maze16.txt
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.*;

class Stack<Item> implements Iterable<Item>
{
	private int n;
	public Node first;

	public class Node
	{
		public Item item;
		public Node next;
	}

	public Stack()
	{
		first = null;
		n = 0;
	}

	public boolean isEmpty()
	{
		return first == null;
	}

	public int size()
	{
		return n;
	}

	public void push(Item item)
	{
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		n++;
	}

	public Item pop()
	{
		if (isEmpty()) throw new NoSuchElementException("Stack underflow");
		Item item = first.item;
		first = first.next;
		n--;
		return item;
	}
	public Item peek()
	{
		if (isEmpty()) throw new NoSuchElementException("Stack underflow");
	    return first.item;
    }

	public Iterator<Item> iterator()
	{
		return new ListIterator();
	}

	private class ListIterator implements Iterator<Item>
	{
		private Node current = first;
		public boolean hasNext()
		{
			return current != null;
		}

		public void remove()
		{
			throw new UnsupportedOperationException();
		}

		public Item next()
		{
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
}

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

    public Maze(Data data)
    {
    	this.data = data;
        this.n = data.n;
        StdDraw.setXscale(0, n+2);
        StdDraw.setYscale(0, n+2);
    }

    public void drawDot(int x, int y, String color)
    {

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

	public String fromCoords(int x, int y)
	{
		return Integer.toString(x) + "," + Integer.toString(y);
	}

	public int[] fromString(String coords)
	{
		String[] xy = coords.split(",");
		int x = Integer.parseInt(xy[0]);
		int y = Integer.parseInt(xy[1]);

		return new int[] {x,y};
	}

    public void solve()
    {
		// Create stack to hold string variables
	    Stack<String> stack = new Stack<String>();
	    // Initialize starting position to top left of maze
		int dx = 1;
		int dy = this.n;
		// Create boolean 2D array to know if a coordinate has been visited or not
		boolean visited[][] = new boolean[n+1][n+1];
		// Sets all coordinates as not visited
		for (int a = 1; a < n + 1; a++)
		{
			for (int b = 1; b < n + 1; b++)
			{
				visited[a][b] = false;
			}
		}
		// Adds starting position to stack
		stack.push(fromCoords(dx,dy));
		// Once dot reaches bottom right of maze. Stops the loop
		while (dx != n || dy != 1)
		{
			// Checks if there is a wall at coordinate and if the spot it wants to go to has been visited
			if (!this.data.north[dx][dy] && !visited[dx][dy+1])
			{
				// Moves dot to new position, places blue dot, adds coordinate to visited array, and pushes it's coords to stack
				dy++;
				this.drawDot(dx, dy, "BLUE");
				visited[dx][dy] = true;
				stack.push(fromCoords(dx,dy));
				continue;
			}
			else if (!this.data.east[dx][dy] && !visited[dx+1][dy])
			{
				dx++;
				this.drawDot(dx, dy, "BLUE");
				visited[dx][dy] = true;
				stack.push(fromCoords(dx,dy));
				continue;
			}
			else if (!this.data.west[dx][dy] && !visited[dx-1][dy])
			{
				dx--;
				this.drawDot(dx, dy, "BLUE");
				visited[dx][dy] = true;
				stack.push(fromCoords(dx,dy));
				continue;
			}
			else if (!this.data.south[dx][dy] && !visited[dx][dy-1])
			{
				dy--;
				this.drawDot(dx, dy, "BLUE");
				visited[dx][dy] = true;
				stack.push(fromCoords(dx,dy));
				continue;
			}
			// Only executes if dot can't go any direction
			else
			{
				// If the stack isn't empty, it stacks the string coordinates and sets dx and dy to the new values
				if (!stack.isEmpty())
				{
					int[] last = fromString(stack.pop());
					dx = last[0];
					dy = last[1];
				}
				// If the stack is empty, it sets coordinates back to top left of maze
				else
				{
					dx = 1;
					dy = this.n;
				}
				// Draws a gray dot when backtracking
				this.drawDot(dx, dy, "GRAY");
			}
		}
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

   public static void main (String args[])
   {
       	if (args.length == 1)
       	{
		   	Data data = new Data(args[0]);
		   	Maze maze = new Maze(data);

           	maze.draw();
      		maze.solve();
       	}
       	else
       	{
			System.out.println("Please provide an input file ...");
       	}
   }
}

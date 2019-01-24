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

    public void solve()
    {
		// North = 1, East = 2, South = 3, West = 4
	    Stack<Integer> stack = new Stack<Integer>();
		int dx = 1;
		int dy = this.n;
		int d = 0;
		int pd = 0;
		int backtrack = -1;
		int skip[][] = new int[n+1][n+1];
		boolean visited[][] = new boolean[n+1][n+1];
		stack.push(d);
		while (dx != n || dy != 1)
		{
			if (!this.data.north[dx][dy] && !visited[dx][dy] && (pd * backtrack != -3) && (pd * backtrack != 1) &&
				(!stack.isEmpty() && stack.peek() * backtrack != 3) && (skip[dx][dy] != 1))
			{
				if (backtrack == 1)
				{
					skip[dx][dy] = d;
				}
				backtrack = -1;
				d = 1;
				pd = d;
				skip[dx][dy] = d;
				dy++;
				this.drawDot(dx, dy, "BLUE");
				visited[dx][dy] = true;
				try {
				System.out.println(dx + " " + dy +  " " + d + " " + stack.peek() + " " + skip[dx][dy]);
				}
				catch (NoSuchElementException e)
				{

				}
				stack.push(d);
				continue;
			}
			else if (!this.data.east[dx][dy] && !visited[dx][dy] && (pd * backtrack != -4) && (pd * backtrack != 2) &&
					(!stack.isEmpty() && stack.peek() * backtrack != 4) && (skip[dx][dy] != 2))
			{
				if (backtrack == 1)
				{
					skip[dx][dy] = d;
				}
				backtrack = -1;
				d = 2;
				pd = d;
				skip[dx][dy] = d;
				dx++;
				this.drawDot(dx, dy, "BLUE");
				visited[dx][dy] = true;
				try {
				System.out.println(dx + " " + dy +  " " + d + " " + stack.peek() + " " + skip[dx][dy]);
				}
				catch (NoSuchElementException e)
				{

				}
				stack.push(d);
				continue;
			}
			else if (!this.data.south[dx][dy] && !visited[dx][dy] && (pd * backtrack != -1) && (pd * backtrack != 3) &&
					(!stack.isEmpty() && stack.peek() * backtrack != 1) && (skip[dx][dy] != 3))
			{
				if (backtrack == 1)
				{
					skip[dx][dy] = d;
				}
				backtrack = -1;
				d = 3;
				pd = d;
				skip[dx][dy] = d;
				dy--;
				this.drawDot(dx, dy, "BLUE");
				visited[dx][dy] = true;
				try {
				System.out.println(dx + " " + dy +  " " + d + " " + stack.peek() + " " + skip[dx][dy]);
				}
				catch (NoSuchElementException e)
				{

				}
				stack.push(d);
				continue;
			}
			else if (!this.data.west[dx][dy] && !visited[dx][dy] && (pd * backtrack != -2) && (pd * backtrack != 4) &&
					(!stack.isEmpty() && stack.peek() * backtrack != 2) && (skip[dx][dy] != 4))
			{
				if (backtrack == 1)
				{
					skip[dx][dy] = d;
				}
				backtrack = -1;
				d = 4;
				pd = d;
				skip[dx][dy] = d;
				dx--;
				this.drawDot(dx, dy, "BLUE");
				visited[dx][dy] = true;
				try {
				System.out.println(dx + " " + dy +  " " + d + " " + stack.peek() + " " + skip[dx][dy]);
				}
				catch (NoSuchElementException e)
				{

				}				stack.push(d);
				continue;
			}
			else
			{
				/*if (backtrack) {
					pd = d + 1;
					continue;
				}*/
				backtrack = 1;
				pd = d;
				if (!stack.isEmpty())
				{
					d = stack.pop();
				}
				//pd = d;
				//skip[dx][dy] = d;
				if (d == 1)
				{
					this.drawDot(dx, dy, "GRAY");
					dy--;
				}
				else if (d == 2)
				{
					this.drawDot(dx, dy, "GRAY");
					dx--;
				}
				else if (d == 3)
				{
					this.drawDot(dx, dy, "GRAY");
					dy++;
				}
				else if(d == 4)
				{
					this.drawDot(dx, dy, "GRAY");
					dx++;
				}
				skip[dx][dy] = d;
				//d--;
				this.drawDot(dx, dy, "GRAY");
				System.out.println("back: " + dx + " " + dy +  " " + d + " " + pd + " " + skip[dx][dy]);
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

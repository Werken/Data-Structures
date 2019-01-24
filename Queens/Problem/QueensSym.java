// Garrett Ewens
// Data Structures
// October 1, 2018
// javac Queens.java StdDraw.java
// java Queens 10
import java.util.Arrays;

class QueensSym
{
    void draw(int[] board)
    {
        int n = board.length;
        StdDraw.setXscale(0, n+2);
        StdDraw.setYscale(0, n+2);

        for (int x=1; x<=n; x++) {
            for (int y=1; y<=n; y++) {
                if ((y % 2) != 0) {
                    if ((x % 2) != 0)  {
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.filledSquare(x+0.5, y+0.5, 0.5);
                    }
                    else {
                        StdDraw.setPenColor(StdDraw.ORANGE);
                        StdDraw.filledSquare(x+0.5, y+0.5, 0.5);
                    }
                }
                else {
                    if ((x % 2) != 0)  {
                        StdDraw.setPenColor(StdDraw.ORANGE);
                        StdDraw.filledSquare(x+0.5, y+0.5, 0.5);
                    }
                    else {
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.filledSquare(x+0.5, y+0.5, 0.5);
                    }
                }
            }
        }
        StdDraw.show();
        StdDraw.pause(200);
    }

    // add your code here
    static int[][] solution;

	public QueensSym(int n)
	{
		solution = new int[n][n];
		// "Empties" board
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				solution[i][j] = 0;
			}
		}
	}
	public void solve(int n)
	{
		if (checkQueens(0, n))
		{
			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < n; j++)
				{
					if (solution[i][j] == 1)
					{
						StdDraw.setPenColor(StdDraw.RED);
						StdDraw.filledCircle(i+1+0.5, j+1+0.5, 0.375);
						StdDraw.filledCircle(i+1+0.5, j+1+0.5, 0.375);
						StdDraw.show();
						StdDraw.pause(200);
					}
				}
			}
		}
		else
			System.out.println("No Solution");
	}
	public boolean checkQueens(int queen, int n)
	{
		int symN = n / 2;
		if (n % 2 != 0)
		{
			solution[symN][symN] = 1;
		}
		if (queen == symN) // If this executes then the problem is solved
			return true;
		for (int row = 0; row < n; row++)
		{
			// Checks if a piece can be placed at (row, col) and it's symmetrical position
			if (isSafe(solution, row, queen))
			{
				solution[row][queen] = 1;
				if (isSafe(solution, (n-row-1), (n-queen-1)))
				{
					solution[n - row - 1][n - queen - 1] = 1;
					// Solves following pieces recursively
					if (checkQueens(queen + 1, n))
						return true;
				}
				// Backtracks
				solution[row][queen] = 0;
				solution[n - row - 1][n - queen - 1] = 0;
			}
		}
		// Couldn't find a solution
		return false;
	}
	public boolean isSafe(int[][] board, int row, int col)
	{
		// Places pieces one column at a time
		// Checks if a Queen was placed in the same row
		for (int i = 0; i < col; i++)
		{
			if (board[row][i] == 1)
				return false;
		}
		// Checks if attacked at upper diagonal
		for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
		{
			if (board[i][j] == 1)
				return false;
		}
		// Checks if attacked at lower diagonal
		for (int i = row, j = col; i < board.length && j >= 0; i++, j--)
		{
			if (board[i][j] == 1)
				return false;
		}
		return true;
	}

   static public void main (String args[])
   {
       if (args.length == 1) {
           int n = Integer.parseInt(args[0]);

           // add your code here
           QueensSym queens = new QueensSym(n);
		   int board[] = new int[n];

           queens.draw(board);
           queens.solve(n);
       }
       else
           System.out.println("Please input the size of the board ...");

   }
}



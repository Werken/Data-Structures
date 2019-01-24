import java.lang.StackOverflowError;

class Stack
{
	private int topIndex;
	private boolean[][] elements;

	Stack(int n)
	{
		this.topIndex = 0;
		this.elements = new boolean[n][n];
	}

	public boolean isEmpty()
	{
		return (this.topIndex == -1);
	}

	public boolean isFull()
	{
		return (this.topIndex == (this.elements.length - 1));
	}

	public void push(boolean element)
	{
		if (this.isFull())
			throw new StackOverflowError("Push attempted on a full stack.");
		else
		{
			this.topIndex++;
			this.elements[this.topIndex] = element;
		}
	}

	public void pop()
	{
		if (this.isEmpty())
			throw new StackOverflowError("Pop attempted on empty stack.");
		else
		{
			this.elements[this.topIndex] = null;
			this.topIndex--;
		}
	}

	public boolean top()
	{
		boolean topOfStack = false;
		if (this.isEmpty())
			throw new StackOverflowError("Top attempted on empty stack.");
		else
			topOfStack = this.elements[this.topIndex];
		return topOfStack;
	}
}
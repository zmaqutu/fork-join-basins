import java.util.concurrent.RecursiveAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.util.Collections;

public class ParallelThreads extends RecursiveAction //<ArrayList<String>>
{
	static int low;
	static int high;
	static float[][] grid;
	static float [] array;			//this is a 1D version of my terain grid
	static ArrayList<String> points = new ArrayList<String>();
	static int SEQUENTIAL_CUTOFF = 1000;
	static int startingRow,startingCol,finishingRow,finishingCol = 0;

	ParallelThreads(float [][] grid, float [] array, int low, int high)
	{
		this.grid = grid ;
		this.array = array;
		this.low = low;
		this.high = high;
	}

	protected void compute()
	{
		static boolean[][] puntas = new boolean[grid.length][grid[0].length];
		if(high - low < SEQUENTIAL_CUTOFF)
		{
			for(int i = 0; i < grid.length; i++)
			{
				for(int j = 0; j < grid[0].length; j++)
				{
					if(low -1 == i * (grid[0].length) + j)
					{
						startingRow = i;
						startingCol = j;
					}
					if(high - 1 == i * (grid[0].length) + j)
					{
						finishingRow = i;
						finishingCol = j;
					}
				}
			}
			if(startingRow == 0 || startingCol == 0) 
			{
				startingRow = 1;
				startingCol = 1;
			}
			if(finishingRow == grid.length - 1  || finishingCol == grid[0].length - 1)
			{
				finishingRow -= 1;
				finishingCol -= 1;
			}
				
			if(startingRow != 0 && startingRow != grid.length-1 && startingCol != 0 &&
					startingCol != grid[0].length - 1 && finishingRow != 0 && finishingRow != grid.length - 1 &&
					finishingCol != 0 && finishingCol != grid[0].length - 1)
			{
				processMatrix();
				System.out.println("Check working");
			}
			for(int p = 0; p < points.size();p++)
			{
				System.out.println(points.get(p));
			}
			
		}
		else
		{
			ParallelThreads left = new ParallelThreads(grid, array, low, (high + low) /2);
			ParallelThreads right = new ParallelThreads(grid, array, (high + low) /2, high);
			System.out.println("Low is now : " + low);
			System.out.println("High is now :" + high);
			System.out.println("My boolean has a length of " + puntas.length);
			left.fork();
			right.compute();
			left.join();

			//points.addAll(left.points.addAll(right.points));
			//points.addAll(right.points);
			//(left.points).addAll(right.points);
		}
	}
	public static void processMatrix()
	{
		float row1[] = new float[3];
		float row2[] = new float[3];
		float row3[] = new float[3];
		float center = 0;

		for(int i = startingRow; i <= finishingRow; i++ )
		{
				for(int j = 1; j < grid[0].length - 1; j++ )// like this because I'm only splitting by row
				{
					row1[0] = grid[i-1][j-1];
					row1[1] = grid[i-1][j];
					row1[2] = grid[i-1][j+1];
					
					row2[0] = grid[i][j-1];
					row2[1] = grid[i][j];
					row2[2] = grid[i][j+1];
					
					row3[0] = grid[i+1][j-1];
					row3[1] = grid[i+1][j];
					row3[2] = grid[i+1][j+1];

					center = grid[i][j] + 0.01f;

					if(row1[0] >= center && row1[1] >= center && row1[2] >= center && row2[0] >= center && row2[2] >= center && row3[0] >= center && row3[1] >= center && row3[2] >= center)
					{
						String rowString = Integer.toString(i);
						String colString = Integer.toString(j);
						String coordinates = rowString.concat(" " + colString);
						System.out.println("Is this reachable");//not reachable because of startig and finishing vals
						points.add(coordinates);
					}
				}
		}
	}
}

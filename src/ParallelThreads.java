import java.util.concurrent.RecursiveAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.util.Collections;

public class ParallelThreads extends RecursiveAction
{
	int low;
	int high;
	float[][] grid;
	float [] array;			//this is a 1D version of my terain grid
	final boolean  [][] basins;
	int SEQUENTIAL_CUTOFF;
	int startingRow = 0;
	int startingCol = 1;
	int finishingRow = 0;
	int finishingCol = 1;
	
	/**
	 * This is the default constructor for the ParallelThreads class
	 * @param grid - this is the grid we need to search for basins*   
	 * @param array - this is the array we will be splitting*   
	 * @param basins - this is the boolean array where we will store our basins*   
	 * @param high - this is the upper bound for our array for split  
	 * @param low - this is the lower bound for our array split*   
	 *  
	 */
	ParallelThreads(float [][] grid, float [] array,boolean [][] basins, int low, int high)
	{
		this.grid = grid ;
		this.array = array;
		this.low = low;
		this.high = high;
		this.basins = basins;
	}
	/**
	 * This is the overridden compute method we will use to parallelise our computation
	 * @return void
	 * */
	protected void compute()
	{
		SEQUENTIAL_CUTOFF = (grid.length * grid[0].length) / 8;
		if(high - low < SEQUENTIAL_CUTOFF)
		{
			for(int i = 0; i < grid.length; i++)
			{
				for(int j = 0; j < grid[0].length; j++)
				{
					if(low == i * (grid[0].length) + 0)
					{
						startingRow = i;
						//startingCol = j;
					}
					if(high - 1 == i * (grid[0].length) + j)
					{
						finishingRow = i;
						finishingCol = j-1;
					}
				}
			}
			if(low == 0) 
			{
				startingRow = 1;
				startingCol = 1;
			}
			if(startingCol != 1)
			{
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
				//System.out.println("Check working");
			}
			/*for(int z = 0;z < basins.length;z++)
			{
				for(int a = 0; a < basins[0].length;a++)
				{
					if(basins[z][a])
					{
						System.out.println(z + " " + a);
					}
				}
			}*/
			
		}
		else
		{
			ParallelThreads left = new ParallelThreads(grid, array,basins, low, (high + low) /2);
			ParallelThreads right = new ParallelThreads(grid, array,basins, (high + low) /2, high);
			left.fork();
			//left.compute();
			right.compute();
			left.join();

			/*for(int p = 0; p < basins.length;p++)
			{
				for(int q = 0; q < basins[0].length;q++)
				{
					basins[p][q] = left.basins[p][q] || right.basins[p][q];

				}
			}*/

		}
	}
	/**
	 * This method only runs within our sequential block and processes the grid within the given low and high bounds
	 * All other variables are stored globally within the class
	 * @return void
	 * */
	public void processMatrix()
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
						basins[i][j] = true;
					}
				}
		}
	}
}

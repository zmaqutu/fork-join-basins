import java.util.concurrent.RecursiveAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.util.Collections;

public class ParallelThreads extends RecursiveAction //<ArrayList<String>>
{
	int low;
	int high;
	static float[][] grid;
	static float [] array;			//this is a 1D version of my terain grid
	static ArrayList<String> points = new ArrayList<String>();
	static boolean [][] basins;
	static int SEQUENTIAL_CUTOFF = 10000;
	static int startingRow = 0;
	static int startingCol = 1;
	static int finishingRow = 0;
	static int finishingCol = 1;

	ParallelThreads(float [][] grid, float [] array,boolean [][] basins, int low, int high)
	{
		this.grid = grid ;
		this.array = array;
		this.low = low;
		this.high = high;
		this.basins = basins;
	}

	protected void compute()
	{
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
			/*if(startingRow == 0)
			{
				startingRow += 1;
			}*/
			if(startingCol != 1)
			{
				startingCol = 1;
			}
			if(finishingRow == grid.length - 1  || finishingCol == grid[0].length - 1)
			{
				finishingRow -= 1;
				finishingCol -= 1;
			}
			/*System.out.println("StartingRow: " + startingRow);
			System.out.println("StartingCol: " + startingCol);
			System.out.println("finishingRow: " + finishingRow);
			System.out.println("finishingCol: " + finishingCol);
			*/		
			
			if(startingRow != 0 && startingRow != grid.length-1 && startingCol != 0 &&
					startingCol != grid[0].length - 1 && finishingRow != 0 && finishingRow != grid.length - 1 &&
					finishingCol != 0 && finishingCol != grid[0].length - 1)
			{
				processMatrix();
				System.out.println("Check working");
			}
			/*for(int p = 0; p < points.size();p++)
			{
				System.out.println(points.get(p));
			}*/
			for(int z = 0;z < basins.length;z++)
			{
				for(int a = 0; a < basins[0].length;a++)
				{
					if(basins[z][a])
					{
						System.out.println(z + " " + a);
					}
				}
			}
			
		}
		else
		{
			ParallelThreads left = new ParallelThreads(grid, array,basins, low, (high + low) /2);
			ParallelThreads right = new ParallelThreads(grid, array,basins, (high + low) /2, high);
			//System.out.println("Starting row is : " + startingRow);
			//System.out.println("Finishing row is: " + finishingRow);
			//left.fork();
			//right.compute();
			left.compute();
			right.compute();
			//left.join();
			//System.out.println(left.basins[154][212]);
			//System.out.println(right.basins[154][212]);

			for(int p = 0; p < basins.length;p++)
			{
				for(int q = 0; q < basins[0].length;q++)
				{
					basins[p][q] = left.basins[p][q] || right.basins[p][q];

				}
			}

			//points.addAll(left.points.addAll(right.points));
			//points.addAll(right.points);
			//(left.points).addAll(right.points);
		}
	}
	public void processMatrix()
	{
		float row1[] = new float[3];
		float row2[] = new float[3];
		float row3[] = new float[3];
		float center = 0;

		System.out.println("Starting row in Process is : " + startingRow);
		System.out.println("Finishing row in Process is: " + finishingRow);
		System.out.println("When low is : " + low + " and high is: " + high);
		
		
		
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
						basins[i][j] = true;
						if(basins[i][j])
						{
							System.out.println("There is a basin at: " + i + " " + j);
						}
						//System.out.println("Is this reachable");//not reachable because of startig and finishing vals
						//points.add(coordinates);
					}
				}
		}
	}
}

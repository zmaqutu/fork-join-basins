import java.util.concurrent.RecursiveAction;
import java.util.ArrayList;
import java.util.Arrays;

public class FindBasins extends RecursiveAction
{
	static int low;
	static int high;
	static int col;
	static float[][] grid;
	static ArrayList<String> points = new ArrayList<String>();

	static final int SEQUENTIAL_CUTOFF = 4;

	FindBasins(float[][] gird, int low, int high)
	{
		this.grid = grid;
		this.low = low;
		this.high = high;
	}

	protected void compute()
	{
		if(high - low <= SEQUENTIAL_CUTOFF)
		{
			//ArrayList<String> processedPoints = processMatrix();
			//ArrayList<String> processedPoints = new ArrayList<String>();
			//processedPoints = processMatrix();
			//processedPoints.add("2 1");
			//processedPoints.add("4 2");
			//points.add("2 9");
			//return points;
			float row1[] = new float[3];
			float row2[] = new float[3];
			float row3[] = new float[3];
			float center = 0;

			for(int i = low + 1; i < high - 1; i++)
			{
				for(int j = 1; j < grid[0].length - 1; j++)
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
					if(row1[0] >= center && row1[1] >= center && row1[2] >= center && row2[0] >= center && row2[1] >= center && row2[2] >= center && row3[0] >= center && row3[1] >= center && row3[2] >= center)
                                	{
                                        	String rowString = Integer.toString(i);
                                        	String colString = Integer.toString(j);
                                        	String coordinates = rowString.concat(" " + colString);
                                        	//points.add(coordinates);
                                	}
                        	}
			}
			//System.out.println("No Need for parallel " + points.get(0));
		}
		else
		{
			FindBasins left = new FindBasins(grid,low,(high + low)/2);
			FindBasins right = new FindBasins(grid,(high + low)/2, high);
			System.out.println("parallel work needed test is ");
			
			left.fork();
			right.compute();
			left.join();

			//points = left.points + right.pointis
			points.addAll(left.points);
			points.addAll(right.points);
			//(left.points).addAll(right.points);
			//points = left.points;

			/*ArrayList<String> rightPoints = right.compute();
			ArrayList<String> leftPoints = left.join();
			leftPoints.addAll(rightPoints);
			return leftPoints;*/
		}
	}
	public static ArrayList<String> processMatrix()
	{
		//ArrayList<String> points = new
		float row1[] = new float[3];
		float row2[] = new float[3];
		float row3[] = new float[3];
		float center = 0;

		for(int i = 1; i < high; i++)
		{
			for(int j = 1; j < grid[0].length; j++)
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

				if(row1[0] >= center && row1[1] >= center && row1[2] >= center && row2[0] >= center && row2[1] >= center && row2[2] >= center && row3[0] >= center && row3[1] >= center && row3[2] >= center)
				{
					String rowString = Integer.toString(i);
					String colString = Integer.toString(j);
					String coordinates = rowString.concat(" " + colString);
					points.add(coordinates);
				}
			}
		}
		//System.out.println(Arrays.toString(points.toArray()));
		return points;
	}
}

import java.util.*;
import java.util.concurrent.*;
import java.io.PrintWriter;
import java.io.*;
import java.util.ArrayList;

public class SequentialBasins
{
	public static void printBasinsToFile(ArrayList<Integer> basins, int count)
	{
		System.out.println(basins);
	}
	public static void main(String [] args)
	{	
		String fileName = "4x4.txt";

		File file = new File(fileName);
		int row = 0;
		int col = 0;
		int count = 0;
		ArrayList<Integer> basins; 


		try
		{
			Scanner readSize = new Scanner(file);
			row = readSize.nextInt();
			col = readSize.nextInt();

			readSize.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		float grid[][] = new float[row][col];
		try
		{
			Scanner inputStream = new Scanner(file);
			row = inputStream.nextInt();
			col = inputStream.nextInt();
			while(inputStream.hasNext())
			{
				for(int i = 0; i < row;i++)
				{
					for(int j = 0; j < col;j++)
					{
						grid[i][j] = inputStream.nextFloat();
					}
				}
			}
			inputStream.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		//System.out.println(Arrays.deepToString(grid).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));

		float row1[] = new float[3];
		float row2[] = new float[3];
		float row3[] = new float[3];
		float center = 0;

		for(int i = 1; i < row-1; i++)
		{
			for(int j = 1;j < col-1; j++)
			{
				//Assign first row of ring
				row1[0] = grid[i - 1][j - 1];
				row1[1] = grid[i - 1][j];
				row1[2] = grid[i - 1][j+1];

				//Assign second row of ring
				row2[0] = grid[i][j-1];
				row2[1] = grid[i][j];
				row2[2] = grid[i][j+1];

				//Assign third row of ring
				row3[0] = grid[i + 1][j - 1];
				row3[1] = grid[i + 1][j];
				row3[2] = grid[i + 1][j + 1];

				center = grid[i][j] + 0.01f;

				if(row1[0] >= center && row1[1] >= center && row1[2] >= center && row2[0] >= center && row2[2] >= center && row3[0] >= center && row3[1] >= center && row3[2] >= center)
				{
					//System.out.println("There is a basin at " + i + "," + j);
					count++;
				}
			}
		}
		System.out.println("There are " + count + " basins");
	}
}

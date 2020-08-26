import java.util.Scanner;
import java.util.concurrent.*;
import java.io.*;
import java.util.concurrent.ForkJoinPool;
import java.util.ArrayList;
import java.util.Arrays;

public class ParallelBasins
{
	static String fileName = "4x4.txt";
	static String outputFile;
	static final ForkJoinPool fjPool = new ForkJoinPool();
	static void findParallelBasins(float [][] grid, float[] array)
	{
		 fjPool.invoke(new ParallelThreads(grid,array,0,array.length));
	}
	public static int[] getSize()
	{
		int row = 0;
		int col = 0;
		int[] matrixSize = new int[2];

		File file = new File(fileName);
		try
		{
			Scanner readSize = new Scanner(file);
			row = readSize.nextInt();
			col = readSize.nextInt();
			matrixSize[0] = row;
			matrixSize[1] = col;
			readSize.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return matrixSize;
	}
	public static float[][] read_from_file()
	{
		File file = new File(fileName);
		int [] gridSize = new int[2];
		gridSize = getSize();

		int row = gridSize[0];
		int col = gridSize[1];

		float [][] grid = new float[row][col];

		try
		{
			Scanner inputStream = new Scanner(file);
			inputStream.nextInt();
			inputStream.nextInt();

			while(inputStream.hasNext())
			{
				for(int i =0; i < row;i++)
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
		return grid;
	}
	public static float[] getArray()
	{
		int [] gridSize = getSize();
		int arrayLength = gridSize[0] * gridSize[1];
		float [] array = new float[arrayLength];

		File file = new File(fileName);
		try
		{
			Scanner inputStream = new Scanner(file);
			inputStream.nextInt();
			inputStream.nextInt();
			while(inputStream.hasNext())
			{
				for(int i = 0; i < array.length;i++)
				{
					array[i] = inputStream.nextFloat();
				}
			}
			inputStream.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return array;
	}
	public static void main(String [] args)
	{
		float [] oneDArray = getArray();
		System.out.println("Basin Works");
		float matrix[][] = read_from_file();
		//System.out.println(Arrays.deepToString(matrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		findParallelBasins(matrix, oneDArray);
		//System.out.println(Arrays.toString(basinsToPrint.toArray()));
	}
}

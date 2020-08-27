import java.util.Scanner;
import java.util.concurrent.*;
import java.io.*;
import java.util.concurrent.ForkJoinPool;
import java.util.ArrayList;
import java.util.Arrays;

public class ParallelBasins
{
	static String fileName;
	static String outputFile;
	static long startTime= 0;
	static final ForkJoinPool fjPool = new ForkJoinPool();
	static boolean[][] findParallelBasins(float [][] grid, float[] array, boolean[][] basins)
	{
		ParallelThreads app = new ParallelThreads(grid,array,basins,0,array.length);
		fjPool.invoke(app);
		return app.basins;
		//System.out.println("There is a basin at " + app.basins[154][212]);
		//fjPool.invoke(new ParallelThreads(grid,array,basins,0,array.length));
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
	private static void tick()
	{
		startTime = System.nanoTime();
	}
	private static float tock()
	{
		return(System.nanoTime() - startTime) / 1000000000.0f;
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
	/**
	 * This method takes in a grid of booleans and prints them to file
	 * @param basins -- this is a grid of booleans
	 * @return void
	 * */
	public static void printBasinsToFile(boolean[][] basins)
	{
		int count = 0;
		ArrayList<String> coordinates = new ArrayList<String>();
		//outputFile = "parallelOutput.txt";
		for(int i = 0; i < basins.length; i++)
		{
			for(int j = 0; j < basins[0].length; j++)
			{
				if(basins[i][j])
				{
					count++;
					coordinates.add(i + " " + j);
				}
			}
		}
		try
		{
			PrintWriter outputStream = new PrintWriter(outputFile);
			outputStream.println(count);
			for(String cor: coordinates)
			{
				outputStream.print(cor + System.lineSeparator());
			}
			outputStream.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String [] args)
	{
		fileName = args[0];
		outputFile = args[1];
		float [] oneDArray = getArray();
		int count = 0;
		float matrix[][] = read_from_file();
		boolean [][] basins = new boolean[matrix.length][matrix[0].length];
		
		tick();
		basins = findParallelBasins(matrix, oneDArray, basins);
		float time = tock();

		printBasinsToFile(basins);

		System.out.println(time);
	}
}

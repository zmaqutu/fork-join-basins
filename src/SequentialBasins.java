import java.util.*;
import java.util.concurrent.*;
import java.io.PrintWriter;
import java.io.*;
import java.util.ArrayList;

public class SequentialBasins
{
	static String fileName;
	static String outputFile;
	static int count = 0;
	static long startTime = 0;
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

                float grid[][] = new float[row][col];
		try
		{
			Scanner inputStream = new Scanner(file);
			inputStream.nextInt();
			inputStream.nextInt();
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
		return grid;
	}
	public static void printBasinsToFile(int count, ArrayList<String> basins)
	{
		try
		{
			PrintWriter outputStream = new PrintWriter(outputFile);
			outputStream.println(count);
			for(String cor: basins)
			{
			outputStream.print(cor + System.lineSeparator());
			}
			outputStream.close();

		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		//System.out.println(count);
		//basins.forEach(System.out::println);
	}
	private static void tick()
	{
		startTime = System.nanoTime();
	}
	private static float tock()
	{
		return(System.nanoTime() - startTime) / 1000000000.0f;
	}
	public static ArrayList<String> processMatrix(float [][] grid)
	{
		int row, col;
		int [] gridSize = new int[2];
                gridSize = getSize();

                row = gridSize[0];
                col = gridSize[1];
		ArrayList<String> basins = new ArrayList<>();
		float row1[] = new float[3];
                float row2[] = new float[3];
                float row3[] = new float[3];
                float center = 0;

                for(int i = 1; i < row-1; i++)
                {
                        for(int j = 1;j < col-1; j++) //col-1 is the last index, therefore j must me less than the last index strictly
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
                                        String rowString = Integer.toString(i);
                                        String colString = Integer.toString(j);
                                        String coordinates = rowString.concat(" " + colString);
                                        basins.add(coordinates);
                                        count++;
                                }
                        }
                }
		return basins;
	}
	public static void main(String [] args)
	{
		fileName = args[0];
		outputFile = args[1];
		ArrayList<String> basins = new ArrayList<String>();

		float [][] grid = read_from_file();
		tick();
		basins =  processMatrix(grid);
		float time = tock();
		printBasinsToFile(count,basins);
		System.out.println(time);
		
	}
}

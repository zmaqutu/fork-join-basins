import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;


public class average
{
	public static void main(String [] args)
	{
		String fileName = "../../output/ParallelTimeOutputs.txt";
		
		File file = new File(fileName);

		Scanner scan = new Scanner(System.in);

		float arr [] = new float[100];

		int i = 0;
		float average;
		float sum = 0;

		try
		{
			Scanner inputStream = new Scanner(file);

			while(inputStream.hasNext())
			{
				String data = inputStream.nextLine();

				float time = Float.parseFloat(data);

				arr[i] = time;

				i++;
			}
			inputStream.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		for(int j = 0; j < arr.length; j++)
		{
			sum = sum + arr[j];
		}
		average = sum/arr.length;

		String averageFile = "averageTIME.txt";

		try
		{
			PrintWriter outputStream = new PrintWriter(averageFile);
			outputStream.println("The average TIME is: " + average);
			outputStream.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

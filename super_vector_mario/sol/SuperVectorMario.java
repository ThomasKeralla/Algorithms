import edu.princeton.cs.algs4.*;
import java.util.*;
import java.math.*;

public class SuperVectorMario {

    private static int xLength;
    private static int yLength;
    private static int[][] track;
    private static int xVeloc;
    private static int yVeloc;
    private static ArrayList<int[]> startPos;
    private static ArrayList<int[]> finishPos;

    public SuperVectorMario(){}

    public static void main(String[] args) {
        
        yLength = StdIn.readInt();
        xLength = StdIn.readInt();

	System.err.println("x: " + xLength + " y: " + yLength);

    track = new int [yLength][xLength];
    startPos = new ArrayList<>();
    finishPos = new ArrayList<>();
	
	int yIndex = 0;
    
    StdIn.readLine(); // move to the first of track lines

	while (StdIn.hasNextLine()){
		String readLine = StdIn.readLine();
        for (int i=0; i < xLength; i++){
            char c = readLine.charAt(i);
			int intToInsert = 9;
			if (c == 'O') intToInsert = 0;
			else if (c == ' ') intToInsert = 1;
			else if (c == 'S') {
                intToInsert = 2;
                startPos.add(new int[]{yIndex,i});
            }
            else if (c == 'F') {
                intToInsert = 3;
                finishPos.add(new int[]{yIndex,i});

            }
			track[yIndex][i] = intToInsert;
        } 
        yIndex++;
	}
    printTwoDimArray(track);
    System.err.println();
    System.err.println("Starting coordinates: ");
    for (int[] arr : startPos){
        System.err.println("y: " + arr[0] + " x: " + arr[1]);
    }
    System.err.println("Finish coordinates: ");
    for (int[] arr : finishPos) {
        System.err.println("y: " + arr[0] + " x: " + arr[1]);
    }

    }

    private static void printTwoDimArray(int[][] arr){
    	for (int i=0; i<arr.length; i++){
		System.err.println(Arrays.toString(arr[i]));
	}
    }

}

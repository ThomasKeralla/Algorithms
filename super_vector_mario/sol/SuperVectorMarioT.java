import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Queue;
import java.util.*;
import java.math.*;

public class SuperVectorMarioT {

    private static int xLength;
    private static int yLength;
    private static char[][] track;
    private static HashMap<Integer, Boolean> map = new HashMap<>();
    private static ArrayList<int[]> startPos = new ArrayList<>();
    private static ArrayList<int[]> finishPos = new ArrayList<>();
    private static ArrayList<Integer> results = new ArrayList<>();

    // bfs Queue
    static Queue<int[]> qu = new Queue<>();

    public static void main(String[] args) {
        
        yLength = StdIn.readInt();
        xLength = StdIn.readInt();

        System.err.println("x: " + xLength + " y: " + yLength);

        track = new char [yLength][xLength];	
        
        StdIn.readLine(); // move to the first of track lines

        while (StdIn.hasNextLine()){
        
            for(int i = 0; i < yLength; i++) {
            String readLine = StdIn.readLine();
            for (int j=0; j < xLength; j++){
                char c = readLine.charAt(j);
                track[i][j] = c;
                
                if (c == 'S') {
                    startPos.add(new int[]{i,j,0,0,1});
                }
                else if (c == 'F') {
                    finishPos.add(new int[]{i,j});
                }	
            } 
        }

        for(int[] movement : startPos) {
            qu = new Queue<>();
            bfs(movement);    
        }

        int result = Integer.MAX_VALUE;
        for(int n : results){
            if(n < result)
                result = n;
        }

        StdOut.println(result);
        }
    }

    static void bfs(int[] movement) {

        qu.enqueue(movement);
        
        while(!qu.isEmpty()) {
            int[] pos = qu.dequeue();

            move(pos);

            for(int[] f : finishPos) {
                if(f[0] == pos[0] && f[1] == pos[1]) {
                    results.add(pos[4]);
                    return;
                }
            }
        }
    }

    static void move(int[] pos) {
        
        //new position values: position + valocity 
        int newPosY = pos[0] + pos[2];
        int newPosX = pos[1] + pos[3];
    
        if(((newPosY < 0) || (newPosY >= yLength)) || ((newPosX < 0) || (newPosX >= xLength))) {
            return;
        } else {
            //move
            pos[0] = newPosY;
            pos[1] = newPosX;

            if(track[newPosY][newPosX] != 'O' && map.get(Arrays.hashCode(pos))== null) {
            
                map.put(Arrays.hashCode(pos),true);
                int y = pos[0];
                int x = pos[1];
                int vy = pos[2];
                int vx = pos[3];
                int c = pos[4];
                qu.enqueue(new int[]{y,x,vy-1,vx-1,c+1});
                qu.enqueue(new int[]{y,x,vy-1,vx,c+1});
                qu.enqueue(new int[]{y,x,vy,vx-1,c+1});
                qu.enqueue(new int[]{y,x,vy,vx,c+1});
                qu.enqueue(new int[]{y,x,vy+1,vx+1,c+1});
                qu.enqueue(new int[]{y,x,vy+1,vx,c+1});
                qu.enqueue(new int[]{y,x,vy,vx+1,c+1});
                qu.enqueue(new int[]{y,x,vy-1,vx+1,c+1});
                qu.enqueue(new int[]{y,x,vy+1,vx-1,c+1});
            } 
        }
    }

}


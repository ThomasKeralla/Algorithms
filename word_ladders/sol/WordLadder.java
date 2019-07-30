import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Stopwatch;

public class WordLadder {

    public static void main(String[] args) {
        In in = new In(args[0]);
        Stopwatch timer = new Stopwatch();
        //StrDigraphThread G = new StrDigraphThread(in);
        //StrDigraphThread2 G = new StrDigraphThread2(in);
        StrDigraphThread G = new StrDigraphThread(in);
        //G.print();
        double graphtime = timer.elapsedTime();
        System.out.println("Time for creating graph = "+graphtime);
        System.out.println("Graph done, beginning BFS");
        double cleanBFStime = 0.0;
        double totalBFStime = 0.0;

        In pathCheck = new In(args[1]);
        String[] input = pathCheck.readAllLines(); 

        for(int i = 0; i < input.length; i++) {
            //System.out.println("PRINT "+input[i]);
            String source = input[i].substring(0,5);
            String pathTo = input[i].substring(input[i].length()-5);
            String indexPath = G.getIndexWordFromIndex(G.findSourceIndex(pathTo));
            double bfsBegin = timer.elapsedTime();
            StrBFS bfsInput = new StrBFS(G, source);
            cleanBFStime += (timer.elapsedTime()-bfsBegin);
            if (bfsInput.hasPathTo(indexPath)) {
                
                StdOut.printf("%s to %s (%d):  ", source, pathTo, bfsInput.distTo(indexPath));
                //StdOut.println(bfsInput.distTo(indexPath));

                for (int x : bfsInput.pathTo(indexPath)) {
                    if (G.getWordFromIndex(x).equals(source))
                        StdOut.print(G.getWordFromIndex(x));
                    else
                        StdOut.print(" -> " + G.getWordFromIndex(x));
                }
                StdOut.println();
            
            } else {
                StdOut.printf("%s to %s (-):  not connected\n", source, pathTo);
                //StdOut.println(-1);
            }
            totalBFStime += timer.elapsedTime() - bfsBegin;
        }
        System.out.println("Clean time: "+ cleanBFStime);
        System.out.println("Total BFS time: "+ totalBFStime);
        double bfsTime = timer.elapsedTime();
        System.out.println("Time total with findSourceIndex = "+ (bfsTime-graphtime));
   
    }

}


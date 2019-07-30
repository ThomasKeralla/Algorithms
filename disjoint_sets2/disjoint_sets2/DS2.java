import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.QuickUnionUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class DS2 {

  public static void main(String[] args){

    long start_time = System.currentTimeMillis();

    int range = StdIn.readInt();
    int number_of_operations = StdIn.readInt();

    // QuickFindUF unioner = new QuickFindUF(range);
    QuickUnionUF unioner = new QuickUnionUF(range);
    // WeightedQuickUnionUF unioner = new WeightedQuickUnionUF(range);

    System.err.println(String.format("range: %s operations: %s", range, number_of_operations));

    for (int i=0; i<number_of_operations; i++){
      String operation = StdIn.readString();
      int p = StdIn.readInt();
      int q = StdIn.readInt();

      // System.err.println(String.format("%s %s %s", operation,p,q));
      // System.err.println(String.format("Operation %s of %s",i,number_of_operations-1));

      if (operation.equals("q")){
        unioner.connected(p,q);
        // StdOut.println( unioner.connected(p,q) ? "yes":"no" );
      } else { // if operation == u
        unioner.union(p,q);
      }
    }
    long end_time = System.currentTimeMillis();
    long time_differnece = end_time - start_time;
    System.err.println(String.format("Finished running UnionFind, time elapsed: %s ms",time_differnece));
  }
}

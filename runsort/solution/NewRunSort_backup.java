import edu.princeton.cs.algs4.*;
// import java.util.*;

public class RunSort {

  public static int rounds = 0;
  public static int numMerges = 0;
  // private static ArrayList<TreeMap> indexes;

  // This class should not be instantiated.
  private RunSort(){}

  // stably merge a[lo..mid] with a[mid+1..hi] using aux[lo..hi]
  private static Comparable[] merge(Comparable[] a, int lo, int mid, int hi) {

    Comparable[] aux = new Comparable[a.length];

    // boolean mergeDebug = true;

    // copy to aux[]
    for (int k = lo; k <= hi; k++) {
      // p("k: "+k);
      aux[k] = a[k];
    }
    //
    // p("a");
    // printArrayWithIndexes(a);
    // p("aux");
    // printArrayWithIndexes(aux);
    // p("\n");

    // merge back to a[]
    int lowerIndex = lo;     // start index of lo - mid array
    int upperIndex = mid+1;  // start index of mid+1 - hi array

    for (int k = lo; k <= hi; k++) {

      if (lowerIndex > mid){ // if we have no more lower indexes, but have more upper, then take next upper
        // p("1: chose remaining upper items"); // : "+aux[upperIndex]);
        // get all of the reamining items from upper
        for (int z=k; z<=mid; z++){
          a[z] = aux[upperIndex];
          upperIndex++;
        }
        break; // break out of loop
        // if (upperIndex != hi) upperIndex++;
        // if (upperIndexesLeft != 0) upperIndexesLeft--;
      }
      else if (upperIndex > hi){ // if we have no more upper indexes, but have more lower, take those
        // p("2: chose remainign lower items"); //: "+aux[lowerIndex]);
        // get all of the remaining items from lower
        for (int x=k; x<=hi; x++){
          a[x] = aux[lowerIndex];
          lowerIndex++;
        }
        break; // break out of the loop
        // lowerIndexesLeft--;
      }

      else if (lLessThanR(aux[lowerIndex], aux[upperIndex])){ // if lower is less than upper, then take lower
        // p("3: chose lower item: "+aux[lowerIndex]);
        a[k] = aux[lowerIndex];
        lowerIndex++;
        // lowerIndexesLeft--;
      }
      else if (lLessThanR(aux[upperIndex], aux[lowerIndex])){ // else if upper is lower than lower, then take upper
        // p("4: chose upper item: "+aux[upperIndex]);
        a[k] = aux[upperIndex];
        upperIndex++;
        // if (upperIndex != hi) upperIndex++;
        // if (upperIndexesLeft != 0) upperIndexesLeft--;
      }
      else if (lSameAsR(aux[upperIndex],aux[lowerIndex])){ // if they are equal take one from both
        a[k] = aux[upperIndex];
        upperIndex++;
        k++;
        a[k] = aux[lowerIndex];
        lowerIndex++;
      }
      // todo remove

    }
    // p("inside merge");
    // printArrayWithIndexes(a);
    return a;
  }

  /**
   * Rearranges the array in ascending order, using the natural order.
   * @param a the array to be sorted
   */
  public static void sort(Comparable[] a) {
    boolean sortDebug = true;
    int n = a.length;

    // int lo = 0;
    // int mid  = 0;
    // int hi = 0;
    // find lo-mid indexes

    // int test = 0;

    // boolean moreToSortInRound = true;

    boolean aIsSorted = false;

    while (!aIsSorted){
      boolean moreToSortInRound = true;
      int lo = 0;
      int mid  = 0;
      int hi = 0;
      boolean shouldCallMerge = false;
      while (moreToSortInRound){
        // p("\nlo is "+lo);
        p(">> finding mid");
        for (int i=lo; i<n; i++){
          // p("i: "+i);
          if (i == n-1){
            p("mid: nothing more to sort: "+i+" "+n);
            moreToSortInRound = false;
            shouldCallMerge = false;
          } else {
            if (lLessThanR(a[i+1],a[i])){
              mid = i;
              shouldCallMerge = true;
              p("inside else if");
              break;
            }
            else if (lLessThanR(a[i],a[i+1]) || lSameAsR(a[i],a[i+1])){
              // p("updated mid - i: "+i);
              mid = i+1;
              shouldCallMerge = true;
            }
            else {
              p("break");
              break;
            }
          }
        }
        p("\n>> finding hi");
        for (int j=mid+1; j<n; j++){
          if (j == n-1){
            hi = j;
            shouldCallMerge = true;
            p("hi: nothing more to sort: "+j+" "+n);
            moreToSortInRound = false;
          } else {
            // p("j: "+j);
            if (lLessThanR(a[j+1],a[j])){
              hi = j;
              shouldCallMerge = true;
              p("inside else if");
              break;
            }
            else if (lLessThanR(a[j],a[j+1]) || lSameAsR(a[j],a[j+1])){
              // p("updated hi - j: "+j);
              hi = j+1;
              shouldCallMerge = true;
            }
            else {
              p("break");
              break;
            }
          }
        }

        if (shouldCallMerge && sortDebug){
          numMerges = numMerges + 1; //todo remove
          p("\n");
          printArrayWithIndexes(a);
          p(String.format("lo: %1$s mid: %2$s hi: %3$s",lo,mid,hi));
          p("Now calling merge..\n");
        }

        // actually merge
        if (shouldCallMerge){
          merge(a, lo, mid, hi);
          // printArrayWithIndexes(a);
        } else {
          p("should not call merge ...");
        }

        // p(lo+" "+hi);
        lo = hi+1;
        // p(mid+" "+lo);
        mid = lo;
        // p(hi+" "+lo);
        hi = lo;

        // p(hi+" < "+n);

        if (hi < n-1){
          moreToSortInRound = true;
        } else {
          moreToSortInRound = false;
        }

        // test++;
        // if (test == 2) moreToSortInRound = false;

        // index = hi;
        // moreToSortInRound = false;

      } // end of round

      rounds = rounds + 1;
      p("\tround "+rounds);


      p("is sorted?");
      System.out.println(isSorted(a));

      aIsSorted = isSorted(a);

      // if (rounds == 3) aIsSorted = true;

    } // should be sorted now

  }

  /***********************************************************************
  *  Helper sorting functions.
  ***************************************************************************/

  // is v < w ?
  private static boolean lLessThanR(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  private static boolean lSameAsR(Comparable v, Comparable w) {
    return v.compareTo(w) == 0;
  }

  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  /***************************************************************************
  *  Check if array is sorted - useful for debugging.
  ***************************************************************************/
  private static boolean isSorted(Comparable[] a) {
    for (int i = 1; i < a.length; i++)
        if (less(a[i], a[i-1])) return false;
    return true;
  }

  // print array to standard output
  private static void show(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
        StdOut.println(a[i]);
    }
  }

  private static void printArrayWithIndexes(Comparable[] a){
    for (int i=0; i<a.length; i++){ System.err.print(i + " "); }
    System.err.println();
    for (int j=0; j<a.length; j++){ System.err.print(a[j] + " "); }
    System.err.println();
  }

  private static void p(String s){
    System.err.println(s);
  }

  /**
   * Reads in a sequence of strings from standard input; bottom-up
   * mergesorts them; and prints them to standard output in ascending order.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    String[] a = StdIn.readAllStrings();
    // String[] a = {"S","O","R","T","E","X","A","M","P","L","E"};
    // String[] a = {"R","U","N","S","O","R","T","E","X","A","M","P","L","E"};
    // String[] a = {"R","U","N","S","O","R","T","E","X","A","M"};
    System.err.println(); // print a blank line before result
    RunSort.sort(a);
    show(a);
    //todo remove debug
    p("\n\n***** debug *****");
    printArrayWithIndexes(a);
    p("totalNumber of merges: " + numMerges);
    p("total num of rounds " + rounds);
  }
}

import edu.princeton.cs.algs4.*;
// import java.util.*;

public class RunSort {

  // This class should not be instantiated.
  private RunSort(){}

  private static Comparable[] merge(Comparable[] a, int lo, int mid, int hi) {

    Comparable[] aux = new Comparable[a.length];

    // copy to aux[]
    for (int k = lo; k <= hi; k++) {
      aux[k] = a[k];
    }

    // merge back to a[]
    int lowerIndex = lo;     // start index of lo - mid array
    int upperIndex = mid+1;  // start index of mid+1 - hi array

    for (int k = lo; k <= hi; k++) {
      if (lowerIndex > mid){ // if we have no more lower indexes, but have more upper, then take next upper
        for (int z=k; z<=mid; z++){
          a[z] = aux[upperIndex];
          upperIndex++;
        }
        break;
      }
      else if (upperIndex > hi){ // if we have no more upper indexes, but have more lower, take those
        for (int x=k; x<=hi; x++){
          a[x] = aux[lowerIndex];
          lowerIndex++;
        }
        break;
      }
      else if (lLessThanR(aux[lowerIndex], aux[upperIndex])){ // if lower is less than upper, then take lower
        a[k] = aux[lowerIndex];
        lowerIndex++;
      }
      else if (lLessThanR(aux[upperIndex], aux[lowerIndex])){ // else if upper is lower than lower, then take upper
        a[k] = aux[upperIndex];
        upperIndex++;
      }
      else if (lSameAsR(aux[upperIndex],aux[lowerIndex])){ // if they are equal take one from both
        a[k] = aux[upperIndex];
        upperIndex++;
        k++;
        a[k] = aux[lowerIndex];
        lowerIndex++;
      }
    }
    return a;
  }

  public static void sort(Comparable[] a) {
    boolean sortDebug = true;
    int n = a.length;
    boolean aIsSorted = false;
    while (!aIsSorted){
      boolean moreToSortInRound = true;
      int lo = 0;
      int mid  = 0;
      int hi = 0;
      boolean shouldCallMerge = false;
      while (moreToSortInRound){
        for (int i=lo; i<n; i++){
          if (i == n-1){
            moreToSortInRound = false;
            shouldCallMerge = false;
          } else {
            if (lLessThanR(a[i+1],a[i])){
              mid = i;
              shouldCallMerge = true;
              break;
            }
            else if (lLessThanR(a[i],a[i+1]) || lSameAsR(a[i],a[i+1])){
              mid = i+1;
              shouldCallMerge = true;
            }
            else {
              break;
            }
          }
        }
        for (int j=mid+1; j<n; j++){
          if (j == n-1){
            hi = j;
            shouldCallMerge = true;
            moreToSortInRound = false;
          } else {
            if (lLessThanR(a[j+1],a[j])){
              hi = j;
              shouldCallMerge = true;
              break;
            }
            else if (lLessThanR(a[j],a[j+1]) || lSameAsR(a[j],a[j+1])){
              hi = j+1;
              shouldCallMerge = true;
            }
            else {
              break;
            }
          }
        }
        if (shouldCallMerge){
          merge(a, lo, mid, hi);
        }
        lo = hi+1;
        mid = lo;
        hi = lo;
        if (hi < n-1){ moreToSortInRound = true;
        } else { moreToSortInRound = false;}
      } // end of round
      aIsSorted = isSorted(a);
    } // should be sorted now
  }

  private static boolean lLessThanR(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  private static boolean lSameAsR(Comparable v, Comparable w) {
    return v.compareTo(w) == 0;
  }

  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

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

  public static void main(String[] args) {
    String[] a = StdIn.readAllStrings();
    RunSort.sort(a);
    show(a);
  }
}

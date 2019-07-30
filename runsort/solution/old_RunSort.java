import edu.princeton.cs.algs4.*;

public class RunSort {

  private static Comparable[] aux; // auxiliary array for merges

  // public static void sort(Comparable[] a) {
  //   // Do lg N passes of pairwise merges.
  //   int N = a.length;
  //   aux = new Comparable[N];
  //   for (int sz = 1; sz < N; sz = sz+sz) // sz: subarray size
  //     for (int lo = 0; lo < N-sz; lo += sz+sz) // lo: subarray index
  //       merge(a, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
  // }



  // public static void merge(Comparable[] a, int lo, int mid, int hi){
  //   StdOut.println("lo " + lo + " mid " + mid + " hi " + hi);
  //
  //   aux = new Comparable[hi-lo];
  //
  //   // copy to aux[]
  //   for (int k = lo; k < hi; k++) {
  //       aux[k] = a[k];
  //   }
  //   // show(aux);
  //
  //   // merge back to a[]
  //   int i = lo; // 3
  //   int j = mid; // 3
  //   // k 4
  //   // hi 4
  //   for (int k = lo; k < hi; k++) {
  //       if      (i > mid)              a[k] = aux[j++];
  //       else if (j > hi)               a[k] = aux[i++];
  //       else if (less(aux[j], aux[i])) a[k] = aux[j++];
  //       else                           a[k] = aux[i++];
  //   }
  //
  // }

  public static void merge(Comparable[] a, int low, int middle, int hi) {
    int index = 0;
    aux = new Comparable[a.length];

    int lo = low;
    int mid = middle;


    while (lo < mid) {
      while(mid < hi) {
        if(a[lo].compareTo(a[mid]) > 0) {
          aux[index++] = a[lo++];
          break;
        } else {
          aux[index++] = a[mid++];
        }
      }

      if(mid == hi-1) {
        for(int x=index; x < hi; x++) {
          aux[x] = a[lo++];
        }

      }
      if(lo == mid-1) {
        for(int y=index; y < mid; y++) {
          aux[y] = a[mid++];
        }
      }

    }
  }

  // for(int i=0; lo < mid; i++) {
  //   for(int mid=midd; mid < hi; mid++) {
  //     if(a[lo].compareTo(a[mid]) > 0) {
  //       aux[index++] = a[lo];
  //       lo++;
  //       break;
  //     } else {
  //       aux[index++]= a[mid];
  //     }
  //   }



  public static void sortRun (Comparable[] a) {
    int N = a.length;
    for(int i=0; 0<N; i++) {
      for(int j=i; j<N-1; j++) {
        if(a[j].compareTo(a[j+1]) > 0){
          for(int n=j+1; n < N-1; n++) {
            if(a[n].compareTo(a[n+1]) > 0) {
              merge(a, i, j, n);
              i = n;
              break;
            }
          }
          break;
        }
      }
      break;
    }
  }


  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }
  private static void show(Comparable[] a) {
      for (int i = 0; i < a.length; i++) {
          StdOut.println(a[i]);
      }
  }

  public static void main(String[] args) { // Read strings from standard input, sort them, and print.
    // String[] a = StdIn.readAllStrings();
    String[] a = StdIn.readAllStrings();
    // Integer[] a = new Integer[b.length];
    // for (int i=0; i<b.length; i++){
    //   a[i] =(Integer) b[i];
    // }
    sortRun(a);
    // assert isSorted(a);
    show(a);
  }

}






// public static void merge(Comparable[] a, int lo, int mid, int hi){
//   StdOut.println("lo " + lo + " mid " + mid + " hi " + hi);
//
//   int i = lo;
//   int j = mid + 1;
//
//   for(int x =0; x<a.length;x++){
//   }
//
//   for (int k = lo; k <= hi; k++){
//     aux[k] = a[k];
//   }
//
//   for (int k = lo; k <= hi; k++){
//     if      (i > mid)              { a[k] = aux[j++]; }
//     else if (j > hi)               { a[k] = aux[i++]; }
//     else if (less(aux[j], aux[i])) { a[k] = aux[j++]; }
//     else                           { a[k] = aux[i++]; }
//   }
// }



// public static void alt_sortRun(Comparable[] a){
//   int N = a.length;
//
//   // int i = 0;
//   int lo = 0;
//   int mid = 0;
//   int hi = 0;
//
//   for (int i=0; i<N; i++){
//     StdOut.println("i "+i);
//
//     for (int j=i; j<N-1; j++){
//       StdOut.println(a[j] + " compared to " + a[j+1] + "comparison "+ (a[j].compareTo(a[j+1])) );
//       if (a[j].compareTo(a[j+1]) < 0){
//         StdOut.println("should move to finding hi");
//
//         for (int n=mid; n<N; n++){
//
//           StdOut.println(a[j] + " compared to " + a[n] + "comparison "+ (a[j].compareTo(a[n])) );
//
//           if (a[j].compareTo(a[n]) < 0){
//             StdOut.println("lo " + lo + " mid " + mid + " hi " + hi);
//             break;
//           } else {
//             hi = n;
//           }
//         }
//
//         break;
//       } else {
//         mid = j;
//       }
//
//     }
//     // i = hi;
//   }
// }

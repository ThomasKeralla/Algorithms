import edu.princeton.cs.algs4.*;

public class AltRunSort {

  private RunSort(){}

  public static void sort (Comparable[] a) {

    int numb_runs= 1;
    //ArrayList<Integer> log = new ArrayList<>();
    // aux = new Comparable[a.length];
    Comparable[] aux = new Comparable[a.length];
    int N = a.length;
    int[] log = new int[a.length];
    int log_index= 0;

    for(int i=0; 0<N-1; i++) {
      for(int j=i; j<N-1; j++) {
        if(a[j].compareTo(a[j+1]) < 0){
          for(int n=j+1; n < N-1; n++) {
            if(a[n].compareTo(a[n+1]) < 0) {
              merge(a, aux, i, j, n);
              i = n;
              numb_runs++;
              log[log_index++]= n;
              //log.add(n);
              break;
            }
          }
        }
        break;
      }
    }
    int floor = 0;
    //int lo_ceil = log.get(0);
    //int hi_ceil = log.get(1);
    int lo_ceil = log[0];
    int hi_ceil = log[1];
    while(numb_runs > 1) {
      if(hi_ceil == log.length-1) {
        floor = 0;
        lo_ceil = log[0];
        hi_ceil = log[1];
        log_index = 0;
      }
      merge(a, aux, floor, lo_ceil, hi_ceil);
      floor = floor+2;
      lo_ceil = lo_ceil+2;
      hi_ceil = hi_ceil+2;
      numb_runs = numb_runs-1;
      log[log_index++]=hi_ceil;
    }
  }

    //set list a equal to aux
    // a = aux;

  private static void merge(Comparable[] a, Comparable[] aux, int low, int middle, int hi) {
    int index = 0;
    int lo;
    int mid ;
    for(lo=low; lo < middle; lo++) {
      for(mid=middle; mid < hi; mid++) {
        if(a[lo].compareTo(a[mid]) > 0) {
          //this should work but otherwise remove mid-- and make a break
          //also remove lo--
          aux[index++] = a[lo++];
          //a[index++] = a[lo++];
          mid--;
        } else {
          aux[index++]= a[mid];
          //a[index++]= a[mid];
          lo--;
        }
      }
    if(mid == hi-1) {
      for(int i = index; i< hi; i++) {
        //a[index++] = a[lo++];
        aux[index++] = a[lo++];
      }
    }
      if(lo == mid-1) {
        for(int j =index; j< hi; j++) {
          //a[index++] = a[mid++];
          aux[index++] = a[mid++];
        }
      }
    }
  }


  private static void show(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      StdOut.println(a[i]);
    }
  }

  public static void main(String[] args) { // Read strings from standard input, sort them, and print.
    // String[] a = StdIn.readAllStrings();
    String[] a = StdIn.readAllStrings();
    StdOut.println(a.length);
    // Integer[] a = new Integer[b.length];
    // for (int i=0; i<b.length; i++){
    //   a[i] =(Integer) b[i];
    // }
    sort(a);
    // assert isSorted(a);
    show(a);
  }
}

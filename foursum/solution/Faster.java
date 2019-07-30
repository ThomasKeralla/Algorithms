import java.util.Scanner;
import java.util.Arrays;
import java.util.Collection;
import edu.princeton.cs.algs4.*;

public class Faster
{
public static int indexOf(long[] a, long key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
                // Key is in a[lo..hi] or not present.
                int mid = lo + (hi - lo) / 2;
                if      (key < a[mid]) hi = mid - 1;
                else if (key > a[mid]) lo = mid + 1;
                else return mid;
        }
        return -1;
}
public static void main(String[] args)
{
        Scanner S= new Scanner(System.in);
        int N = Integer.parseInt(S.nextLine());
        long[] vals = new long[N];
        for(int i= 0; i < N; i+= 1) vals[i] = Long.parseLong(S.nextLine());

        long[] unsorted = vals;
        Arrays.sort(vals);

        for (int i = 0; i<N; ++i) {
                for (int j = i+1; j<N; ++j) {
                        for (int k = j+1; k<N; ++k) {
                                        long sumThree = unsorted[i]+unsorted[j]+unsorted[k];
                                          if (indexOf(vals, -sumThree) > k)
                                          {
                                            System.out.println("True");
                                            System.exit(0);
                                          }
                        }
                }
        }
        System.out.println("False");
        System.exit(0);
      }
}

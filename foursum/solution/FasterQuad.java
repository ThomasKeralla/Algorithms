import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import edu.princeton.cs.algs4.*;

public class FasterQuad
{

  private static class Sum implements Comparable<Sum>{
    public int first;
    public int second;
    public long sum;

    public static boolean noCommon(int aFirst, int aSecond, int bFirst, int bSecond){
      if(aFirst == bFirst || aFirst == bSecond
          || aSecond == bFirst || aSecond == bSecond){
            return false;
          }
          return true;
    }

    @Override
    public int compareTo(Sum that) {
        return Long.compare(this.sum, that.sum);
    }
  }

public static int choose(int total, int choose){
    if(total < choose)
        return 0;
    if(choose == 0 || choose == total)
        return 1;
    return choose(total-1,choose-1)+choose(total-1,choose);
}

public static void main(String[] args)
{
        Scanner S= new Scanner(System.in);
        int N = Integer.parseInt(S.nextLine());
        long[] vals = new long[N];
        for(int i= 0; i < N; i+= 1) vals[i] = Long.parseLong(S.nextLine());

        int size = choose(N, 2);
        Sum []pairs = new Sum[size];
        int index = 0;

        for(int i = 0; i<pairs.length; i++){
          pairs[i] = new Sum();
        }

        for(int i=0; i<N; i++) {
                for(int x=i+1; x<N; x++) {
                  pairs[index].sum = vals[i] + vals[x];
                  pairs[index].first = i;
                  pairs[index].second = x;
                  index++;
                }
        }

        Arrays.sort(pairs);

        int i = 0;
        int j = size -1;
        while(i < size && j >= 0){
          if((pairs[i].sum + pairs[j].sum == 0) && Sum.noCommon(pairs[i].first, pairs[i].second, pairs[j].first, pairs[j].second)){
            System.out.println("True");
            System.exit(0);
          }else if(pairs[i].sum + pairs[j].sum < 0){
            i++;
          }else{
            j--;
          }
        }
        System.out.println("False");
        System.exit(0);
      }
}

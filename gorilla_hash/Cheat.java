
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.*;

import java.util.*;
import java.math.*;

public class Hashing {
    public static int k = 20;
    public static int d = 10000;
    public static int current = 0;
    public static ArrayList<String> species = new ArrayList<String>();
    public static HashMap<String, int[]> st = new HashMap<String, int[]>();
    public static HashSet<String> hs;

    public static void main(String[] args) {


        StringBuilder sb = new StringBuilder("");
        In file = new In(args[0]);
        while (file.hasNextLine()) {	// while file not empty
            String s = file.readLine();
            if (s.startsWith(">")) {	//if new species
                species.add(s);

                if (sb.length() != 0) {		
                    calculateProfile(sb);
                    sb = new StringBuilder("");
                    current++;
                }
            } else {
                sb.append(s);
            }

        }
        calculateProfile(sb); // since we calculate the profile after reading the next ">",
        sb = new StringBuilder(""); // we need to add this to calculate the profile of the last element
        current++;

        StdOut.println("--------------------------------------------------");

        for (int i = 0; i < species.size(); i++) {	// print all the results
            StdOut.println(species.get(i));
            StdOut.println("--------------------------------------------------");
            for (int j = 0; j < species.size(); j++) {
                StdOut.println(species.get(j));
                StdOut.println(cosinus(st.get(species.get(i)), st.get(species.get(j))));
            }
            StdOut.println("--------------------------------------------------");
        }


    } 

    public static void calculateProfile(StringBuilder sb) {
        int[] profile = new int[d];
        splitt(sb, profile);
        st.put(species.get(current), profile);

    }

    public static void splitt(StringBuilder sb, int[] profile) {	// form all kgrams and 
        hs = new HashSet<>();
        for (int i = 0; i <= sb.length() - k; i++) {
            String kgram = sb.substring(i, k + i);
            if (!hs.contains(kgram)) {
                int kGramHash = hash(kgram);
                profile[kGramHash]++;
                hs.add(kgram);
            }
        }
    }


    public static int hash(String str) {
        return ((str.hashCode() & 0x7fffffff) % d);
    }

    public static double lengthVec(int[] vector) {
        int sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum = sum + (vector[i] * vector[i]);
        }
        double res = Math.sqrt(sum);
        return res;
    }

    public static long scalarProduct(int[] v1, int[] v2) {
        long sum = 0;
        for (int i = 0; i < v1.length && i < v2.length; i++)
            sum += v1[i] * v2[i];
        return sum;
    }

    public static String cosinus(int[] v1, int[] v2) {
        return String.format("%2f", (scalarProduct(v1, v2) / (lengthVec(v1) * lengthVec(v2))));
    }

}



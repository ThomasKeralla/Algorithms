import java.util.Arrays;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;

public class StrDigraph {

    public String[] input;
    public Bag[] adjacency; 

    int wordLength;

    public StrDigraph(In in) {
        
        wordLength = 5; //could use args[1] for this

        input = in.readAllLines();
        for(int n = 0; n < input.length; n++)
            input[n] = n + input[n];
        
        //System.out.println(Arrays.toString(input));

        adjacency = new Bag[input.length];

        for(int i = 0; i < adjacency.length; i++)
            adjacency[i] = new Bag<String>();
   
        makeConnections();
    }

    public void print() {
        for(int i = 0; i < input.length; i++) {
            System.err.println("printing for: "+ input[i].substring(input[i].length() - wordLength));
            Bag<String> b = adjacency[i];
            if(b.isEmpty())
                System.err.println("NULL");
            else
                for(String s : b) {
                    System.err.print(" "+s.substring(s.length() - wordLength) +" ");
                }
            System.err.println();
        }
        System.err.println();
    }

    public int V(){
        return input.length;
    }



    private void makeConnections() {
        for(int i = 0; i < input.length;i++) {
            for(int j = 0; j < input.length; j++) {
                if(input[i].equals(input[j]))
                    continue;
                if(check(input[i],input[j]))
                    adjacency[i].add(input[j]);
            }
        }
    }

    private boolean check(String v, String w) {
        char[] vValue = v.substring((v.length() - wordLength) +1).toCharArray();
        String wValue = w.substring(w.length() - wordLength);

        for (int i = 0; i < wordLength-1; i++) {
            if (!wValue.contains(""+vValue[i])) {
                return false;
            } else {
                wValue = wValue.substring(0, wValue.indexOf(""+vValue[i]))
                        + wValue.substring(wValue.indexOf(""+vValue[i]) + 1);
            }
        }
        return true;
    }

    public int findSourceIndex(String s) {
        if(s.length()-wordLength > 0) //if word with index is used
            return getWordIndex(s);
        for(int i = 0; i < input.length; i++){
            if(input[i].substring(input[i].length()-wordLength).equals(s))
                return i;
        }
        throw new RuntimeException("String not in graph");
    }

    public int getWordIndex(String s) {
        return Integer.parseInt(s.substring(0, s.length()-wordLength));
    }

    public String getWordFromIndex(int index) {
        return input[index].substring(input[index].length()-wordLength);
    }
    public String getIndexWordFromIndex(int index) {
        return input[index];
    }

}
import java.util.Arrays;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class StrDigraphThread {

    public String[] input;
    public Bag[] adjacency; 

    int wordLength;
    Thread[] threads;
    Stopwatch timer;

    //timings
    double indexTime;
    double makeConnectionsStart;
    double makeConnectionsend;
    double findSourceIndexStart;
    double findSourceIndexEnd;

    public StrDigraphThread(In in) {
        
        wordLength = 5; //could use args[1] for this

        input = in.readAllLines();
        System.out.println("inputlength: "+input.length);

        timer = new Stopwatch();
        if(input.length > 1000) {
            //large input, run it threaded
            System.out.println("Threadpool: "+getThreadNumber(50));
            threads = new Thread[getThreadNumber(50)];
            adjacency = new Bag[input.length];
            System.out.println("THREADED!!! INPUT_SIZE:"+input.length+" adj_size: "+adjacency.length);
        
        for(int j = 0; j < threads.length; j++) {
            final int count = j;
            final int stripeSize = input.length/threads.length;

            threads[j] = new Thread(() -> {
                for(int n = count*stripeSize; n < (count*stripeSize + stripeSize); n++) {
                    input[n] = n + input[n];
                    adjacency[n] = new Bag<String>();
                } 
            });
            threads[j].start();
        }
        for(int j = 0; j < threads.length; j++) {
                try {
                threads[j].join();
                } catch(InterruptedException e) {}
            } 
        } else {
            adjacency = new Bag[input.length];
            //not large input just run it serialized
        for(int n = 0; n < input.length; n++) {
            input[n] = n + input[n];
            adjacency[n] = new Bag<String>();
            }
        }
        double indexTime = timer.elapsedTime();
        System.out.println("Done assigning index: "+indexTime);
        //System.out.println(Arrays.toString(input));
   
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

    public int getThreadNumber(int n) {
        for(int i = n; i > 0; i--) {
            if(input.length % i == 0)
                return i;
        }
        return 1;
    }



    private void makeConnections() {
        makeConnectionsStart = timer.elapsedTime();
        Thread[] t2 = new Thread[getThreadNumber(100)];
        for(int n = 0; n < t2.length; n++) {
            final int count = n;
            final int stripeSize = input.length/t2.length;

            t2[n] = new Thread(() -> {
                
                for(int i = count * stripeSize; i < (count * stripeSize)+stripeSize; i++) {
                    for(int j = 0; j < input.length; j++) {
                        if(input[i].equals(input[j]))
                            continue;
                        if(check(input[i],input[j]))
                            adjacency[i].add(input[j]);
                    }
                }  
            });
            t2[n].start();
        }

        for(int i = 0; i < t2.length; i++) { 
            try {
                t2[i].join();
            } catch(InterruptedException e) {}
        }
        makeConnectionsend = timer.elapsedTime();
        System.out.println("makeConnection time: "+(makeConnectionsend-makeConnectionsStart));
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
        findSourceIndexStart = timer.elapsedTime();
            AtomicBoolean search = new AtomicBoolean(true);
            AtomicInteger result = new AtomicInteger(-1);
        Thread[] t = new Thread[getThreadNumber(50)];
        for(int n = 0; n < t.length; n++) {
            final int count = n;
            final int stripeSize = input.length/t.length;
            t[n] = new Thread(()-> {
                int index = count * stripeSize;
                int upper = count * stripeSize + stripeSize;
                while(search.get() && index < upper) {
                    if(input[index].substring(input[index].length()-wordLength).equals(s)) {
                        if(search.compareAndSet(true,false))
                            result.getAndSet(index);
                    }
                    index++;
                }
            });
            t[n].start();
        }

        for(int n = 0; n < t.length; n++) {
            try {
                t[n].join();
            } catch(InterruptedException e) {}
        }
        if(result.get() != -1) {
            return result.get();
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
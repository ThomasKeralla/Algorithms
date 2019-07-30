import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class DSufm {
    private int[] parent;  // parent[i] = parent of i
    private int count;     // number of components
    private Node[] nodes;

    private class Node{
      public int parentNode;
      public int[] childs;
      public int size;
      public int node;

      public Node(){
        size = 0;
        parentNode = -1;
      }

      public void addChild(int x){

        if(size != 0){
          int [] temp = new int[size];
          for(int i=0; i<size; i++){
            temp[i] = childs[i];
          }
          size++;
          childs = new int[size];
          int i=0;
          for(Integer z: temp){
            if(x != z){
              childs[i] = z;
              i++;
            }
          }

          childs[size-1] = x;
        }else{
          size++;
          childs = new int[size];
          childs[0] = x;
        }
      }

      public void deleteChild(int x){
        if(size == 1){
          childs = null;
          size--;
          return;
        }
        if(size<1){
          return;
        }
        boolean flag = false;
        for(Integer i: childs){
          if(i == x){
            flag = true;
          }
        }
        if(!flag){return;}

        int [] temp = childs;
        childs = new int[size-1];
        int i = 0;
        for(Integer z: temp){
          if(x != z){
            childs[i] = z;
            i++;
          }
        }
        if(size>0){
          size--;
        }

      }

      public void moved(){
        childs = null;
        size = 0;
      }
    }


    public DSufm(int n) {
        parent = new int[n];
        nodes = new Node[n];
        count = n;
        for (int i = 0; i < n; i++) {
            Node node = new Node();
            nodes[i] = node;
            parent[i] = i;
        }
    }


    public int count() {
        return count;
    }


    public int find(int p) {

        if(!validate(p)){
          return -1;
        }
        while (p != parent[p]){
            p = parent[p];
          }
        return p;
    }

    // validate that p is a valid index
    private boolean validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
          return false;
            // throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
        return true;
    }


    public boolean query(int p, int q) {
      if(find(p) == -1 || find(q) == -1){
        return false;
      }
        return find(p) == find(q);
    }


    public void union(int p, int q) {

        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        parent[rootP] = rootQ;
        nodes[p].parentNode = rootQ;
        nodes[rootQ].addChild(p);
        count--;
    }

    public void unionLeaves(int p, int q) {
        int rootQ = find(q);
        int rootP = find(p);
        if (rootP == rootQ) return;
        parent[p] = rootQ;
        nodes[p].parentNode = rootQ;
        nodes[rootQ].addChild(p);
        count--;
    }

    public void move(int s, int t){
      if(query(s, t)){
        return;
      }
      if(nodes[s].size == 0 && nodes[s].parentNode == -1 && find(s)==s){
        nodes[s].moved();
        union(s, t);
        return;
      }
      if(find(s)!=s){

        int rootS = find(s);
        if(nodes[s].size != 0){
          for(int i=0; i<nodes[s].size; i++){
            parent[nodes[s].childs[i]] = rootS;
            nodes[nodes[s].childs[i]].parentNode = rootS;
          }

        }
        if(nodes[s].parentNode != -1){
          nodes[nodes[s].parentNode].deleteChild(s);
        }

        nodes[s].moved();
        unionLeaves(s, t);
      }else{

        if(nodes[s].size != 0){

          int firstChild = nodes[s].childs[0];
          nodes[firstChild].parentNode = -1;
          parent[firstChild] = firstChild;
          for(int i=1; i<nodes[s].size; i++){
            parent[nodes[s].childs[i]] = firstChild;
            nodes[nodes[s].childs[i]].parentNode = firstChild;
          }
        }

        nodes[s].moved();
        unionLeaves(s, t);
      }
    }

    public void printParents(){
      System.err.println();
      for (int i=0; i < parent.length; i++){
        System.err.print(i+" ");
      }
      System.err.print(" index i");
      System.err.println();
      for (int i=0; i < parent.length; i++){
        System.err.print(parent[i]+" ");
      }
      System.err.print(" parent[i]");
      System.err.println();
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        DSufm uf = new DSufm(n);
        int x = StdIn.readInt();
        if(n<0)return;
        while (!StdIn.isEmpty()) {
            String com = StdIn.readString();
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            System.err.println("\ninput "+com+" "+p+" "+q);
            if(q>-1 && p>-1 && q<n && p<n){
            if(com.equals("q")){
              if(uf.query(p, q) == true){
                StdOut.println("yes");
              }else{
                StdOut.println("no");
              }
            }else if(com.equals("u")){
              uf.union(p, q);
            }else if(com.equals("m")){
              uf.move(p, q);
            }
          }
          uf.printParents();
        }
    }
}

import edu.princeton.cs.algs4.*;

public class Quick_union {

  private int[] id;
  private int count;
  private int id_array_accesses;

  public Quick_union(int number_of_components){
    id_array_accesses = 0;
    count = number_of_components;
    id = new int[number_of_components];
    for (int i=0; i<number_of_components; i++){
      id[i] = i;
    }
  }

  public int count(){
    return count;
  }

  public boolean check_if_connected(int p, int q){
    // StdOut.println(String.format("%s %s",find(p),find(q)));
    return find(p) == find(q);
  }

  public static void main(String[] args){

    int number_of_components = StdIn.readInt();
    StdOut.println(String.format("Number of components: %s", number_of_components));
    StdOut.println();

    Quick_union union_finder = new Quick_union(number_of_components);

    while (!StdIn.isEmpty()){

      int p = StdIn.readInt();
      int q = StdIn.readInt();

      if (union_finder.check_if_connected(p,q)){
        StdOut.println(String.format("Already unioned %s and %s", p, q));
        continue;
      }

      union_finder.union(p,q);
      StdOut.println(String.format("Unioned %s and %s", p, q));
    }

    StdOut.println();
    StdOut.println("Finished running Quick_union ...");
    union_finder.print_id_array_accesses();
    union_finder.print_id_array_contents();
  }

  public void print_id_array_accesses(){
    StdOut.println(String.format("id array was accessed %s number of times", id_array_accesses));
  }

  public void print_id_array_contents(){
    StdOut.print("          ");
    for (int i=0; i<10; i++){
      StdOut.print(i+" ");
    }
    StdOut.println();
    StdOut.print("id array: ");
    for (int i = 0 ; i < id.length ; i++){
      StdOut.print(id[i]+" ");
    }
    StdOut.println();
  }

  public void id_array_was_accessed(){
    id_array_accesses++;
  }

  /*
    ___  _   _ ___ ___ _  __  _   _ _  _ ___ ___  _  _
   / _ \| | | |_ _/ __| |/ / | | | | \| |_ _/ _ \| \| |
  | (_) | |_| || | (__| ' <  | |_| | .` || | (_) | .` |
   \__\_\\___/|___\___|_|\_\  \___/|_|\_|___\___/|_|\_|
  */

  public int find(int p){
    while (p != id[p]){
      p = id[p];
      id_array_was_accessed();
    }
    return p;
  }

  public void union(int p, int q){
    int p_root = find(p);
    int q_root = find(q);

    if (p_root == q_root){
      return;
    }

    id[p_root] = q_root;
    id_array_was_accessed();
  }

}

import edu.princeton.cs.algs4.*;

public class Union_find {

  private int[] id;
  private int count;
  private int id_array_accesses;

  public Union_find(int number_of_components){
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
    return find(p) == find(q);
  }

  public static void main(String[] args){

    int number_of_components = StdIn.readInt();
    StdOut.println(String.format("Number of components: %s", number_of_components));
    StdOut.println();

    Union_find union_finder = new Union_find(number_of_components);

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
    StdOut.println("Finished running Union_find ...");
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
    ___  _   _ ___ ___ _  __  ___ ___ _  _ ___
   / _ \| | | |_ _/ __| |/ / | __|_ _| \| |   \
  | (_) | |_| || | (__| ' <  | _| | || .` | |) |
   \__\_\\___/|___\___|_|\_\ |_| |___|_|\_|___/
  */

  public int find(int p){
    id_array_was_accessed();
    return id[p];
  }
  public void union(int p, int q){
    // Put p and q into the same component.
    int pID = find(p);
    int qID = find(q);

    // Nothing to do if p and q are already unioned
    if (pID == qID) return;

    // Rename p’s component to q’s name.
    for (int i = 0; i < id.length; i++){
      if (id[i] == pID){
        id[i] = qID;
        id_array_was_accessed();
      }else{
        id_array_was_accessed();
      }
    }
  }


}

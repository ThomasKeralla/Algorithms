import java.util.Iterator;
import java.util.NoSuchElementException;

import java.lang.RuntimeException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;



public class RandomQueue<Item> implements Iterable<Item> {
  private Item[] queue;
  private int numberOfItems;
  // Your code goes here. 
  // Mine takes ca. 60 lines, my longest method has 5 lines.
    
    public RandomQueue() { // create an empty random queue
      queue = (Item[]) new Object[0]; 
      numberOfItems = 0;
    }
    public boolean isEmpty() {// is it empty?
      if(numberOfItems == 0)
        return true;
      else 
        return false;
    }

    public int size() {// return the number of elements
      return numberOfItems;
    }

    public void enqueue(Item item) {// add an item
      if(size() == 0) {
        queue = (Item[]) new Object[2]; 
      }
        queue[numberOfItems] = item;
        numberOfItems++;

        if(numberOfItems == queue.length) {
          Item[] temp = (Item[]) new Object[2*queue.length];
          for(int i=0; i<queue.length;i++) {
          temp[i] = queue[i];
        }
        queue = temp;
      }
    }

    public Item sample(){ // return (but do not remove) a random item
      return queue[StdRandom.uniform(numberOfItems)];
    }

    public Item dequeue(){ // remove and return a random item
      if(isEmpty()) {
        throw new RuntimeException();
      }
      int index = StdRandom.uniform(numberOfItems);
      Item returnItem = queue[index];
      queue[index] = queue[numberOfItems-1];
      queue[numberOfItems-1] = null;
      numberOfItems--;

      if(numberOfItems == queue.length / 4) {
        Item[] temp = (Item[]) new Object[queue.length / 2];
        for(int i=0; i<numberOfItems;i++) {
          temp[i] = queue[i];
        }
        queue = temp;
      } 
      return returnItem;
    }

    public Iterator<Item> iterator() { // return an iterator over the items in random order
        return new RandomQueueIterator(numberOfItems, queue);
    }

    private class RandomQueueIterator implements Iterator<Item> {
      private int numberOfItems;
      private Item[] queue;

      public RandomQueueIterator(int numberOfItems, Item[] queue) {
        this.numberOfItems = numberOfItems;
        this.queue = queue.clone();
      }

      public boolean hasNext()  {
        if(numberOfItems != 0) {
          return true;
        }
        else {
          return false;
        }
      }

      public void remove(){ throw new UnsupportedOperationException();  }

      public Item next() {
        if (!hasNext()){ 
          throw new NoSuchElementException();
        }
        int randomIndex = StdRandom.uniform(numberOfItems);
        Item returnItem = queue[randomIndex];
        Item temp = queue[numberOfItems-1];
        queue[randomIndex] = temp;
        numberOfItems--;
        return returnItem; 
      }
    }
    
  // The main method below tests your implementation. Do not change it.
    public static void main(String args[])
  {
    // Build a queue containing the Integers 1,2,...,6:
    RandomQueue<Integer> Q= new RandomQueue<Integer>();
    for (int i = 1; i < 7; ++i) Q.enqueue(i); // autoboxing! cool!
 
    // Print 30 die rolls to standard output
    StdOut.print("Some die rolls: ");
    for (int i = 1; i < 30; ++i) StdOut.print(Q.sample() +" ");
    StdOut.println();

    // Let's be more serious: do they really behave like die rolls?
    int[] rolls= new int [10000];
    for (int i = 0; i < 10000; ++i)
      rolls[i] = Q.sample(); // autounboxing! Also cool!
    StdOut.printf("Mean (should be around 3.5): %5.4f\n", StdStats.mean(rolls));
    StdOut.printf("Standard deviation (should be around 1.7): %5.4f\n",
      StdStats.stddev(rolls));
    
    // Now remove 3 random values
    StdOut.printf("Removing %d %d %d\n", Q.dequeue(), Q.dequeue(), Q.dequeue());
    // Add 7,8,9
    for (int i = 7; i < 10; ++i) Q.enqueue(i); 
    // Empty the queue in random order
    while (!Q.isEmpty()) StdOut.print(Q.dequeue() +" ");
    StdOut.println();

    // Let's look at the iterator. First, we make a queue of colours:
    RandomQueue<String> C= new RandomQueue<String>();
    C.enqueue("red"); C.enqueue("blue"); C.enqueue("green"); C.enqueue("yellow"); 

    Iterator<String> I= C.iterator();
    Iterator<String> J= C.iterator();

    StdOut.print("Two colours from first shuffle: "+I.next()+" "+I.next()+" ");
    
    StdOut.print("\nEntire second shuffle: ");
    while (J.hasNext()) StdOut.print(J.next()+" ");

    StdOut.println("\nRemaining two colours from first shuffle: "+I.next()+" "+I.next());
  }
}

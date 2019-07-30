/*
Algorithms And Data Structures Exercises week 1
*/

import edu.princeton.cs.algs4.*;

class exercises_week1{

  public static void main(String[] args) {

    // 1.1.3
    // int x = Integer.parseInt(args[0]);
    // int y = Integer.parseInt(args[1]);
    // int z = Integer.parseInt(args[2]);
    // compare_three_ints(x,y,z);

    //1.1.7b
    // one_one_seven_b();
    // result 499500

    //1.1.7c
    // one_one_seven_c();
    // result: 1023

    //1.1.8
    // one_one_eight();
    /* result:
    b
    197
    e
    */

    //1.1.14
    // System.out.println(""+lg(4096));
    // System.out.println(""+lg(256));

    //1.2.4
    one_two_four();
    /* result:
    world
    hello
    */

  }

  //1.2.4
  public static void one_two_four(){
    String string1 = "hello";
    String string2 = string1;
    string1 = "world";
    StdOut.println(string1);
    StdOut.println(string2);
  }

  // 1.1.14
  public static int lg(int N){
    int count = 0;
    int sum = 1;
    while (2*sum <= N){
      sum = sum * 2;
      count++;
    }
    return count;
  }

  // 1.1.3
  public static void compare_three_ints(int x, int y, int z){
    if (x == y && y == z){
      System.out.println("equal");
    }else{
      System.out.println("not equal");
    }
  }

  // 1.1.7b
  public static void one_one_seven_b(){
    int sum = 0;
    for (int i = 1; i < 1000; i++){
      for (int j = 0; j < i; j++){
        sum++;
      }
    }
    StdOut.println(sum);
  }

  // 1.1.7c
  public static void one_one_seven_c(){
    int sum = 0;
    for (int i = 1; i < 1000; i *= 2){
      for (int j = 0; j < i; j++){
        sum++;
      }
    }
    StdOut.println(sum);
  }

  // 1.1.8
  public static void one_one_eight(){
    System.out.println('b');
    System.out.println('b' + 'c');
    System.out.println((char) ('a' + 4));
  }

}

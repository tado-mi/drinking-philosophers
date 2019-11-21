public class Test {

 private static final int x = 5;
 private final Integer[] integer = new Integer[x];

 public Test() {
  System.out.println(integer.length);
 }

 public static void main(String[] args) {
  Test test1 = new Test(); 
 }
 
}

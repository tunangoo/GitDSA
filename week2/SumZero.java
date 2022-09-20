import edu.princeton.cs.algs4.*;

public class SumZero {
    public static void main(String[] args) {
        //In in = new In("D:\\CN1 - K66 - UET\\HK1 - 22_23\\INT2210_25\\coursera\\algs4-data\1Kints.txt");
        int[] arr = new int[10000];
        int n = StdIn.readInt();
        for(int i = 0; i < n; i++) {
            arr[i] = StdIn.readInt();
        }
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++) {
                if(arr[i] + arr[j] == 0) {
                    StdOut.println(arr[i] + " " + arr[j]);
                }
            }
        }
    }
}

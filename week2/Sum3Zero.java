import edu.princeton.cs.algs4.*;

public class Sum3Zero {
    public static void main(String[] args) {
        int[] arr = new int[10000];
        int n = StdIn.readInt();
        for(int i = 0; i < n; i++) {
            arr[i] = StdIn.readInt();
        }
        for(int i = 0; i < n; i++) {
            for(int j = i + 1; j < n; j++){
                for(int t = j + 1; t < n; t++) {
                    if(arr[i] + arr[j] + arr[t] == 0) {
                        StdOut.println(arr[i] + " " + arr[j] + " " + arr[t]);
                    }
                }
            }
        }
    }
}

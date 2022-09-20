import edu.princeton.cs.algs4.*;

public class UFClient2 {
    public static void main(String[] args) {
        //In in = new In("D:\\CN1 - K66 - UET\\HK1 - 22_23\\INT2210_25\\coursera\\algs4-data\\tiny.txt");
        int N = StdIn.readInt();
        UF uf = new UF(N);
        int cnt = 0;
        while(!StdIn.isEmpty() && uf.count() > 1) {
            cnt++;
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if(p < 0 || q < 0) {
                System.out.println("FAILED");
                return;
            }
            if(!uf.connected(p, q)) {
                uf.union(p, q);
            }
        }
        if(uf.count() == 1) {
            StdOut.println(cnt);
        }
        StdOut.println("FAILED");
    }
}

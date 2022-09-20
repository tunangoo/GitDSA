import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    final static int MAXN = 100005;
    int[] bit = new int[MAXN];
    void update(int u, int w) {
        while(u < MAXN) {
            bit[u] += w;
            u += u&-u;
        }
    }
    int get(int u) {
        int sum = 0;
        while(u > 0) {
            sum += bit[u];
            u -= u&-u;
        }
        return sum;
    }

    public static void minimumBribes(List<Integer> q) {
        int n = q.size();
        int[] a = new int[MAXN];
        int ans = 0;
        Result ft = new Result();
        for(int i = 1; i <= n; i++) {
            int x;
            x = q.get(i - 1);
            ft.update(i, 1);
            a[i] = x;
        }
        for(int i = 1; i <= n; i++) {
            int x = ft.get(a[i] - 1);
            if(x > 2) {
                ans = -1000000000;
            }
            ans += x;
            ft.update(a[i], -1);
        }
        if(ans < 0) {
            System.out.println("Too chaotic");
        } else {
            System.out.println(ans);
        }
    }

}

public class NewYearChaos {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> q = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList());

                Result.minimumBribes(q);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
    }
}

import java.util.Arrays;
import java.util.Scanner;

public class BinarySearch {
    public static int binarySearch(int[] a, int number) {
        int l = 0, r = a.length;
        while(l <= r) {
            int m = (l + r) / 2;
            if(a[m] < number) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        if(l < a.length && a[l] == number) {
            return l;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for(int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        int number = scanner.nextInt();
        System.out.println(binarySearch(a, number));
    }
}

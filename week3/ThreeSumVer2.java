import java.util.Arrays;
import java.util.Scanner;

public class ThreeSumVer2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] a = new int[n];
        for(int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        Arrays.sort(a);
        int countThreeSum = 0;
        for(int i = 0; i < n; i++) {
            int left = n - 1, right = n - 1;
            for(int j = i + 1; j < n; j++) {
                if(right <= j) {
                    break;
                }
                left = Math.max(left, j);
                while(left > j && a[left] + a[i] + a[j] >= 0) left--;
                while(right > j && a[right] + a[i] + a[j] > 0) right--;
                countThreeSum += right - left;
            }
        }
        System.out.println(countThreeSum);
    }
}

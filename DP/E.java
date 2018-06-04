import java.io.*;
import java.math.BigInteger;
import java.util.*;
 
public class K {
 
    private static int MAX = 1000_000_000;
 
    public static void main(String[] args) throws IOException {
 
        //String fileInName = "nice.in";
        //String fileOutName = "nice.out";
 
        //File file = new File(fileInName);
        //PrintWriter writer = new PrintWriter(new FileWriter(fileOutName));
        //Scanner sc = new Scanner(file);
        Scanner sc = new Scanner(System.in);
 
        int n = sc.nextInt();
        if (n == 0) {
            System.out.println(0);
            System.out.println(0 + " " + 0);
            return;
        }
        int[] cost = new int[n];
        for (int i = 0; i < n; i++) {
            cost[i] = sc.nextInt();
        }
        int[][] arr = new int[n][n + 1 + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = MAX;
            }
        }
        if (cost[0] <= 100) {
            arr[0][0] = cost[0];
        } else {
            arr[0][1] = cost[0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < arr[0].length - 1; j++) {
                if (j == 0) {
                    if (cost[i] <= 100) {
                        arr[i][j] = Math.min(arr[i - 1][j] + cost[i], arr[i - 1][j + 1]);
                    } else {
                        arr[i][j] = arr[i - 1][j + 1];
                    }
                } else {
                    if (cost[i] <= 100) {
                        arr[i][j] = Math.min(arr[i - 1][j] + cost[i], arr[i - 1][j + 1]);
                    } else {
                        arr[i][j] = Math.min(arr[i - 1][j + 1], arr[i - 1][j - 1] + cost[i]);
                    }
                }
            }
        }
        int end = 0;
        int min = MAX;
        for (int j = 0; j < arr[0].length; j++) {
            if (min >= arr[n - 1][j]) {
                end = j;
                min = arr[n - 1][j];
            }
        }
        System.out.println(min);
        //System.out.println(end + " " + (n - end)/2);
        //System.out.println((n - end)/2);
        int k2 = end;
 
 
        ArrayList<Integer> ans = new ArrayList<>();
        int kolvo = 0;
        for (int i = n - 1; i > 0; i--) {
            if (arr[i][end] == arr[i - 1][end + 1]) {
                ans.add(i + 1);
                kolvo++;
                end++;
            } else if (cost[i] > 100) {
                end--;
            }
        }
 
        System.out.println(k2 + " " + kolvo);
        for (int i = ans.size() - 1; i >= 0; i--) {
            System.out.println(ans.get(i));
        }
 
        //System.out.println(str);
        //System.out.println(s.length() - result(0, s.length() - 1, arr, s));
        //System.out.println(arr[0][s.length() - 1]);
 
        sc.close();
    }
}

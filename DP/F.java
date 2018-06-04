import java.io.*;
import java.math.BigInteger;
import java.util.*;
 
public class K {
 
    private static int MAX = 1000_000_000;
 
 
 
    public static boolean isPair(char ch1, char ch2) {
        if (ch1 == '(') {
            if (ch2 == ')') {
                return true;
            } else {
                return false;
            }
        } else
        if (ch1 == '{') {
            if (ch2 == '}') {
                return true;
            } else {
                return false;
            }
        } else
        if (ch1 == '[') {
            if (ch2 == ']') {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
 
    public static int result(int l, int r, int[][] arr, String s) {
        if (arr[l][r] != MAX) {
            //System.out.println("12345678");
            return arr[l][r];
        } else if (l == r) {
            arr[l][r] = 1;
            return 1;
        } else if (l == r - 1) {
            if (isPair(s.charAt(l), s.charAt(r))) {
                arr[l][r] = 0;
 
                return 0;
            } else {
                arr[l][r] = 2;
                return 2;
            }
        } else {
            int otv1 = 0;
            if (isPair(s.charAt(l), s.charAt(r))) {
                otv1 = result(l + 1, r - 1, arr, s);
            } else {
                otv1 = MAX;
            }
            int otv2 = MAX;
            for (int i = l; i < r; i++) {
                int temp = result(l, i, arr, s) + result(i + 1, r, arr, s);
                if (otv2 > temp) {
                    otv2 = temp;
                }
            }
            arr[l][r] = Math.min(otv1, otv2);
            return arr[l][r];
        }
    }
 
    public static void main(String[] args) throws IOException {
 
        //String fileInName = "nice.in";
        //String fileOutName = "nice.out";
 
        //File file = new File(fileInName);
        //PrintWriter writer = new PrintWriter(new FileWriter(fileOutName));
        //Scanner sc = new Scanner(file);
        Scanner sc = new Scanner(System.in);
 
        String s = sc.next();
 
        int[][] arr = new int[s.length()][s.length()];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                arr[i][j] = MAX;
            }
        }
        System.out.println(s.length() - result(0, s.length() - 1, arr, s));
        //System.out.println(arr[0][s.length() - 1]);
 
        sc.close();
    }
}

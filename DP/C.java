import java.io.*;
import java.math.BigInteger;
import java.util.*;
 
public class K {
 
    public static boolean might(int i, int j, int n) {
        boolean flag = true;
        int ipr = 3;
        int jpr = 4;
        int m = 0;
        while (m < n) {
            if ((i%2) == (j%2) && jpr == (i%2) && ipr == jpr) {
                flag = false;
            } else {
                ipr = i%2;
                jpr = j%2;
            }
            m++;
            j /= 2;
            i /= 2;
        }
        return flag;
    }
 
    public static void main(String[] args) throws IOException {
 
        //String fileInName = "nice.in";
        //String fileOutName = "nice.out";
 
        //File file = new File(fileInName);
        //PrintWriter writer = new PrintWriter(new FileWriter(fileOutName));
        //Scanner sc = new Scanner(file);
        Scanner sc = new Scanner(System.in);
 
        int n = sc.nextInt();
 
        BigInteger[][] arr = new BigInteger[10][n + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i][1] = BigInteger.valueOf(1);
        }
        for (int i = 2; i < n + 1; i++) {
            arr[0][i] = arr[4][i-1].add(arr[6][i-1]);
            arr[1][i] = arr[6][i-1].add(arr[8][i-1]);
            arr[2][i] = arr[7][i-1].add(arr[9][i-1]);
            arr[3][i] = arr[4][i-1].add(arr[8][i-1]);
            arr[4][i] = arr[0][i-1].add(arr[3][i-1]).add(arr[9][i-1]);
            arr[6][i] = arr[0][i-1].add(arr[1][i-1]).add(arr[7][i-1]);
            arr[7][i] = arr[2][i-1].add(arr[6][i-1]);
            arr[8][i] = arr[1][i-1].add(arr[3][i-1]);
            arr[9][i] = arr[2][i-1].add(arr[4][i-1]);
        }
 
        BigInteger answer = BigInteger.valueOf(0);
        if (n == 1) {
            System.out.println(8);
        } else {
            for (int i = 0; i < 10; i++) {
                if (i != 0 && i != 8 && i != 5) {
                    answer = answer.add(arr[i][n]);
                }
 
            }
            System.out.println(answer.mod(BigInteger.valueOf(1000000000)));
        }
 
        sc.close();
    }
}

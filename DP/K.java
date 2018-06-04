import java.io.*;
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
 
        String fileInName = "nice.in";
        String fileOutName = "nice.out";
 
        File file = new File(fileInName);
        PrintWriter writer = new PrintWriter(new FileWriter(fileOutName));
        Scanner sc = new Scanner(file);
 
        int m = sc.nextInt();
        int n = sc.nextInt();
 
        if (n >= m) {
            int t = m;
            m = n;
            n = t;
        }
        int answer = 0;
        int k = (int)Math.pow(2,n);
        int [][] opport = new int[k][k];
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                if (might(i, j, n)) {
                    opport[i][j] = 1;
                }
            }
        }
 
        int[][] ans = new int[m][k];
        for (int i = 0; i < k; i++) {
            ans[0][i] = 1;
        }
 
        for (int f = 0; f < m - 1; f++) {
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < k; j++) {
                    if (opport[j][i] == 1) {
                        ans[f+1][i] += ans[f][j];
                    }
                }
            }
        }
        for (int i = 0; i < k; i++) {
            answer += ans[m - 1][i];
        }
        writer.println(answer);
        writer.close();
    }
}

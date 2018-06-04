import java.util.*;
import java.io.*;
 
public class D {
 
 
 
    public static void main(String[] args) {
        try {
 
            String fileIn = "input.txt";
            String fileOut = "output.txt";
            File file = new File(fileIn);
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            Scanner sc = new Scanner(file);
 
            String word1 = sc.next();
 
            String word2 = sc.next();
            int n = word1.length();
            int m = word2.length();
            int[][] arr = new int[m+1][n+1];
            for (int i = 1; i <= m; i++) {
                arr[i][0] = i;
            }
            for (int i = 1; i <= n; i++) {
                arr[0][i] = i;
            }
 
            for (int j = 1; j < n+1; j++) {
                for (int i = 1; i < m+1; i++) {
                    if (word1.charAt(j-1) == word2.charAt(i-1)) {
                        arr[i][j] = Math.min(Math.min(arr[i-1][j] + 1, arr[i][j-1] + 1), arr[i-1][j-1]);
                    } else {
                        arr[i][j] = Math.min(Math.min(arr[i-1][j] + 1, arr[i][j-1] + 1), arr[i-1][j-1] + 1);
                    }
                }
            }
            writer.println(arr[m][n]);
 
            sc.close();
            writer.close();
        } catch (FileNotFoundException e){
 
        } catch (IOException e){
        }
    }
}

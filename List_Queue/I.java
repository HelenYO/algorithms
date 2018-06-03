import java.util.*;
import java.io.*;
public class I {
 
    public static void main(String[] args) {
        try {
            String fileIn = "hemoglobin.in";
            String fileOut = "hemoglobin.out";
            File file = new File(fileIn);
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            BufferedReader in = new BufferedReader(new FileReader(fileIn));
             
            int n = Integer.valueOf(in.readLine());
            int[] arr = new int[n];
            int k = -1;
            for (int i = 0; i < n; i++) {
                String s = in.readLine();
                if (s.charAt(0) == '+') {
                    k++;
                    arr[k] = Integer.parseInt(s.substring(1,s.length()));
                } else if (s.charAt(0) == '-') {
                    writer.println(arr[k]);
                    k--;
                } else if (s.charAt(0) == '?') {
                    int sum = 0;
                    int m = Integer.parseInt(s.substring(1,s.length()));
                    for (int j = k; j > k-m; j--) {
                        sum += arr[j];
                    }
                    writer.println(sum);
                }
            }
             
            in.close();
            writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
}

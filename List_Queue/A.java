import java.util.*;
import java.io.*;
public class A {
 
    public static void main(String[] args) {
        try {
            char[] arr = new char[200000];
            String fileIn = "decode.in";
            String fileOut = "decode.out";
            File file = new File(fileIn);
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            Scanner sc = new Scanner(file);
            String s = sc.next();
            int k = 0;
            int n = -1;
            while(k < s.length()) {
                char temp = s.charAt(k);
                if (n >= 0) {
                    if (arr[n] == temp) {
                        n = n - 1;
                    } else {
                        arr[n + 1] = temp;
                        n ++;
                    }
                } else {
                    arr[n + 1] = temp;
                    n ++;
                }
                k++;
            }
            for (int i = 0; i < n + 1; i++) {
                writer.print(arr[i]);
            }
            //writer.print(123);
            sc.close();
            writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
}

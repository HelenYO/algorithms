import java.util.*;
import java.io.*;
public class B {
 
    public static void main(String[] args) {
        try {
            int[] arr = new int[10000];// (-1; )--1;[-2;]--2;
            String fileIn = "brackets.in";
            String fileOut = "brackets.out";
            File file = new File(fileIn);
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            Scanner sc = new Scanner(file);
            String s = sc.next();
            int k = 0;
            int n = -1;
            while(k < s.length()) {
                char temp = s.charAt(k);
                int tem;
                if (temp == '(') {
                    tem = 1;
                } else if (temp == ')') {
                    tem = -1;
                } else if (temp == '[') {
                    tem = 2;
                } else if (temp == ']') {
                    tem = -2;
                } else if (temp == '{') {
                    tem = 3;
                } else {
                    tem = -3;
                }
                 
                if (n >= 0) {
                    if (arr[n] + tem == 0 && arr[n] > 0) {
                        n = n - 1;
                    } else {
                        arr[n + 1] = tem;
                        n++;
                    }
                } else {
                    arr[n + 1] = tem;
                    n++;
                }
                k++;
            }
            if (n == -1) {
                writer.print("YES");
            } else {
                writer.print("NO");
            }
            sc.close();
            writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
}

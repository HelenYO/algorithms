import java.util.*;
import java.io.*;
public class C {
 
    public static void main(String[] args) {
        try {
            int[] arr = new int[100];
            String fileIn = "postfix.in";
            String fileOut = "postfix.out";
            File file = new File(fileIn);
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            Scanner sc = new Scanner(file);
            //String s = sc.next();
            //int k = 0;
            int n = -1;
            while(sc.hasNext()) {
                char temp = sc.next().charAt(0);
                if (temp == '+') {
                    int a = arr[n-1];
                    int b = arr[n];
                    arr[n-1] = a + b;
                    n--;
                } else if (temp == '-') {
                    int a = arr[n-1];
                    int b = arr[n];
                    arr[n-1] = a - b;
                    n--;
                } else if (temp == '*') {
                    int a = arr[n-1];
                    int b = arr[n];
                    arr[n-1] = a * b;
                    n--;
                } else {
                    arr[n+1] = Integer.parseInt(temp+"");
                    n++;
                }
            }
            writer.print(arr[0]);
            //writer.print(123);
            sc.close();
            writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
}

import java.util.*;
import java.io.*;
 
public class D {
 
 
 
    public static void main(String[] args) {
/*
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] otv = new int[n];
        int[] values = new int[n];
 
        for (int i = 0; i < n; i++) {
            values[i] = sc.nextInt();
        }
 
        int[] prev = new int[n];
        for (int i = 0; i < n; i++) {
            prev[i] = -1;
        }
 
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (values[i] > values[j] && otv[j] >= otv[i]) {
                    otv[i] = otv[j] + 1;
                    prev[i] = j;
                }
            }
        }
 
        int max = 0;
        int num = 0;// номер максимального ответа
        for (int i = 0; i < n; i++) {
            if (otv[num] < otv[i]) {
                num = i;
            }
        }
 
        System.out.println(otv[num] + 1);
 
        int[] ans = new int[otv[num] + 1];
        int number = n - 1;
 
        for (int i = otv[num] ; i >= 0; i--) {
            ans[i] = values[num];
            num = prev[num];
        }
 
        for (int i = 0; i < ans.length; i++) {
            System.out.print((ans[i]) + " ");
        }
        sc.close();
        */
        try {
 
            String fileIn = "input.txt";
            String fileOut = "output.txt";
            File file = new File(fileIn);
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            Scanner sc = new Scanner(file);
 
            int n = sc.nextInt();
            int k = sc.nextInt();
            int[] values = new int[n + 1];
            for (int i = 2; i < n; i++) {
                values[i] = sc.nextInt();
            }
            /*for (int i = 1; i <= n; i++) {
                System.out.print(values[i]);
            }
            System.out.println();*/
            int[] ans = new int[n + 1];
            int[] prev = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                ans[i] = -10_000;
            }
            ans[1] = 0;
 
            for (int i = 2; i <= n; i++) {
                for (int j = 1; j <= k; j++) {
                    if (i - j < 1) {
                        break;
                    }
                    //System.out.println(j);
                    if (ans[i - j] + values[i] > ans[i]) {
                        prev[i] = i - j;
                        ans[i] = ans[ i - j] + values[i];
                    }
                }
            }
 
            /*for (int i = 1; i <= n; i++) {
                System.out.print(ans[i]);
            }
            System.out.println();*/
 
            /*for (int i = 1; i <= n; i++) {
                System.out.print(prev[i]);
            }
            System.out.println();*/
 
            writer.println(ans[n]);
            System.out.println(ans[n]);
 
            int[] out = new int[n];
            int cht = n;
            int num = 0;
            while (cht > 1) {
                out[num] = cht;
                cht = prev[cht];
                num++;
            }
            System.out.println(num);
            writer.println(num);
            writer.print(1 + " ");
            System.out.print(1 + " ");
            for (int i = num - 1; i >= 0; i--) {
                System.out.print(out[i] + " ");
                writer.print(out[i] + " ");
            }
 
            sc.close();
            writer.close();
        } catch (FileNotFoundException e){
 
        } catch (IOException e){
        }
    }
}

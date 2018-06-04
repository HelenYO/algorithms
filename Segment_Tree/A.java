import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
 
public class A {
 
    public static void main(String[] args) throws FileNotFoundException{
        String fileIn = "sum0.in";
        String fileOut = "sum0.out";
 
        int INDENT16 = (1 << 16) - 1;
        int INDENT30 = (1 << 30) - 1;
 
        Scanner sc = new Scanner(new File(fileIn));
        int n = sc.nextInt();
        long x = sc.nextLong();
        long y = sc.nextLong();
        long a0 = sc.nextLong();
 
        long[] a = new long[n];
 
        int m = sc.nextInt();
        int z = sc.nextInt();
        int t = sc.nextInt();
        int b0 = sc.nextInt();
 
        //long[] b = new long[m * 2];
 
        a[0] = a0;
        for (int i = 1; i < n; i++) {
            a[i] = (a[i - 1] * x + y) & INDENT16;
        }
 
 
        /*b[0] = b0;
        for (int i = 1; i < 2 * m; i++) {
            b[i] = (b[i - 1] * z + t) & INDENT30;
        }*/
 
 
        long[] sums = new long[n];
        sums[0] = a[0];
        for (int i = 1; i < n; i++) {
            sums[i] = sums[i - 1] + a[i];
        }
 
        long sum = 0;
 
        int b2i = b0;
        int b2i1 = (b2i * z + t) & INDENT30;
        for (int i = 0; i < m; i++) {
            int c2i = b2i % n;
            int c2i1 = b2i1 % n;
            int cmin = Math.min(c2i, c2i1);
            int cmax = Math.max(c2i, c2i1);
            if (cmin != 0) {
                sum += (sums[cmax] - sums[cmin - 1]);
            } else {
                sum += sums[cmax];
            }
            b2i = (b2i1 * z + t) & INDENT30;
            b2i1 = (b2i * z + t) & INDENT30;
            //System.out.println(sum);
        }
 
        PrintWriter writer = new PrintWriter(new File(fileOut));
        writer.print(sum);
        writer.close();
        //System.out.println(sum);
 
    }
}

import java.io.*;
import java.util.StringTokenizer;
//import java.util.Scanner;
 
class Scanner {
    BufferedReader br;
    StringTokenizer st;
 
    Scanner(File inName) {
        try {
            br = new BufferedReader(new FileReader(inName));
        } catch (FileNotFoundException e) {
        }
    }
 
    String next() {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
            }
        }
        return st.nextToken();
    }
 
    int nextInt() {
        int radix = Integer.parseInt(next());
        return radix;
    }
 
    long nextLong() {
        long radix = Long.parseLong(next());
        return radix;
    }
 
    boolean hasNextLine() throws IOException{
        return br.ready();
    }
}
 
public class B {
    public static void main(String[] args) throws FileNotFoundException {
 
        String fileIn = "rsq.in";
        String fileOut = "rsq.out";
        Scanner sc = new Scanner(new File(fileIn));
 
        int n = sc.nextInt();
        int size = findNear(n);
        long[] a = new long[size];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextLong();
        }
        long[] tree = new long[4 * n];
        build(tree, a, 1, 0, size - 1);
        try(PrintWriter writer = new PrintWriter(new FileWriter(fileOut))) {
            while (sc.hasNextLine()) {
                String comand = sc.next();
                if (comand.equals("sum")) {
                    long temp = sum(tree, 1, 1, size - 1, sc.nextInt(), sc.nextInt());
                    writer.println(temp);
                } else {
                    update(tree, 1, 0, size - 1, sc.nextInt() - 1, sc.nextLong());
                }
            }
        } catch (IOException e) {
 
        }
    }
 
    private static int findNear(int n) {
        /*int temp = 1;
        int N = n;
        while (N  > temp) {
            temp *= 2;
        }
        return temp;*/
        if (n == 0) return 0;
        if (n == 1) return 2;
        int k = 0;
        while (n != 1) {
            n /= 2;
            k++;
        }
        return 1 << k + 1;
    }
 
    private static long sum (long[] t, int v, int tl, int tr, int l, int r) {
        if (l > r)
            return 0;
        if (l == tl && r == tr)
            return t[v];
        int tm = (tl + tr) / 2;
        return sum (t,v*2, tl, tm, l, Math.min(r,tm))
                + sum (t,v*2+1, tm+1, tr, Math.max(l,tm+1), r);
    }
 
    private static void update (long[] t, int v, int tl, int tr, int pos, long val) {
        if (tl == tr) {
            t[v] = val;
        }
        else {
            int tm = (tl + tr) / 2;
            if (pos <= tm) {
                update(t, v * 2, tl, tm, pos, val);
            }
            else {
                update(t, v * 2 + 1, tm + 1, tr, pos, val);
            }
            t[v] = t[v*2] + t[v*2+1];
        }
    }
 
    private static void build (long[] t, long a[], int v, int tl, int tr) {
        if (tl == tr) {
            t[v] = a[tl];
        }
        else {
            int tm = (tl + tr) / 2;
            build (t, a, v*2, tl, tm);
            build (t, a, v*2 + 1, tm+1, tr);
            t[v] = t[v * 2 + 1] + t[v * 2];
        }
    }
}

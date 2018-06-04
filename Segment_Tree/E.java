import java.io.*;
import java.util.StringTokenizer;
//import java.util.Scanner;
 
class Matrix {
    long a;
    long b;
    long c;
    long d;
 
    public Matrix(long a, long b, long c, long d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
 
 
public class E {
 
    private static Matrix[] tree;
    private static Matrix E = new Matrix(1, 0, 0, 1);
    private static int r;
    //private static Matrix[] a;
 
    static class Scanner implements AutoCloseable {
        BufferedReader br;
        StringTokenizer st;
 
        Scanner(InputStream is) {
            br = new BufferedReader(new InputStreamReader(is));
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
 
        public void close() throws Exception {
            br.close();
        }
    }
 
    public static void main(String[] args) throws FileNotFoundException {
 
        Scanner sc = new Scanner(new FileInputStream(new File("crypto.in")));
 
        r = sc.nextInt();
        int n = sc.nextInt();
        int m = sc.nextInt();
 
        final Matrix[]a = new Matrix[n];
        for (int i = 0; i < n; i++) {
            a[i] = new Matrix(sc.nextLong(), sc.nextLong(), sc.nextLong(), sc.nextLong());
        }
        tree = new Matrix[4 * n];
        build(a,1, 0, n - 1);
 
        try (PrintWriter writer = new PrintWriter(new File("crypto.out"))) {
            for(int i = 0 ; i < m ; i++) {
                Matrix temp = sum(1, 0, n - 1, sc.nextInt() - 1, sc.nextInt() - 1);
                writer.println(temp.a + " " + temp.b + "\n" + temp.c + " " + temp.d + "\n");
            }
        } catch (IOException e) {
 
        }
    }
 
    static Matrix sum(int v, int tl, int tr, int l, int r) {
        if (l > r)
            return E;
        if (l == tl && r == tr) {
            return tree[v];
        }
        int tm = (tl + tr) / 2;
        return multi(sum(2 * v, tl, tm, l, Math.min(tm, r)),
                sum(2 * v + 1, tm + 1, tr, Math.max(tm + 1, l), r));
    }
 
    private static Matrix multi(Matrix left, Matrix right) {
        long a,b,c,d;
        a = (left.a * right.a + left.b * right.c) % r;
        b = (left.b * right.d + left.a * right.b) % r;
        c = (left.d * right.c + left.c * right.a) % r;
        d = (left.d * right.d + left.c * right.b) % r;
        return new Matrix(a,b,c,d);
    }
 
    static void build(Matrix[] a, int v, int tl, int tr) {
        if (tl == tr) {
            tree[v] = a[tl];
            return;
        }
        int tm = (tl + tr) / 2;
        build(a, 2 * v, tl, tm);
        build(a, 2 * v + 1, tm + 1, tr);
        tree[v] = multi(tree[2 * v], tree[2 * v + 1]);
    }
}

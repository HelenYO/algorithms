import java.io.*;
import java.util.StringTokenizer;
 
class Scanner implements AutoCloseable {
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
 
    boolean hasNextLine() throws IOException {
        return br.ready();
    }
 
    public void close() throws IOException {
        br.close();
    }
}
 
public class L {
    final static int MAX = Integer.MAX_VALUE;
    static int[] tree;
    private static int n;
 
    private static void update(int v, int tl, int tr, int place, int value) {
        if (tl == tr) {
            tree[v] = value;
            return;
        }
        int tm = (tl + tr) / 2;
        if (place > tm) {
            update(2 * v + 1, tm + 1, tr, place, value);
        } else {
            update(2 * v, tl, tm, place, value);
        }
        tree[v] = Math.min(tree[2 * v], tree[2 * v + 1]);
    }
 
    private static int enter(int v, int tl, int tr, int l, int r) {
        if (l > r) {
            return MAX;
        }
        if (l == tl && r == tr) {
            return tree[v];
        }
        int tm = (tl + tr) / 2;
        return Math.min(
                enter(v * 2, tl, tm, l, Math.min(r, tm)),
                enter(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r)
        );
    }
 
    private static int findNear(int n) {
        if (n == 0) return 0;
        if (n == 1) return 2;
        int k = 0;
        while (n != 1) {
            n /= 2;
            k++;
        }
        return 1 << k + 1;
    }
 
    public static void main(String[] args) throws FileNotFoundException {
        String fileIn = "parking.in";
        String fileOut = "parking.out";
        Scanner sc = new Scanner(new File(fileIn));
        PrintWriter writer = new PrintWriter(new File(fileOut));
        n = sc.nextInt();
        int m = sc.nextInt();
        tree = new int[4 * n];
        int npro = findNear(n);
        build(1, 0, npro - 1);
        for (int i = 0; i < m; i++) {
            String temp = sc.next();
            if (temp.equals("enter")) {
                int tmp = enter(1, 0, npro - 1, sc.nextInt() - 1, npro - 1);
                if (tmp == MAX) {
                    writer.println(tree[1] + 1);
                    update(1, 0, npro - 1, tree[1], MAX);
                } else {
                    writer.println(tmp + 1);
                    update(1, 0, npro - 1, tmp, MAX);
                }
            } else {
                int tmp = sc.nextInt() - 1;
                update(1, 0, npro - 1, tmp, tmp);
            }
        }
        writer.close();
    }
 
    public static void build(int v, int tl, int tr) {
        if (tl == tr) {
            if (tl < n) {
                tree[v] = tl;
            } else {
                tree[v] = MAX;
            }
        }
        else {
            int tm = (tl + tr) / 2;
            build(v * 2, tl, tm);
            build(v * 2 + 1, tm + 1, tr);
            tree[v] = Math.min(tree[v * 2], tree[v * 2 + 1]);
        }
    }
}

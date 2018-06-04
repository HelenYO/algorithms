import java.io.*;
import java.util.*;
 
class Scanner implements AutoCloseable {
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
 
    boolean hasNextLine() throws IOException {
        return br.ready();
    }
 
    public void close() throws Exception {
        br.close();
    }
}
 
public class H {
    private static int[] tree;
    private static int[] tree2;
    private static int[] leaves;
 
    public static void main(String[] args) throws Exception {
        String fileIn = "rmq.in";
        String fileOut = "rmq.out";
 
        Scanner sc = new Scanner(new FileInputStream(new File(fileIn)));
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[][] questions = new int[m][3];
        for (int i = 0; i < m; i++) {
            questions[i][0] = sc.nextInt() - 1;
            questions[i][1] = sc.nextInt() - 1;
            questions[i][2] = sc.nextInt();
        }
        tree = new int[4 * n];
        for (int i = 0; i < 4 * n; i++) {
            tree[i] = Integer.MIN_VALUE;
        }
        for (int i = 0; i < m; i++) {
            set(1, 0, n - 1, questions[i][0], questions[i][1], questions[i][2]);
        }
 
        leaves = new int[n];
        findLeaves(1, 0, n - 1);
        tree2 = new int[4 * n];
        build(1, 0, n - 1);
        PrintWriter writer = new PrintWriter(new File(fileOut));
        boolean flag = true;
 
        for (int i = 0; i < m; i++) {
            if (questions[i][2] != findMin(1, 0, n - 1, questions[i][0], questions[i][1])) {
                writer.println("inconsistent");
                flag = false;
                break;
            }
        }
        if (flag) {
            writer.println("consistent");
            //writer.println();
            for (int i = 0; i < n; i++) {
                writer.print(leaves[i] + " ");
            }
        }
        writer.close();
 
    }
 
    private static int findMin(int v, int tl, int tr, int l, int r) {
        if (l > r)
            return Integer.MAX_VALUE;
        if (l == tl && r == tr)
            return tree2[v];
        int tm = (tl + tr) / 2;
        return Math.min(findMin(v * 2, tl, tm, l, Math.min(r, tm)), findMin(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r));
    }
 
    private static void build(int v, int tl, int tr) {
        if (tl == tr)
            tree2[v] = leaves[tl];
        else {
            int tm = (tl + tr) / 2;
            build(v * 2, tl, tm);
            build(v * 2 + 1, tm + 1, tr);
            tree2[v] = Math.min(tree2[v * 2], tree2[v * 2 + 1]);
        }
    }
 
    private static void findLeaves(int v, int l, int r) {
        if (l > r) {
            return;
        }
        if (l == r) {
            leaves[l] = tree[v];
            return;
        }
        propagate(v);
        int m = (l + r) / 2;
        findLeaves(2 * v, l, m);
        findLeaves(2 * v + 1, m + 1, r);
    }
 
    private static void set(int v, int tl, int tr, int l, int r, int set) {
        if (l > r) {
            return;
        }
        if (l == tl && r == tr) {
            tree[v] = Math.max(set, tree[v]);
            return;
        }
        int tm = (tl + tr) / 2;
        propagate(v);
        set(v * 2, tl, tm, l, Math.min(r, tm), set);
        set(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r, set);
        tree[v] = Math.max(Math.min(tree[v * 2], tree[v * 2 + 1]), tree[v]);
    }
 
    private static void propagate(int v) {
        tree[v * 2] = Math.max(tree[v * 2], tree[v]);
        tree[v * 2 + 1] = Math.max(tree[v * 2 + 1], tree[v]);
    }
}

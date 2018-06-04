import java.io.*;
import java.util.StringTokenizer;
 
 
class Node {
    long add;
    long set;
    long min;
    boolean isSet;
 
    Node(long add, long set, long min, boolean isSet) {
        this.add = add;
        this.set = set;
        this.min = min;
        this.isSet = isSet;
    }
}
 
public class C {
 
    private static int n;
    private static Node[] tree;
 
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
 
        boolean hasNextLine() throws IOException {
            return br.ready();
        }
 
        public void close() throws Exception {
            br.close();
        }
    }
 
//    private static int findNear(int n) {
//        if (n == 0) return 0;
//        if (n == 1) return 2;
//        int k = 0;
//        while (n != 1) {
//            n /= 2;
//            k++;
//        }
//        return 1 << k + 1;
//    }
 
    public static void main(String[] args) throws Exception {
        String fileIn = "rmq2.in";
        String fileOut = "rmq2.out";
 
        Scanner sc = new Scanner(new FileInputStream(new File(fileIn)));
        n = sc.nextInt();
 
        //final long[] a = new long[findNear(n)];
        final long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = sc.nextLong();
        }
        tree = new Node[n * 4];
        //build(a, 1, 0, findNear(n) - 1);
        build(a, 1, 0, n - 1);
        PrintWriter writer = new PrintWriter(new File(fileOut));
 
        while (sc.hasNextLine()) {
            String temp = sc.next();
            if (temp.charAt(0) == 's') {
                int i = sc.nextInt();
                int j = sc.nextInt();
                int x = sc.nextInt();
                setI(i - 1, j - 1, x);
                /*
                for (int k = 1; k < 16; k++) {
                    System.out.print(tree[k].set+" ");
                }
 
                System.out.println();
 
                */
            } else if (temp.charAt(0) == 'a') {
                int i = sc.nextInt();
                int j = sc.nextInt();
                int x = sc.nextInt();
                addI(i - 1, j - 1, x);
                //
//                System.out.println("add:");
//                for (int k = 1; k < 16; k++) {
//
//                    System.out.print(tree[k].set + " ");
//                }
 
               // System.out.println();
 
                //
            } else {
                int i = sc.nextInt();
                int j = sc.nextInt();
                writer.println(minI(i - 1, j - 1));
            }
        }
        writer.close();
    }
 
    private static void propagate(int v) {
        //System.out.println(v);
 
        if (tree[v].isSet) {
 
            //System.out.println(v);
            tree[v * 2].isSet = true;
            tree[v * 2].set = tree[v].set;
            tree[v * 2].add = 0;
            tree[v * 2].min = tree[v].set;
 
            tree[v * 2 + 1].isSet = true;
            tree[v * 2 + 1].set = tree[v].set;
            tree[v * 2 + 1].add = 0;
            tree[v * 2 + 1].min = tree[v].set;
 
            tree[v].isSet = false;
            tree[v].set = 0;
 
        } else if (tree[v].add != 0) {
            if (tree[v * 2].isSet) {
                tree[v * 2].set += tree[v].add;
                tree[v * 2].min += tree[v].add;
                //tree[v * 2].isSet = false;
                //tree[v * 2].set = 0;
            } else {
                tree[v * 2].add += tree[v].add;
                tree[v * 2].min += tree[v].add;
                //tree[v * 2].isSet = false;
                //tree[v * 2].set = 0;
            }
 
            if (tree[v * 2 + 1].isSet) {
                tree[v * 2 + 1].set += tree[v].add;
                tree[v * 2 + 1].min += tree[v].add;
                //tree[v * 2 + 1].isSet = false;
                //tree[v * 2 + 1].set = 0;
            } else {
                tree[v * 2 + 1].add += tree[v].add;
                tree[v * 2 + 1].min += tree[v].add;
                //tree[v * 2 + 1].isSet = false;
                //tree[v * 2 + 1].set = 0;
            }
            tree[v].add = 0;
        }
    }
 
    private static long minI(int i, int j) {
        //return min(1, 0, findNear(n) - 1, i, j);
        return min(1, 0, n - 1, i, j);
    }
 
    private static long min(int v, int tl, int tr, int l, int r) {
        if (l > r)
            return Long.MAX_VALUE;
        if (l == tl && tr == r) {
            return tree[v].min;
        } else {
            int tm = (tl + tr) / 2;
            propagate(v);
 
            return Math.min(
                    min(v * 2, tl, tm, l, Math.min(r, tm)),
                    min(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r));
        }
    }
 
    private static void setI(int i, int j, int x) {
        //set(1, 0, n - 1, i, j, x);
        set(1, 0, n - 1, i, j, x);
    }
 
    private static void set(int v, int tl, int tr, int l, int r, int set) {
        if (l > r)
            return;
        if (l == tl && tr == r) {
            tree[v].isSet = true;
            tree[v].set = set;
            tree[v].min = set;
            tree[v].add = 0;
        } else {
 
            int tm = (tl + tr) / 2;
            propagate(v);
 
            set(v * 2, tl, tm, l, Math.min(r, tm), set);
            set(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r, set);
            tree[v].min = Math.min(tree[2 * v].min, tree[2 * v + 1].min);
        }
    }
 
    private static void addI(int i, int j, int x) {
        //add(1, 0, findNear(n) - 1, i, j, x);
        add(1, 0, n - 1, i, j, x);
    }
 
    private static void add(int v, int tl, int tr, int l, int r, int add) {
        if (l > r)
            return;
        if (l == tl && tr == r) {
            if (tree[v].isSet) {
                tree[v].set += add;
                tree[v].min += add;
                //tree[v].add = 0;//
            } else {
                tree[v].add += add;
                tree[v].min += add;
                //tree[v].set = 0;//
            }
        } else {
            int tm = (tl + tr) / 2;
            propagate(v);
 
            add(v * 2, tl, tm, l, Math.min(r, tm), add);
            add(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r, add);
            tree[v].min = Math.min(tree[2 * v].min, tree[2 * v + 1].min);
        }
    }
 
    private static void build(long[] a, int v, int tl, int tr) {
        if (tl == tr) {
            //if (tl >= n) {
            //    tree[v] = new Node(0, 0, Long.MAX_VALUE, false);
            //} else {
            tree[v] = new Node(0, 0, a[tl], false);
            //}
        } // long add, long set, long min, boolean isSet
        else {
            int tm = (tl + tr) / 2;
            build(a, v * 2, tl, tm);
            build(a, v * 2 + 1, tm + 1, tr);
            long temp = Math.min(tree[v * 2 + 1].min, tree[v * 2].min);
            tree[v] = new Node(0, 0, temp, false);
        }
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class D {
    private static int maxCell = 0;
 
    static class Scanner {
        BufferedReader br;
        StringTokenizer st;
 
        Scanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
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
 
        public void close() throws Exception {
            br.close();
        }
    }
 
    static class Node {
        Node left;
        Node right;
        int y;
        int count;
        int countZ;
        int number;
 
        Node(Node left, Node right, int y, int count, int countZ, int number) {
            this.left = left;
            this.right = right;
            this.y = y;
            this.count = count;
            this.countZ = countZ;
            this.number = number;
        }
    }
 
    static class Pair {
        Node first;
        Node second;
 
        Pair(Node a, Node b) {
            first = a;
            second = b;
        }
    }
 
    private static Node Merge(Node L, Node R) {
        if (L == null) return R;
        if (R == null) return L;
 
        if (L.y > R.y) {
            Node newR = Merge(L.right, R);
            update(newR);
            int count;
            if (L.number != 0) {
                count = 0;
            } else {
                count = 1;
            }
            Node temp = new Node(L.left, newR, L.y, 1, count, L.number);//
            update(temp);
            return temp;
        } else {
            Node newL = Merge(L, R.left);
            int count;
            if (R.number != 0) {
                count = 0;
            } else {
                count = 1;
            }
            Node temp = new Node(newL, R.right, R.y, 1, count, R.number);//
            update(temp);
            return temp;
        }
    }
 
    private static Pair split(Node v, int x) {
        if (v == null) {
            return new Pair(null, null);
        }
        int curIndex = cnt(v.left);
 
        if (x > curIndex) {
            Pair p = split(v.right, x - curIndex - 1);
            v.right = p.first;
            update(v);
            return new Pair(v, p.second);
        } else {
            Pair p = split(v.left, x);
            v.left = p.second;
            update(v);
            return new Pair(p.first, v);
        }
    }
 
    private static void insert(int x) {
        Pair p = split(glava, x);
        Node m = new Node(null, null, rand(), 1, 1, 0);//
        glava = Merge(Merge(p.first, m), p.second);
    }
 
    private static int rand() {
        return (int) (Math.random() * 100000000);
    }
 
    private static int cnt(Node t) {
        if (t == null) {
            return 0;
        } else {
            return t.count;
        }
    }
 
    private static int cntZ(Node t) {
        if (t == null) {
            return 0;
        } else {
            return t.countZ;
        }
    }
 
    private static void update(Node t) {
        if (t != null) {
            t.count = 1 + cnt(t.left) + cnt(t.right);
            if (t.number == 0) {
                t.countZ = 1 + cntZ(t.left) + cntZ(t.right);
            } else {
                t.countZ = cntZ(t.left) + cntZ(t.right);
            }
        }
    }
 
    private static int findZ(Node v) {
        if (v.left == null || v.left.countZ == 0) {
            if (v.number == 0) {
                if (v.left == null) {
                    return 0;
                } else {
                    return cnt(v.left);
                }
            } else {
                return 1 + cnt(v.left) + findZ(v.right);
            }
        } else {
            return findZ(v.left);
        }
    }
 
    private static void shift(int L, int k) {
        Pair p = split(glava, L - 1);
        Pair p2 = split(p.second, 1);
        if (p2.first.number == 0) {
            if (L > maxCell) {
                maxCell = L;
            }
            glava = Merge(p.first, Merge(new Node(null, null, rand(), 1, 0, k), p2.second));
        } else {
            int numberOfZ = findZ(p2.second);
            int tmp = L + 1 + numberOfZ;
            if (tmp > maxCell) {
                maxCell = tmp;
            }
            Pair p3 = split(p2.second, numberOfZ);
            Pair p4 = split(p3.second, 1);
            Node temp = new Node(null, null, rand(), 1, 0, k);
            glava = Merge(p.first, Merge(temp, Merge(p2.first, Merge(p3.first, p4.second))));
        }
    }
 
    private static void inorderTraversal(Node v) {
        if (v != null) {
            inorderTraversal(v.left);
            arr.add(v.number);
            inorderTraversal(v.right);
        }
    }
 
    private static Node glava;
 
    private static ArrayList<Integer> arr;
 
    public static void main(String[] args) throws Exception{
        //int m;
        //int n;
        Scanner sc = new Scanner();
        int n = sc.nextInt();
        int m = sc.nextInt();
        maxCell = n;
        arr = new ArrayList<>();
        glava = new Node(null, null, rand(), 1, 1, 0);
        for (int i = 1; i <= n + m; i++) {
            insert(i + 1);
        }
 
        for (int k = 1; k < n + 1; k++) {
            int L = sc.nextInt();
            //cin >> L;
            shift(L, k);
        }
        inorderTraversal(glava);
 
        System.out.println(maxCell);
        for (int i = 0; i < maxCell; i++) {
            System.out.print(arr.get(i) + " ");
        }
        sc.close();
    }
 
}

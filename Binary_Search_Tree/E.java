import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Main {
 
 
    static class Node {
        Node left;
        Node right;
        long x;
        long y;
        long summa;
 
        Node(Node left, Node right, long x, long y, long summa) {
            this.left = left;
            this.right = right;
            this.x = x;
            this.y = y;
            this.summa = summa;
        }
    }
 
    private static Node glava = null;
 
    private static Node Merge(Node L, Node R) {
        if (L == null) return R;
        if (R == null) return L;
 
        if (L.y > R.y) {
            Node newR = Merge(L.right, R);
            update(newR);
            Node temp = new Node(L.left, newR, L.x, L.y, 1);
            update(temp);
            return temp;
        } else {
            Node newL = Merge(L, R.left);
            Node temp = new Node(newL, R.right, R.x, R.y, 1);
            update(temp);
            return temp;
        }
    }
 
    static class pair {
        Node first;
        Node second;
 
        pair(Node a, Node b) {
            first = a;
            second = b;
        }
    }
 
    private static pair split(Node v, long x) { // <,  >=
        if (v == null) {
            return new pair(null, null);//
        } else if (x > v.x) {
            pair p = split(v.right, x);
            v.right = p.first;
            update(v);
            update(p.second);
            return new pair(v, p.second);
        } else {
            pair p = split(v.left, x);
            v.left = p.second;
            update(p.first);
            update(v);
            return new pair(p.first, v);
        }
    }
 
    private static void insert(long x) {
        if (exists(glava, x)) {
            return;
        }
        pair p = split(glava, x);
        Node m = new Node(null, null, x, (long)(Math.random() * 1000000000), 1);
        glava = Merge(Merge(p.first, m), p.second);
        update(glava);
    }
 
    private static long cnt(Node t) {
        if (t == null) {
            return 0;
        } else {
            return t.summa;
        }
    }
 
    private static void update(Node t) {
        if (t != null) {
            t.summa = t.x + cnt(t.left) + cnt(t.right);
        }
    }
 
    private static long sum(long l, long r) {
        pair p = split(glava, l);
        update(p.second);
        pair p2 = split(p.second, r + 1);
        update(p2.first);
        long answer;
        if (p2.first == null) {
            answer = 0;
        } else {
            answer = p2.first.summa;
        }
        glava = Merge(p.first, Merge(p2.first, p2.second));
        return answer;
    }
 
    public static void main(String[] args) {
        Scanner2 sc = new Scanner2(System.in);
        String komand;
        long n = sc.nextLong();
        //cin >> n;
        boolean flag = false;
        long flagY = 0;
        while (n > 0) {
            komand = sc.next();
            if (komand.charAt(0) == '+') {
                long x = sc.nextLong();
                //cin >> x;
                if (glava != null) {
                    if (flag) {
                        insert((long)(x + flagY) % 1000000000);
                    } else {
                        insert(x);
                    }
                } else {
                    glava = new Node(null, null, x, (long)(Math.random() * 1000000000), 1);
                }
                flag = false;
            } else {
                long l = sc.nextLong();
                long r = sc.nextLong();
                //cin >> l >> r;
                flagY = sum(l, r);
                //cout << flagY << endl;
                System.out.println(flagY);
                flag = true;
            }
            n--;
        }
    }
 
    private static boolean exists(Node v, long x) {
        if (v == null) {
            return false;
        }
        if (x > v.x) {
            if (v.right != null) {
                return exists(v.right, x);
            } else {
                return false;
            }
        } else if (x < v.x) {
            if (v.left != null) {
                return exists(v.left, x);
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
 
    static class Scanner2 implements AutoCloseable {
        BufferedReader br;
        StringTokenizer st;
 
        Scanner2(InputStream is) {
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
 
}

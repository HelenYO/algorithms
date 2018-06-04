import java.io.*;
import java.util.StringTokenizer;
 
/*class Node {
    long sum;
    long kolvo;
    boolean colorR;
    boolean colorL;
    boolean setB;
    boolean setW;
 
    Node(long sum, long kolvo, boolean colorR, boolean colorL, boolean setB, boolean setW) {
        this.sum = sum;
        this.kolvo = kolvo;
        this.colorR = colorR;
        this.colorL = colorL;
        this.setB = setB;
        this.setW = setW;
    }
}*/
 
public class D {
 
    private static int size;
    //private static Node[] tree;
    private static long[] sum;
    private static long[] kolvo;
    private static boolean[] colorR;
    private static boolean[] colorL;
    private static boolean[] setB;
    private static boolean[] setW;
 
 
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
 
    public static void main(String[] args) throws Exception {
        String fileIn = "painter.in";
        String fileOut = "painter.out";
 
        Scanner sc = new Scanner(new FileInputStream(new File(fileIn)));
        int n = sc.nextInt();
 
        final boolean[] comand = new boolean[n];
        final int[] place = new int[n];
        final int[] delta = new int[n];
 
        int min = Integer.MAX_VALUE;
        int max = 0;
 
        for (int i = 0; i < n; i++) {
            String temp = sc.next();
            if (temp.equals("W")) {
                comand[i] = false;
            } else {
                comand[i] = true;
            }
            place[i] = sc.nextInt();
            delta[i] = sc.nextInt();
            if (place[i] < min) {
                min = place[i];
            }
            if (place[i] + delta[i] > max) {
                max = place[i] + delta[i];
            }
        }
 
        int indent = min;
        size = max - indent + 1;
 
        //tree = new Node[size * 4];
 
        sum = new long[size * 4];
        kolvo = new long[size * 4];
        colorR = new boolean[size * 4];
        colorL = new boolean[size * 4];
        setB = new boolean[size * 4];
        setW = new boolean[size * 4];
 
        //build(1, 0, size - 1);
        PrintWriter writer = new PrintWriter(new File(fileOut));
 
        for (int i = 0; i < n; i++) {
            if (comand[i]) {
                blackI(place[i] - indent, place[i] + delta[i] - 1- indent);
            } else {
                whiteI(place[i] - indent, place[i] + delta[i] - 1 - indent);
            }
            writer.println(kolvo[1] + " " + sum[1]);
        }
 
        writer.close();
    }
 
    private static void propagate(int v, int tl, int tr) {
        int tm = (tl + tr) / 2;
        if (setB[v]) {
            //System.out.println(v + " " + tl + " " + tr);
            sum[v * 2] = tm - tl + 1;//
            kolvo[v * 2] = 1;
            colorR[v * 2] = true;
            colorL[v * 2] = true;
            setB[v * 2] = true;
            setW[v * 2] = false;
 
            sum[v * 2 + 1] = tr - tm - 1 + 1;//
            kolvo[v * 2 + 1] = 1;
            colorR[v * 2 + 1] = true;
            colorL[v * 2 + 1] = true;
            setB[v * 2 + 1] = true;
            setW[v * 2 + 1] = false;
        } else if (setW[v]) {
            sum[v * 2] = 0;
            kolvo[v * 2] = 0;
            colorR[v * 2] = false;
            colorL[v * 2] = false;
            setB[v * 2] = false;
            setW[v * 2] = true;
 
            sum[v * 2 + 1] = 0;
            kolvo[v * 2 + 1] = 0;
            colorR[v * 2 + 1] = false;
            colorL[v * 2 + 1] = false;
            setB[v * 2 + 1] = false;
            setW[v * 2 + 1] = true;
        }
    }
 
    private static void whiteI(int l, int r) {
        white(1, 0, size - 1, l, r);
    }
 
    private static void white(int v, int tl, int tr, int l, int r) {
        if (l > r) {
            return;
        }//long sum, long kolvo, boolean color, boolean colorR, boolean colorL
        if (l == tl && tr == r) {
            //tree[v] = new Node(0, 0, false, false, false, true);
            sum[v] = 0;
            kolvo[v] = 0;
            colorL[v] = false;
            colorR[v] = false;
            setB[v] = false;
            setW[v] = true;
        } else {
            int tm = (tl + tr) / 2;
            propagate(v, tl, tr);
 
            white(v * 2, tl, tm, l, Math.min(r, tm));
            white(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r);
            sum[v] = sum[v * 2] + sum[v * 2 + 1];
            if (colorR[v * 2] && colorL[v * 2 + 1]) {
                kolvo[v] = kolvo[v * 2] + kolvo[v * 2 + 1] - 1;
            } else {
                kolvo[v] = kolvo[v * 2] + kolvo[v * 2 + 1];
            }
            colorL[v] = colorL[v * 2];
            colorR[v] = colorR[v * 2 + 1];
            setB[v] = false;
            setW[v] = false;
        }
    }
 
    private static void blackI(int l, int r) {
        black(1, 0, size - 1, l, r);
    }
 
    private static void black(int v, int tl, int tr, int l, int r) {
        if (l > r) {
            return;
        }
        if (l == tl && tr == r) {
            //tree[v] = new Node(r - l + 1, 1, true, true, true, false);
            sum[v] = r - l + 1;
            kolvo[v] = 1;
            colorL[v] = true;
            colorR[v] = true;
            setB[v] = true;
            setW[v] = false;
        } else {
            int tm = (tl + tr) / 2;
            propagate(v, tl, tr);
 
            black(v * 2, tl, tm, l, Math.min(r, tm));
            black(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r);
            sum[v] = sum[v * 2] + sum[v * 2 + 1];
            if (colorR[v * 2] && colorL[v * 2 + 1]) {
                kolvo[v] = kolvo[v * 2] + kolvo[v * 2 + 1] - 1;
            } else {
                kolvo[v] = kolvo[v * 2] + kolvo[v * 2 + 1];
            }
            colorL[v] = colorL[v * 2];
            colorR[v] = colorR[v * 2 + 1];
            setB[v] = false;
            setW[v] = false;
        }
    }
 
    /*private static void build(int v, int tl, int tr) {
        if (tl == tr) {
            //tree[v] = new Node(0, 0, false, false, false, false);
            sum[v] = 0;
            kolvo[v] = 0;
            colorL[v] = false;
            colorR[v] = false;
            setB[v] = false;
            setW[v] = false;
        } //long sum, long kolvo, boolean color, boolean colorR, boolean colorL
        else {
            int tm = (tl + tr) / 2;
            build(v * 2, tl, tm);
            build(v * 2 + 1, tm + 1, tr);
            //long temp = Math.min(tree[v * 2 + 1].min, tree[v * 2].min);
            tree[v] = new Node(0, 0, false, false, false, false);
        }
    }*/
}

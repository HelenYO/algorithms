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
 
public class F {
    private static int n;
    final private static long MAXIMUM = Long.MAX_VALUE;
    private static long[] a;
    private static long ST[][];
 
    public static long log(long size) {
        if (size != 1) {
            return log(size / 2) + 1;
        } else {
            return 0;
        }
    }
 
    public static void main(String[] args) throws FileNotFoundException {
        String fileIn = "sparse.in";
        String fileOut = "sparse.out";
        Scanner sc = new Scanner(new File(fileIn));
        PrintWriter writer = new PrintWriter(new File(fileOut));
        n = sc.nextInt();
        int m = sc.nextInt();
        long a1 = sc.nextLong();
        long u1 = sc.nextLong();
        long v1 = sc.nextLong();
 
        a = new long[n];
        a[0] = a1;
        for (int i = 1; i < n; i++) {
            a[i] = (23 * a[i - 1] + 21563) % 16714589;
        }
 
        int tmp = (int) (Math.log(n) / Math.log(2)) + 1;
        ST = new long [n][tmp];
        buildST();
 
        int ans = 0;
        long v0 = 0;
        long u0 = 0;
        for (int i = 1; i < m + 1; i++) {
            if (u1 > v1) {
                ans = min(v1, u1);
            } else {
                ans = min(u1, v1);
            }
            v0 = v1;
            u0 = u1;
            u1 = ((17 * u1 + 751 + ans + 2 * i) % n) + 1;
            v1 = ((13 * v1 + 593 + ans + 5 * i) % n) + 1;
 
        }
 
        writer.print(u0 + " " + v0 + " " + ans);
        writer.close();
 
    }
 
    public static int min(long l, long r) {
        long temp = log(r - l + 1);
        return (int)Math.min(ST[(int)l - 1][(int) temp], ST[(int)r - (1 << temp) + 1 - 1][(int) temp]);
    }
 
    private static void buildST() {
        for (int i = 0; i < n; i++) {
            ST[i][0] = a[i];
        }
        int tmp = (int) (Math.log(n) / Math.log(2)) + 1;
        for (int j = 1; j < tmp; j++) {
            for(int i = 0; i < n; i++) {
                //int temp = (int)Math.pow(2, j - 1);
                int temp = 1 << (j - 1);
                if (i + temp >= n) break;
                ST[i][j] = Math.min(ST[i][j - 1], ST[i + temp][j - 1]);
            }
        }
    }
}

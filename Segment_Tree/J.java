import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
 
 
public class J {
 
 
    private static int[] tree;
    private static int n;
 
    private static int ansMin;
    private static int ansV;
    private static int ansTl;
    private static int ansTr;
    private static int ansIndex;
 
 
    public static void main(String[] args) throws Exception {
 
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        tree = new int[n * 4];
        //build(1, 0, n - 1);
 
        ansMin = Integer.MAX_VALUE;
 
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            String temp = sc.next();
            if (temp.charAt(0) == 'd') {
                defendI(sc.nextInt(), sc.nextInt(), sc.nextInt());
            } else {
                attackI(sc.nextInt(), sc.nextInt());
            }
        }
 
    }
 
    private static void defendI(int a, int b, int c) {
        defend(1, 0, n - 1, a - 1, b - 1, c);
    }
 
    private static void defend(int v, int tl, int tr, int l, int r, int set) {
        if (l > r) {
            return;
        }
        if (l == tl && tr == r) {
            tree[v] = Math.max(set, tree[v]);
        } else {
            int tm = (tl + tr) / 2;
            propagate(v);
            defend(v * 2, tl, tm, l, Math.min(r, tm), set);
            defend(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r, set);
            tree[v] = Math.min(tree[v * 2], tree[v * 2 + 1]);
        }
    }
 
    private static void attackI(int a, int b) {
        attack(1, 0, n - 1, a - 1, b - 1);
        find(ansV, ansTl,ansTr);
        System.out.println(ansMin + " " + (ansIndex));
        ansMin = Integer.MAX_VALUE;
    }
 
    private static void attack(int v, int tl, int tr, int l, int r) {
        if (l > r)
            return;
        if (l == tl && tr == r) {
            if (ansMin > tree[v]) {
                ansMin = tree[v];
                ansV = v;
                ansTl = tl;
                ansTr = tr;
            }
        } else {
            int tm = (tl + tr) / 2;
            propagate(v);
 
            attack(v * 2, tl, tm, l, Math.min(r, tm));
            attack(v * 2 + 1, tm + 1, tr, Math.max(l, tm + 1), r);
        }
 
    }
 
 
    private static void find(int v, int l, int r) {
        if (l == r) {
            ansIndex = l + 1;
            return;
        }
        propagate(v);
        int tm = (l + r ) / 2;
        if (tree[2 * v] == ansMin) {
            find(2 * v, l, tm);
            return;
        }
        find(2 * v + 1, tm + 1, r);
    }
 
    private static void propagate(int v) {
        tree[2 * v] = Math.max(tree[v], tree[2 * v]);
        tree[2 * v + 1] = Math.max(tree[v], tree[2 * v + 1]);
    }
 
    /*private static void build(int v, int tl, int tr) {
        if (tl == tr) {
            tree[v] = 0;
        } else {
            int tm = (tl + tr) / 2;
            build(v * 2, tl, tm);
            build(v * 2 + 1, tm + 1, tr);
            //long temp = Math.min(tree[v * 2 + 1].min, tree[v * 2].min);
            tree[v] = 0;
        }
    }*/
}

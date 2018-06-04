import java.io.*;
import java.util.*;
 
 
class Limit {
    int x;
    int isOpen;
    int top;
    int floor;
    Limit(int x, int isOpen, int top, int floor) {
        this.x = x;
        this.isOpen = isOpen;
        this.top = top;
        this.floor = floor;
    }
    public int getX() {
        return x;
    }
    public  int getIsOpen() {
        return isOpen;
    }
    public static final Comparator<Limit> COMPARE_BY_X = new Comparator<Limit>() {
        @Override
        public int compare(Limit lhs, Limit rhs) {
            int x1 = lhs.getX();
            int x2 = rhs.getX();
            if (x1 == x2) {
                return -(lhs.getIsOpen() - rhs.getIsOpen());
            }
            return x1 - x2;
        }
    };
}
 
class Node {
    int max;
    int add;
    Node(int max, int add) {
        this.max = max;
        this.add = add;
    }
}
 
public class G {
 
    private static Node[] tree;
    private static int n;
    private static Limit[]limits;
 
    public static void main(String[] args) throws Exception {
 
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        limits = new Limit[n * 2];
 
        int index = 0;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();
            if (y1 < minY) {
                minY = y1;
            }
            if (y2 < minY) {
                minY = y2;
            }
            if (y1 > maxY) {
                maxY = y1;
            }
            if (y2 > maxY) {
                maxY = y2;
            }
            limits[index++] = new Limit(x1, 1, y1, y2);
            limits[index++] = new Limit(x2, -1, y1, y2);
        }
 
        maxY -= minY;
        for (int i = 0; i < n * 2; i++) {
            limits[i].floor -= minY;
            limits[i].top -= minY;
        }
 
        //System.out.println(maxY + " " + minY);
 
        Arrays.sort(limits, Limit.COMPARE_BY_X);
 
        /*for (int i = 0; i < n * 2; i++) {
            System.out.print(limits[i].x + " ");
        }
        System.out.println();
        for (int i = 0; i < n * 2; i++) {
            System.out.print(limits[i].isOpen + " ");
        }
        System.out.println();
        */
 
        tree = new Node[4 * (maxY + 1)];
        build(1, 0 , maxY);
 
        int max = 0;
        int maxx = 0;
        int maxy = 0;
        for (int i = 0; i < n * 2; i++) {
            add(1, 0, maxY, limits[i].top, limits[i].floor, limits[i].isOpen);
            if (tree[1].max > max) {
                maxy = find(tree[1].max, 1, 0, maxY);
                maxx = limits[i].x;
                max = tree[1].max;
            }
        }
        System.out.println(max);
        System.out.println(maxx + " " + (maxy + minY));
    }
 
    private static int find (int what, int v , int tl, int tr) {
        if (tl == tr) {
            return tl;
        }
        propagate(v);
        int tm = (tl + tr)/2;
        if (what == tree[v * 2].max) {
            return find(what, v * 2, tl, tm);
        } else {
            return find(what, v * 2 + 1, tm + 1, tr);
        }
    }
 
    private static void build(int v, int tl, int tr) {
        if (tl > tr) {
            return;
        } if (tl == tr) {
            tree[v] = new Node(0, 0);
            return;
        }
        tree[v] = new Node(0, 0);
        int tm = (tl + tr)/2;
        build(v * 2, tl, tm);
        build(v * 2 + 1, tm + 1, tr);
    }
 
    private static void propagate(int v) {
        tree[v * 2].max += tree[v].add;
        tree[v *2].add += tree[v].add;
        tree[v * 2 + 1].max += tree[v].add;
        tree[v *2 + 1].add += tree[v].add;
        tree[v].add = 0;
    }
 
    private static void add(int v, int tl, int tr, int l, int r, int add) {
        if (l> r) {
            return;
        }
        if(l == tl && r == tr) {
            tree[v].max += add;
            tree[v].add += add;
        } else {
            int tm = (tl + tr)/2;
            propagate(v);
            add(v * 2, tl, tm, l, Math.min(r,tm), add);
            add(v * 2 + 1, tm + 1, tr, Math.max(l,tm+1), r, add);
            tree[v].max = Math.max(tree[v * 2].max, tree[v * 2 + 1].max);
        }
    }
/*
5
0 0 3 3
1 1 4 4
2 2 3 3
7 7 8 8
3 3 5 5
 
5
0 0 3 3
1 1 4 4
2 2 3 3
7 7 8 8
3 3 5 5
 */
 
}

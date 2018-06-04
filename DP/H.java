import java.io.*;
import java.math.BigInteger;
import java.util.*;
 
public class K {
 
    public static boolean might(int i, int j, int n) {
        boolean flag = true;
        int ipr = 3;
        int jpr = 4;
        int m = 0;
        while (m < n) {
            if ((i%2) == (j%2) && jpr == (i%2) && ipr == jpr) {
                flag = false;
            } else {
                ipr = i%2;
                jpr = j%2;
            }
            m++;
            j /= 2;
            i /= 2;
        }
        return flag;
    }
 
    public static void findChet(int num, int[] pred, int[] arr) {
        int  number = num;
        int chet = 0;
        while (number != 0) {
            if (arr[number] != -1) {
                chet += arr[number];
                arr[num] = chet % 2;
                return;
            } else {
                chet++;
                number = pred[number];
            }
        }
        arr[num] = chet % 2;
    }
 
    public static ArrayList<Integer> findDad(int num, int[] pred) {
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 1; i < pred.length; i++) {
            if (pred[i] == num) {
                arr.add(i);
            }
        }
        return arr;
    }
 
    public static int otv (int num, int[] pred, int[] otvet) {
        if (otvet[num] != 0) {
            return otvet[num];
        } else {
            ArrayList<Integer> children = findDad(num, pred);
            int otvet1 = 0;//если не берем этот элемент
            for (int i = 0; i < children.size(); i++) {
                //if (otvet[children.get(i)] == -1) {
                    otvet1 += otv(children.get(i), pred, otvet);
                //} else {
                 //   otvet1 += otvet[children.get(i)];
                //}
            }
            int otvet2 = 0;//берем этот элемент
            for (int i = 0; i < children.size(); i++) {
                ArrayList<Integer> prechildren = findDad(children.get(i), pred);
                for (int j = 0; j < prechildren.size(); j++) {
                    if (otvet[prechildren.get(j)] == -1) {
                        otvet2 += otv(prechildren.get(j), pred, otvet);
                    } else {
                        otvet2 += otvet[prechildren.get(j)];
                    }
                }
            }
            otvet[num] = Math.max(otvet1, otvet2 + 1);
            return otvet[num];
        }
 
    }
 
    public static void main(String[] args) throws IOException {
 
        //String fileInName = "nice.in";
        //String fileOutName = "nice.out";
 
        //File file = new File(fileInName);
        //PrintWriter writer = new PrintWriter(new FileWriter(fileOutName));
        //Scanner sc = new Scanner(file);
        Scanner sc = new Scanner(System.in);
 
        int n = sc.nextInt();
 
        int[] pred = new int[n+1];
        for (int i = 0; i < n; i++) {
            pred[i+1] = sc.nextInt();
        }
 
 
        int[] otvet = new int[n+1];
        int ans = otv(findDad(0, pred).get(0), pred, otvet);
        System.out.println(ans);
        /*int[] arr = new int[n+1];
        int[] pred = new int[n+1];
        for (int i = 0; i < n; i++) {
            pred[i+1] = sc.nextInt();
        }
        for ( int i = 0; i < n+1; i++) {
            arr[i] = -1;
        }
 
        for (int i = 1; i < n+1; i++) {
            findChet(i, pred, arr);
        }
 
        int kolvo = 0;
        for (int i = 1; i < n+1; i++) {
            System.out.print(arr[i] + " ");
            if (arr[i] == 0) {
                kolvo++ ;
            }
        }
        System.out.println();
        System.out.println(Math.max(kolvo, n - kolvo));*/
 
        sc.close();
    }
}

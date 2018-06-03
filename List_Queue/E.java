import java.util.*;
import java.io.*;
public class E {
 
    public static int[] onion(String s) {
        int[] num = new int[2];
        boolean flag = false;
        String s1 = "";
        for (int i = 6;i<s.length();i++) {
            if (s.charAt(i)!=' ') {
                s1 = s1 + s.charAt(i); 
            } else {
                if (!flag) {
                    num[0] = Integer.parseInt(s1) - 1;
                    s1 = "";
                } else {
                    num[1] = Integer.parseInt(s1) - 1;
                }
            }
        }
        if (s1.length() != 0) {
            num[1] = Integer.parseInt(s1) - 1;
        }
        return num;
    }
 
    public static int findPred(int[][] arr, int m) {
        /*int k = m;
        while (arr[k][0] != k) {
            k = arr[k][0];
        }
        return k;*/
        int k = m;
        if (arr[k][0]!=k) {
            arr[k][0] = findPred(arr,arr[k][0]);
        }
        return arr[k][0];
    }
 
    public static void main(String[] args) {
        try {
            //int[][] arr = new int[100000][3];//0-rod,1-min,2-max
            String fileIn = "dsu.in";
            String fileOut = "dsu.out";
            File file = new File(fileIn);
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            BufferedReader in = new BufferedReader(new FileReader(fileIn));
             
            int n = Integer.valueOf(in.readLine());
            int[][] arr = new int[n][4];//0-rod,1-min,2-max,3-kolvo(only parent has right value)
            for (int i = 0;i<n;i++) {
                arr[i][0] = i;
                arr[i][1] = i;
                arr[i][2] = i;
                arr[i][3] = 1;
            }
            int kolvo;
            while(in.ready()) {
                String s = in.readLine();
                if (s.charAt(0)=='g') {
                    int m = Integer.parseInt(s.substring(4,s.length())) - 1;
                    writer.println((arr[findPred(arr,m)][1]+1)+" "+(arr[findPred(arr,m)][2]+1)+" "+arr[findPred(arr,m)][3]);
                } else {
                    int[] num = onion(s);
                    int pred1 = findPred(arr,num[0]);
                    int pred2 = findPred(arr,num[1]);
                    if (pred2 != pred1) {
                        arr[pred1][1] = Math.min(arr[pred1][1], arr[pred2][1]);//min
                        arr[pred1][2] = Math.max(arr[pred1][2], arr[pred2][2]);//max
                        arr[pred1][3] = arr[pred1][3] + arr[pred2][3];//kolvo
                        arr[pred2][0] = pred1;//rod//num[1]
                        /*for (int i = 0; i < n; i++) {
                            if (arr[i][0] == pred2) {
                                arr[i][0] = pred1;
                            }
                        }*/
                         
                    }
                    /*for(int j = 0;j<4;j++){
                        for (int k = 0;k<n;k++) {
                            System.out.print(arr[k][j]);
                        }
                        System.out.println();
                    }
                    System.out.println();*/
                }
            }
            in.close();
            writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
}

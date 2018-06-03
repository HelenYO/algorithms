import java.util.*;
import java.io.*;
public class F {
 
    public static int[] onion(String s) {
        int[] num = new int[2];
        boolean flag = false;
        String s1 = "";
        for (int i = 0;i<s.length();i++) {
            if (s.charAt(i)!=' ') {
                s1 = s1 + s.charAt(i); 
            } else {
                if (!flag) {
                    num[0] = Integer.parseInt(s1);
                    s1 = "";
                } else {
                    num[1] = Integer.parseInt(s1);
                }
            }
        }
        if (s1.length() != 0) {
            num[1] = Integer.parseInt(s1);
        }
        //System.out.println(num[0]+" "+num[1]);
        return num;
    }
 
    public static void main(String[] args) {
        try {
            String fileIn = "formation.in";
            String fileOut = "formation.out";
            File file = new File(fileIn);
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            BufferedReader in = new BufferedReader(new FileReader(fileIn));
             
            String s = in.readLine();
            int[] temp = onion(s);
            int n = temp[0] + 1;
            int m = temp[1];
            int[][] arr = new int[n][3];//0-right,1-left
            for (int i = 0; i < n; i++) {
                arr[i][0] = 0;
                arr[i][1] = 0;
            }
            //System.out.println(n+" "+m);
            for (int i = 0; i < m; i++) {
                s = in.readLine();
                if (s.charAt(2) == 'f') {//left
                    temp = onion(s.substring(5,s.length()));
                    int I = temp[0];
                    int J = temp[1];
                    arr[I][0] = J;
                    arr[I][1] = arr[J][1];
                    arr[J][1] = I;// я не меняю ссылку от другого элемента на новый
                    if (arr[I][1] != 0) {
                        arr[arr[I][1]][0] = I;
                    }
                } else if (s.charAt(2) == 'g') {//right
                    temp = onion(s.substring(6,s.length()));
                    int I = temp[0];
                    int J = temp[1];
                    arr[I][0] = arr[J][0];
                    arr[I][1] = J;
                    arr[J][0] = I;
                    if (arr[I][0] != 0) {
                        arr[arr[I][0]][1] = I;
                    }
                } else if (s.charAt(2) == 'a') {//leave
                    int I = Integer.parseInt(s.substring(6,s.length()));
                    int predlev = arr[I][1];
                    int predprav = arr[I][0];
                    if (predlev != 0) {
                        if (predprav != 0) {
                            arr[predprav][1] = predlev;
                            arr[predlev][0] = predprav;
                            arr[I][0] = 0;
                            arr[I][1] = 0;
                        } else {
                            arr[predlev][0] = 0;
                            arr[I][0] = 0;
                            arr[I][1] = 0;
                        }
                    } else {
                        arr[predprav][1] = 0;
                        arr[I][0] = 0;
                        arr[I][1] = 0;
                    }
                   /* arr[predprav][1] = predlev;
                    arr[predlev][0] = predprav;
                    arr[I][0] = 0;
                    arr[I][1] = 0;*/
                } else {
                    int I = Integer.parseInt(s.substring(5,s.length()));
                    writer.println(arr[I][1]+" "+arr[I][0]);
                }
            }
            in.close();
            writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
}

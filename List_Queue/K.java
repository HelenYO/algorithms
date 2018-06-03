import java.util.*;
import java.io.*;
public class K {
 
    public static void answer (int[] arr, int[] r, int[] l, int[] value) {
        try {
            String fileOut = "kenobi.out";
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            //System.out.println(arr[3]);
            writer.println(arr[3]);
            int temp = arr[2];
            int k = arr[3];
            //System.out.print(value[temp]+" ");
            while (k > 0) {
                //System.out.print(value[temp]+" ");
                writer.print(value[temp]+" ");
                temp = r[temp];
                k--;
            }
            writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
 
    public static void push(int next, int number, int[] arr, int[] r, int [] l, int[] value) {//кладет новый меч//ok
 
        if (arr[3] == 0) {
            arr[2] = number;
            arr[0] = number;
            arr[1] = number;
            arr[3]++;
            value[arr[2]] = next;//0-last,1-mid,2-first,3-size
        } else {
            r[arr[0]] = number;
            l[number] = arr[0];//
            arr[0] = number;
            value[arr[0]] = next;
            arr[3]++;
            if (arr[3] % 2 == 0 && arr[3] != 2) arr[1] = r[arr[1]];//
        }
        //System.out.println("push:"+" "+arr[0]+" "+arr[1]+" "+arr[2]+" "+arr[3]);
    }
 
    public static void pop(int[] arr, int[] r, int[] l, int[] value) {//надо изменить на доставание из конца, еще
        //int result = value[arr[2]];
        if(arr[3] != 0) {
            arr[0] = l[arr[0]];//
            r[arr[0]] = 0;//
            arr[3]--;
            if(arr[3] % 2 == 1 && arr[3] != 1) arr[1] = l[arr[1]];//
        }
        //return result;//0-last,1-mid,2-first,3-size
 
        //System.out.println("pop:"+" "+arr[0]+" "+arr[1]+" "+arr[2]+" "+arr[3]);
    }
 
    public static void cheat(int[] arr, int[] r, int[] l, int[] value) {//надо изменить поиск центра и просто передвигать
        if (arr[3] >= 2) {
            /*if (arr[3] == 1) {
                r[arr[0]] = number;
                arr[0] = number;
                value[arr[0]] = next;
                arr[3]++;
            } else {
               r[number] = r[arr[1]];
               r[arr[1]] = number;
               value[number] = next;
               arr[3]++;
               if(arr[3] % 2 == 1) arr[1] = number;
            }*/
            l[arr[2]] = arr[0];
            r[arr[0]] = arr[2];
            //System.out.println(l[arr[0]]+" "+r[arr[0]]);
            arr[2] = r[arr[1]];
            l[arr[2]] = 0;
            r[arr[1]] = 0;
            int temp = arr[1];
            if (arr[3] % 2 == 1) {
                arr[1] = l[arr[0]];
            } else {
                arr[1] = arr[0];
            }
            arr[0] = temp;
        }
        //System.out.println("mom:"+" "+arr[0]+" "+arr[1]+" "+arr[2]+" "+arr[3]);
    }
 
    public static void main(String[] args) {
        try {
            String fileIn = "kenobi.in";
            String fileOut = "kenobi.out";
            File file = new File(fileIn);
            //PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            BufferedReader in = new BufferedReader(new FileReader(fileIn));
 
            int n = Integer.valueOf(in.readLine());
            int[] arr = new int[4];//0-last,1-mid,2-first,3-size
            int[] r = new int[n+1];//
            int[] l = new int[n+1];//
            int[] value = new int[1000000];
             
             
            //int[][] arr = new int[n+1][2];//0-right,1-left,
            int k = -1;
            int center;
            int end;
            int begin;
            for (int i = 0; i < n; i++) {
                String s = in.readLine();
                if (s.charAt(0) == 'a') {
                    int next = Integer.parseInt(s.substring(4,s.length()));
                    push(next,i,arr,r,l,value);
                } else if (s.charAt(0) == 't') {
                    pop(arr,r,l,value);
                } else if (s.charAt(0) == 'm') {
                    cheat(arr,r,l,value);
                }
            }
            answer(arr,r,l,value);
            in.close();
            //writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
}

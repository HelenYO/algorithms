import java.util.*;
import java.io.*;
public class G2 {
 
    public static void push(int next, int number, int[] arr, int[] r, int[] value) {
 
        if (arr[3] == 0) {
            arr[2] = number;
            arr[0] = number;
            arr[1] = number;
            arr[3]++;
            value[arr[2]] = next;//0-last,1-mid,2-first,3-size
        } else {
            r[arr[0]] = number;
            arr[0] = number;
            value[arr[0]] = next;
            arr[3]++;
            if (arr[3] % 2 == 1) arr[1] = r[arr[1]];
        }
    }
 
    public static int pop(int[] arr, int[] r, int[] value) {
        int result = value[arr[2]];
        arr[2] = r[arr[2]];
        arr[3]--;
        if(arr[3] % 2 == 1) arr[1] = r[arr[1]];
        return result;//0-last,1-mid,2-first,3-size
    }
 
    public static void cheat(int next, int number, int[] arr, int[] r, int[] value) {
        if (arr[3] == 0) {
            arr[2] = number;
            arr[0] = number;
            arr[1] = number;
            arr[3]++;
            value[arr[2]] = next;//0-last,1-mid,2-first,3-size
        } else if (arr[3] == 1) {
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
        }
    }
 
    public static void main(String[] args) {
        try {
            String fileIn = "hospital.in";
            String fileOut = "hospital.out";
            File file = new File(fileIn);
             
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            BufferedReader in = new BufferedReader(new FileReader(fileIn));
             
            int n = Integer.valueOf(in.readLine());
 
            int[] arr = new int[4];//0-last,1-mid,2-first,3-size
            int[] r = new int[1000000];
            int[] value = new int[1000000];
 
            for(int i = 0; i < n; i++) {
                 String s = in.readLine();
                if (s.charAt(0) == '+') {
                    int next = Integer.parseInt(s.substring(2,s.length()));
                    push(next,i,arr,r,value);
                } else if (s.charAt(0) == '*') {
                    int next = Integer.parseInt(s.substring(2,s.length()));
                    cheat(next,i,arr,r,value);
                } else if (s.charAt(0) == '-') {
                    writer.println(pop(arr,r,value));
                }
            }
            in.close();
            writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
}

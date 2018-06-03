import java.util.*;
import java.io.*;
public class D2 {
 
    public static void main(String[] args) {
        try {
            int[][] arr = new int[1000000][2];
            String fileIn = "stack-min.in";
            String fileOut = "stack-min.out";
            File file = new File(fileIn);
            //OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fileOut, false), "UTF-8");
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            BufferedReader in = new BufferedReader(new FileReader(fileIn));
            //int n = sc.nextInt();
            int n = Integer.valueOf(in.readLine());
             
            int k = -1;
            for (int i = 0; i < n; i++) {
                //int m = sc.nextInt();
                String s = in.readLine();
                char m = s.charAt(0);
                if (m == '1') {
                    int t = Integer.parseInt(s.substring(2,s.length()));
                    //int t = sc.nextInt();
 
                    arr[k+1][0] = t;
                    if (k==(-1)||t < arr[k][1]) {
                        arr[k+1][1] = t;
                    } else {
                        arr[k+1][1] = arr[k][1];
                    }
                    k++;
                } else if (m == '2') {
                    k--;
                } else {
                    writer.println(arr[k][1]);//
                    //writer.append('\n');
                }
            }
            in.close();
            //sc.close();
            writer.close();
            //writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
}

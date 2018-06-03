import java.util.*;
import java.io.*;
public class J {
 
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
        return num;
    }
 
    public static void main(String[] args) {
        try {
            String fileIn = "bureaucracy.in";
            String fileOut = "bureaucracy.out";
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            BufferedReader in = new BufferedReader(new FileReader(fileIn));
             
            int[] temp = onion(in.readLine());
            int n = temp[0];
            int m = temp[1];
            int a;
            ArrayList<Integer> arr = new ArrayList<Integer>();
            String s = in.readLine();
            int k = 0;
 
            for (int i = 0; i < n; i++) {
                String tem = "";
                while(k < s.length()  &&  s.charAt(k)!= ' ') {
                    tem += s.charAt(k);
                    k++;
                     
                }
                int tempi = Integer.parseInt(tem);
                arr.add(tempi);
                k++;
                 
            }
 
 
 
            int stat = m / n;
            m = m - (m/n) * n;
            for (int i = n-1;i >= 0; i--) {
                if (arr.get(i) > stat) {
                    arr.set(i,arr.get(i) - stat);
                } else {
                    m += stat - arr.get(i);
                    arr.remove(i);
                }
            }
 
 
 
 
 
            k = 0;
            while (m > 0 && arr.size() > 0) {
                if (m > arr.size()) {
                    m -= arr.size();
                    for (int i = arr.size() - 1; i >= 0; i--) {
                        arr.set(i, arr.get(i)-1);
                        if (arr.get(i) <= 0) {
                            arr.remove(i);
                        } 
                    }
                } else {
                     
                    for (int i = m-1; i >= 0; i--) {
                        arr.set(i, arr.get(i)-1);
                        if (arr.get(i) <= 0) {
                            arr.remove(i);
                        } else {
                            k++;
                        }
                    }
                    m = 0;
                }
            }
 
            if (arr.size() == 0) {
                writer.println(-1);
            } else {
                writer.println(arr.size());
                for (int i = k; i<arr.size();i++) {
                    writer.print(arr.get(i)+" ");
                }
                for (int i = 0;i<k;i++) {
                    writer.print(arr.get(i)+" ");
                }
            }
 
             
            in.close();
            writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
}

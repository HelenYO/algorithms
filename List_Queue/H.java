import java.io.*;
public class H2 {
 
    public static String out(int time) {
        int hour = time/60;
        int minutes = time - hour * 60;
        String s = hour +" "+minutes;
        return s;
    }
 
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
            String fileIn = "saloon.in";
            String fileOut = "saloon.out";
            PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            BufferedReader in = new BufferedReader(new FileReader(fileIn));
            int n = Integer.valueOf(in.readLine());
             
            int[] line = new int[1000_000];
            int[] arr = new int[1000_000];
            int[] come = new int[1000_000];
            int[] escape = new int[1000_000];
            int last = 0;
            int first = 0;
            int hours = 0;
            int minutes = 0;
 
            for (int i = 0; i < n; i++) {
                String s = in.readLine();
                String s1 = "";
                int k = 0;
                for (int j = 0; j < s.length(); j++) {
                    if (s.charAt(j)!=' ') {
                        s1 = s1 + s.charAt(j); 
                        k++;
                    } else {
                        hours = Integer.parseInt(s1);
                        break;
                    }
                }
                int[] temp = onion(s.substring(k+1));
                minutes = temp[0];
                arr[i] = temp[1];
                come[i] = 60*hours + minutes;
            }
 
            int next = 0;
            int now = 0;
 
            while (next<n||last!=first) {
                if (last == first) {
                    line[last] = next;
                    now = come[next];
                    last++;
                    next++;
                } else {
                    while ( Math.abs(now - come[next]) < 20) {
                        if (arr[next] >= last - first) {
                            line[last] = next;
                            last++;
                        } else {
                            escape[next] = come[next];
                        }
                        next++;
                    }
                    now = now + 20;
                    escape[line[first]] = now;
                    first++;
                }
            }
 
            for (int i = 0; i < n; i++) {
                writer.println(out(escape[i]));
            }
             
            in.close();
            writer.close();
        } catch (FileNotFoundException e){
        } catch (IOException e){
        }
    }
}

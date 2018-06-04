import java.util.*;
import java.io.*;
 
public class D {
 
 
 
    public static void main(String[] args) {
 
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] otv = new int[n];
        int[] values = new int[n];
 
        for (int i = 0; i < n; i++) {
            values[i] = sc.nextInt();
        }
 
        int[] prev = new int[n];
        for (int i = 0; i < n; i++) {
            prev[i] = -1;
        }
 
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (values[i] > values[j] && otv[j] >= otv[i]) {
                    otv[i] = otv[j] + 1;
                    prev[i] = j;
                }
            }
        }
 
        int max = 0;
        int num = 0;// номер максимального ответа
        for (int i = 0; i < n; i++) {
            if (otv[num] < otv[i]) {
                num = i;
            }
        }
 
        System.out.println(otv[num] + 1);
 
        int[] ans = new int[otv[num] + 1];
        int number = n - 1;
 
        for (int i = otv[num] ; i >= 0; i--) {
            ans[i] = values[num];
            num = prev[num];
        }
 
        for (int i = 0; i < ans.length; i++) {
            System.out.print((ans[i]) + " ");
        }
        sc.close();
        /*//try {
 
            //String fileIn = "input.txt";
            //String fileOut = "output.txt";
            //File file = new File(fileIn);
            //PrintWriter writer = new PrintWriter(new FileWriter(fileOut));
            Scanner sc = new Scanner(System.in);
 
            int n = sc.nextInt();
            //sc = new Scanner (args[1]);
            int[][] arr = new int[n][2];
            for (int i = 0; i < n; i++) {
                arr[i][1] = sc.nextInt();
            }
            arr[0][0] = 1;
            int number = 0;
            for (int i = 1; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    if (arr[j][1] < arr[i][1] && arr[j][0] >= arr[i][0]) {
                        arr[i][0] = arr[j][0] + 1;
                        if (arr[i][0] > arr[number][0]) {
                            number = i;
                        }
                    }
                }
            }
            //writer.println(arr[number][0]);
        //for (int i = 0;i<n;i++) {
         //       System.out.print(arr[i][0]+" ");
        //}
            System.out.println(arr[number][0]);
            //System.out.println(number + "  123");
            int num = number;
            ArrayList<Integer> ar = new ArrayList<>();
            ar.add(arr[number][1]);
            for (int i = arr[number][0] - 1; i > 0; i--) {
                while (arr[num][0] != i || arr[num][1] >= arr[number][1]) {
                    //System.out.println(arr[num][1]);
                    num--;
                }
                //System.out.println(arr[num][1]);
                ar.add(arr[num][1]);
            }
            for (int i = ar.size() - 1; i > -1; i--) {
                //writer.print(ar.get(i)+" ");
                System.out.print(ar.get(i)+" ");
            }
 
            sc.close();
            //writer.close();
        //} catch (FileNotFoundException e){
 
        //} catch (IOException e){
        //}*/
    }
}

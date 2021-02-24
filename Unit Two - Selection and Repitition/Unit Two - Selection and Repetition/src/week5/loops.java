package week5;

import java.util.Scanner;

public class loops {
    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in); 

       ecco2010(4, 3, 1, 2);

       scanner.close();
    }
    private static void ecco2010(int m, int n, int p, int q){
    
        for (int j = 0; j < q; j ++){
            for (int i = 0; i < (n + 2 * p + 2 * q); i ++){
            System.out.print("#");
            }
            System.out.print("\n");
        }
        String temp = "";
        for (int i = 0; i < q; i ++){
            temp = temp + "#";
            System.out.print("#");
        }
        for (int i = 0; i < (n + 2*p); i ++){
            System.out.print("+");
        }
        System.out.println(temp);

        for (int i = 0; i < m; i ++){
            System.out.print(temp + "+");
            for (int j = 0; j < n ; j ++){
                System.out.print(".");
            }
            System.out.println("+" + temp);
        }
        System.out.print(temp);
        for (int i = 0; i < (n + 2*p); i ++){
            System.out.print("+");
        }
        System.out.println(temp);

        for (int j = 0; j < q; j ++){
            for (int i = 0; i < (n + 2 * p + 2 * q); i ++){
            System.out.print("#");
            }
            System.out.print("\n");
        }
    }
}

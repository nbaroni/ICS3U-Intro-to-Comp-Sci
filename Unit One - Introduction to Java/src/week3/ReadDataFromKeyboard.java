import java.util.Scanner;

public class ReadDataFromKeyboard {
    public static void main(String[] args) {
        exampleOne();
    }
    private static void exampleOne(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a 5 digit number");
        int number = Integer.parseInt(scanner.nextLine());
        int sum = FunctionsAndScanner.getSum(number);
        System.out.println(sum);

        scanner.close();
    }
}

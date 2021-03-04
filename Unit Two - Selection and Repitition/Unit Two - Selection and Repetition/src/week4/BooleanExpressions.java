package week4;
import java.util.Scanner;

public class BooleanExpressions {
    public static void main(String[] args) {
      primitiveBooleans();

      primitiveBooleanExpressions();
    }

    private static void primitiveBooleanExpressions(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter a colour: ");
        String colour = scanner.nextLine().toLowerCase();

        System.out.print("How many shoes?: ");
        int numShoes = Integer.parseInt(scanner.nextLine()); 

        //are there at least four pairs of red shoes?
        System.out.println(colour.equalsIgnoreCase("red") && (numShoes >= 4)); 

        //are there at least four pairs of shoes OR the shoes are blue?
        System.out.println(colour.equalsIgnoreCase("blue") || (numShoes >= 4)); // || means or

        /** short circuit expressions
         *  
         * colour.equalsIgnoreCase("red") && (numShoes >= 4)
         * If the colour is not red there is no way both expressions can be true so as a result Java wont look at the numShoes expression
         * 
         * colour.equalsIgnoreCase("blue") || (numShoes >= 4)
         * If the colour is blue then we don't need to look at the numShoes expression
         */
        
    }
    private static void primitiveBooleans(){
        boolean isYellow = true; //boolean primitives are either true or false
        boolean hasDog = false;

        boolean isTrue = (7 + 3) == 10; // == is the equality operator that is used to check them

        System.out.println(7 != 7); // != is used for not equals

    }
}

public class SqrtPractice {
    public static void main(String[] args) {
        double a = 2.0; //put in your values
        double b = 3.0;
        double c = 0.1;
        double x1 = ((-1 * b) + Math.sqrt((b * b) - (4 * a * c))) / (2 * a); //the formula, does it twice
        double x2 = ((-1 * b) + -1 * (Math.sqrt((b * b) - (4 * a * c)))) / (2 * a);
        System.out.println("The answers are " + x1 + " and " + x2); //prints the answer to the console
    }
}

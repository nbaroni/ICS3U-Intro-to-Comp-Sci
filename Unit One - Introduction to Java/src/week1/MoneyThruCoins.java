public class MoneyThruCoins {
    public static void main(String[] args) {
        double p = 0.01 * 8; //second number is how many pennies you have
        double n = 0.05 * 2; //second number is how many nickels you have
        double d = 0.1 * 12; //second number is how many dimes you have
        double q = 0.25 * 1; //second number is how many quarters you have
        double l = 1 * 13; //second number is how many loonies you have
        double t = 2 * 0; //second number is how many toonies you have
        double o = p + n + d + q + l + t;
        System.out.println("You have $" + o); //prints the output to the console
    }
}

public class CalculateSlope {
    public static void main(String[] args) {
        double x1 = 2.0; //first and second x points of the line
        double x2 = 3.0;
        double y1 = 4.0; //first and second y points of the line
        double y2 = 6.0;
        double s = (x1 + x2) / (y1 + y2);
        System.out.println("The slope is " + s); //prints the output to the console
    }
}

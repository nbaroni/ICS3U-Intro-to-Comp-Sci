public class WritingFunctions {
    public static void main(String[] args) {
        int x = 7;
        int y = 8;
        int z = (x + y);
        
        System.out.println(z);
        x = addOne(x);

        System.out.println(x);

        double x1, x2, y1, y2;
        x1 = 2.0;
        x2 = -0.2;
        y1 = 8.0;
        y2 = 2.0;

        double out = getSlope(x1, x2, y1, y2);
        System.out.println(out);
    }
    private static int addOne(int x){
        x = x + 1;
        return x;
    }
    private static double getSlope(double x1, double x2, double y1, double y2){
    double out = (x1 + x2) / (y1 + y2);
    return out;
    }
}
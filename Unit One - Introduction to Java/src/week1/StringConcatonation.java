public class StringConcatonation {
    public static void main(String[] args) {
        exampleOne();
        exampleTwo();
        getRandom(6, 12);
    }
    private static void getRandom(int x, int y){ //example for getting a random number between a lower and upper number
        for(int i = 0; i < 100; i++){
            double z = Math.random() * (y - x + 1) + x;  
            System.out.println((int)z);
        }
    }
    private static void exampleTwo(){
        for(int i = 0; i < 100; i ++){
            double rand = Math.random() * 10;
            System.out.println((int)rand);
        }
    }
    private static void exampleOne(){
        int x = 90;
        int y = 94;
        int z = 88;
        double average = (x + y + z) / 3; //you need one or more doubles in your operations for java to print a decimal
        System.out.println(average);
        average = (double)(x + y + z) / 3;
        System.out.println(average);
    }
}

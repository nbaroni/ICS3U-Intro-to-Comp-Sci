public class FunctionsAndScanner {
    public static void main(String[] args) {
        int number = 20384;

        int sum = getSum(number);
        System.out.println(sum);

        sum = getSumVersion2(number);
        System.out.println(sum);

        String timeString = "5:34.221";

        double timeInSeconds = convertToSeconds(timeString);
        System.out.println(timeInSeconds);
    }
    /**
     * converts a time into seconds
     * @param timeInSeconds time in format 'm:ss.sss'.
     * @return
     */
    private static double convertToSeconds(String timeString){
        int colon = timeString.indexOf(":");
        int minutesAsSeconds = Integer.parseInt(timeString.substring(0, colon)) * 60;
        double seconds = Double.parseDouble(timeString.substring(colon + 1));


        return minutesAsSeconds + seconds;
    }
    public static int getSumVersion2(int number){
        String numberasString = "" + number;
        int digit1 = Integer.parseInt(numberasString.substring(0,1));
        int digit2 = Integer.parseInt(numberasString.substring(1, 2));
        int digit3 = Integer.parseInt(numberasString.substring(2, 3));
        int digit4 = Integer.parseInt(numberasString.substring(3, 4));
        int digit5 = Integer.parseInt(numberasString.substring(4));

        return digit1 + digit2 + digit3 + digit4 + digit5;


    }
    private static int getSum(int number){
        int digit1 = number / 10000;
        int digit2 = number / 1000 % 10;
        int digit3 = number /100 % 10;
        int digit4 = number / 10 % 10;
        int digit5 = number % 10;

        return digit1 + digit2 + digit3 + digit4 + digit5;
    }
}

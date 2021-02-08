public class Homework {
    public static void main(String[] args) {
        double x = susan(); //question 1
        System.out.println("the total purchace price is $" + addComma(x));

        double length, width; //question 2, question 10

        //length and width are the input values
        length = 4.5;
        width = 2.3;

        //imports the input values to the method
        String output = perimeterArea(length, width);
        
        //prints the answer to the console
        System.out.println(output);

        int regular = minutesInYear(); //q3
        int leap = minutesInLeapYear();
        System.out.println("In a normal year, there are " + addComma(regular) + " minutes in a year, and in a leap year there are " + addComma(leap) + " minutes in a year!");

        output = lightTravel(); //q4
        System.out.println(output);

        x = winPercent(); //q5
        System.out.println("In 1927, the Yankees' win percentage was " + addComma(x) + "%");

        System.out.println("The object's momentum is " + addComma(10 * 12)); //q6

        double temperature = 98.0; //q7
        System.out.println(temperature + " degrees Fahrenheit is " + round((temperature - 32) * (5.0/9), 3) + " degrees celsius."); 

        int number = 16; //q8
        System.out.println("The square root of " + number + " is " + round(Math.sqrt(number), 3) + " and the square of the number is " + addComma(Math.pow(number, 2)));

        int ItemSold = 12387; //q9
        System.out.println("The company will pay " + ItemSold * 0.27 + "$.");

        int m, v; //q11
        m = 124;
        v = 1;
        System.out.println("The kinetic energy is " + Math.pow(((1/2) * m * v), 2));
    }
    private static double winPercent(){
        double wins, loss;
        wins = 110.0;
        loss = 44.0;

        //to calculate win percentage, we divide wins by the sum of wins and losses
        double winPercentage = wins / (wins + loss);

        //now we round
        winPercentage = round(winPercentage, 3);
        return winPercentage;
    }
    private static double susan(){
        double price = 985.00;
        double tax = 0.055;
        tax = tax + 1;
        return tax * price;
    }
    private static String perimeterArea(double length, double width){
        //perimeter first, calculate
        double perimeter = 2 * (length + width);

        //now round
        perimeter = round(perimeter, 2);

        //now calculating area
        double area = length * width;

        //now round
        area = round(area, 2);

        //returns a string because it looks cleaner in the main method
        return "The perimeter is " + perimeter + " and the area is " + area;
    }
    private static int minutesInYear(){ //calculates how many minutes there are in a year
        return 365 * 24 * 60; //365 days, 24 hours in a day, 60 minutes in an hour
    }
    private static int minutesInLeapYear(){ //calculates how many minutes there are in a leap year
        return 366 * 24 * 60; //366 days, 24 hours in a day, 60 minutes in an hour
    }
    private static String lightTravel(){
        //first calculate how many seconds are in a year
        int out = minutesInYear() * 60;

        //then how many km it travels in a year
        out = (out * 3 * 10 ^ 8) / 1000;
        String output = addComma((double)out);

        return "Light travels at " + output + "km per year!"; //return a string becuase it looks cleaner in the main method
    }
    private static double round(double input, double decimalPlaces){ //rounds my numbers for me because it's easier to write this once
        input = (int)(input * (Math.pow(10, decimalPlaces)));
        return input / (Math.pow(10, decimalPlaces));
    }
    private static String addComma(double input){ //this will add commas to all my strings so it looks cleaner

        if(input % 1 != 0){ //will run the code in here if the input has a decimal
            
            //just seperates the decimal from the input number
            String pastDecimal = String.valueOf(input);
            int indexOfDecimal = pastDecimal.indexOf(".");
            pastDecimal = pastDecimal.substring(indexOfDecimal);

            if(input < 0){ //will run the code in here in the input is negative
                input = Math.abs(input);
                int length = String.valueOf((int)input).length();
    
                //look below for comprehensive comments, this is just copy pasted code with one change but I didn't add the comments because waste of space
                if(length % 3 == 0){
                    length = length - 1;
                }
                int numCommas = length / 3;
                
                int numCommas2 = numCommas * 3;
                String temp = String.valueOf((int)input);
                String output = String.valueOf((int)input);
                for (int i = 0; i < numCommas; i++){
                    int index = output.length() - numCommas2;
    
                    String temp2 = output.substring(0, index);
                    String temp3 = output.substring(temp.length() - numCommas2 + i);
                    output = temp2 + "," + temp3;
                    numCommas2 = numCommas2 - 3;
                }
                return "-" + output + pastDecimal;
            } else {
                int length = String.valueOf((int)input).length();
    
                //first we need to determine the number of commmas we need to place.
                //I have this if statement because if the number of characters in the string was even to 3, the method would place an extra comma
                if(length % 3 == 0){
                    length = length - 1;
                }
                int numCommas = length / 3;
                
                //temp is a temporary variable, and output will be the output. I define it now because it will be used in the for loop.
                int numCommas2 = numCommas * 3; //this is an index, used to use the number of commas but changed that, so i just added a 2 to the end of the variable to make it easier
                String temp = String.valueOf((int)input);
                String output = String.valueOf((int)input);
                //this for loop places the commas
                for (int i = 0; i < numCommas; i++){
                    int index = output.length() - numCommas2;
    
                    //the program will place the commas from right to left, one at a time.
                    String temp2 = output.substring(0, index);
                    String temp3 = output.substring(temp.length() - numCommas2 + i);
                    output = temp2 + "," + temp3;
                    numCommas2 = numCommas2 - 3;
                }
                return output + pastDecimal;
            }

        } else if(input < 0){ //will run the code in here in the input is negative
            input = Math.abs(input);
            int length = String.valueOf((int)input).length();

            //look below for comprehensive comments, this is just copy pasted code with one change but I didn't add the comments because waste of space
            if(length % 3 == 0){
                length = length - 1;
            }
            int numCommas = length / 3;
            
            int numCommas2 = numCommas * 3;
            String temp = String.valueOf((int)input);
            String output = String.valueOf((int)input);
            for (int i = 0; i < numCommas; i++){
                int index = output.length() - numCommas2;

                String temp2 = output.substring(0, index);
                String temp3 = output.substring(temp.length() - numCommas2 + i);
                output = temp2 + "," + temp3;
                numCommas2 = numCommas2 - 3;
            }
            return "-" + output;
        } else {
            int length = String.valueOf((int)input).length();

            //first we need to determine the number of commmas we need to place.
            //I have this if statement because if the number of characters in the string was even to 3, the method would place an extra comma
            if(length % 3 == 0){
                length = length - 1;
            }
            int numCommas = length / 3;
            
            //temp is a temporary variable, and output will be the output. I define it now because it will be used in the for loop.
            int numCommas2 = numCommas * 3; //this is an index, used to use the number of commas but changed that, so i just added a 2 to the end of the variable to make it easier
            String temp = String.valueOf((int)input);
            String output = String.valueOf((int)input);
            //this for loop places the commas
            for (int i = 0; i < numCommas; i++){
                int index = output.length() - numCommas2;

                //the program will place the commas from right to left, one at a time.
                String temp2 = output.substring(0, index);
                String temp3 = output.substring(temp.length() - numCommas2 + i);
                output = temp2 + "," + temp3;
                numCommas2 = numCommas2 - 3;
            }
            return output;
        }
    }
}

import java.util.Scanner;

public class CrossCountry {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //asks the user what format they would like to put thier times in
        System.out.print("Would you like your times to be converted from sss.sss to mm:sss.sss? (y/n): ");
        String choice = scanner.nextLine();

        //just chooses which method to run based on the user's inpuut
        if(choice.toLowerCase().startsWith("y")){
        inMinutes();
        }else {
        inSeconds();
        }

    }
    //either this method or the one below it will run based on the user's first input
    private static void inMinutes(){
        String name = welcome();

        String lapOne = countMinutes(name, "first mile");
        String lapTwo = countMinutes(name, "second mile");
        String finalSection = countMinutes(name, "final stretch");

        finalStatement(String.valueOf(lapOne), String.valueOf(lapTwo), String.valueOf(finalSection), name);
    }
    
    private static void inSeconds(){

        System.out.println("Your times will be converted from m:ss.sss to sss.sss");
        String name = welcome();

        double lapOne = countSeconds(name, "first mile");
        double lapTwo = countSeconds(name, "second mile");
        double finalSection = countSeconds(name, "final stretch");

        finalStatement(String.valueOf(lapOne), String.valueOf(lapTwo), String.valueOf(finalSection), name);
    }

    /**
     * This method counts the seconds in the lap -- converts the time from m:ss.sss to sss.sss
     * @param name -- the runner's name
     * @param lap -- the name of the lap that the runner is on
     * @return -- the converted time
     */
    private static double countSeconds(String name, String lap){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter " + name + "\'s time for the " + lap + " in the format \"m:ss.sss\": ");
        String time = scanner.nextLine();

        return convertToSeconds(time);
    }
    /**
     * This method counts the minutes in the lap -- converts the time from sss.sss to m:ss.sss
     * @param name -- the runner's name
     * @param lap -- the name of the lap that the runner is on
     * @return -- the converted time
     */
    private static String countMinutes(String name, String lap){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter " + name + "\'s time for the " + lap + " in the format \"sss.sss\": ");
        String time = scanner.nextLine();

        return convertToMinutes(time);
    }

    //This method is responsible for the welcome message.
    private static String welcome(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWelcome to the Bayview Glen Cross-Country team time split converter program!\n\n");
        
        //Will get the runner's name.
        System.out.print("Please enter the runner's name: ");
        String name = scanner.nextLine();
        
        return name;
    }
    /**
     * This method actually converts the time, and the method countSeconds just calls this one.
     * @param timeString -- the time in a string
     * @return -- the time in minutes and seconds
     */
    private static double convertToSeconds(String timeString){
        int colon = timeString.indexOf(":");

        //using knowledge of where the colon is, the program will extract the string from before it and after it
        //it will then multiply what's before the colon by sixty and add the decimal onto that, leaving us with a converted time
        int minutesAsSeconds = Integer.parseInt(timeString.substring(0, colon)) * 60;
        double seconds = Double.parseDouble(timeString.substring(colon + 1));

        return minutesAsSeconds + seconds;
    }

    //same as the convertToSeconds method, except it converts the time from sss.sss to m:ss.sss
    private static String convertToMinutes(String timeString){

        //rounds the number back up if it went down because there was this issue with the program rounding select numbers down for no reason
        double timeDouble = (int)((Double.parseDouble(timeString) + 0.0005) * (Math.pow(10, 4)));
        timeString = String.valueOf(timeDouble / (Math.pow(10, 4)));

        //in the rare case that the milliseconds will add up to a number that does not include a decimal, I need this so the program won't break.
        //I dont put the double timeDouble in here because it is divisible by zero
        if(Double.parseDouble(timeString) %1 == 0){
            return (Integer.parseInt(timeString) / 60) + ":" + (Integer.parseInt(timeString) % 60);
        }

        //I have to calculate how many decimal places there are here because of when the program adds all the numbers up later.
        int secsToMins = Integer.parseInt(timeString.substring(0, timeString.indexOf(".")));

        //I encountered a bug where if the number of seconds starts with zero, Java would print the number like "9:7.006". This fixes that by adding the zero needed if the seconds number starts with zero.
        if(((secsToMins % 60) /10) == 0 ){
            return (secsToMins / 60) + ":0" + (secsToMins % 60) + timeString.substring(timeString.indexOf("."), timeString.indexOf(".") +4);
        }
        //we can do all the other calculations and construct the string in the return statement
        //we take the whole minutes before the colon, then the remainer of seconds after it then the last four characters (the decimal and milliseconds)
        return (secsToMins / 60) + ":" + (secsToMins % 60) + timeString.substring(timeString.indexOf("."), timeString.indexOf(".") +4);

    }
    /**
     * While experimenting with the indexOf method, I realized that if the string does not contain the character that java is looking for, it will return a -1. 
     * This is how I check for if the time given is in the sss.sss format or the m:sss.sss format, so I can use this method for both the inMinutes and inSeconds scenarios. 
     * This method is just the final statement of the program.
     * @param l1 -- first lap
     * @param l2 -- second lap
     * @param l3 -- third lap
     * @param name -- runner's name
     */
    private static void finalStatement(String l1, String l2, String l3, String name){
        
        //i use a print statement (as opposed to a println) because I add onto it later and it saves me from typing it twice
        System.out.print(name + "\'s final time is ");

        if(l1.indexOf(":") > -1){ //this code will run if the original choice was yes

        double total = convertToSeconds(l1) + convertToSeconds(l2) + convertToSeconds(l3);
        total = ((int)((total + 0.0005) * (Math.pow(10, 3))) / Math.pow(10, 3));
        
        System.out.println(total + " seconds, or " + convertToMinutes(String.valueOf(total)));

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWould you like the program to run again for another runner? (y/n): ");
            
        String choice = scanner.nextLine();
        
        //will run if the user types Yes, yes, Yup, yup, y, Y, or really anything else that starts with y, like Yuck!!!!!!!! :(
            if(choice.toLowerCase().startsWith("y")){
            System.out.print("Would you like your times to be converted from sss.sss to mm:sss.sss? (y/n): ");
            choice = scanner.nextLine();

            //just chooses which method to run based on the user's inpuut
                if(choice.toLowerCase().startsWith("y")){
                inMinutes();
                } else {
                inSeconds();
                }
            } else {
            scanner.close();
            }
        } else { //this code will run if the original choice was no
            //since the code in here will only run if the times are in the sss.sss format, i can just add the doubles together
            double total = Double.parseDouble(l1) + Double.parseDouble(l2) + Double.parseDouble(l3);

            //again, I round because Java sucks sometimes and will round numbers down for no reason which will mess up the answer
            total = ((int)((total + 0.0005) * (Math.pow(10, 3))) / Math.pow(10, 3));

            //This giant line of code just rounds the double for me because I was having issues with it displaying a bunch of decimals that were all .0.
            //I also included this line becuase if there are less than 3 decimal places, the ones on the end that are 0 will show up that way, which looks better.
            System.out.println(total + " seconds, or " + convertToMinutes(String.valueOf(total)));

            Scanner scanner = new Scanner(System.in);
            System.out.print("\nWould you like the program to run again for another runner? (y/n): ");
            
            String choice = scanner.nextLine();

            if(choice.toLowerCase().startsWith("y")){
                System.out.print("Would you like your times to be converted from sss.sss to mm:sss.sss? (y/n): ");
                choice = scanner.nextLine();

                //just chooses which method to run based on the user's inpuut
                if(choice.toLowerCase().startsWith("y")){
                inMinutes();
                } else {
                inSeconds();
                }
            } else {
            scanner.close();
            }
        }
    }
}

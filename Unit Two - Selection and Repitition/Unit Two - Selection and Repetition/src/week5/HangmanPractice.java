package week5;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class HangmanPractice {
    public static void main(String[] args) {
        readFromFile();
        getInputFromUser();
    }
    
    /**
     * read a number from the keybaord and square it
     */
    private static void getInputFromUser() {
        Scanner in = new Scanner(System.in);

        System.out.print("Please enter your favourite number: ");
        String numberAsText = in.nextLine();

        boolean validInput = false;
        while(!validInput){
            try {
            int number = Integer.parseInt(numberAsText);
            validInput = true;
            
            System.out.println(Math.pow(number, 2));
            
            }catch(NumberFormatException ex){
            System.out.print("Please enter your favourite number: ");
            numberAsText = in.nextLine();
            }
        }
    
    in.close();
    }
    private static void readFromFile() {
        try {
        Scanner scanner = new Scanner(new File("src\\week5\\clues.dat"));

        while (scanner.hasNextLine()){
            String text = scanner.nextLine();
            System.out.println(text);
        }
        scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not locate the file.");
            System.exit(0);
        }


    }
}

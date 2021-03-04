package week5;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Hangman {

    private static final int ALLOWED_BAD_GUESSES = 6;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("\nWelcome to Hang man!\n\n\n");
        boolean playAgain = true;
        while (playAgain){
            String sentence = getMessage();
            String usedLetter = "";
            int incorrectGuesses = 0;

            boolean isWinner = false;
            boolean isLoser = false;
        
            while(!isWinner && !isLoser){
                String hiddenMsg = encryptMessage(sentence, usedLetter);
                System.out.println(hiddenMsg);

                String guess = getLetter(in, usedLetter);
                usedLetter += guess;

                if (sentence.indexOf(guess) < 0){
                    incorrectGuesses ++;
                    drawHangman(incorrectGuesses);
                }
            
                if (sentence.indexOf(guess) < 0){

                    System.out.println("NOPE you have used " + incorrectGuesses + " incorrect guesses.");
                }
                hiddenMsg = encryptMessage(sentence, usedLetter);
                

                if (hiddenMsg.indexOf("_") < 0){
                    isWinner = true;
                    System.out.println("\n\nYOU WINNNNNNNNNNNNNNN!\n");
                    System.out.println("       Г. .⅂");
                    System.out.println("       L_U_⅃");
                
                }else if (incorrectGuesses == ALLOWED_BAD_GUESSES){
                    isLoser = true;
                    System.out.println("\n\nYou lost :(((((((((((((((((((((((((((((((((");
                }
            }
        playAgain = playAnother(in);
        }
    }
    private static void drawHangman(int incorrectGuesses){
        if (incorrectGuesses == 1){
            System.out.println("      ---------");
            System.out.println("      |       |");
            System.out.println("      |       O");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("--------------");
        }else if (incorrectGuesses == 2){
            System.out.println("      ---------");
            System.out.println("      |       |");
            System.out.println("      |       O");
            System.out.println("      |       |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("--------------");
        }else if (incorrectGuesses == 3){
            System.out.println("      ---------");
            System.out.println("      |       |");
            System.out.println("      |       O");
            System.out.println("      |    ---|");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("--------------");
        }else if (incorrectGuesses == 4){
            System.out.println("      ---------");
            System.out.println("      |       |");
            System.out.println("      |       O");
            System.out.println("      |    ---|---");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("--------------");
        }else if (incorrectGuesses == 5){
            System.out.println("      ---------");
            System.out.println("      |       |");
            System.out.println("      |       O");
            System.out.println("      |    ---|---");
            System.out.println("      |      /");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("--------------");
        }else if (incorrectGuesses == 6){
            System.out.println("      ---------");
            System.out.println("      |       |");
            System.out.println("      |       O");
            System.out.println("      |    ---|---");
            System.out.println("      |      /\\");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("      |");
            System.out.println("--------------");
        }
    }
    
    private static String getMessage(){
        try {
            Scanner in = new Scanner (new File("src\\week5\\clues.dat"));
            int numberOfClues = Integer.parseInt(in.nextLine());
            int clueNumber = (int)(Math.random() * numberOfClues +1);
            
            for (int i = 1; i < clueNumber; i ++){
                in.nextLine();
            }
            return in.nextLine();
        }catch (FileNotFoundException e){
            System.out.println("\nIncorect filename lol");
            System.exit(0);
        }
        return null;
    }
    private static boolean playAnother(Scanner in){
        while(true){
            System.out.print("\n\nWould you like to play again? (y/n): ");
            String choice = in.nextLine().toUpperCase();
            if (choice.startsWith("Y")){
                return true;
            }else if (choice.startsWith("N")){
                return false;
            }
        }
    }
    private static String getLetter(Scanner in, String usedLetter){
        String letter = "";
        boolean validInput = false;
        String validCharacters = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";

        while(!validInput){
            System.out.print("Used Letters: " + displayUsedLetters(usedLetter));
            System.out.println();
            System.out.print("Please enter a letter: ");
            letter = in.nextLine();

            if (letter.length() == 1 && validCharacters.indexOf(letter) >= 0 && usedLetter.indexOf(letter) < 0){
                validInput = true;
            }else{
                System.out.println("Not a valid guess");
            }
        }

        return letter.toUpperCase();
    }
    private static String displayUsedLetters(String usedLetter){
        String letters = "";
        for (int i = 0; i < usedLetter.length(); i ++){
            letters += usedLetter.substring(i, i+1);
        }
        return letters;
    }
    /**
     * "Baseball is the best sport out there"
     * _ _ _ _ _ _ _ _ / _ _ / _ _ _ / _ _ _ _ / _ _ _ _ _ / _ _ _ / _ _ _ _ _
     * @param sentence
     * @return
     */
    private static String encryptMessage(String sentence, String usedLetter){
        String hiddenMsg = "";

        for (int i = 0; i < sentence.length(); i ++){
            String temp = sentence.substring(i, i +1);
            if (temp.equals(" ")){
                hiddenMsg += "/ ";
            }else if (usedLetter.indexOf(temp) < 0){
                hiddenMsg += "_ ";

            }else{
                hiddenMsg += temp + " ";
            }
        }
        return hiddenMsg;
    }
}

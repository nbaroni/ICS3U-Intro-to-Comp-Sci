package week6;

import java.util.Scanner;

public class ThreeCardPoker {
    private static final int STRAIGHT_FLUSH = 40;
	private static final int THREE_OF_A_KIND = 30;
	private static final int STRAIGHT = 6;
	private static final int FLUSH = 3;
	private static final int PAIR = 1;
	private static final int HIGH_CARD = 0;
    private static final int STARTING_MONEY = 500;
    private static final int HEARTS_SUIT = 1;
    private static final int CLUBS_SUIT = 2;
    private static final int SPADES_SUIT = 3;
    private static final int DIAMONDS_SUIT = 4;
    private static final int JACK_CARD = 11;
    private static final int QUEEN_CARD = 12;
    private static final int KING_CARD = 13;
    private static final int ACE_LOW_CARD = 1;
    private static final int ACE_HIGH_CARD = 1;
    private static final int PLAYER_WIN = 1;
    private static final int TIE = 0;
    private static final int DEALER_WIN = -1;
    private static final int MAX_CARD = 3;
    private static final int MIDDLE_CARD = 2;
    private static final int LOWER_CARD = 1;


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int wallet = STARTING_MONEY;
        boolean playAgain = true;

        while(wallet >= 0 && playAgain){
            System.out.println("\n\n\nWelcome to poker! You have $" + wallet + " in your wallet!");
            int anteWager = wager("ante", in, false);
            wallet -= anteWager;
            wallet(wallet);

            int pairWager = wager("pair plus", in, true);
            wallet -= pairWager;
            wallet(wallet);

            System.out.println("\nDealing cards...");
            String playerRawHand = dealCards();
            String dealerRawHand = dealCards();

            boolean willContinue = playWager(playerRawHand, in);
            String playerHand = handFindOut(playerRawHand);

            if (willContinue){
                int playWager = anteWager;
                wallet -= playWager;
                wallet(wallet);

                String dealerHand = handFindOut(dealerRawHand);

                //the way I organized the strings, every string that is a pair or higher has a comma in it. 
                if (dealerHand.indexOf(",") == -1 && (dealerHand.substring(0, dealerHand.indexOf(" ")).length() < 3 || dealerHand.substring(0, dealerHand.indexOf(" ")).equals("Jack"))){
                    System.out.println("\nThe dealer's hand is too bad, (they have a " + dealerHand.substring(0, dealerHand.length()  -2) + ") so you will lose your ante wager and have your play wager refunded.");
                    wallet += playWager;
                    wallet(wallet);
                }else{
                    System.out.print("\nThe dealer's hand is a " + dealerHand.substring(0, dealerHand.length() -2) + ". ");
                    int result = compareHands(playerHand, dealerHand, dealerRawHand, playerRawHand);
                    if (result == PLAYER_WIN){
                        System.out.println("Your " + playerHand.substring(0, playerHand.length() -2) + " beats that, so you win double both the play and ante wager!");
                        wallet += 2 * (playWager + anteWager);
                        wallet(wallet);
                    }else if (result == DEALER_WIN){
                        System.out.println("Your " + playerHand.substring(0, playerHand.length() -2) + " loses to that, so you lose your money. :(");
                        wallet(wallet);
                    }else{
                        System.out.println("You tied with the dealer, so you will lose your ante wager and have your play wager refunded.");
                        wallet += playWager;
                        wallet(wallet);
                    }
                }
            }
            if (pairWager != 0){
                int pairPayout = pairWagerPayout(pairWager, playerHand);
                System.out.println("\nYou made $" + pairPayout + " from the optional pair wager!");
                wallet += pairPayout;
                wallet(wallet);
            }else{
                System.out.println("You didn't make anything from the pair wager since you didn't bet on it!");
                wallet(wallet);
            }
            if (wallet >= 0){
                playAgain = willPlayAgain(in);
            }
        }
        if (wallet < 0){
            System.out.println("\nYou ran out of money so the game ended.");
        }
        if (wallet > STARTING_MONEY){
            System.out.println("Today, you made a profit of $" + (STARTING_MONEY - wallet) + "!");
        }else if (wallet < STARTING_MONEY){
            System.out.println("Today, you lost $" + (Math.abs(STARTING_MONEY - wallet)) + ".");
        }else{
            System.out.println("Today, you broke even.");
        }
        System.out.println("\nHave a nice day!");
        in.close();
    }

    private static boolean willPlayAgain(Scanner in) {
        while (true){
            System.out.print("\nWould you like to play again? (y/n): ");
            String choice = in.next();
            if (choice.toLowerCase().startsWith("y")){
                return true;
            }else if (choice.toLowerCase().startsWith("n")){
                return false;
            }
            System.out.println("Invalid input, please try again.");
        }
    }

    private static int compareHands(String playerHand, String dealerHand, String dealerRawHand, String playerRawHand) {
        if (Integer.parseInt(playerHand.substring(playerHand.length() -2)) > Integer.parseInt(dealerHand.substring(dealerHand.length() -2))){
            return PLAYER_WIN;
        }else if (Integer.parseInt(playerHand.substring(playerHand.length() -2)) < Integer.parseInt(dealerHand.substring(dealerHand.length() -2))){
            return DEALER_WIN;
        }else{
            if (getCardNumber(playerRawHand, MAX_CARD) > getCardNumber(dealerRawHand, MAX_CARD)){
                return PLAYER_WIN;
            }else if (getCardNumber(playerRawHand, MAX_CARD) < getCardNumber(dealerRawHand, MAX_CARD)){
                return DEALER_WIN;
            }else{
                if (getCardNumber(playerRawHand, MIDDLE_CARD) > getCardNumber(dealerRawHand, MIDDLE_CARD)){
                    return PLAYER_WIN;
                }else if (getCardNumber(playerRawHand, MIDDLE_CARD) < getCardNumber(dealerRawHand, MIDDLE_CARD)){
                    return DEALER_WIN;
                }else {
                    if (getCardNumber(playerRawHand, LOWER_CARD) > getCardNumber(dealerRawHand, LOWER_CARD)){
                        return PLAYER_WIN;
                    }else if (getCardNumber(playerRawHand, LOWER_CARD) < getCardNumber(dealerRawHand, LOWER_CARD)){
                        return DEALER_WIN;
                    }else {
                        return TIE;
                    }
                }
            }
        }
    }
    
    private static void wallet(int wallet) {
        System.out.println("You have $" + wallet + " in your wallet.");
    }

    private static String getCard() {
        return getFace() + getSuit();
    }

    private static String getSuit() {
        String suit = String.valueOf((int)(Math.random() * 4) +1); 

        if (Integer.parseInt(suit) == HEARTS_SUIT){
            suit = "h";
        }else if (Integer.parseInt(suit) == CLUBS_SUIT){
            suit = "c";
        }else if (Integer.parseInt(suit) == SPADES_SUIT){
            suit = "s";
        }else if (Integer.parseInt(suit) == DIAMONDS_SUIT){
            suit = "d";
        }
        return suit;
    }

    private static String getFace() {
        String card = String.valueOf((int)(Math.random() * 13) +1); 

        if (Integer.parseInt(card) == JACK_CARD){
            card = "J";
        }else if (Integer.parseInt(card) == QUEEN_CARD){
            card = "Q";
        }else if (Integer.parseInt(card) == KING_CARD){
            card = "K";
        }else if (Integer.parseInt(card) == ACE_LOW_CARD){
            card = "A";
        }
        return card;
    }
    public static boolean isUnique(String playerHand, String card) {	
		return playerHand.indexOf(card) == -1;
    }
    private static String dealCards(){
        String hand = "";

        for (int i = 0; i < 3; i++) {
            Boolean hasCard= false;
            while(!hasCard){
                String card = getCard();
                if (isUnique(hand, card)){
                    hand += card + " ";
                    hasCard= true;
                }
            }
        }
        return hand;
    }
    private static int wager(String type, Scanner in, boolean isOptional){

        while (true){
            System.out.print("\nPlease enter the amount of money you would like to bet for the " + type + " wager. ($50 - $100)");

            if (isOptional){
                System.out.print(" If you do not wish to bet, enter $0: $");
            }else{
                System.out.print(": $");
            }

            String amt = in.next();
            try{
                if ((amt.equals("0") && isOptional)  || (Integer.parseInt(amt) >= 50 && Integer.parseInt(amt) <= 100)){
                    return Integer.parseInt(amt);
                }else{
                    System.out.println("You are not allowed to bet this much money for this wager, please try again.");
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid input, please try again.");
            }
        }
    }
    private static int getCardNumber(String rawHand, int max){
        String temp = rawHand;

        int num1, num2, num3;
        try{
            num1 = Integer.parseInt(temp.substring(0, temp.indexOf(" ") -1));
            temp = temp.substring(temp.indexOf(" ") +1);
        }catch (NumberFormatException ec){
            if (temp.substring(0, 1).equals("J")){
                num1 = JACK_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else if (temp.substring(0, 1).equals("Q")){
                num1 = QUEEN_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else if (temp.substring(0, 1).equals("K")){
                num1 = KING_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else{
                num1 = ACE_HIGH_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }
        }
        try{
            num2 = Integer.parseInt(temp.substring(0, temp.indexOf(" ") -1));
            temp = temp.substring(temp.indexOf(" ") +1);
        }catch (NumberFormatException ecx){
            if (temp.substring(0, 1).equals("J")){
                num2 = JACK_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else if (temp.substring(0, 1).equals("Q")){
                num2 = QUEEN_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else if (temp.substring(0, 1).equals("K")){
                num2 = KING_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else{
                num2 = ACE_HIGH_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }
        }
        try{
            num3 = Integer.parseInt(temp.substring(0, temp.indexOf(" ") -1));
            temp = temp.substring(temp.indexOf(" ") +1);
        }catch (NumberFormatException ecz){
            if (temp.substring(0, 1).equals("J")){
                num3 = JACK_CARD;
            }else if (temp.substring(0, 1).equals("Q")){
                num3 = QUEEN_CARD;
            }else if (temp.substring(0, 1).equals("K")){
                num3 = KING_CARD;
            }else{
                num3 = ACE_HIGH_CARD;
            }
        }
        int maxim = Math.max(num1, Math.max(num2, num3));
        if (max == MAX_CARD){
            return maxim;
        }else if (max == MIDDLE_CARD){
            int min = Math.min(num1, Math.min(num2, num3));
            if (num1 != maxim && num1 != min){
                return num1;
            }else if (num2 != maxim && num2 != min){
                return num2;
            }else {
                return num3;
            }
        }else{
            return Math.min(num1, Math.min(num2, num3));
        }
    }
    private static int pairWagerPayout(int pairWager, String hand){
        int handType = Integer.parseInt(hand.substring(hand.length() -2));
        if (handType != 0){
            return handType * pairWager + pairWager;
        }
        return 0;
    }
    private static boolean playWager(String playerHand, Scanner in){
        System.out.println("Your cards are flipped over. They are: " + playerHand);

        while(true){
            System.out.print("\nWould you like to (p)lace a play wager, which would be the same amount as your ante wager, or would you like to (f)old?: ");
            String choice = in.next(); 

            if (choice.toLowerCase().startsWith("p") || choice.toLowerCase().startsWith("f")){
                return choice.startsWith("p");
            }
            System.out.println("Invalid input, please try again.");
        }
    }
    private static String handFindOut(String hand){
        String temp = hand;

        int num1, num2, num3;
        try{
            num1 = Integer.parseInt(temp.substring(0, temp.indexOf(" ") -1));
            temp = temp.substring(temp.indexOf(" ") +1);
        }catch (NumberFormatException ec){
            if (temp.substring(0, 1).equals("J")){
                num1 = JACK_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else if (temp.substring(0, 1).equals("Q")){
                num1 = QUEEN_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else if (temp.substring(0, 1).equals("K")){
                num1 = KING_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else{
                num1 = ACE_LOW_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }
        }
        try{
            num2 = Integer.parseInt(temp.substring(0, temp.indexOf(" ") -1));
            temp = temp.substring(temp.indexOf(" ") +1);
        }catch (NumberFormatException ecx){
            if (temp.substring(0, 1).equals("J")){
                num2 = JACK_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else if (temp.substring(0, 1).equals("Q")){
                num2 = QUEEN_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else if (temp.substring(0, 1).equals("K")){
                num2 = KING_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }else{
                num2 = ACE_LOW_CARD;
                temp = temp.substring(temp.indexOf(" ") +1);
            }
        }
        try{
            num3 = Integer.parseInt(temp.substring(0, temp.indexOf(" ") -1));
            temp = temp.substring(temp.indexOf(" ") +1);
        }catch (NumberFormatException ecz){
            if (temp.substring(0, 1).equals("J")){
                num3 = JACK_CARD;
            }else if (temp.substring(0, 1).equals("Q")){
                num3 = QUEEN_CARD;
            }else if (temp.substring(0, 1).equals("K")){
                num3 = KING_CARD;
            }else{
                num3 = ACE_LOW_CARD;
            }
        }
        temp = hand;
        String suit1 = temp.substring(1, 2);
        temp = temp.substring(temp.indexOf(" ") +1);
        String suit2 = temp.substring(1, 2);
        temp = temp.substring(temp.indexOf(" ") +1);
        String suit3 = temp.substring(1, 2);
        temp = temp.substring(temp.indexOf(" ") +1);

        int max = Math.max(num1, Math.max(num2, num3));
        int min = Math.min(num1, Math.min(num2, num3));

        if (min == ACE_LOW_CARD){
            temp = "Ace";
        }else if (max == QUEEN_CARD){
            temp = "Queen";
        }else if (max == KING_CARD){
            temp = "King";
        }else if (max == JACK_CARD){
            temp = "Jack";
        }else{
            temp = String.valueOf(max);
        }

        if (num1 == num2 || num2 == num3 || num3 == num1){
            if (num1 == num2 && num2 == num3){
                return "3 of a kind, " + temp + " high" + THREE_OF_A_KIND;
            }else{
                if (num1 == num2){
                    max = num1;
                }else if (num2 == num3){
                    max = num2;
                }else{
                    max = num3;
                }
                if (max == ACE_LOW_CARD){
                    temp = "Ace";
                }else if (max == QUEEN_CARD){
                    temp = "Queen";
                }else if (max == KING_CARD){
                    temp = "King";
                }else if (max == JACK_CARD){
                    temp = "Jack";
                }else{
                    temp = String.valueOf(max);
                }
                return "1 pair, " + temp + " high0" + PAIR;
            }
        }


        if (suit1.equals(suit2) && suit2.equals(suit3)){
            if (max - min == 2){
                return "Straight flush, " + temp + " high" + STRAIGHT_FLUSH;
            }else{
                return "Flush, " + temp + " high0" + FLUSH;
            }
        }else if (max - min == 2){
            return "Straight, " + temp + " high0" + STRAIGHT;
        }
        if (num1 == ACE_LOW_CARD){
            num1 = ACE_HIGH_CARD;
        }
        if (num2 == ACE_LOW_CARD){
            num2 = ACE_HIGH_CARD;
        }
        if (num3 == ACE_LOW_CARD){
            num3 = ACE_HIGH_CARD;
        }
    
        max = Math.max(num1, Math.max(num2, num3));
        min = Math.min(num1, Math.min(num2, num3));

        if (suit1.equals(suit2) && suit2.equals(suit3)){
            if (max - min == 2){
                return "Straight flush, " + temp + " high" + STRAIGHT_FLUSH;
            }else{
                return "Flush, " + temp + " high0" + FLUSH;
            }
        }else if (max - min == 2){
            return "Straight, " + temp + " high0" + STRAIGHT;
        }else{
            return temp + " high0" + HIGH_CARD;
         }
    }
}


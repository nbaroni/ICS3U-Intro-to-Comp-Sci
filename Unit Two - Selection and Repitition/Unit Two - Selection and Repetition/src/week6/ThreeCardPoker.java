package week6;

import java.util.Scanner;

public class ThreeCardPoker {
    private static final String STRAIGHT_FLUSH = "40";
	private static final String THREE_OF_A_KIND = "30";
	private static final String STRAIGHT = "06";
	private static final String FLUSH = "03";
	private static final String PAIR = "01";
	private static final String HIGH_CARD = "00";
    private static final int STARTING_MONEY = 500;
    private static final int HEARTS_SUIT = 1;
    private static final int CLUBS_SUIT = 2;
    private static final int SPADES_SUIT = 3;
    private static final int DIAMONDS_SUIT = 4;
    private static final int JACK_CARD = 11;
    private static final int QUEEN_CARD = 12;
    private static final int KING_CARD = 13;
    private static final int ACE_LOW_CARD = 1;
    private static final int ACE_HIGH_CARD = 14;
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

            //will subtract the money that was bid on the wager from the user's balance then display that to them
            wallet -= anteWager;
            wallet(wallet);

            int pairWager = wager("pair plus", in, true);
            wallet -= pairWager;
            wallet(wallet);

            System.out.println("\nDealing cards...");
            String playerRawHand = dealCards();
            String dealerRawHand = dealCards();

            //once the player is shown their hand, they will be asked if they want to continue playing or if they would like to fold
            boolean willContinue = playWager(playerRawHand, in);
            String playerHand = handFindOut(playerRawHand);

            if (willContinue){
                int playWager = anteWager;
                wallet -= playWager;
                wallet(wallet);

                String dealerHand = handFindOut(dealerRawHand);

                //The way I organized the strings, every string that is a pair or higher has a comma in it. 
                //Additionally, face cards will have a length of 3 or greater before the space in the string, so this method will catch those, and I made an exception for Jacks.
                if (dealerHand.indexOf(",") == -1 && (dealerHand.substring(0, dealerHand.indexOf(" ")).length() < 3 || dealerHand.substring(0, dealerHand.indexOf(" ")).equals("Jack"))){
                    System.out.println("\nThe dealer's hand is too bad, (they have " + dealerRawHand + "which gives them a " + dealerHand.substring(0, dealerHand.length()  -2) + ") so you will lose your ante wager and have your play wager refunded.");
                    wallet += playWager;
                    wallet(wallet);
                }else{ //the code will only make it here if the dealer has a high enough hand
                    System.out.print("\nThe dealer's hand is a " + dealerHand.substring(0, dealerHand.length() -2) + ", and their cards are " + dealerRawHand.substring(0, dealerRawHand.length() -1) + ". ");

                    //This calculates who wins the game. It will return 1 if the player wins, -1 if the dealer wins, and 0 if they tie. 
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

            //after we calculate who wins, we can calaulate the pair wager. I have this condition for the if statement because the pairWager is optional.
            if (pairWager != 0){
                int pairPayout = pairWagerPayout(pairWager, playerHand);
                System.out.println("\nYou made $" + pairPayout + " from the optional pair wager!");
                wallet += pairPayout;
                wallet(wallet);
            }else{
                System.out.println("\nYou didn't make anything from the pair wager since you didn't bet on it!");
                wallet(wallet);
            }

            //since I don't want the player playing the game if they either have no money or are in debt, I will only ask them if they want to play again if they have money in their wallet
            if (wallet >= 0){
                playAgain = willPlayAgain(in);
            }
        }

        //since the casino is a greedy business, it will allow you to play on borrowed money, assuming you will win money back that round
        //if you don't you will go into debt and as this statement says, and the casino will charge your credit card
        if (wallet < 0){
            System.out.println("\nYou ran out of money so the game ended. You now owe the casino $" + Math.abs(wallet) + " which will be charged to your credit card. Get good.");
        }

        //statement to see how much money the player made/lost
        if (wallet > STARTING_MONEY){
            System.out.println("Today, you made a profit of $" + (Math.abs(STARTING_MONEY - wallet)) + "!");
        }else if (wallet < STARTING_MONEY){
            System.out.println("Today, you lost $" + (Math.abs(STARTING_MONEY - wallet)) + ".");
        }else{
            System.out.println("Today, you broke even.");
        }

        //if you want your customers to come back, you must be nice to them! :)
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
    /**
     * This method detirmines who wins (between the player and the dealer) through comparing their hands
     * @param playerHand the player's hand in the form: (hand,) # highxx
     * @param dealerHand the dealer's hand in the form: (hand,) # highxx
     * @param dealerRawHand the dealer's hand in the form: #s #s #s
     * @param playerRawHand the player's hand in the form: #s #s #s
     * @return who wins. If the player wins, it will return a 1, if the dealer wins, it will return -1 and if they tie it will return 0.
     */
    private static int compareHands(String playerHand, String dealerHand, String dealerRawHand, String playerRawHand) {
        /**
         * since the last two chars in both the playerHand and dealerHand strings are numbers which are the pairPlus multiplier, 
         * I can compare them to see if one hand type outright beats the other. For example, if the player has a straight
         * and the dealer has a pair, it will be caught here.
         */
        if (Integer.parseInt(playerHand.substring(playerHand.length() -2)) > Integer.parseInt(dealerHand.substring(dealerHand.length() -2))){
            return PLAYER_WIN;
        }else if (Integer.parseInt(playerHand.substring(playerHand.length() -2)) < Integer.parseInt(dealerHand.substring(dealerHand.length() -2))){
            return DEALER_WIN;
        }else{ //the code in this else loop will only run if the player and the dealer have the same hand type
            if (getCardNumber(playerRawHand, MAX_CARD) > getCardNumber(dealerRawHand, MAX_CARD)){
                return PLAYER_WIN;
            }else if (getCardNumber(playerRawHand, MAX_CARD) < getCardNumber(dealerRawHand, MAX_CARD)){
                return DEALER_WIN;
            }else{ //the code in here will run if the player and the dealer have the same hand type and the same high card
                if (getCardNumber(playerRawHand, MIDDLE_CARD) > getCardNumber(dealerRawHand, MIDDLE_CARD)){
                    return PLAYER_WIN;
                }else if (getCardNumber(playerRawHand, MIDDLE_CARD) < getCardNumber(dealerRawHand, MIDDLE_CARD)){
                    return DEALER_WIN;
                }else { //this code will only run if the player and the dealer have the same hand type and the same high and middle cards
                    if (getCardNumber(playerRawHand, LOWER_CARD) > getCardNumber(dealerRawHand, LOWER_CARD)){
                        return PLAYER_WIN;
                    }else if (getCardNumber(playerRawHand, LOWER_CARD) < getCardNumber(dealerRawHand, LOWER_CARD)){
                        return DEALER_WIN;
                    }else { //If every card is the same, the program will return a tie
                        return TIE;
                    }
                }
            }
        }
    }
    
    /**
     *  This is a simple method that just prints a statement about your wallet.
     *  @param wallet is how much money the player has. 
    */
    private static void wallet(int wallet) {
        System.out.println("You have $" + wallet + " in your wallet.");
    }

    private static String getCard() {
        return getFace() + getSuit();
    }

    /**
    * This method detirmines what suit your cards will be through random number generation.
    */
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

    /**
    * This method detirmines what number your cards will be through random number generation.
    */
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
    /**
     * This method just checks if the card that was just generated is unique, to prevent duplicate cards.
     * @param playerHand player's hand
     * @param card the card
     * @return
     */
    public static boolean isUnique(String playerHand, String card) {	
		return playerHand.indexOf(card) == -1;
    }

    /**
     * This method deals the cards and checks if they are unique or not.
     * @return
     */
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

    /**
     * This method handles both the pair plus and the ante wager. 
     * @param type This is the type of wager. Just adds it to the print statement.
     * @param in The scanner
     * @param isOptional If this is true, it will allow the user to bet $0 on the wager.
     * @return the wager amount
     */
    private static int wager(String type, Scanner in, boolean isOptional){
        //I put an infinite loop here because there's no point in giving it an endpoint, because once the method gets a valid answer it will just return it, ending the loop.
        while (true){
            System.out.print("\nPlease enter the amount of money you would like to bet for the " + type + " wager. ($50 - $100)");

            //the optional addon for the string.
            if (isOptional){
                System.out.print(" If you do not wish to bet, enter $0: $");
            }else{
                System.out.print(": $");
            }

            String amt = in.next();

            //since I am using parseInt() and I don't want the program to crash, I must catch the exception here, If I catch it, it will also return an invalid input
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
    /**
     * This method simply gets the card numbers for calculating what hand they make. Jacks become 11, queens become 12, kings 13 and aces 1.
     * @param temp temporary string that includes incrimentally less of the hand as it is calculating.
     * @return the card number
     */
    private static int getActualCardNumber(String temp){
        int num;
        try{
            num = Integer.parseInt(temp.substring(0, temp.indexOf(" ") -1));
        }catch (NumberFormatException ec){
            if (temp.substring(0, 1).equals("J")){
                num = JACK_CARD;
            }else if (temp.substring(0, 1).equals("Q")){
                num = QUEEN_CARD;
            }else if (temp.substring(0, 1).equals("K")){
                num = KING_CARD;
            }else{
                num = ACE_LOW_CARD;
            }
        }
        return num;
    }
    /**
     * This method gets the numbers of the specified highest, middle or lowest card for comparing both the dealers and players hands to find out who wins the game.
     * @param rawHand the hand. Raw is infront of it because it is in the format: #s #s #s 
     * @param max allows the program to determine what card to return. If max == 3, it will return the highest card, if max == 2 it will return the middle card and if max == 1 it will return the lowest card.
     * @return the card specified
     */
    private static int getCardNumber(String rawHand, int max){
        String temp = rawHand;
        int num1 = getActualCardNumber(temp);
        temp = temp.substring(temp.indexOf(" ") +1);

        int num2 = getActualCardNumber(temp);
        temp = temp.substring(temp.indexOf(" ") +1);

        int num3 = getActualCardNumber(temp);

        //This just detirmines which card is which essentially so it will know what to return. Since max is already used, I use maxim for the highest card.
        int maxim = Math.max(num1, Math.max(num2, num3));
        int min = Math.min(num1, Math.min(num2, num3));

        //This is here incase there is a pair, for example, where the pair itself has a lower value than the third card. 
        //Just sets the maximum value as the pair and the minimum value as the other card.
        if (num1 == num2){
            if (min == num1){
                min = num3;
            }
            maxim = num1;
        }else if (num2 == num3){
            if (min == num2){
                min = num1;
            }
            maxim = num2;
        }else{
            if (min == num3){
                min = num2;
            }
            maxim = num3;
        }


        if (max == MAX_CARD){
            return maxim;
        }else if (max == MIDDLE_CARD){
            //since this will only run if the program is grabbing the middle card, I need to grab the card that is neither the highest nor lowest card.
            if (num1 != maxim && num1 != min){
                return num1;
            }else if (num2 != maxim && num2 != min){
                return num2;
            }else if (num3 != maxim && num3 != min){
                return num3;
            }else{
                //I have this one in case there's a pair, as of course in the pair the middle card will be the maximum card.
                return maxim;
            }
        }else{
            //simply returns the lowest card
            return min;
        }
    }
    /**
     * This method calculates the pair wager payout. Out of every hand, the last two digits of the string are the payout multiplier so I just use that.
     * @param pairWager the amount that the user bet
     * @param hand the user's hand
     * @return the payout
     */
    private static int pairWagerPayout(int pairWager, String hand){
        int handType = Integer.parseInt(hand.substring(hand.length() -2));
        if (handType != 0){
            return handType * pairWager + pairWager;
        }
        return 0;
    }

    /**
     * All this method does is detirmine if the player will fold or not. 
     * @param playerHand the player's hand
     * @param in the scanner
     * @return true -- the game will continue and compare the player and dealer's hands, false -- the game will end and go straight to the pair plus wager
     */
    private static boolean playWager(String playerHand, Scanner in){
        System.out.println("Your cards are flipped over. They are: " + playerHand);

        //I have an infinite loop because the program will repeat it until the user puts a valid input in, since it will stop the loop if it returns something of course.
        //It will only return a valid input.
        while(true){
            System.out.print("\nWould you like to (p)lace a play wager, which would be the same amount as your ante wager, or would you like to (f)old?: ");
            String choice = in.next(); 

            if (choice.toLowerCase().startsWith("p") || choice.toLowerCase().startsWith("f")){
                return choice.startsWith("p");
            }
            System.out.println("Invalid input, please try again.");
        }
    }
    /**
     * Will produce a hand in the form: (hand,) # highxx
     * If the player doesn't have a pair or anything higher than it, what is in the brackets won't be returned, including the comma.
     * The two numbers are the end markes as 'xx' are simply the multiplier for the pair plus wager
     * @param hand the hand being converted
     * @return (hand,) # highxx
     */
    private static String handFindOut(String hand){
        //throughout this entire section, I juggle around with a temporary string. This is just to make it easier to grab every card.
        String temp = hand;

        int num1 = getActualCardNumber(temp);
        temp = temp.substring(temp.indexOf(" ") +1);

        int num2 = getActualCardNumber(temp);
        temp = temp.substring(temp.indexOf(" ") +1);

        int num3 = getActualCardNumber(temp);

        temp = hand;
        String suit1 = temp.substring(1, 2);
        temp = temp.substring(temp.indexOf(" ") +1);
        String suit2 = temp.substring(1, 2);
        temp = temp.substring(temp.indexOf(" ") +1);
        String suit3 = temp.substring(1, 2);
        temp = temp.substring(temp.indexOf(" ") +1);

        //grabs the maximum and minimum cards because if the hand is a straight, max - min == 2
        int max = Math.max(num1, Math.max(num2, num3));
        int min = Math.min(num1, Math.min(num2, num3));

        //since I didn't want to define a new string, I just use temp as the high card. Since Ace is the high card, but it will show up as 1, I try to isolate that first.
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

        //if there is a pair or a three of a kind, the program will pick it up here.
        if (num1 == num2 || num2 == num3 || num3 == num1){
            if (num1 == num2 && num2 == num3){
                return "3 of a kind, " + temp + " high" + THREE_OF_A_KIND;
            }else{ 
                //This code converts the high card to the paired card in a pair.
                //because for example if the cards were: 7h, 7s, 9d, the cards that would be "high" would be the cards the hand has two of.
                if (num1 == num2){
                    max = num1;
                }else if (num2 == num3){
                    max = num2;
                }else{
                    max = num3;
                }
                //I have this thing of code again because the king, queen, jack and aces are still numbers at this point, and since this
                //is being displayed to the player it must say the name of the face cards
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
                return "1 pair, " + temp + " high" + PAIR;
            }
        }
        
        //will convert all the aces from ones to fourteens in memory
        if (num1 == ACE_LOW_CARD){
            num1 = ACE_HIGH_CARD;
        }
        if (num2 == ACE_LOW_CARD){
            num2 = ACE_HIGH_CARD;
        }
        if (num3 == ACE_LOW_CARD){
            num3 = ACE_HIGH_CARD;
        }
    
        //this section of code will run if it's a flush/straight flush
        if (suit1.equals(suit2) && suit2.equals(suit3)){
            if (max - min == 2){
                if (temp.equals("Ace") && (num1 == 3 || num2 == 3 || num3 == 3)){
                    //I need this here because otherwise, if a straight flush is made up of an ace a two and a three, it will say it's ace high
                    //since im doing this for only one case, I don't need a variable
                    return "Straight flush, 3 high" + STRAIGHT_FLUSH;
                }
                return "Straight flush, " + temp + " high" + STRAIGHT_FLUSH;
            }else{
                return "Flush, " + temp + " high" + FLUSH;
            }
        }else if (max - min == 2 && temp.equals("Ace") && (num1 == 3 || num2 == 3 || num3 == 3)){
            //I have this here because there is only one case. Since a straight made up of Ace, two, three is a 3 high and not an ace high.
            return "Straight, 3 high" + STRAIGHT;
        }

        max = Math.max(num1, Math.max(num2, num3));
        min = Math.min(num1, Math.min(num2, num3));

        /**
         * This will catch all straights that are not Ace, two, three
         */
        if (max - min == 2){
            return "Straight, " + temp + " high" + STRAIGHT;
        }else{
            return temp + " high" + HIGH_CARD;
         }
    }
}


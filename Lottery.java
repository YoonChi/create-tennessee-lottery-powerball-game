import java.util.Scanner;
import java.util.Random;

public class Lottery {

    public static long numPossibleTickets(int k, int n, int m) { // Calculate the number of possible items

        int num =  1;
        int den = 1;

        if (k >= 0 && n >= 0 && n >= k) {
            for (int i = k; i > 1; i--) {
                den *= i;
            }

            for (int i = n; i >= n - k + 1; i--) {
                num *= i;
            }
        }

        return m * (num/den);

    }

    public static int [] getPlayerNumbers(Scanner in, int k, int n) {

        int [] playerNumbers = new int [k];

        int index = 1;

        for (int i = 0; i < k; i++) {
            boolean proceed = false;

            System.out.print("Enter number " + index + " (must be 1-" + n + ", cannot repeat): ");

            int num = in.nextInt();

            while (num > n || num < 1) { // confirm number is between 1 and k
                System.out.print("Error - number must be between 1 and " + n + "." + " Please try again: ");
                num = in.nextInt();
            }

            // confirm user does not enter duplicate numbers
            if (i != 0) {
                while (!proceed) {
                    boolean duplicate = false;
                    for (int j = 0; j <= i; j++) {
                        if (num == playerNumbers[j]) {
                            System.out.print("Error - you’ve already entered " + num + ". Please try again. ");
                            num = in.nextInt();
                            duplicate = true;
                        }
                    }

                    if (!duplicate) {
                        proceed = true;
                        playerNumbers[i] = num;
                    }
                }
            }
            else {
                playerNumbers[i] = num;
            }
            index++;
        }

        return playerNumbers;

    }

    public static int [] getDrawnNumbers(int k, int n) {  // Randomly draws k distinct integers between 1 and n (inclusive).
        Random rand = new Random();

        int [] result = new int[k];

        for (int i = 0; i < k; i++) {
            int drawNum = rand.nextInt(n) + 1;
            result[i] = drawNum;
        }

        return result;
    }

    public static int countMatches(int[] a, int[] b) {  //This method should return the number of elements in array a that also appear in array b.
        int count = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == b[j]) {
                    count++;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Random rand = new Random();
        
        System.out.println(" ");
        System.out.println("DESIGN-A-LOTTO v1.0");
        System.out.println("-------------------");
        System.out.println(" ");
        System.out.println("*****");
        System.out.println("First, let's set up the game!");

        System.out.println(" "); 

        System.out.print("How many distinct numbers should the player pick? ");
        int k = scnr.nextInt();

        // check k ≥ 1
        while (k < 1) {
            System.out.print("Error - input must be at least 1. Please try again. ");
            k = scnr.nextInt();
        }

        System.out.print("OK. Each of those " + Integer.toString(k) + " numbers should range from 1 to what? ");
        int n = scnr.nextInt();

        // check n ≥ k
        while (n < k) {
            System.out.print("Error - range must be at least 1 to " + Integer.toString(k) + " to have a valid game. Please try again. ");
            n = scnr.nextInt();
        }

        System.out.print("OK. And finally, the bonus number should range from 1 to what? ");
        int m = scnr.nextInt();

        // check m ≥ 1
        while (m < 1) {
            System.out.print("Error - input must be at least 1. Please try again. ");
            m = scnr.nextInt();
        }

        System.out.println("*****");
        long numOfTickets = numPossibleTickets(k, n, m);

        double chanceOfWin = ((double) 1 / numOfTickets) * 100;

        System.out.println("There are " + numOfTickets + " possible tickets in this game. Each ticket has " + chanceOfWin + "% chance of winning the jackpot. Let's play, good luck!");

        System.out.print("How many tickets would you like to buy? ");
        int t = scnr.nextInt();

        // check t ≥ 1.
        while (t < 1) {
            System.out.print("Error - must buy at least 1 ticket! Please try again: ");
            t = scnr.nextInt();
        }

        int index = 1;
        int [][] ticketArray = new int[t][k];
        int [] bonusArray = new int[t];

        for (int i = 0; i < t; i++) {
            System.out.println("* Ticket #" + index + " of " + t + " *");

            int[] playerNumbers = getPlayerNumbers(scnr, k, n); // pick your distinct numbers

            // store each elements in playerNumbers array into an array of t rows and k columns
            ticketArray[i] = playerNumbers;

            System.out.print("Now pick your bonus number (must be 1-" + m + "): ");

            int bonusNum = scnr.nextInt();

            while (bonusNum > m || bonusNum < 1) {
                System.out.print("Error - number must be between 1 and " + m + ". Please try again. ");
                bonusNum = scnr.nextInt();
            }

            bonusArray[i] = bonusNum;

            index++;
        }

        // print out user's tickets so far
        System.out.println(" ");
        System.out.println("Your tickets so far: ");
        System.out.println("--------------------");

        int y = 0;
        for (int x = 0; x < ticketArray.length; x++) {
            for (int z = 0; z < ticketArray[x].length; z++) {
                System.out.print(ticketArray[x][z] + "      ");
            }

            System.out.print("||     Bonus: " + bonusArray[y]);
            System.out.println(" ");
            y++;
        }

        // simulate the lottery drawing
        int [] lotteryDrawing = getDrawnNumbers(k, n);

        // print out the lottery drawing
        System.out.println(" ");
        System.out.println("*****");
        System.out.println("The moment of truth has arrived! Here are the drawn numbers: ");

        for (int i = 0; i < lotteryDrawing.length; i++) {
            System.out.print(lotteryDrawing[i] + "      ");
        }

        // generate a random bonus number
        int drawBonus = rand.nextInt(m) + 1;

        System.out.print("||     Bonus: " + drawBonus);
        System.out.println(" ");

        int [] a;
        int [] matches = new int [t];

        // find best ticket that matches more of the k distinct numbers than any other ticket
        for (int i = 0; i < t; i++) {
            a = ticketArray[i];
            int numOfMatch = countMatches(a, lotteryDrawing);
            matches[i] = numOfMatch;
        }

        // get the highest number of matched distinct numbers (k)
        int max = 0;

        for (int i = 0; i < matches.length; i++) {
            if (matches[i] > max) {
                max = matches[i];
            }
        }

        int [] bestTicket;

        // look for best tickets with matching bonus number with the drawn bonus number
        boolean best = false;
        for (int i = 0; i < matches.length; i++) {
            if (matches[i] == max) {
                if (bonusArray[i] == drawBonus) {
                    best = true;
                }
            }
        }

        if (max != 0) {
            System.out.println(" ");
            System.out.println("Your best ticket(s): ");
            System.out.println(" ");

            for (int i = 0; i < matches.length; i++) {
                if (best) {
                    if (matches[i] == max && bonusArray[i] == drawBonus) {
                        bestTicket = ticketArray[i];

                        for (int j = 0; j < bestTicket.length; j++) {
                            System.out.print(bestTicket[j] + "      ");
                        }

                        System.out.print("||     Bonus: " + bonusArray[i]);
                        System.out.println(" ");
                    }
                }
                else {
                    if (matches[i] == max) {
                        bestTicket = ticketArray[i];

                        for (int j = 0; j < bestTicket.length; j++) {
                            System.out.print(bestTicket[j] + "      ");
                        }

                        System.out.print("||     Bonus: " + bonusArray[i]);
                        System.out.println(" ");
                    }
                }
            }
        }

        System.out.println(" ");
        System.out.println("You matched " + max + "/" + k + " drawn numbers.");

        if (best) {
            System.out.println("You did match the bonus number.");
        }

        System.out.println(" ");

        if (max == k && best) {
            System.out.println("WOOHOO, JACKPOT!!");
        }
    }
}

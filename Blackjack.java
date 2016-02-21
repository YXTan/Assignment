//Yongxin Tan Assignment2
import java.io.*;
import java.util.Scanner;

public class Blackjack {

    public static void main(String[] args) throws IOException {

		//set necessary variables
        String username = "";
        double totalMoney = 100.0;
        double bet = 0.0;
        int hands = 0;
        int handsWin = 0;
        int winOrlose = 0;
        Card cardu = new Card();//create object for users' card
        Card cardd = new Card();//create object for dealers' card
        User user = new User(username, hands, handsWin, totalMoney);//create user object
        Scanner scan = new Scanner(System.in);
        double debtNeedReturn = 0;

        System.out.println("Welcome to Infinite BlackJack!");
        System.out.println();
        while (username.length() == 0) {//get users' name and make sure it is not blank
            System.out.print("Please enter your username> ");
            username = scan.next();
            user.setName(username);
        }
        try {//check whether the file already exist
            File openfile = new File(username);
            Scanner scanfile = new Scanner(openfile);//scan file
            while (scanfile.hasNext()) {
                String str = scanfile.nextLine();
                if (str.startsWith("_money")) {
                    String[] sp = str.split("_");//split the string with "_" as delimiter
                    totalMoney = Double.parseDouble(sp[2]);//get input
                    user.setMoney(totalMoney);//call methed
                } else if (str.startsWith("_number of hands played")) {
                    String[] sp2 = str.split("_");
                    hands = Integer.parseInt(sp2[2]);
                    user.setTotalHands(hands);
                } else if (str.startsWith("_number of hands won")) {
                    String[] sp3 = str.split("_");
                    handsWin = Integer.parseInt(sp3[2]);
                    //System.out.println(handsWin);
                    user.setHandsWin(handsWin);
                }
            }
            System.out.println("Welcome back, " + username);
            System.out.println("Everything is just how you left it.");
        } catch (FileNotFoundException e) {//file already exist
            System.out.println("Welcome, " + username + "!");
            System.out.println("Have fun on your first time playing Infinite Blackjack.");
            System.out.println();
        }

        int play = 1;//0 for not play and 1 for play
        do {
            System.out.println("Name:            " + user.getName());
            System.out.println("Total Hands:     " + user.getTotalHands());
            System.out.println("Hands Won:       " + user.getHandsWin());
            System.out.printf("Money:         $%.1f", user.getMoney());
            System.out.println();
            boolean vnum = false;
            while (!vnum) {
                System.out.print("Play a hand? (1/0) > ");
                if (scan.hasNextInt() == true) {//check the input
                    int choice = scan.nextInt();
                    vnum = true;

                    if (choice == 0) {//does not play, quit the program
                        play = 0;
                        user.saveFile();
                        System.out.println("Thank you for playing Infinite Blackjack!");
                    } else if (choice == 1) {
                        boolean valid = false;
                        while (!valid) {
                            System.out.print("Enter amount to bet > ");
                            if (scan.hasNextDouble() == true) {//check input
                                bet = scan.nextDouble();
                                if (bet <= totalMoney && bet > 0) {
                                    valid = true;
                                    scan.nextLine();
                                    user.setMoney(bet);
                                    System.out.println("Let us go!");
                                } else if (bet > totalMoney) {
                                    valid = false;
                                    System.out.println("You don't have enough money! Try again!");
                                } else {
                                    valid = false;
                                    System.out.println("Not enough money! Try again!");
                                }
                            } else {
                                scan.next(); // throw value away, it's not an int
                            }
                        }

                        int aceTime = 0;
                        int totalU = 0;
                        int score = 0;
                        int totalD = 0;
                        boolean busted = false;
                        //get first card, using object and methods
                        Card cardu1 = new Card();
                        String CardU1 = cardu1.getCardNumberS();
                        String ColorU1 = cardu1.getCardColorS();
                        int cardNumU1 = cardu1.getScore();
                        //System.out.println(cardNumU1);
                        if (cardNumU1 == 1) {
                            aceTime++;
                        }
                        //get second card
                        Card cardu2 = new Card();
                        String CardU2 = cardu2.getCardNumberS();
                        String ColorU2 = cardu2.getCardColorS();
                        int cardNumU2 = cardu2.getScore();
                        //System.out.println(cardNumU2);
                        if (cardNumU2 == 1) {
                            aceTime++;
                        }

                        totalU += cardNumU1 + cardNumU2;
                        score = cardu.getScore(totalU, aceTime);
                        System.out.println("PLAYER DEAL");
                        System.out.println("Cards: " + CardU1 + ColorU1 + " " + CardU2 + ColorU2);//show the card
                        System.out.println("Score: " + score);
                        System.out.println();
                        //bonus points: double the bet
                        int duo;
                        boolean duobet = false;
                        boolean valid2 = false;
                        while(!valid2 && score != 21){
                            System.out.println("Do you want to double the bet? 1 for yes 0 for no");
                            if (scan.hasNextInt() == true){
                                duo = scan.nextInt();
                                scan.nextLine();
                                if (duo == 1){
                                    duobet = true; 
                                    double bet2 = bet * 2;
                                    if (bet2 <= totalMoney){
                                    	valid2 = true;
                                    	bet = bet2; 
                                        System.out.println("OK, double the bet!");
                                    }else{
                                    	valid2 = false;
                                    	bet = bet;
                                    	System.out.println("Not enough money, try again!");
                                    }
	
                                }else if (duo == 0){
                                    duobet = false;
                                    System.out.println("Keep Going!");
                                    valid2 = true;
                                }else{
                                    System.out.println("Try again!");
                                    valid2 = false;
                                }
                            }else{
                                scan.next();
                            }
                        }
                        System.out.println();
                        int stayOrhit = 1;
                        while (stayOrhit == 1) {//0 for stay and 1 for hit
                            System.out.print("Stay or Hit? ");//let user decide stay or hit
                            String s = scan.nextLine();
                            if (s.equalsIgnoreCase("S") || s.equalsIgnoreCase("Stay")) {
                                stayOrhit = 0;
                            } else if (s.equalsIgnoreCase("H") || s.equalsIgnoreCase("Hit")) {
                                stayOrhit = 1;
                                cardu.setCardNumber();
                                String CardU = cardu.getCardNumberS();
                                String ColorU = cardu.getCardColorS();
                                int cardNumU = cardu.getScore();
                                if (cardNumU == 1) {
                                    aceTime++;
                                }
                                System.out.println("Cards: " + CardU + ColorU);
                                totalU += cardNumU;
                                score = cardu.getScore(totalU, aceTime);
                                System.out.println("Score: " + score);
                                if (score > 21) {
                                    stayOrhit = 0;
                                    busted = true;
                                }
                                if (duobet == true){
                                    stayOrhit = 0;
                                    System.out.println("You can only have one card if you double the bet!");
                                }
                            } else {
                                System.out.println("Try again please");
                            }
                            //System.out.println(stayOrhit);//0 stay 1 hit
                            hands++;
                            user.setTotalHands(hands);
                        }
                        //System.out.println("1");
                        if (stayOrhit == 0 && busted == false) {//user stays and not busted
                            boolean hit = true;
                            int times = 0;
                            int aceTimeD = 0;
                            int scoreD = 0;
							//get first card for dealer
                            Card cardd1 = new Card();
                            String CardD1 = cardd1.getCardNumberS();
                            String ColorD1 = cardd1.getCardColorS();
                            int cardNumD1 = cardd1.getScore();
                            if (cardNumD1 == 1) {
                                aceTimeD++;
                            }
                            //get second card
                            Card cardd2 = new Card();
                            String CardD2 = cardd2.getCardNumberS();
                            String ColorD2 = cardd2.getCardColorS();
                            int cardNumD2 = cardd2.getScore();
                            if (cardNumD2 == 1) {
                                aceTimeD++;
                            }
							//set dealer's status using methods
                            totalD += cardNumD1 + cardNumD2;
                            scoreD = cardd.getScore(totalD, aceTimeD);
                            System.out.println("DEALER DEAL");
                            System.out.println("Cards: " + CardD1 + ColorD1 + " " + CardD2 + ColorD2);
                            System.out.println("Score: " + scoreD);
                            cardd.setDealerStatus(totalD, aceTimeD);
                            hit = cardd.getDealerStatus();
                            if (hit == true) {
                                System.out.println("HIT!");
                            } else {
                                System.out.println("STAY!");
                            }

                            while (hit == true) {//the dealer keeps hitting
                                cardd.setCardNumber();
                                String CardD = cardd.getCardNumberS();
                                String ColorD = cardd.getCardColorS();

                                int cardNumD = cardd.getScore();
                                if (cardNumD == 1) {
                                    aceTimeD++;
                                }

                                System.out.println("Cards: " + CardD + ColorD);
                                totalD += cardNumD;
                                score = cardd.getScore(totalD, aceTimeD);
                                System.out.println("Score: " + score);
                                cardd.setDealerStatus(totalD, aceTimeD);
                                hit = cardd.getDealerStatus();
                                if (hit == true) {
                                    System.out.println("HIT!");
                                } else {
                                    System.out.println("STAY!");
                                }
                                play = 1;
                                times++;
                            }
                        }
                        winOrlose = cardu.judegeWinOrLose(totalU, totalD, hands);
                        bet = cardu.returnMoney(winOrlose, bet);
                        //using method to judge who win the game and reset the money 

                        if (winOrlose == 1 || winOrlose == 2) {
                            handsWin++;
                            user.setHandsWin(handsWin);
                            System.out.println("You won $" + bet + "!");
                            //System.out.println(handsWin);
                            totalMoney += bet;
                            if (totalMoney > debtNeedReturn && debtNeedReturn != 0) {
                                totalMoney -= debtNeedReturn;
                                System.out.println("Time to return the money!");
                                System.out.println("You returned $" + debtNeedReturn + " and "
                                        + "now you have $" + totalMoney);
                                System.out.println();
                            }
                        } else if (winOrlose == 0) {
                            totalMoney += bet;
                        } else {
                            System.out.println("You lost all your money!");
                            totalMoney += bet;
                        }
                        user.setMoney(totalMoney);

                    } else {
                        System.out.println("Enter the right number!");
                        vnum = false;
                    }
                } else {
                    scan.next();
                }
            }
            if (totalMoney <= 0) {
                System.out.println("You have no more money to bet!");
                //bonus point2: bank
                boolean lol = false;
                while (!lol) {
                    System.out.println("Do you want to borrow money from the bank? 1 for yes 0 for no!");
                    if (scan.hasNextInt() == true) {
                        int keep = scan.nextInt();
                        if (keep == 0) {
                            System.out.println("Thank you for playing Infinite Blackjack!");
                            user.setMoney(totalMoney);
                            play = 0;
                            lol = true;
                        } else if (keep == 1) {
                            play = 1;
                            lol = true;
                            boolean lol2 = false;
                            while (!lol2) {
                                System.out.println("How much do you want to borrow?");
                                if (scan.hasNextDouble() == true) {
                                    double debt = scan.nextDouble();
                                    if (debt > 0) {
                                        totalMoney += debt;
                                        debtNeedReturn = 1.2 * debt;//return 1.2 times the bet
                                        user.setMoney(totalMoney);
                                        System.out.println("Remember to return $" + debtNeedReturn + "!");
                                        System.out.println();
                                        lol2 = true;
                                    } else {
                                        System.out.println("You loser, get out of my casino!");
                                        lol2 = true;
                                        play = 0;
                                    }
                                } else {
                                    scan.next();
                                }
                            }
                        } else {
                            System.out.println("Enter the right number!");
                            lol = false;
                        }
                    } else {
                        scan.next();
                    }
                }
            }
        } while (play == 1);
    }
}

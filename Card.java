
import java.util.Random;

public class Card {

    private int cardNumber, cardColor, score;
    private String card, color;
    private final Random ran = new Random();
    private boolean hitD;
    private double money;

    public Card() {
		setCardNumber();
		setCardColor();
    }

    public void setCardNumber() {
        cardNumber = ran.nextInt(13) + 1;

        if (cardNumber == 11) {
            card = "J";
            score = 10;
        } else if (cardNumber == 12) {
            card = "Q";
            score = 10;
        } else if (cardNumber == 13) {
            card = "K";
            score = 10;
        } else {
            card = "" + cardNumber;
            score = cardNumber;
        }
    }

    public String getCardNumberS() {
        return card;
    }

    public int getScore() {
        return score;
    }

    public void setCardColor() {
        cardColor = ran.nextInt(4) + 1;

        if (cardColor == 1) {
            color = "h";//heart
        } else if (cardColor == 2) {
            color = "d";//diamond
        } else if (cardColor == 3) {
            color = "s";//spade
        } else {
            color = "c";//club
        }
    }

    public String getCardColorS() {
        return color;
    }

//    public int getCardColorI() {
//        return cardColor;
//    }

    public void setDealerStatus(int totalD, int aceTime) {

        if (totalD >= 18 && totalD <= 21) {
            hitD = false;
        } else if (totalD == 17 && aceTime == 0) {
            hitD = false;
        } else if (totalD == 7 && aceTime == 1) {
            hitD = true;
        } else if (totalD > 21){
            hitD = false;
        } else {
            hitD = true;
        }
    }

    public boolean getDealerStatus() {
        return hitD;
    }

    public int judegeWinOrLose(int totalU, int totalD, int hands) {
        int win = -100;//o for push 1 for win -1 for lose 2 for Blackjack
        if (hands == 2 && totalU == 21) {
            System.out.println("You have a Blackjack!");
            win = 2;
        } else if ((totalD > totalU) && (totalD <= 21)) {
            System.out.println("The house wins!");
            win = -1;
        } else if ((totalU > totalD) && (totalU <= 21)) {
            System.out.println("You win!");
            win = 1;
        } else if (totalU == totalD) {
            System.out.println("Push");
            win = 0;
        } else if (totalU > 21){
            System.out.println("Player busted!");
            win = -1;
        } else if (totalD > 21 && totalU <= 21){
            System.out.println("House busted!");
            win = 1;
        }
        return win;
    }

    public double returnMoney(int status, double bet) {
        if (status == 2) {
            money = bet * 2.5;
        } else if (status == 1) {
            money = bet * 2;
        } else if (status == 0) {
            money = 0;
        } else if (status == -1) {
            money = bet * -1;
        }
        return money;
    }
    
    public int getScore(int points, int numAces) {
	int s = 0;

	// If there are no aces, or if score is less than 21 with aces at
	// 11 points each, then the actual score is just
	// equal to the number of points.
	
	if (numAces == 0 || points <= 21) {
	    s = points;
	}else if (points > 21){
            s = points;
        }else {

	    // Otherwise, we need to check what is the BEST score is,
	    // and that gets a little complicated.  We set a placeholder
	    // -1 for best score, and a placeholder potential score.
	    // We will keep track of what the best score is, and try
	    // different potential scores against it.  Whatever is
	    // highest without going over 21 will win as the best score.
	    
	    int bestScore = -1;
	    int potentialScore = points;

	    // Loop through _number of aces_ times.  Each time, try an
	    // increasing number of aces as a 1 value instead of an
	    // 11 value (thus, subtract 10 * j from the total points
	    // value, which assumes all Aces are equal to 11 points).
	    
	    for (int j = 0; j <= numAces; j++) {
		potentialScore = (points - (10 * j));

		// For each iteration, if the potential score is
		// better than the already-best score, but it is NOT
		// over 21 (causing us to bust), then the
		// potential score should count as our new best score.
		
		if (potentialScore > bestScore && potentialScore <= 21) {
		    bestScore = potentialScore;
		}
	    }

	    // We could have busted even when all of our aces were set
	    // to one point.  In this case, we might never have gotten a
	    // valid "best" score.  But our best potential score is the closest
	    // to a best score we have, so we will replace our placeholder -1
	    // best with the best potential score we got.

	    // Otherwise, just set the score to the best score.
	    
	    if (bestScore == -1) {
			s = potentialScore;
	    } else {
			s = bestScore;
	    }
	}
	return s;
    }
}

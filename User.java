
import java.io.*;

public class User {

    private String userName;
    private int totalHands;
    private int handsWin;
    private double money;

    public User() {
		
    }

    public User(String name, int hands, int winTimes, double money) {
        userName = name;
        totalHands = hands;
        handsWin = winTimes;
        this.money = money;        
    }

    public void setName(String name) {
        userName = name;
    }

    public String getName() {//because userName is private
        return userName;
    }

    public void setTotalHands(int hands) {
        totalHands = hands;
    }

    public int getTotalHands() {
        return totalHands;
    }

    public void setHandsWin(int winTimes) {
        handsWin = winTimes;
    }

    public int getHandsWin() {
        return handsWin;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return this.money;
    }

    public void saveFile() throws IOException{
            PrintWriter output = new PrintWriter(userName);
            output.println("_number of hands played_" + totalHands);
            output.println("_number of hands won_" + handsWin);
            output.println("_money_" + money);
			output.close();
    }
}

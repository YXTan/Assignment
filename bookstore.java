
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class bookstore {

    public static void main(String[] args) throws FileNotFoundException {
        //create necessary variables
        final double BOOK_PRICE = 5.00;
        final double BOOKMARK_PRICE = 1.00;
        final double PACK_PRICE = 5.00;
        final double PAINTINGS_PRICE = 100.00;
        final double TAX = 0.07;
        final double LUCKY_DISCOUNT = 0.9;
		
		//create scanners
        Scanner scan = new Scanner(System.in);
        Scanner scan1 = new Scanner(System.in);
        Scanner scan2 = new Scanner(System.in);

        int wtInLine = 0, count = 1;//make two flags to control the while loop
        while (wtInLine == 0) {
            int bookNum = 0, bookmarkNum = 0, packNum = 0, paintingsNum = 0, totalMarks = 0, extrafee = 0;
			//reset variables every time
				
            System.out.print("Is there another constumer waiting in line? 1 for yes and 2 for no: ");
            int i = scan.nextInt();
            if (i == 2) {
                System.out.println("Thank you for your time!");
                System.exit(0);//exit the program
            } else if (i == 1) {

                int choice = 0;
                while (choice == 0) {
                	System.out.println("Here is the list!");
                	giveList();//call a method to give the list
                    System.out.println("Enter your choice from 1 to 4!");//choose what to buy
                    System.out.println("You have already bought " + bookNum + " books, " + totalMarks + " bookm"
                            + "arks, and " + paintingsNum + " paintings.");
                            //show current status(every time)
                    choice = scan1.nextInt();

                    if (choice == 1) {
                        int b = 0;
                        while (b == 0) {
                            System.out.print("How many books do you want to buy? ");
                            //get the number of books
                            b = scan.nextInt();
                            if (b > 0) {
                                bookNum = b;
                                
                                //open and read file and add extra personalized fees
                                int personalizeOrNot = 0;
                                while (personalizeOrNot == 0){
                                System.out.println("Do you want to personalize a book for an additional $1.00?");
                                System.out.print("1 for yes and 2 for no ");
                                int per = scan.nextInt();
                                if (per == 1){
                                    File file = new File("Inventory.txt");
                                    Scanner scanFile = new Scanner(file);
                                    System.out.println("Here are what we have now!");//show another list 
                                    while (scanFile.hasNext()){
                                        String str = scanFile.nextLine();
                                        System.out.println(str);                   
                                    }
                                    
                                    System.out.print("Please enter you choice using the book name number ");
                                    int nameNum = scan2.nextInt();
                                    String name = "" + nameNum;
                                    //System.out.println(name);
                                    
                                    Scanner scanFile2 = new Scanner(file);
                                    while (scanFile2.hasNext()){
                                        String str = scanFile2.nextLine();
                                        if (str.startsWith(name)){
                                            int start = str.indexOf(": ") + 1;
                                            int variable = Integer.parseInt((str.substring(start, str.length()).trim()));
                                            //read from the file that how many books left
                                            int enough = 0;
                                            while (enough == 0){
                                            System.out.print("How many do you want to buy? ");
                                            int num = scan.nextInt();
                                            if (num <= variable){
                                                System.out.println("Got it!");
                                                bookNum += num;
                                                //System.out.println(bookNum);
                                                extrafee = num;
                                                //System.out.println(extrafee);
                                                enough = 1;
                                                personalizeOrNot = 1;
                                                choice = 0;//quit the loop
                                            }else if (num > variable){
                                                System.out.println("Sorry we do not have that many!"); 
                                                enough = 0;
                                            }
                                            }
                                        }
                                    }
                                }else if (per == 2){
                                    personalizeOrNot = 1;
                                    choice = 0;
                                }else{
                                    System.out.println("Try again please!");
                                    personalizeOrNot = 0;
                                    choice = 0;
                                }
                                }
                            } else {
                                System.out.println("Error, make sure you enter the right number!");
                                b = 0;
                            }
                        }
                    } else if (choice == 2) {
                        int bm = 0;
                        while (bm == 0) {
                            System.out.print("How many bookmarks do you want to buy? ");
                            bm = scan.nextInt();
                            if (bm > 0) {
                                if (bm >= 6) {
                                    packNum = bm / 6;
                                    bookmarkNum = bm % 6;
                                    totalMarks = bm;
                                } else {
                                    bookmarkNum = bm;
                                    totalMarks = bm;
                                }
                                choice = 0;
                            } else {
                                System.out.println("Error, make sure you enter the right number!");
                                bm = 0;
                            }
                        }
                    } else if (choice == 3) {
                        int p = 0;
                        while (p == 0) {
                            System.out.print("How many paintings do you want to buy? ");
                            p = scan.nextInt();
                            if (p > 0) {
                                paintingsNum = p;
                                choice = 0;
                            } else {
                                System.out.println("Error, make sure you enter the right number!");
                                p = 0;
                            }
                        }
                    } else if (choice == 4) {
                        choice = 11111;
                    } else {
                        System.out.println("Please enter the right number!");
                        choice = 0;
                        System.out.println();
                    }
                }
                //bookworm card
                int k = 0;
                boolean vip = false;
                while (k == 0) {
                    System.out.print("Do you have a Bookworm Card? 1 for yes and 2 for no ");
                    int bookCard = scan.nextInt();                    
                    if (bookCard == 1) {
                        vip = true;
                        k = 1;
                    } else if (bookCard == 2) {
                        vip = false;
                        k = 1;
                    }else{
                        System.out.println("Try again please!");
                        k = 0;
                    }                    
                }
                //print the receipt and do the calculation
                double booksTotal, packsTotal, bookmarksTotal, paintingsTotal, subtotal, total, tax, extra;
					
                booksTotal = bookNum * BOOK_PRICE;
                extra = extrafee * 1;
                paintingsTotal = paintingsNum * PAINTINGS_PRICE;
                packsTotal = packNum * PACK_PRICE;
                bookmarksTotal = bookmarkNum * BOOKMARK_PRICE;
                subtotal = booksTotal + paintingsTotal + bookmarksTotal + packsTotal + extra;
                tax = subtotal * TAX;

                if (count % 3 == 0) {
                    total = (tax + subtotal) * LUCKY_DISCOUNT;
                }else if (vip == true){
                    total = tax + (subtotal * 0.75);
                }else if (vip == true && count % 3 == 0){
                    total = (tax + (subtotal * 0.75)) * LUCKY_DISCOUNT;
                } 
                else {
                    total = tax + subtotal;
                }
				
				//print out the recipst	with proper format
                System.out.println();
                System.out.println("Here is the receipt");
                System.out.println("----------------------");
                if (count % 3 == 0) {
                    System.out.println("You get lucky discount!");
                } else {
                    System.out.println("You did not get a discount! Better luck next time!");
                }
                System.out.println();
                if (booksTotal != 0) {
                    System.out.printf("%4d Book(s)            $%.2f\n", bookNum, booksTotal);
                    if (extrafee != 0){
                    System.out.printf("%4d Personalized Books $%.2f\n", extrafee, extra);
                    }
                }
                if (bookmarksTotal != 0) {
                    System.out.printf("%4d Bookmark packs(s)  $%.2f\n", packNum, packsTotal);
                    System.out.printf("%4d Single Bookmark(s) $%.2f\n", bookmarkNum, bookmarksTotal);
                }
                if (paintingsTotal != 0) {
                    System.out.printf("%4d Painting(s)        $%.2f\n", paintingsNum, paintingsTotal);
                }
                System.out.println();
                System.out.printf("Subtotal:     $%.2f\n", subtotal);               
                System.out.printf("Tax:          $%.2f\n", tax);
                System.out.printf("Total:        $%.2f\n", total);

                System.out.println("----------------------");
                System.out.println();
				
				//make the payment
                boolean enough = false;
                while (enough == false) {
                    System.out.print("Enter amount paid (no dollar sign) ");
                    double amount = scan.nextDouble();
                    //System.out.println(amount);
                    if (amount >= total) {
                        System.out.printf("Change:       $%.2f\n", (amount - total));
                        enough = true;
                    } else {
                        System.out.println("Not enough money! Please re-enter!");
                    }
                }
                System.out.println("Thank you!");
                count++;
                //System.out.println(count);
            } else {
                System.out.println("Please enter the right number!");
            }
        }
    }
public static void giveList(){//a method for listing
     System.out.println();
     System.out.println("1 -- Books ($5.00 each)");
     System.out.println("2 -- Bookmarks ($1.00 each, or a pack of six for $5.00)");
     System.out.println("3 -- Paintings of Books ($100.00 each)");
     System.out.println("4 -- Checkout");
     System.out.println();
}
}
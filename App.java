package workshop03;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args ) throws IOException
    {
        //Create new directory during first time execution of program.
        String dirPath = args[0];
        File cartDirectory = new File(dirPath);
        if (cartDirectory.exists()) {
            System.out.println("Directory already exists.");
        }
        else {
            cartDirectory.mkdir();
        }

        System.out.println("Welcome to your shopping cart");

        ArrayList<String> cart = new ArrayList<String>();
        Console con = System.console();
        String input = "";
        //To keep track of current logged in user.
        String user = "";

        while (!input.equals("quit")) {
            input = con.readLine();

            if (input.startsWith("login")) {
                //Initiate scanner to scan from index 6 onward.
                Scanner scan = new Scanner(input.substring(6));

                while (scan.hasNext()) {
                    user = scan.next();
                }

                //Check if user has existing cart in database. Create new file in directory if data does not exist.
                File userFile = new File(dirPath + File.separator + user);
                if (userFile.exists()) {
                    //TO-DO: Change display to read user file and inform if cart is empty or contains items.
                    System.out.println("User " + user + " already exists in database.");
                } 
                else {
                    userFile.createNewFile();
                }
            }

            if (input.equals("users")) {
                File directoryPath = new File(dirPath);

                String[] userListing = directoryPath.list();
                System.out.println("The following users are registered");
                //For loop to display userListing.
                for (int i = 1; i < userListing.length + 1; i++) {
                    System.out.println((i) + ". " + userListing[i]);
                }
            }

            if (input.startsWith("add")) {
                //Replace commas with space.
                input = input.replace(',', ' ');

                //Read existing items in user cart into ArrayList.
                File readFile =  new File(dirPath + File.separator + user);
                BufferedReader br = new BufferedReader(new FileReader(readFile));

                String readFileInput = "";

                //Reset cart item arraylist as we will be reading and printing list from scratch in next step.
                cart = new ArrayList<String>();
                
                //while loop to read through item records in file
                while ((readFileInput = br.readLine()) != null) {
                    cart.add(readFileInput);
                }

                //Use FileWriter and PrintWriter/BufferedWriter to write to a user file.
                FileWriter fw = new FileWriter(dirPath + File.separator + user, true);
                PrintWriter pw = new PrintWriter(fw);

                String currentScanString = "";
                Scanner inputScanner = new Scanner(input.substring(4));

                //Set scanstring as the next item from substring index 4 onward, add all available items to cart list.
                while (inputScanner.hasNext()) {
                    currentScanString = inputScanner.next();
                    // cart.add(currentScanString);
                    // System.out.println(currentScanString + " added to cart");
                    // System.out.println(cart);
                    // pw.write(currentScanString + "\n");
                    if (cart.contains(currentScanString)){
                        System.out.println("Cart already contains " + currentScanString);
                    }
                    else {
                        cart.add(currentScanString);
                        System.out.println(currentScanString + " added to cart");
                        System.out.println(cart);

                        //Write to file using PrintWriter.
                        pw.write(currentScanString + "\n");
                    }
                }
                //Flush and close FileWriter and PrintWriter.
                pw.flush();
                pw.close();
                fw.close();
                br.close();
            }

            if (input.equals("list")) {
                //Check if a user is logged in.
                //File class and BufferedReader class to read cart items from file.
                File readFile =  new File(dirPath + File.separator + user);
                BufferedReader br = new BufferedReader(new FileReader(readFile));

                String readFileInput = "";

                //Reset cart item arraylist as we will be reading and printing list from scratch in next step.
                cart = new ArrayList<String>();
                
                //while loop to read through item records in file
                while ((readFileInput = br.readLine()) != null) {
                    cart.add(readFileInput);
                }

                for (int j = 0; j < cart.size(); j++) {
                    System.out.println((j + 1) + ". " + cart.get(j));
                }

                //exit from while loop, close BufferedReader class/object
                br.close();
            }

            if (input.startsWith("delete")) {
                //Split input string and remove item from index 1
                String[] stringVal = input.split(" ");                  //input = 1
                int indexToDelete = Integer.parseInt(stringVal[1]) - 1;         //index = input - 1 = 0
                if (indexToDelete + 1 > cart.size()) {
                    System.out.println("Item index out of range");
                }
                else {
                    System.out.println(cart.get(indexToDelete) + " removed from cart");
                    cart.remove(indexToDelete);
                }

                //Open FileWriter and BufferedWriter.
                FileWriter fw = new FileWriter(dirPath + File.separator + user, false);
                BufferedWriter bw = new BufferedWriter(fw);

                //Loop to write items in to file.
                int listIndex = 0;
                while (listIndex < cart.size()) {
                    bw.write(cart.get(listIndex));
                    bw.newLine();
                    listIndex++;
                }
                
                //flush and close bw and fw
                bw.flush();
                fw.flush();
                bw.close();
                fw.close();
            }
        }
    }
}

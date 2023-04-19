package workshop03;

import java.io.Console;
import java.io.File;
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
                    System.out.println("User " + user + " already exists in database.");
                } 
                else {
                    userFile.createNewFile();
                }
            }

            if (input.equals("users")) {
                File directoryPath = new File(dirPath);

                String[] userListing = directoryPath.list();
                System.out.println("List of users in database " + dirPath);
                //For loop to display userListing.
                for (String listing : userListing) {
                    System.out.println(listing);
                }
            }

            if (input.startsWith("add")) {
                //Replace commas with space.
                input = input.replace(',', ' ');

                //Use FileWriter and PrintWriter to write to a user file.
                FileWriter fw = new FileWriter(dirPath + File.separator + user);
                PrintWriter pw = new PrintWriter(fw);

                String currentScanString = "";
                Scanner inputScanner = new Scanner(input.substring(4));

                //Set scanstring as the next item from substring index 4 onward, add all available items to cart list.
                while (inputScanner.hasNext()) {
                    currentScanString = inputScanner.next();
                    cart.add(currentScanString);
                    
                    //Write to file using PrintWriter.
                    pw.write(currentScanString + "\n");
                
                }
                //Flush and close FileWriter and PrintWriter.
                pw.flush();
                pw.close();
                fw.close();
            }
        }
    }
}

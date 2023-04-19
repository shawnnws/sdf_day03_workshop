package workshop03;

import java.io.File;
import java.util.ArrayList;

public class App 
{
    public static void main( String[] args )
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
        
    }
}

// Imports
import java.util.Calendar;
import java.util.Scanner;
import java.lang.Exception;
import java.lang.Thread;

// Main Class
public class Main {
    // This is the main method of the program, which is called when the program is run
    public static void main(String[] args) throws Exception {
        menu(); // Call the menu method
    }

    // This is a Scanner object used to read input from the user
    public static Scanner myObj = new Scanner(System.in);

    // Method to display the main menu of the program 
    public static void menu() throws Exception {
        // Define an array of strings to store the names of the months
        String months[] = {
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        };

        // Create a Calendar object to get the current date and time
        Calendar calendar = Calendar.getInstance();
        // Print the main menu header
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(
                "\u001B[41m"
                        + "            *** Welcome to Doctor Appointment System. Please Choose An appropriate Option. ***"
                        + "\u001B[0m");
        System.out.println("--------------------------------------------------------------------------------");
        
        // Print the current date and time
        System.out.print("Date: " + months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.DATE) + " "
                + calendar.get(Calendar.YEAR));

        System.out.println("\t\t\t\t\t\tTime: " + calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE)
                + ":" + calendar.get(Calendar.SECOND));
        
        // Print the main menu options
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("1: For Doctors");
        System.out.println("2: For Patients");
        System.out.println("3: For Admin");
        System.out.println("4: To Exit");

        // Check if there's another line available
        if (myObj.hasNextInt()) {
            // Read user input
            int response = myObj.nextInt();

            // Process user input
            switch (response) {
                case 1:
                    Doctor.main(null);
                    break;
                case 2:
                    Patient.main(null);
                    break;
                case 3:
                    Admin.main(null);
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please Enter a valid Choice");
                    Thread.sleep(1000);
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    myObj.nextLine();
                    menu();
                    break;
            }
        } else {
            System.out.println("Please enter valid Options");
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            myObj.nextLine();
            menu();
        }

        myObj.close(); // Closing scanner
    }
}
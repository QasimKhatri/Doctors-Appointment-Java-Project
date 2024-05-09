// Imports
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Patient Class
class Patient {
    // Scanner object used to read input from the user
    private static Scanner myObj = new Scanner(System.in);

    // Main method of the program, called when the program is run
    public static void main(String[] args) throws InterruptedException {
        // Print the main menu header
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("\u001B[41m" + "*** Welcome to Patient's Portal. Please Choose An appropriate Option. ***"
                + "\u001B[0m");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("\n1: Login as Patient");
        System.out.println("2: Register as Patient");
        System.out.println("3: Back to Main Menu");

        try {
            // Read user input
            switch (myObj.nextInt()) {
                case 1:
                    login();
                    break;
                case 2:
                    myObj.nextLine();
                    reg();
                    login();
                    break;
                case 3:
                    Main.main(null);
                    break;
                default:
                    System.out.println("Please Enter a valid Choice");
                    Thread.sleep(1000);
                    myObj.nextLine();
                    Patient.main(null);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Please Enter a valid Choice");
            Thread.sleep(1000);
            myObj.nextLine(); // consume the invalid input
            Patient.main(null);
        }
    }

    // Method to log in as a patient
    public static void login() throws Exception {
        // Print the login header
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println("\u001B[41m" + "*** Please Login With Correct Credentials. ***" + "\u001B[0m");
        System.out.println("--------------------------------------------------------------------------------");

        try {
            // Create a Scanner object to read from the PatientsDB.txt file
            Scanner fileScan = new Scanner(new File("PatientsDB.txt"));

            // Print prompts for the user to enter their username and password
            System.out.println("Enter Your UserName: ");
            String uName = myObj.next();
            System.out.println("Enter Your Password: ");
            String pass = myObj.next();
            boolean found = false;

            // Loop through the lines in the PatientsDB.txt file
            while (fileScan.hasNextLine()) {
                // Read a line from the file
                String input = fileScan.nextLine();
                // Split the line into an array of strings
                String[] tempArr = input.split(" ");
                // Get the username and password from the array
                String usernameFromFile = tempArr[4];
                String passwordFromFile = tempArr[5];

                // Check if the entered username and password match the ones in the file
                if (usernameFromFile.equals(uName) && passwordFromFile.equals(pass)) {
                    // If they match, set the found variable to true
                    found = true;
                    // Call the patientMenu method
                    patientMenu(uName, tempArr[0]);
                    // Break out of the loop
                    break;
                }
            }

            // If the user was not found, print a message and return to the patient menu
            if (!found) {
                System.out.println("User Not found");
                Thread.sleep(1000);
                Patient.main(null);
            }

            // Close the Scanner object
            fileScan.close();
        } catch (Exception e) {
            // If an error occurred, print an error message and return to the patient menu
            System.out.println("An error occurred while login: " + e.getMessage());
            System.out.print("\033[H\033[2J");
            System.out.flush();
            myObj.nextLine();
            Patient.main(null);
        }
    }

    // Method to register as a patient
    public static void reg() throws Exception {
        try {
            // Create a PrintWriter object to write to the PatientsDB.txt file
            PrintWriter pw = new PrintWriter(new FileWriter("PatientsDB.txt", true));
            // Print prompts for the user to enter their name, age, gender, contact, username, and password
            System.out.println("Enter your name:");
            String fullName = myObj.next();
            while (!stringValid(fullName) || fullName.equals("")) {
                System.out.println("Enter valid name please");
                fullName = myObj.next();
            }

            System.out.println("Enter your Age:");
            String Age = myObj.next();
            while (!numValid(Age) || Age.equals("")) {
                System.out.println("Enter valid age");
                Age = myObj.next();
            }

            System.out.println("Enter your gender: ");
            String gender = "";
            while (true) {
                String choice = myObj.next().toUpperCase();
                if (choice.equals("MALE") || choice.equals("FEMALE")) {
                    gender = choice;
                    break;
                } else {
                    System.out.println("Please specify your gender whether Male or Female! ");
                }
            }

            System.out.println("Enter your Contact:");
            String Contact = myObj.next();
            while (!contactValid(Contact) || Contact.equals("")) {
                System.out.println("Enter valid contact number containing 10 digits: ");
                Contact = myObj.next();
            }

            System.out.println(
                    "Choose Your Username. Note: Don't use spaces or symbols. It will be used as login. And its Case sensitive");
            String Uname = myObj.next();
            System.out.println(
                    "Enter Your Password. Note: Use easy password as currently we don't have any recovery option");
            String pass = myObj.next();

            // Write the user's information to the PatientsDB.txt file
            pw.println(fullName + " " + Age + " " + gender + " " + Contact + " " + Uname + " " + pass);
            System.out.println("Successfully Registered. Now Please Use Right Information to login.");

            // Close the PrintWriter object
            pw.close();

        } catch (IOException e) {
            // If an error occurred, print an error message and return to the main menu
            System.out.println("An error occurred while registering: " + e.getMessage());
            System.out.print("\033[H\033[2J");
            System.out.flush();
            myObj.nextLine();
            Patient.main(null);
        }
    }

    // Method to display the patient menu
    public static void patientMenu(String uName, String patName) throws Exception {
        // Print the patient menu header
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(
                "\u001B[41m" + "*** Welcome To Patient's Menu. Please Choose Appropriate Options. ***" + "\u001B[0m");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(
                "\nWelcome " + uName.substring(0, 1).toUpperCase() + uName.substring(1) + " Choose your Option");
        System.out.println("1: Appointment Menu");
        System.out.println("2: Cancel Your Appointment ");
        System.out.println("3: To Exit");
        try {
            // Read user input
            int response = myObj.nextInt();
            switch (response) {
                case 1:
                    // Call the bookAppointment method
                    Appointment.bookAppointment(uName, patName);
                    patientMenu(uName, patName);
                    break;
                case 2:
                    // Call the cancelAppointment method
                    Appointment.cancelAppointment(uName);
                    patientMenu(uName, patName);
                    break;
                case 3:
                    // Return to the main menu
                    Patient.main(null);
                    break;
                default:
                    System.out.println("Please Enter a valid Choice");
                    myObj.nextLine();
                    patientMenu(uName, patName);
                    break;
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Please enter valid Options");
            myObj.nextLine();
            patientMenu(uName, patName);
        }
    }

    // Method to view list of all Patients
    public static void allPatients() {
        try {
            File myObj = new File("PatientsDB.txt");
            Scanner myReader = new Scanner(myObj);
            System.out.println();
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println(
                    "\u001B[41m"
                            + "            *** Welcome To Patients' List. Please Choose Wisely ***"
                            + "\u001B[0m");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println();
            System.out.println();
            System.out.println(
                    "______________________________________________________________________________________________");
            System.out.println(
                    "______________________________________________________________________________________________");
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                // System.out.println(data);
                String[] name = data.split(" ");
                System.out.println();
                System.out.println();
                for (int i = 0; i < 4; i++) {
                    System.out.print(String.format("%20s", name[i]));
                }
            }
            System.out.println();
            System.out.println(
                    "______________________________________________________________________________________________");
            System.out.println(
                    "______________________________________________________________________________________________");
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Validation method for checking if a string contains only alphabets and spaces
    public static boolean stringValid(String str) {
        return str.matches("[a-zA-Z ]+");
    }

    // Validation method for checking if a string contains only numeric characters
    public static boolean numValid(String str) {
        return str.matches("\\d+");
    }

    // Method for validating contact number
    public static boolean contactValid(String input) {

        try {
            Pattern p = Pattern.compile("^\\d{10}$");
            Matcher m = p.matcher(input);
            return (m.matches());

        } catch (Exception e) {
            return false;
        }
    }
}

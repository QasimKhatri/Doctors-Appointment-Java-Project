
// Imports
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.Exception;
import java.lang.Thread;
import java.util.regex.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Doctor Class
class Doctor {
    // Scanner object used to read input from the user
    private static Scanner myObj = new Scanner(System.in);

    // Main method of the program, called when the program is run
    public static void main(String[] args) throws InterruptedException {
        System.out.println();
        System.out.println();
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(
                "\u001B[41m" + "            *** Welcome Doctor. Please Choose An appropriate Option. ***"
                        + "\u001B[0m");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println("1: Login as Doctor");
        System.out.println("2: Register as Doctor");
        System.out.println("3: Back To Main Menu");
        
        try {
            int choice = myObj.nextInt();
            myObj.nextLine(); // Consume the newline after reading integer
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    reg(); // No need to call login here if reg() should lead to login
                    break;
                case 3:
                    Main.main(args);
                    break;
                default:
                    System.out.println("Please Enter a valid Choice");
                    Thread.sleep(1000);
                    Doctor.main(null);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Please Enter a Valid Choice");
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            myObj.nextLine(); // Clear any incorrect input
            Doctor.main(null);
        }

        myObj.close();
    }

    // Method to log in as a doctor
    public static void login() throws Exception {
        try {
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("\u001B[41m" + "            *** Welcome to Login. Please Enter Correct Credentials. ***"
                    + "\u001B[0m");
            System.out.println("--------------------------------------------------------------------------------");

            System.out.println("Enter Your UserName: "); // Prompt for username
            String uName = myObj.nextLine(); // Read the username
            System.out.println("Enter Your Password: "); // Prompt for password
            String pass = myObj.nextLine(); // Read the password

            boolean found = false;
            String docName = ""; // Variable to store doctor's name

            Scanner fileScan = new Scanner(new File("DoctorsDB.txt"));
            while (fileScan.hasNextLine()) {
                String input = fileScan.nextLine();
                String[] tempArr = input.split(" ");
                String Username = tempArr[4];
                String Password = tempArr[5];

                if ((Username.equals(uName)) && (Password.equals(pass))) {
                    found = true;
                    docName = tempArr[0]; // Get the doctor's name
                    break;
                }
            }

            fileScan.close();

            if (found) {
                doctorLoginMenu(uName, docName);
            } else {
                System.out.println("User Not found");
                Thread.sleep(1000);
                main(null); // Re-run main to restart the process
            }

        } catch (Exception e) {
            System.out.println("An error occurred while logging in: " + e.getMessage());
            System.out.print("\033[H\033[2J");
            System.out.flush();
            Doctor.main(null); // Retry the main loop in case of errors
        }
    }

    // Method to display the doctor menu
    public static void doctorLoginMenu(String uName, String docName) throws Exception {
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(
                "\u001B[41m"
                        + "            *** Welcome to Doctor's Menu. Please Choose An appropriate Option. ***"
                        + "\u001B[0m");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println(
                "Welcome " + uName.substring(0, 1).toUpperCase() + uName.substring(1) + " Choose your Option");
        System.out.println("1: For All Doctors List");
        System.out.println("2: For All Booked appointments");
        System.out.println("3: For All Patients Info ");
        System.out.println("4: To Exit");
        try {
            int response = myObj.nextInt();
            switch (response) {
                case 1:
                    allDocs();
                    doctorLoginMenu(uName, docName);
                    break;
                case 2:
                    bookedAppointments("Dr." + docName);
                    doctorLoginMenu(uName, docName); // Booked Appointments
                    break;
                case 3:
                    Patient.allPatients();
                    doctorLoginMenu(uName, docName);

                    break;
                case 4:
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    Main.main(null);
                    break;
                default:
                    System.out.println("Please Enter a valid Choice");
                    myObj.nextLine();
                    doctorLoginMenu(uName, docName);
                    break;
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Please enter valid Options");
            myObj.nextLine();
            doctorLoginMenu(uName, docName);
        }

    }

    // Method to register Doctor
    public static void reg() throws Exception {
        try {
            System.out.println("Enter your name:");
            String fullName = myObj.nextLine();
            while (!stringValid(fullName) || fullName.isEmpty()) {
                System.out.println("Enter valid name please");
                fullName = myObj.nextLine();
            }
    
            System.out.println("Enter your Age:");
            String Age = myObj.nextLine();
            while (!numValid(Age) || Age.isEmpty()) {
                System.out.println("Enter valid age");
                Age = myObj.nextLine();
            }
    
            System.out.println("Enter your Designation:");
            String Designation = myObj.nextLine();
            while (!stringValid(Designation) || Designation.isEmpty()) {
                System.out.println("Enter valid Designation please");
                Designation = myObj.nextLine();
            }
    
            System.out.println("Enter your Specialization:");
            String Specialization = myObj.nextLine();
            while (!stringValid(Specialization) || Specialization.isEmpty()) {
                System.out.println("Enter valid Specialization please");
                Specialization = myObj.nextLine();
            }
    
            System.out.println();
            System.out.println(
                    "Choose Your Username. Note: Don't use spaces or symbols. It will be used as login. And it's case sensitive.");
            String Uname = myObj.nextLine();
            System.out.println(
                    "Enter Your Password. Note: Use an easy password, as currently, we don't have any recovery option.");
            String pass = myObj.nextLine();
    
            String strForList = fullName.replace(" ", "_") + " " + Age + " " + Designation + " " + Specialization + " " + Uname + " " + pass + "\n";
    
            if (!validate(Uname)) {
                try {
                    FileWriter fw = new FileWriter("DoctorsDB.txt", true);
    
                    // Write the new doctor details into the file
                    fw.write(strForList);
                    System.out.println("Successfully Registered. Please use correct information to log in.");
                    fw.close();
                    
                    Thread.sleep(1000);
                    
                    // After successful registration, prompt the doctor to log in
                    login(); 
    
                } catch (Exception e) {
                    System.out.println("An error occurred while writing to file: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while registering: " + e.getMessage());
            System.out.print("\033[H\033[2J");
            System.out.flush();
            myObj.nextLine();
            Doctor.main(null);
        }
    }

    static void bookedAppointments(String searched) {
        String filePath = "Appointments.txt";

        // Replace "search_string" with the string you want to search for
        String searchString = searched;
        System.out.println();
        System.out.println();
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line of the file
            while ((line = reader.readLine()) != null) {
                // If the line contains the search string, print it
                if (line.contains(searchString)) {
                    System.out.println(line);
                }

            }

            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println();
            System.out.println();

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    // Method to view list of all Doctors
    public static void allDocs() {
        try {
            System.out.println();
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println(
                    "\u001B[41m"
                            + "            *** Welcome To Doctors' List. Please Choose Wisely ***"
                            + "\u001B[0m");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println();
            System.out.println();
            File myObj = new File("DoctorsDB.txt");
            Scanner myReader = new Scanner(myObj);
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

    // Validate User
    public static boolean validate(String Name) throws Exception {
        boolean found = false;
        try (Scanner fileScan = new Scanner(new File("DoctorsDB.txt"))) {
            while (fileScan.hasNextLine()) {
                String input = fileScan.nextLine();
                String[] tempArr = input.split(" ");
                String Username = tempArr[4];
                Username = Username.toUpperCase();
                Name = Name.toUpperCase();

                if (Username.equals("")) {
                    break;
                } else if (Username.equals(Name)) {
                    found = true;
                    break; // No need to continue searching if found
                }
            }
        }

        if (found) {
            System.out.println("User already Registered. Please try with some other Username. Thanks");
            reg(); // Not sure what reg() does, assuming it's a method call
        } else {
            System.out.println("Registering User");
        }
        return found;
    }

    // method for validating Strings from Doctors
    public static boolean stringValid(String input) {
        return Pattern.matches("[a-zA-Z/s]+", input);
    }

    // method for validating numbers from Doctors
    public static boolean numValid(String input) {
        try {

            int n = Integer.parseInt(input);
            if (n > 0 && n < 100)
                return true;
            else
                return false;

        } catch (Exception e) {
            return false;
        }

    }

}

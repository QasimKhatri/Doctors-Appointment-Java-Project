// Imports
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.Exception;
import java.lang.Thread;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

// Admin Class
class Admin {
    private static Scanner myObj = new Scanner(System.in);
    static int response;

    public static void main(String[] args) throws InterruptedException {
        System.out.println();
        System.out.println();
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(
                "\u001B[41m" + "            *** Welcome Admin. Please Choose An appropriate Option. ***" + "\u001B[0m");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println("1: Login as Admin");
        System.out.println("2: Register as Admin");
        System.out.println("3: Back To Main Menu");

        try {
            int choice = myObj.nextInt();
            myObj.nextLine(); 
            // Read user choice
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    myObj.nextLine();
                    registerAdmin();
                    login();
                    break;
                case 3:
                    Main.main(args);
                    break;
                default:
                    System.out.println("Please Enter a valid Choice");
                    Thread.sleep(1000);
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    myObj.nextLine();
                    Admin.main(null);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Please Enter a Valid Choice");
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            myObj.nextLine();
            Admin.main(null);
        }

        myObj.close();
    }

    public static void login() throws Exception {
        try {
            Scanner fileScan = new Scanner(new File("AdminsDB.txt"));
            System.out.println();
            System.out.println();
            System.out.println("\n--------------------------------------------------------------------------------");
            System.out.println("\u001B[41m" + "            *** Welcome to Login. Please Enter Correct Credentials. ***"
                    + "\u001B[0m");
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println();
            System.out.println();

            System.out.println("Enter Your UserName: ");
            String uName = myObj.nextLine();
            System.out.println("Enter Your Password: ");
            String pass = myObj.nextLine();
            boolean found = false; // added this variable
            while (fileScan.hasNextLine()) {
                String input = fileScan.nextLine();
                String[] tempArr = input.split(" ");
                String Username = tempArr[2];
                String Password = tempArr[3];

                if ((Username.equals(uName)) && (Password.equals(pass))) {
                    found = true; // added this to set found
                }
            }
            if (found) {
                adminMenu(uName);
            }
            if (!found) {
                System.out.println("User Not found");
                Thread.sleep(1000);
                main(null);
            }
            fileScan.close();
        } catch (Exception e) {
            System.out.println("Please Enter a Valid Choice");
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            myObj.nextLine();
            main(null);
        }
    }

    public static void adminMenu(String uName) throws Exception {
        System.out.println();
        System.out.println();
        System.out.println("-----------------------------------------------------------------");
        System.out.println("        Welcome Admin " + uName + ". Please Choose appropriate Option.");
        System.out.println("-----------------------------------------------------------------");
        System.out.println();
        System.out.println();

        System.out.println("1: View Registered Doctors");
        System.out.println("2: View Registered Patients");
        System.out.println("3: View Appointments");
        System.out.println("4: Remove Doctors");
        System.out.println("5: Remove Patients");
        System.out.println("6: Remove Appointments");
        System.out.println("7: Go to Main Menu");

        // Added cases Menu
        try {
            int response = myObj.nextInt();
            switch (response) {
                case 1:
                    Doctor.allDocs();// View Registered Doctors
                    Thread.sleep(1000);
                    adminMenu(uName);
                    break;
                case 2:
                    Patient.allPatients();// View Registered Patients
                    Thread.sleep(1000);
                    adminMenu(uName);
                    break;
                case 3:
                    viewAppointments();// View Booked Appointments
                    Thread.sleep(1000);
                    adminMenu(uName);
                    break;
                case 4:
                    Scanner newObj = new Scanner(System.in);
                    System.out.println("Enter the name of the doctor you want to remove");
                    String doctor = newObj.nextLine();
                    doctor = doctor.replace(" ", "_");
                    remove(doctor);
                    adminMenu(uName);
                    break;
                case 5:
                    Scanner patientObj = new Scanner(System.in);
                    System.out.println("Enter the name of the Patient you want to remove");
                    String patient = patientObj.nextLine();
                    patient = patient.replace(" ", "_");
                    removePatient(patient);
                    adminMenu(uName);
                    break;
                case 6:
                    Scanner AppointmentObj = new Scanner(System.in);
                    System.out.println("Enter the name of the Patient you want to remove");
                    String appointment = AppointmentObj.nextLine();
                    removeAppointments(appointment);
                    adminMenu(uName);
                    break;

                case 7:
                    System.out.println("Going Back To main menu.");
                    Main.menu();
                    break;
                default:
                    System.out.println("Please Enter a valid Choice");
                    Thread.sleep(1000);
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    adminMenu(uName);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Please Enter a Valid Choice");
            Thread.sleep(1000);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            myObj.nextLine();
            adminMenu(uName);
        }
    }

    public static void registerAdmin() throws Exception {
        try {
            System.out.println("Enter your full name:");
            String fullName = myObj.nextLine();
            while (!stringValid(fullName) || fullName.isEmpty()) {
                System.out.println("Enter valid name please:");
                fullName = myObj.nextLine();
            }

            System.out.println("Enter your age:");
            String age = myObj.nextLine();
            while (!numValid(age) || age.isEmpty()) {
                System.out.println("Enter valid age:");
                age = myObj.nextLine();
            }

            // Validate if the username already exists
            System.out.println("Choose Your Username:");
            String username = myObj.nextLine();
            while (!validateAdmin(username)) {
                System.out.println("Username already exists. Please choose a different one:");
                username = myObj.nextLine();
            }

            System.out.println("Enter Your Password:");
            String password = myObj.nextLine();

            String adminInfo = fullName.replace(" ", "_") + " " + age + " " + username + " " + password + "\n";

            // Write admin information to file
            try (FileWriter fw = new FileWriter("AdminsDB.txt", true)) {
                fw.write(adminInfo);
                System.out.println("Admin registered successfully!");
            } catch (IOException e) {
                System.out.println("An error occurred while registering the admin.");
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while registering: " + e.getMessage());
            System.out.print("\033[H\033[2J");
            System.out.flush();
            myObj.nextLine();
            Admin.main(null);
        }
    }

    // Helper methods for validation
    public static boolean stringValid(String str) {
        return str.matches("[a-zA-Z ]+");
    }

    public static boolean numValid(String str) {
        return str.matches("\\d+");
    }

    public static boolean validateAdmin(String username) throws FileNotFoundException {
        try (Scanner fileScan = new Scanner(new File("AdminsDB.txt"))) {
            while (fileScan.hasNextLine()) {
                String input = fileScan.nextLine();
                String[] tempArr = input.split(" ");
                String existingUsername = tempArr[2];
                if (existingUsername.equalsIgnoreCase(username.trim())) {
                    return false; // Username already exists
                }
            }
        }
        return true; // Username doesn't exist
    }

    static void adminMenu() throws Exception {

        System.out.println();
        System.out.println();
        System.out.println("-----------------------------------------------------------------");
        System.out.println("        Welcome Admin.Please Choose appropriate Option.");
        System.out.println("-----------------------------------------------------------------");
        System.out.println();
        System.out.println();

        System.out.println("1: View Registered Doctors: ");
        System.out.println("2: View Registered Patients");
        System.out.println("3: View Appointments");
        System.out.println("4: Remove Doctors");
        System.out.println("5: Remove Patients");
        System.out.println("6: Remove Appointments");
        System.out.println("7: Go to Main Menu");

        // Added cases Menu
        try {
            int response = myObj.nextInt();
            switch (response) {
                case 1:
                    Doctor.allDocs();// View Registered Doctors
                    Thread.sleep(1000);
                    adminMenu();
                    break;
                case 2:
                    Patient.allPatients();// View Registered Patients
                    Thread.sleep(1000);
                    adminMenu();
                    break;
                case 3:
                    viewAppointments();// View Booked Appointments
                    Thread.sleep(1000);
                    adminMenu();
                    break;
                case 4:
                    Scanner newObj = new Scanner(System.in);
                    System.out.println("Enter the name of the doctor you want to remove");
                    String doctor = newObj.nextLine();
                    doctor = doctor.replace(" ", "_");
                    remove(doctor);
                    adminMenu();
                    break;
                case 5:
                    Scanner patientObj = new Scanner(System.in);
                    System.out.println("Enter the name of the Patient you want to remove");
                    String patient = patientObj.nextLine();
                    patient = patient.replace(" ", "_");
                    removePatient(patient);
                    adminMenu();
                    break;
                case 6:
                    Scanner AppointmentObj = new Scanner(System.in);
                    System.out.println("Enter the name of the Patient you want to remove");
                    String appointment = AppointmentObj.nextLine();
                    removeAppointments(appointment);
                    adminMenu();
                    break;

                case 7:
                    System.out.println("Going Back To main menu.");
                    Main.menu();
                    break;
                default:
                    System.out.println("Please Enter a valid Choice");
                    Thread.sleep(1000);
                    adminMenu();
                    break;
            }
        } catch (

        Exception e) {
            System.out.println("Please enter valid Options");
            Thread.sleep(1000);
            myObj.next();
            adminMenu();
        }
    }

    static void remove(String lineContent) {
        File originalFile = new File("DoctorsDB.txt");
        File tempFile = new File("tempDoctorsDB.txt");

        // Open the original text file and the temporary text file
        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            // Read the contents of the original text file and remove the lines that contain
            // the specific string
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(lineContent)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close both the original text file and the temporary text file

        // Delete the original text file using the delete() method
        if (originalFile.delete()) {
            System.out.println("Record Deleted Successfully\n Original file deleted successfully");
        } else {
            System.out.println("Record not found.\nFailed to delete the original file");
        }

        // Rename the temporary text file to the name of the original text file using
        // the renameTo() method
        if (tempFile.renameTo(originalFile)) {
            System.out.println("Record Deleted Successfully\nTemp file renamed successfully");
        } else {
            System.out.println("Record not found.\n Failed to rename the temp file");
        }
    }

    static void removePatient(String lineContent) {
        File originalFile = new File("PatientsDB.txt");
        File tempFile = new File("tempPatientsDB.txt");

        // Open the original text file and the temporary text file
        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            // Read the contents of the original text file and remove the lines that contain
            // the specific string
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(lineContent)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close both the original text file and the temporary text file

        // Delete the original text file using the delete() method
        if (originalFile.delete()) {
            System.out.println("Record Deleted Successfully\n Original file deleted successfully");
        } else {
            System.out.println("Record not found.\nFailed to delete the original file");
        }

        // Rename the temporary text file to the name of the original text file using
        // the renameTo() method
        if (tempFile.renameTo(originalFile)) {
            System.out.println("Record Deleted Successfully\nTemp file renamed successfully");
        } else {
            System.out.println("Record not found.\n Failed to rename the temp file");
        }
    }

    static void removeAppointments(String lineContent) {
        File originalFile = new File("Appointments.txt");
        File tempFile = new File("tempAppointments.txt");

        // Open the original text file and the temporary text file
        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            // Read the contents of the original text file and remove the lines that contain
            // the specific string
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(lineContent)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close both the original text file and the temporary text file

        // Delete the original text file using the delete() method
        if (originalFile.delete()) {
            System.out.println("Record Deleted Successfully\n Original file deleted successfully");
        } else {
            System.out.println("Record not found.\nFailed to delete the original file");
        }

        // Rename the temporary text file to the name of the original text file using
        // the renameTo() method
        if (tempFile.renameTo(originalFile)) {
            System.out.println("Record Deleted Successfully\nTemp file renamed successfully");
        } else {
            System.out.println("Record not found.\n Failed to rename the temp file");
        }
    }

    static void viewAppointments() {
        try {
            File myObj = new File("Appointments.txt");
            Scanner myReader = new Scanner(myObj);
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
                for (String i : name) {
                    // System.out.println();
                    System.out.print(String.format("%20s", i));
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
}
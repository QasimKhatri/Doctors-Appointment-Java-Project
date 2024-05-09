// Imports
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

// Appointment Class
public class Appointment {
    private static Scanner myObj = new Scanner(System.in); // Make this a class field

    // Cancel Appointment
    public static void cancelAppointment(String lineContent) throws IOException {
        myObj.nextLine();
        // Ask for the required details
        System.out.println("Please enter the patient name:");
        String patientName = myObj.nextLine().trim(); // Read patient name

        System.out.println("Please enter the doctor name:");
        String doctorName = myObj.nextLine().trim(); // Read doctor name

        System.out.println("Please enter the scheduled date (MM/DD/YYYY):");
        String appointmentDate = myObj.nextLine().trim(); // Read appointment date

        System.out.println("Please enter the scheduled time (e.g., 9:00_AM):");
        String appointmentTime = myObj.nextLine().trim(); // Read appointment time

        File originalFile = new File("Appointments.txt");
        File tempFile = new File("tempAppointments.txt");

        boolean appointmentFound = false; // To track if the appointment was found


        try (BufferedReader reader = new BufferedReader(new FileReader(originalFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!(line.contains(patientName) && line.contains(doctorName) &&
                      line.contains(appointmentDate) && line.contains(appointmentTime))) {
                    writer.write(line); // Keep the appointment if it doesn't match the given details
                    writer.newLine();
                } else {
                    appointmentFound = true; // An appointment with these details exists
                }
            }

            if (appointmentFound) {
                System.out.println("Appointment successfully canceled.");
            } else {
                System.out.println("Incorrect information. Please try again.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while cancelling the appointment.");
            e.printStackTrace(); // Log the error
        }

        // Handle file operations
        if (!originalFile.delete()) {
            System.out.println("Error deleting the original appointment file.");
        }

        if (!tempFile.renameTo(originalFile)) {
            System.out.println("Error renaming the temporary file.");
        }
        
    }

    // Booking Appointment
    public static void bookAppointment(String Name, String patName) {
        System.out.println();
        System.out.println("1: View Registered Doctors");
        System.out.println("2: Select Doctor and Date of Appointment ");
        System.out.println("3: View your Booked Appointments");
        System.out.println("4: Exit");
        try {
            int response = myObj.nextInt();
            switch (response) {
                case 1:
                    Doctor.allDocs();
                    bookAppointment(Name, patName);
                    break;
                case 2:
                    myObj.nextLine();
                    appointment(Name);
                    bookAppointment(Name, patName);
                    break;
                case 3:
                    bookedAppointmentsPatient(Name);
                    bookAppointment(Name, patName);
                    break;
                case 4:
                    Patient.patientMenu(Name, patName);
                default:
                    System.out.println("Please Enter a valid Choice");
                    myObj.nextLine();
                    bookAppointment(Name, patName);
                    break;
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Please enter valid Options");
            myObj.nextLine();
            bookAppointment(Name, patName);
        }
    }

    public static void appointment(String Name) {
        try {
            System.out.println("Enter the name of the doctor whose appointment you want:");
            String doctor = safeNextLine(myObj).replace(" ", "_");

            System.out.println("Enter the date in the following format MM/DD/YYYY");
            String date = safeNextLine(myObj);

            // Ensure valid date format
            while (!validateJavaDate(date)) {
                System.out.println("Please enter a valid date in the format MM/DD/YYYY");
                date = safeNextLine(myObj);
            }

            // Time slot selection
            System.out.println(
                    "Choose a time slot:\n" +
                            "1: For 9:00 AM\n" +
                            "2: For 9:30 AM\n" +
                            "3: For 10:00 AM\n" +
                            "4: For 10:30 AM\n" +
                            "5: For 11:00 AM\n" +
                            "6: For 11:30 AM\n" +
                            "7: For 12:00 PM");

            boolean validTimeSlot = false;
            String time = "";

            while (!validTimeSlot) {
                String choice = safeNextLine(myObj);
                switch (choice) {
                    case "1":
                        time = "9:00_AM";
                        validTimeSlot = true;
                        break;
                    case "2":
                        time = "9:30_AM";
                        validTimeSlot = true;
                        break;
                    case "3":
                        time = "10:00_AM";
                        validTimeSlot = true;
                        break;
                    case "4":
                        time = "10:30_AM";
                        validTimeSlot = true;
                        break;
                    case "5":
                        time = "11:00_AM";
                        validTimeSlot = true;
                        break;
                    case "6":
                        time = "11:30_AM";
                        validTimeSlot = true;
                        break;
                    case "7":
                        time = "12:00_PM";
                        validTimeSlot = true;
                        break;
                    default:
                        System.out.println("Please enter a valid choice from 1 to 7.");
                        break;
                }
            }

            // Prepare appointment details and write to file
            String appointmentDetails = Name + " " + doctor + " " + date + " " + time + "\n";

            System.out.println("Appointment successfully booked with Dr. " + doctor + " at " + time + " on " + date);

            try (FileWriter fw = new FileWriter("Appointments.txt", true)) {
                fw.write(appointmentDetails);
            } catch (Exception e) {
                System.out.println("Error booking the appointment: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error occurred during appointment booking: " + e.getMessage());
        }

    }

    // Helper method to handle unexpected empty input
    private static String safeNextLine(Scanner scanner) {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        } else {
            throw new NoSuchElementException("No input available");
        }
    }

    // Date Validation
    public static boolean validateJavaDate(String strDate) {
        /* Check if date is 'null' */
        if (strDate.trim().equals("")) {
            return true;
        }
        /* Date is not 'null' */
        else {
            /*
             * Set preferred date format,
             */
            SimpleDateFormat SdFormat = new SimpleDateFormat("mm/DD/yyyy");
            SdFormat.setLenient(false);
            /*
             * Create Date object
             * parse the string into date
             */
            try {
                SdFormat.parse(strDate);
                System.out.println(strDate + " is valid date format");
            }
            /* Date format is invalid */
            catch (ParseException e) {
                System.out.println(strDate + " is Invalid Date format");
                return false;
            }
            /* Return true if date format is valid */
            return true;
        }
    }

    // Method to register Patient
    public static boolean validate(String Name) throws Exception {
        boolean found = false;
        try (Scanner fileScan = new Scanner(new File("PatientsDB.txt"))) {
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
            Patient.reg();
        } else {
            System.out.print("Registering User \n");
        }
        return found;
    }

    static void bookedAppointmentsPatient(String searched) {
        String filePath = "Appointments.txt";

        // Replace "search_string" with the string you want to search for
        System.out.println(
                "______________________________________________________");
        String searchString = searched;
        System.out.println();
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
            System.out.println();
            System.out.println();
            System.out.println(
                    "______________________________________________________");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

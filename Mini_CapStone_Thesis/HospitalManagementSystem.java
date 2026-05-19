import java.util.Scanner;
import java.util.ArrayList;

public class HospitalManagementSystem {

    static Scanner sc = new Scanner(System.in);
    static HospitalDatabase db = new HospitalDatabase();

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("     PHILIPPINE GENERAL HOSPITAL INFORMATION SYSTEM");
        System.out.println("==========================================================");

        while (true) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("  1. Login as Patient");
            System.out.println("  2. Login as Doctor / Nurse");
            System.out.println("  3. Login as Admin");
            System.out.print("Your Choice (Type -1 to exit): ");

            String input = sc.nextLine().trim();

            if (input.equals("-1")) {
                System.out.println("\nThank you for using the PGH Information System. Goodbye!");
                break;
            }

            switch (input) {
                case "1":
                    patientLogin();
                    break;
                case "2":
                    staffLogin();
                    break;
                case "3":
                    adminLogin();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    // --- LOGIN METHODS ---

    static void patientLogin() {
        System.out.println("\n----- PATIENT LOGIN -----");
        System.out.print("Enter Patient ID : ");
        String id = sc.nextLine().trim();
        System.out.print("Enter Password   : ");
        String pass = sc.nextLine().trim();

        Patient patient = db.findPatient(id, pass);
        if (patient != null) {
            System.out.println("\nSuccessfully logged in! Welcome, " + patient.getName() + "!");
            patientMenu(patient);
        } else {
            System.out.println("Invalid ID or Password.");
        }
    }

    static void staffLogin() {
        System.out.println("\n----- DOCTOR / NURSE LOGIN -----");
        System.out.print("Enter Staff ID : ");
        String id = sc.nextLine().trim();
        System.out.print("Enter Password : ");
        String pass = sc.nextLine().trim();

        MedicalStaff staff = db.findStaff(id, pass);
        if (staff != null) {
            System.out.println("\nSuccessfully logged in! Welcome, " + staff.getName() + "!");
            staffMenu(staff);
        } else {
            System.out.println("Invalid ID or Password.");
        }
    }

    static void adminLogin() {
        System.out.println("\n----- ADMIN LOGIN -----");
        System.out.print("Enter Admin ID  : ");
        String id = sc.nextLine().trim();
        System.out.print("Enter Password  : ");
        String pass = sc.nextLine().trim();

        if (db.isAdmin(id, pass)) {
            System.out.println("\nSuccessfully logged in! Welcome, Administrator!");
            adminMenu();
        } else {
            System.out.println("Invalid ID or Password.");
        }
    }

    // --- PATIENT MENU ---

    static void patientMenu(Patient patient) {
        while (true) {
            System.out.println("\n----- PATIENT PORTAL -----");
            System.out.println("What do you want to do?");
            System.out.println("  1. View My Profile");
            System.out.println("  2. View My Diagnosis");
            System.out.println("  3. View My Treatment");
            System.out.println("  4. Logout");
            System.out.print("Your Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.println("\n----- MY PROFILE -----");
                    patient.displayInfo();
                }
                case "2" -> {
                    System.out.println("\n----- MY DIAGNOSIS -----");
                    System.out.println("  Patient   : " + patient.getName());
                    System.out.println("  Diagnosis : " + patient.getDiagnosis());
                }
                case "3" -> {
                    System.out.println("\n----- MY TREATMENT -----");
                    System.out.println("  Patient   : " + patient.getName());
                    System.out.println("  Treatment : " + patient.getTreatment());
                }
                case "4" -> {
                    System.out.println("\nLogged out successfully. Goodbye, " + patient.getName() + "!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // --- DOCTOR / NURSE MENU ---

    static void staffMenu(MedicalStaff staff) {
        while (true) {
            System.out.println("\n----- " + staff.getRole().toUpperCase() + " PORTAL -----");
            System.out.println("What do you want to do?");
            System.out.println("  1. View My Profile");
            System.out.println("  2. View All Patients");
            System.out.println("  3. Search Patient Record");
            System.out.println("  4. Update Patient Diagnosis and Treatment");
            System.out.println("  5. Logout");
            System.out.print("Your Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n----- MY PROFILE -----");
                    staff.displayInfo();
                    break;
                case "2":
                    viewAllPatients();
                    break;
                case "3":
                    searchPatient();
                    break;
                case "4":
                    updatePatient();
                    break;
                case "5":
                    System.out.println("\nLogged out successfully. Goodbye, " + staff.getName() + "!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    static void viewAllPatients() {
        System.out.println("\n----- ALL PATIENT RECORDS -----");
        ArrayList<Patient> patients = db.getAllPatients();

        if (patients.isEmpty()) {
            System.out.println("No patients registered yet.");
        } else {
            for (int i = 0; i < patients.size(); i++) {
                System.out.println("\n  [Patient " + (i + 1) + "]");
                patients.get(i).displayInfo();
                System.out.println("  ----------------------------------------");
            }
        }
    }

    static void searchPatient() {
        System.out.println("\n----- SEARCH PATIENT RECORD -----");
        System.out.print("Enter Patient ID: ");
        String id = sc.nextLine().trim();

        Patient p = db.findPatientById(id);
        if (p != null) {
            System.out.println("\nPatient Found:");
            p.displayInfo();
        } else {
            System.out.println("No patient found with that ID.");
        }
    }

    static void updatePatient() {
        System.out.println("\n----- UPDATE PATIENT RECORD -----");
        System.out.print("Enter Patient ID to update: ");
        String id = sc.nextLine().trim();

        Patient p = db.findPatientById(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Current Diagnosis : " + p.getDiagnosis());
        System.out.print("New Diagnosis (press Enter to keep current): ");
        String newDiagnosis = sc.nextLine().trim();
        if (!newDiagnosis.isEmpty()) {
            p.setDiagnosis(newDiagnosis);
        }

        System.out.println("Current Treatment : " + p.getTreatment());
        System.out.print("New Treatment (press Enter to keep current): ");
        String newTreatment = sc.nextLine().trim();
        if (!newTreatment.isEmpty()) {
            p.setTreatment(newTreatment);
        }

        db.updatePatientMedicalRecords(p.getId(), p.getDiagnosis(), p.getTreatment());
        System.out.println("Patient record updated successfully!");
    }

    // --- ADMIN MENU ---

    static void adminMenu() {
        while (true) {
            System.out.println("\n----- ADMIN CONTROL PAGE -----");
            System.out.println("What do you want to do?");
            System.out.println("  1. Register a New Patient");
            System.out.println("  2. Register a New Doctor / Nurse");
            System.out.println("  3. View All Patients");
            System.out.println("  4. View All Medical Staff");
            System.out.println("  5. Logout");
            System.out.print("Your Choice: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    registerPatient();
                    break;
                case "2":
                    registerStaff();
                    break;
                case "3":
                    viewAllPatients();
                    break;
                case "4":
                    viewAllStaff();
                    break;
                case "5":
                    System.out.println("\nAdmin logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    static void registerPatient() {
        System.out.println("\n----- REGISTER NEW PATIENT -----");
        System.out.print("Patient ID        : ");
        String id = sc.nextLine().trim();
        System.out.print("Password          : ");
        String pass = sc.nextLine().trim();
        System.out.print("Full Name         : ");
        String name = sc.nextLine().trim();
        System.out.print("Age               : ");
        int age = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Contact Number    : ");
        String contact = sc.nextLine().trim();
        System.out.print("Diagnosis         : ");
        String diagnosis = sc.nextLine().trim();
        System.out.print("Treatment         : ");
        String treatment = sc.nextLine().trim();
        System.out.print("Assigned Doctor ID: ");
        String docId = sc.nextLine().trim();

        db.addPatient(new Patient(id, pass, name, age, contact, diagnosis, treatment, docId));
        System.out.println("Patient registered successfully!");
    }

    static void registerStaff() {
        System.out.println("\n----- REGISTER NEW MEDICAL STAFF -----");
        System.out.print("Staff ID          : ");
        String id = sc.nextLine().trim();
        System.out.print("Password          : ");
        String pass = sc.nextLine().trim();
        System.out.print("Full Name         : ");
        String name = sc.nextLine().trim();
        System.out.print("Age               : ");
        int age = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Contact Number    : ");
        String contact = sc.nextLine().trim();
        System.out.print("Role (Doctor/Nurse): ");
        String role = sc.nextLine().trim();
        System.out.print("Specialization    : ");
        String spec = sc.nextLine().trim();

        db.addStaff(new MedicalStaff(id, pass, name, age, contact, role, spec));
        System.out.println("Staff registered successfully!");
    }

    static void viewAllStaff() {
        System.out.println("\n----- ALL MEDICAL STAFF -----");
        ArrayList<MedicalStaff> staffList = db.getAllStaff();

        if (staffList.isEmpty()) {
            System.out.println("No staff registered yet.");
        } else {
            for (int i = 0; i < staffList.size(); i++) {
                System.out.println("\n  [Staff " + (i + 1) + "]");
                staffList.get(i).displayInfo();
                System.out.println("  ----------------------------------------");
            }
        }
    }
}
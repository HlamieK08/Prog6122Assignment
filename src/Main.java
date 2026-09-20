import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Comparator;

class HospitalPatientManagement {

    // PATIENT CATEGORY ENUM

    enum PatientCategory {
        INPATIENT,
        OUTPATIENT,
        EMERGENCY

    }

    // PATIENT CLASS
    static class Patient {
        private String patientId;
        private String firstName;
        private String lastName;
        private String patientAge;
        private String patientGender;
        private String medicalCondition;
        private PatientCategory patientCategory;

        public Patient(String patientId, String firstName, String lastName,
                       String patientAge, String patientGender,
                       String medicalCondition,
                       PatientCategory patientCategory) {

            this.patientId = patientId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.patientAge = patientAge;
            this.patientGender = patientGender;
            this.medicalCondition = medicalCondition;
            this.patientCategory = patientCategory;
        }

        // Getters
        public String getPatientId() {
            return patientId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getPatientAge() {
            return patientAge;
        }

        public String getPatientGender() {
            return patientGender;
        }

        public String getMedicalCondition() {
            return medicalCondition;
        }

        public PatientCategory getPatientCategory() {
            return patientCategory;
        }

        // Setters
        public void setPatientId(String patientId) {
            this.patientId = patientId;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setPatientAge(String patientAge) {
            this.patientAge = patientAge;
        }

        public void setPatientGender(String patientGender) {
            this.patientGender = patientGender;
        }

        public void setMedicalCondition(String medicalCondition) {
            this.medicalCondition = medicalCondition;
        }

        public void setPatientCategory(PatientCategory patientCategory) {
            this.patientCategory = patientCategory;
        }

        // Display patient details
        public void displayDetails() {
            System.out.println("Patient ID: " + patientId);
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Age: " + patientAge);
            System.out.println("Gender: " + patientGender);
            System.out.println("Medical Condition: " + medicalCondition);
            System.out.println("Category: " + patientCategory);
        }
    }

    // INPATIENT CLASS
    static class Inpatient extends Patient {

        private String wardNumber;
        private String bedNumber;

        public Inpatient(String patientId, String firstName, String lastName,
                         String patientAge, String patientGender,
                         String medicalCondition,
                         String wardNumber, String bedNumber) {

            super(patientId, firstName, lastName, patientAge,
                    patientGender, medicalCondition,
                    PatientCategory.INPATIENT);

            this.wardNumber = wardNumber;
            this.bedNumber = bedNumber;
        }

        public String getWardNumber() {
            return wardNumber;
        }

        public String getBedNumber() {
            return bedNumber;
        }

        public void setWardNumber(String wardNumber) {
            this.wardNumber = wardNumber;
        }

        public void setBedNumber(String bedNumber) {
            this.bedNumber = bedNumber;
        }

        @Override
        public void displayDetails() {
            super.displayDetails();
            System.out.println("Ward Number: " + wardNumber);
            System.out.println("Bed Number: " + bedNumber);
        }
    }

    // VARIABLES
    public static Scanner scanner = new Scanner(System.in);

    public static ArrayList<Patient> patientList = new ArrayList<>();

    public static Inpatient[] beds = new Inpatient[20];

    // MAIN METHOD
    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n=================================");
            System.out.println("   HOSPITAL PATIENT MANAGEMENT");
            System.out.println("=================================");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Display Registered Patients");
            System.out.println("6. Allocate Bed");
            System.out.println("7. Release Bed");
            System.out.println("8. Display Ward Layout");
            System.out.println("9. Display Available Beds");
            System.out.println("10. Display Occupied Beds");
            System.out.println("11. Display Reports");
            System.out.println("12. Display Patient Categories");
            System.out.println("13. Sort Patients by Surname");
            System.out.println("14. Sort Patients by ID");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1:
                        capturePatient();
                        break;

                    case 2:
                        searchPatient();
                        break;

                    case 3:
                        updatePatient();
                        break;

                    case 4:
                        deletePatient();
                        break;

                    case 5:
                        displayRegisteredPatients();
                        break;

                    case 6:
                        System.out.println("Bed allocation is done when registering an inpatient.");
                        break;

                    case 7:
                        System.out.print("Enter Patient ID: ");
                        String releaseId = scanner.nextLine();

                        if (releaseBedForPatient(releaseId)) {
                            System.out.println("Bed released successfully.");
                        } else {
                            System.out.println("Patient does not have an occupied bed.");
                        }
                        break;

                    case 8:
                        displayWardLayout();
                        break;

                    case 9:
                        displayAvailableBeds();
                        break;

                    case 10:
                        displayOccupiedBeds();
                        break;

                    case 11:
                        displayReports();
                        break;

                    case 12:
                        displayPatientCategory();
                        break;

                    case 13:
                        sortPatientsBySurname();
                        System.out.println("Patients sorted by surname.");
                        break;

                    case 14:
                        sortPatientsById();
                        System.out.println("Patients sorted by ID.");
                        break;

                    case 0:
                        System.out.println("Exiting system...");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                choice = -1;
            }

        } while (choice != 0);

        scanner.close();
    }

    // CAPTURE PATIENT
    public static void capturePatient() {

        System.out.println("\n--- Register Patient ---");

        System.out.print("Enter Patient ID: ");
        String patientId = scanner.nextLine();

        if (findPatientById(patientId) != null) {
            System.out.println("A patient with this ID already exists.");
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Age: ");
        String age = scanner.nextLine();

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Medical Condition: ");
        String condition = scanner.nextLine();

        System.out.println("Select Patient Category:");
        System.out.println("1. Inpatient");
        System.out.println("2. Outpatient");
        System.out.println("3. Emergency");
        System.out.print("Enter choice: ");

        int categoryChoice;

        try {
            categoryChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid category.");
            return;
        }

        if (categoryChoice == 1) {

            Inpatient newPatient = new Inpatient(
                    patientId,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    "Ward 1",
                    ""
            );

            if (allocateBed(newPatient)) {
                patientList.add(newPatient);
                System.out.println("Inpatient registered successfully.");
                System.out.println("Allocated Bed: " + newPatient.getBedNumber());
            } else {
                System.out.println("No beds are available.");
            }

        } else if (categoryChoice == 2) {

            Patient newPatient = new Patient(
                    patientId,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    PatientCategory.OUTPATIENT
            );

            patientList.add(newPatient);
            System.out.println("Outpatient registered successfully.");

        } else if (categoryChoice == 3) {

            Patient newPatient = new Patient(
                    patientId,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    PatientCategory.EMERGENCY
            );

            patientList.add(newPatient);
            System.out.println("Emergency patient registered successfully.");

        } else {
            System.out.println("Invalid category.");
        }
    }

    // SEARCH PATIENT
    public static void searchPatient() {

        System.out.print("Enter Patient ID to search: ");
        String id = scanner.nextLine();

        Patient patient = findPatientById(id);

        if (patient != null) {
            System.out.println("\nPatient Found:");
            patient.displayDetails();
        } else {
            System.out.println("Patient not found.");
        }
    }

    // FIND PATIENT BY ID
    public static Patient findPatientById(String id) {

        for (Patient patient : patientList) {

            if (patient.getPatientId().equalsIgnoreCase(id)) {
                return patient;
            }
        }

        return null;
    }

    // UPDATE PATIENT
    public static void updatePatient() {

        System.out.print("Enter Patient ID to update: ");
        String id = scanner.nextLine();

        Patient patient = findPatientById(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter Age: ");
        String age = scanner.nextLine();

        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Medical Condition: ");
        String condition = scanner.nextLine();

        updatePatientDetails(
                patient,
                firstName,
                lastName,
                age,
                gender,
                condition,
                patient.getPatientCategory()
        );

        if (patient instanceof Inpatient) {

            Inpatient inpatient = (Inpatient) patient;

            System.out.print("Enter Ward Number: ");
            String ward = scanner.nextLine();

            inpatient.setWardNumber(ward);
        }

        System.out.println("Patient updated successfully.");
    }

    // UPDATE PATIENT DETAILS
    public static void updatePatientDetails(
            Patient patient,
            String firstName,
            String lastName,
            String age,
            String gender,
            String condition,
            PatientCategory category) {

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setPatientAge(age);
        patient.setPatientGender(gender);
        patient.setMedicalCondition(condition);
        patient.setPatientCategory(category);
    }

    // DELETE PATIENT
    public static void deletePatient() {

        System.out.print("Enter Patient ID to delete: ");
        String id = scanner.nextLine();

        if (deletePatientById(id)) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient not found.");
        }
    }

    // DELETE PATIENT BY ID
    public static boolean deletePatientById(String id) {

        Patient patient = findPatientById(id);

        if (patient == null) {
            return false;
        }

        if (patient instanceof Inpatient) {
            releaseBedForPatient(id);
        }

        patientList.remove(patient);

        return true;
    }

    // DISPLAY ALL PATIENTS
    public static void displayRegisteredPatients() {

        if (patientList.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }

        System.out.println("\n--- Registered Patients ---");

        for (Patient patient : patientList) {

            patient.displayDetails();

            System.out.println("-------------------------");
        }
    }

    // ALLOCATE BED
    public static boolean allocateBed(Inpatient inpatient) {

        for (int i = 0; i < beds.length; i++) {

            if (beds[i] == null) {

                beds[i] = inpatient;

                String bedNumber = String.format("B%02d", i + 1);

                inpatient.setBedNumber(bedNumber);

                return true;
            }
        }

        return false;
    }

    // ALLOCATE SPECIFIC BED
    public static boolean allocateSpecificBed(
            Inpatient inpatient,
            String bedNumber) {

        if (bedNumber == null) {
            return false;
        }

        String number = bedNumber.toUpperCase();

        if (!number.matches("B(0[1-9]|1[0-9]|20)")) {
            return false;
        }

        int index;

        try {
            index = Integer.parseInt(number.substring(1)) - 1;
        } catch (NumberFormatException e) {
            return false;
        }

        if (beds[index] != null) {
            return false;
        }

        beds[index] = inpatient;
        inpatient.setBedNumber(number);

        return true;
    }

    // DISPLAY WARD LAYOUT
    // Layouting  of 4 x 5 = 20 BEDS
    public static void displayWardLayout() {

        System.out.println("\n========== WARD 1 ==========");

        for (int row = 0; row < 4; row++) {

            for (int column = 0; column < 5; column++) {

                int index = row * 5 + column;

                String bedNumber = String.format("B%02d", index + 1);

                if (beds[index] == null) {
                    System.out.print("[" + bedNumber + " Available] ");
                } else {
                    System.out.print("[" + bedNumber + " Occupied] ");
                }
            }

            System.out.println();
        }
    }

    // DISPLAY AVAILABLE BEDS
    public static void displayAvailableBeds() {

        System.out.println("\n--- Available Beds ---");

        boolean found = false;

        for (int i = 0; i < beds.length; i++) {

            if (beds[i] == null) {

                String bedNumber = String.format("B%02d", i + 1);

                System.out.println(bedNumber);

                found = true;
            }
        }

        if (!found) {
            System.out.println("No beds are available.");
        }
    }

    // DISPLAY OCCUPIED BEDS
    public static void displayOccupiedBeds() {

        System.out.println("\n--- Occupied Beds ---");

        boolean found = false;

        for (int i = 0; i < beds.length; i++) {

            if (beds[i] != null) {

                String bedNumber = String.format("B%02d", i + 1);

                System.out.println(
                        bedNumber + " - " +
                                beds[i].getFirstName() + " " +
                                beds[i].getLastName() +
                                " (" + beds[i].getPatientId() + ")"
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No beds are occupied.");
        }
    }

    // RELEASE BED
    public static boolean releaseBedForPatient(String patientId) {

        for (int i = 0; i < beds.length; i++) {

            if (beds[i] != null &&
                    beds[i].getPatientId().equalsIgnoreCase(patientId)) {

                beds[i].setBedNumber("");
                beds[i] = null;

                return true;
            }
        }

        return false;
    }

    // REPORTS
    public static void displayReports() {

        int totalPatients = patientList.size();
        int occupiedBeds = getOccupiedBedCount();
        int availableBeds = beds.length - occupiedBeds;

        double occupancyPercentage =
                ((double) occupiedBeds / beds.length) * 100;

        System.out.println("\n========== REPORTS ==========");
        System.out.println("Total Registered Patients: " + totalPatients);
        System.out.println("Total Beds: " + beds.length);
        System.out.println("Occupied Beds: " + occupiedBeds);
        System.out.println("Available Beds: " + availableBeds);
        System.out.printf("Bed Occupancy: %.2f%%%n",
                occupancyPercentage);
    }

    // GET OCCUPIED BED COUNT
    public static int getOccupiedBedCount() {

        int count = 0;

        for (Inpatient bed : beds) {

            if (bed != null) {
                count++;
            }
        }

        return count;
    }

    // DISPLAY PATIENT CATEGORIES
    public static void displayPatientCategory() {

        System.out.println("\n--- Patient Categories ---");

        int inpatientCount = 0;
        int outpatientCount = 0;
        int emergencyCount = 0;

        for (Patient patient : patientList) {

            if (patient.getPatientCategory()
                    == PatientCategory.INPATIENT) {

                inpatientCount++;

            } else if (patient.getPatientCategory()
                    == PatientCategory.OUTPATIENT) {

                outpatientCount++;

            } else if (patient.getPatientCategory()
                    == PatientCategory.EMERGENCY) {

                emergencyCount++;
            }
        }

        System.out.println("Inpatients: " + inpatientCount);
        System.out.println("Outpatients: " + outpatientCount);
        System.out.println("Emergency Patients: " + emergencyCount);
    }

    // SORT BY SURNAME
    public static void sortPatientsBySurname() {

        patientList.sort(
                Comparator.comparing(
                        Patient::getLastName,
                        String.CASE_INSENSITIVE_ORDER
                )
        );
    }

    // SORT BY PATIENT ID
    public static void sortPatientsById() {

        patientList.sort(
                Comparator.comparing(
                        Patient::getPatientId,
                        String.CASE_INSENSITIVE_ORDER
                )
        );
    }
}
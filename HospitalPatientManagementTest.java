import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HospitalPatientManagementTest {

    @Test
    void testRegisterPatient() {

        HospitalPatientManagement.Patient patient =
                new HospitalPatientManagement.Patient(
                        "P001",
                        "John",
                        "Doe",
                        "25",
                        "Male",
                        "Flu",
                        HospitalPatientManagement.PatientCategory.OUTPATIENT
                );

        assertEquals("P001", patient.getPatientId());
        assertEquals("John", patient.getFirstName());
        assertEquals("Doe", patient.getLastName());
        assertEquals("25", patient.getPatientAge());
        assertEquals("Male", patient.getPatientGender());
        assertEquals("Flu", patient.getMedicalCondition());
        assertEquals(
                HospitalPatientManagement.PatientCategory.OUTPATIENT,
                patient.getPatientCategory()
        );
    }

    @Test
    void testSearchById() {
        HospitalPatientManagement.Patient patient =
                new HospitalPatientManagement.Patient(
                        "P002",
                        "Jane",
                        "Smith",
                        "30",
                        "Female",
                        "Asthma",
                        HospitalPatientManagement.PatientCategory.OUTPATIENT
                );

        HospitalPatientManagement.patientList.clear();
        HospitalPatientManagement.patientList.add(patient);

        HospitalPatientManagement.Patient found =
                HospitalPatientManagement.findPatientById("P002");

        assertNotNull(found);
        assertEquals("P002", found.getPatientId());
        assertEquals("Jane", found.getFirstName());
        assertEquals("Smith", found.getLastName());
    }

    @Test
    void testUpdatePatient() {
        HospitalPatientManagement.Patient patient =
                new HospitalPatientManagement.Patient(
                        "P003",
                        "Peter",
                        "Jones",
                        "40",
                        "Male",
                        "Headache",
                        HospitalPatientManagement.PatientCategory.OUTPATIENT
                );

        patient.setFirstName("Michael");
        patient.setLastName("Brown");
        patient.setPatientAge("41");
        patient.setPatientGender("Male");
        patient.setMedicalCondition("Migraine");

        assertEquals("Michael", patient.getFirstName());
        assertEquals("Brown", patient.getLastName());
        assertEquals("41", patient.getPatientAge());
        assertEquals("Male", patient.getPatientGender());
        assertEquals("Migraine", patient.getMedicalCondition());
    }

    @Test
    void testDeletePatient() {
        HospitalPatientManagement.Patient patient =
                new HospitalPatientManagement.Patient(
                        "P004",
                        "Sarah",
                        "Williams",
                        "28",
                        "Female",
                        "Fever",
                        HospitalPatientManagement.PatientCategory.OUTPATIENT
                );

        HospitalPatientManagement.patientList.clear();
        HospitalPatientManagement.patientList.add(patient);

        boolean deleted =
                HospitalPatientManagement.deletePatientById("P004");

        assertTrue(deleted);
        assertNull(
                HospitalPatientManagement.findPatientById("P004")
        );
    }

    @Test
    void testAllocateBed() {
        HospitalPatientManagement.beds =
                new HospitalPatientManagement.Inpatient[20];

        HospitalPatientManagement.Inpatient patient =
                new HospitalPatientManagement.Inpatient(
                        "P005",
                        "David",
                        "Mokoena",
                        "35",
                        "Male",
                        "Broken arm",
                        "Ward 1",
                        ""
                );

        HospitalPatientManagement.allocateBed(patient);

        assertNotNull(HospitalPatientManagement.beds[0]);
        assertEquals("B01", patient.getBedNumber());
    }

    @Test
    void testReleaseBed() {
        HospitalPatientManagement.Inpatient patient =
                new HospitalPatientManagement.Inpatient(
                        "P006",
                        "Thandi",
                        "Molefe",
                        "32",
                        "Female",
                        "Injury",
                        "Ward 1",
                        ""
                );

        HospitalPatientManagement.beds =
                new HospitalPatientManagement.Inpatient[20];

        HospitalPatientManagement.patientList.clear();
        HospitalPatientManagement.patientList.add(patient);

        HospitalPatientManagement.allocateBed(patient);

        assertEquals("B01", patient.getBedNumber());

        HospitalPatientManagement.releaseBedForPatient("P006");

        assertEquals("", patient.getBedNumber());
        assertNull(HospitalPatientManagement.beds[0]);
    }

    @Test
    void testDuplicatePatientId() {
        HospitalPatientManagement.patientList.clear();

        HospitalPatientManagement.Patient patient1 =
                new HospitalPatientManagement.Patient(
                        "P007",
                        "John",
                        "Smith",
                        "25",
                        "Male",
                        "Flu",
                        HospitalPatientManagement.PatientCategory.OUTPATIENT
                );

        HospitalPatientManagement.Patient patient2 =
                new HospitalPatientManagement.Patient(
                        "P007",
                        "James",
                        "Brown",
                        "30",
                        "Male",
                        "Asthma",
                        HospitalPatientManagement.PatientCategory.OUTPATIENT
                );

        HospitalPatientManagement.patientList.add(patient1);

        HospitalPatientManagement.Patient duplicate =
                HospitalPatientManagement.findPatientById("P007");

        assertNotNull(duplicate);
        assertEquals("P007", duplicate.getPatientId());
    }

    @Test
    void testOccupiedBed() {
        HospitalPatientManagement.beds =
                new HospitalPatientManagement.Inpatient[20];

        HospitalPatientManagement.Inpatient patient1 =
                new HospitalPatientManagement.Inpatient(
                        "P008",
                        "Alice",
                        "Dlamini",
                        "26",
                        "Female",
                        "Flu",
                        "Ward 1",
                        ""
                );

        HospitalPatientManagement.Inpatient patient2 =
                new HospitalPatientManagement.Inpatient(
                        "P009",
                        "Bob",
                        "Nkosi",
                        "31",
                        "Male",
                        "Injury",
                        "Ward 1",
                        ""
                );

        assertTrue(
                HospitalPatientManagement.allocateSpecificBed(
                        patient1, "B01"
                )
        );

        assertFalse(
                HospitalPatientManagement.allocateSpecificBed(
                        patient2, "B01"
                )
        );
    }

    @Test
    void testFullBeds() {
        HospitalPatientManagement.beds =
                new HospitalPatientManagement.Inpatient[20];

        for (int i = 0; i < 20; i++) {
            HospitalPatientManagement.Inpatient patient =
                    new HospitalPatientManagement.Inpatient(
                            "P" + String.format("%03d", i + 10),
                            "Patient",
                            "Test" + i,
                            "25",
                            "Male",
                            "Check-up",
                            "Ward 1",
                            ""
                    );

            HospitalPatientManagement.beds[i] = patient;
        }

        HospitalPatientManagement.Inpatient extraPatient =
                new HospitalPatientManagement.Inpatient(
                        "P999",
                        "Extra",
                        "Patient",
                        "30",
                        "Female",
                        "Flu",
                        "Ward 1",
                        ""
                );

        HospitalPatientManagement.allocateBed(extraPatient);

        assertEquals("", extraPatient.getBedNumber());
    }
}
public class Patient extends Person {
    private String diagnosis;
    private String treatment;
    private String assignedDoctorId;

    public Patient(String id, String password, String name, int age, String contactNumber,
                   String diagnosis, String treatment, String assignedDoctorId) {
        super(id, password, name, age, contactNumber);
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.assignedDoctorId = assignedDoctorId;
    }

    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
    public String getAssignedDoctorId() { return assignedDoctorId; }

    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setTreatment(String treatment) { this.treatment = treatment; }

    @Override
    public void displayInfo() {
        System.out.println("  Patient ID      : " + id);
        System.out.println("  Name            : " + name);
        System.out.println("  Age             : " + age);
        System.out.println("  Contact No.     : " + contactNumber);
        System.out.println("  Diagnosis       : " + diagnosis);
        System.out.println("  Treatment       : " + treatment);
        System.out.println("  Assigned Doctor : " + assignedDoctorId);
    }
}
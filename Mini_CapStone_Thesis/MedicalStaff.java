public class MedicalStaff extends Person {
    private String role;
    private String specialization;

    public MedicalStaff(String id, String password, String name, int age,
                        String contactNumber, String role, String specialization) {
        super(id, password, name, age, contactNumber);
        this.role = role;
        this.specialization = specialization;
    }

    public String getRole() { return role; }
    public String getSpecialization() { return specialization; }

    @Override
    public void displayInfo() {
        System.out.println("  Staff ID        : " + id);
        System.out.println("  Name            : " + name);
        System.out.println("  Role            : " + role);
        System.out.println("  Age             : " + age);
        System.out.println("  Contact No.     : " + contactNumber);
        System.out.println("  Specialization  : " + specialization);
    }
}
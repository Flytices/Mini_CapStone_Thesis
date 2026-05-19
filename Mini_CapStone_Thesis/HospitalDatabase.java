import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HospitalDatabase {
    // Updated to match your database name from phpMyAdmin
    private static final String URL = "jdbc:mysql://localhost:3308/hospital_db"; 
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public boolean isAdmin(String id, String password) {
        if (id.equals("admin") && password.equals("admin")) {
            return true;
        }
        String query = "SELECT * FROM admins WHERE id = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
            return false;
        }
    }

    public Patient findPatient(String id, String password) {
        String query = "SELECT * FROM patients WHERE id = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Patient(rs.getString("id"), rs.getString("password"), rs.getString("name"),
                        rs.getInt("age"), rs.getString("contact_number"), rs.getString("diagnosis"),
                        rs.getString("treatment"), rs.getString("assigned_doctor_id"));
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        return null;
    }

    public MedicalStaff findStaff(String id, String password) {
        String query = "SELECT * FROM medical_staff WHERE id = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new MedicalStaff(rs.getString("id"), rs.getString("password"), rs.getString("name"),
                        rs.getInt("age"), rs.getString("contact_number"), rs.getString("role"),
                        rs.getString("specialization"));
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        return null;
    }

    public Patient findPatientById(String id) {
        String query = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Patient(rs.getString("id"), rs.getString("password"), rs.getString("name"),
                        rs.getInt("age"), rs.getString("contact_number"), rs.getString("diagnosis"),
                        rs.getString("treatment"), rs.getString("assigned_doctor_id"));
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> list = new ArrayList<>();
        String query = "SELECT * FROM patients";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new Patient(rs.getString("id"), rs.getString("password"), rs.getString("name"),
                        rs.getInt("age"), rs.getString("contact_number"), rs.getString("diagnosis"),
                        rs.getString("treatment"), rs.getString("assigned_doctor_id")));
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<MedicalStaff> getAllStaff() {
        ArrayList<MedicalStaff> list = new ArrayList<>();
        String query = "SELECT * FROM medical_staff";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new MedicalStaff(rs.getString("id"), rs.getString("password"), rs.getString("name"),
                        rs.getInt("age"), rs.getString("contact_number"), rs.getString("role"),
                        rs.getString("specialization")));
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        return list;
    }

    public void addPatient(Patient p) {
        String query = "INSERT INTO patients (id, password, name, age, contact_number, diagnosis, treatment, assigned_doctor_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, p.getId());
            pstmt.setString(2, p.getPassword());
            pstmt.setString(3, p.getName());
            pstmt.setInt(4, p.getAge());
            pstmt.setString(5, p.getContactNumber());
            pstmt.setString(6, p.getDiagnosis());
            pstmt.setString(7, p.getTreatment());
            pstmt.setString(8, p.getAssignedDoctorId());
            pstmt.executeUpdate();
            System.out.println("Patient database record saved successfully!");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void addStaff(MedicalStaff s) {
        String query = "INSERT INTO medical_staff (id, password, name, age, contact_number, role, specialization) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, s.getId());
            pstmt.setString(2, s.getPassword());
            pstmt.setString(3, s.getName());
            pstmt.setInt(4, s.getAge());
            pstmt.setString(5, s.getContactNumber());
            pstmt.setString(6, s.getRole());
            pstmt.setString(7, s.getSpecialization());
            pstmt.executeUpdate();
            System.out.println("Staff database record saved successfully!");
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    public void updatePatientMedicalRecords(String id, String diagnosis, String treatment) {
        String query = "UPDATE patients SET diagnosis = ?, treatment = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, diagnosis);
            pstmt.setString(2, treatment);
            pstmt.setString(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}
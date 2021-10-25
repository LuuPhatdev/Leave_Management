package entity;

import java.time.LocalDate;

public class Employee {
    private int employeeId;
    private String fullName;
    private int gender;
    private LocalDate dateOfBirth;
    private LocalDate dateStart;
    private String phone;
    private String email;
    private double annualLeave;
    private int accountId;
    private int departmentId;
    private int managerId;

    public Employee(int employeeId, String fullName, int gender, LocalDate dateOfBirth, LocalDate dateStart,
                    String phone, String email,
                    double annualLeave, int accountId, int departmentId, int managerId) {
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.dateStart = dateStart;
        this.phone = phone;
        this.email = email;
        this.annualLeave = annualLeave;
        this.accountId = accountId;
        this.departmentId = departmentId;
        this.managerId = managerId;
    }

    public Employee() {
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateStart() { return dateStart; }

    public void setDateStart(LocalDate dateStart) { this.dateStart = dateStart; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAnnualLeave() {
        return annualLeave;
    }

    public void setAnnualLeave(double annualLeave) {
        this.annualLeave = annualLeave;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", dateStart=" + dateStart +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", annualLeave=" + annualLeave +
                ", accountId=" + accountId +
                ", departmentId=" + departmentId +
                ", managerId=" + managerId +
                '}';
    }
}

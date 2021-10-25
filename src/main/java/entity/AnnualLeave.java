package entity;

import java.time.LocalDate;

public class AnnualLeave {
    private int timeOffID;
    private int employeeID;
    private LocalDate dateTimeOff;
    private String descriptionTimeOff;
    private int used;
    private double accrued;
    private double balance;

    public AnnualLeave(){}

    public AnnualLeave(int timeOffID, int employeeID, LocalDate dateTimeOff, String descriptionTimeOff, int used, double accrued, double balance) {
        this.timeOffID = timeOffID;
        this.employeeID = employeeID;
        this.dateTimeOff = dateTimeOff;
        this.descriptionTimeOff = descriptionTimeOff;
        this.used = used;
        this.accrued = accrued;
        this.balance = balance;
    }

    public int getTimeOffID() {
        return timeOffID;
    }

    public void setTimeOffID(int timeOffID) {
        this.timeOffID = timeOffID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public LocalDate getDateTimeOff() {
        return dateTimeOff;
    }

    public void setDateTimeOff(LocalDate dateTimeOff) {
        this.dateTimeOff = dateTimeOff;
    }

    public String getDescriptionTimeOff() {
        return descriptionTimeOff;
    }

    public void setDescriptionTimeOff(String descriptionTimeOff) {
        this.descriptionTimeOff = descriptionTimeOff;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    public double getAccrued() {
        return accrued;
    }

    public void setAccrued(double accrued) {
        this.accrued = accrued;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Time Off ID: "+timeOffID+
                ", Employee ID: "+employeeID+
                ", Date Time Off: "+dateTimeOff+
                ", Description Time Off: "+descriptionTimeOff+
                ", Used: "+used+
                ", Accrued: "+accrued+
                ", Balance: "+balance;
    }
}

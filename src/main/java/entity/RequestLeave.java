package entity;

import java.time.LocalDate;

public class RequestLeave {
    private int requestID;
    private int employeeID;
    private int leaveID;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String requestStatus;
    private String requestTo;
    private int amount;
    private String requestDescription;

    public RequestLeave(){}
    public RequestLeave(int requestID, int employeeID, int leaveID, LocalDate dateStart, LocalDate dateEnd, String requestStatus, String requestTo, int amount, String requestDescription) {
        this.requestID = requestID;
        this.employeeID = employeeID;
        this.leaveID = leaveID;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.requestStatus = requestStatus;
        this.requestTo = requestTo;
        this.requestDescription=requestDescription;
        this.amount = amount;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getLeaveID() {
        return leaveID;
    }

    public void setLeaveID(int leaveID) {
        this.leaveID = leaveID;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestTo() {
        return requestTo;
    }

    public void setRequestTo(String requestTo) {
        this.requestTo = requestTo;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Request ID: "+requestID+
        ", Employee ID: "+employeeID+
                ", Leave ID: "+leaveID+
                ", Date Start: "+dateStart+
                ", Date End: "+dateEnd+
                ", Request Status: "+requestStatus+
                ", Request To: "+requestTo+
                ", Request Description: "+requestDescription+
                ", Amount: "+amount;
    }
}

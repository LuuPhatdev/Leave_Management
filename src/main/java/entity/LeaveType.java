package entity;

public class LeaveType {
    private int leaveID;
    private String leaveType;

    public LeaveType(){}

    public LeaveType(int leaveID, String leaveType) {
        this.leaveID = leaveID;
        this.leaveType = leaveType;
    }

    public int getLeaveID() {
        return leaveID;
    }

    public void setLeaveID(int leaveID) {
        this.leaveID = leaveID;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    @Override
    public String toString() {
        return "Leave ID: "+leaveID+
                ", Leave Type: "+leaveType;
    }
}

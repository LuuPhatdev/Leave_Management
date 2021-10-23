package entity;

public class Department {

    private int depID;
    private String depTitle;
    private int chiefID;

    public Department(){}

    public Department(int depID, String depTitle, int chiefID) {
        this.depID = depID;
        this.depTitle = depTitle;
        this.chiefID = chiefID;
    }

    public int getDepID() {
        return depID;
    }

    public void setDepID(int depID) {
        this.depID = depID;
    }

    public String getDepTitle() {
        return depTitle;
    }

    public void setDepTitle(String depTitle) {
        this.depTitle = depTitle;
    }

    public int getChiefID() {
        return chiefID;
    }

    public void setChiefID(int chiefID) {
        this.chiefID = chiefID;
    }

    @Override
    public String toString() {
        return "dep ID: "+depID+
                ", dep title: "+depTitle+
                ", chief ID: "+chiefID;
    }
}

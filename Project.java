import java.util.ArrayList;
import java.util.List;

public class Project {
    private int projID, total = 0, endTime;
    private String projName;
    private List<Employees> empls = new ArrayList<>();

    public Project() {
    }

    public Project(int projID, String projName, List<Employees> empls, int endTime) {
        this.projID = projID;
        this.projName = projName;
        this.empls = empls;
        this.endTime = endTime;
    }

    public int getProjID() {
        return this.projID;
    }

    public void setProjID(int projID) {
        this.projID = projID;
    }

    public String getProjName() {
        return this.projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public List<Employees> getEmployees() {
        return this.empls;
    }

    public void setEmployees(List<Employees> empls) {
        this.empls = empls;
    }

    public int getEndtime() {
        return this.endTime;
    }

    public void setEndtime(int deadline) {
        this.endTime = deadline;
    }

    public int getTotalPrice(){
        for (int i = 0; i < empls.size(); i++) {
            this.total += empls.get(i).getSalary();
        }
        this.total *= endTime;
        return this.total;
    }

    @Override
    public String toString() {
        return "{" +
            " project id='" + getProjID() + "'" +
            ", project name='" + getProjName() + "'" +
            ", employees='" + getEmployees() + "'" +
            "}";
    }

}
